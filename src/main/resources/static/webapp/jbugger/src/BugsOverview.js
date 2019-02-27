import React, { Component } from 'react';
import BugsColumn from './BugsColumn'
import CreateBugPopover from './popovers/CreateBugPopover'
import MoreBugColumnOptionsPopover from './popovers/MoreBugColumnOptionsPopover'
import { withStyles } from '@material-ui/core/styles';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import './BugsOverview.css';
import { Button } from '@material-ui/core';
import BugDetailsModal from './BugDetailsModal';
import { connect } from 'react-redux';
import { createBug, filterBugs, getAllStatuses, closeModal, setBugs, setBugWithId, startUpdatingBug, reorderStatuses, startDeletingSwimlane } from './redux-stuff/actions/actionCreators';
import UnmountingDelayed from './UnmountingDelayed';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import ConfirmBugColumnDeletionDialog from './popovers/ConfirmBugColumnDeletionDialog';
import StringFormatters from './utils/StringFormatters';

function FirstChild(props) {
  const childrenArray = React.Children.toArray(props.children);
  return childrenArray[0] || null;
}

const styles = theme => ({
  BugsOverview: {
    height: "100%",
    padding: theme.spacing.unit * 3,
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: theme.palette.common.white,
    // marginRight: theme.spacing.unit * 2,
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      // marginLeft: theme.spacing.unit * 3,
      width: 'auto',
    }
  },
  searchIcon: {
    width: theme.spacing.unit * 9,
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
    width: '100%',
  },
  inputInput: {
    paddingTop: theme.spacing.unit,
    paddingRight: theme.spacing.unit,
    paddingBottom: theme.spacing.unit,
    paddingLeft: theme.spacing.unit * 10,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: 200,
    },
  },
  newBugButton: {

  }
});

class BugsOverview extends Component {

  state = {
    isLoading: false,
    bugs: [],
    bugsByStatus: {},
    newBugPopoverAnchorEl: null,
    moreBugColumnOptionsAnchorEL: null,
    newBugStatus: null,
    columnToModifyName: null,
    isConfirmationNeededForBugColumnDeletion: false,
    draggingBugFromStatus: null,
    genericModalOpened: false
  }

  componentDidMount() {
    this.setState({
      isLoading: true
    })

    this.props.dispatch(getAllStatuses());
  }

  handleNewBugPopoverClose = () => {
    this.setState({
      newBugPopoverAnchorEl: null,
    });
  }

  handleMoreBugColumnOptionsPopoverClose = () => {
    this.setState({
      moreBugColumnOptionsAnchorEL: null,
      columnToModifyName: null
    });
  }

  handleClick = event => {
    this.setState({
      newBugPopoverAnchorEl: event.currentTarget,
    });
  };

  openCreateNewBugPopover = (bugStatus, sourceElementForAnchor) => {
    this.setState({
      newBugPopoverAnchorEl: sourceElementForAnchor,
      newBugStatus: bugStatus
    });
  }

  createOnAddBugCallbackForStatus = bugStatus => event => {
    this.openCreateNewBugPopover(bugStatus, event.currentTarget);
  }

  createOnMoreOptionsCallbackForStatus = bugStatus => event => {
    this.setState({
      moreBugColumnOptionsAnchorEL: event.currentTarget,
      columnToModifyName: bugStatus
    });
  }

  handleCreateNewBugFromPopover = (newBug) => {
    let newBugWithStatus = {
      ...newBug,
      status: this.state.newBugStatus,
      attachmentIds: []
    };

    this.props.dispatch(createBug(newBugWithStatus));
    this.handleNewBugPopoverClose();
  }

  onBugFilterInputChange = (event) => {
    this.props.dispatch(filterBugs(event.target.value));
  }

  bugDragStarted = (status) => {
    this.setState({
      draggingBugFromStatus: status
    })
  }

  bugDropped = () => {
    this.setState({
      draggingBugFromStatus: null
    })
  }

  onKeyPressed = (event) => {
    if (event.keyCode == 27 && this.props.activeBugToModifyID) {
      this.props.dispatch(closeModal());
    }
  }

  onBugEditFromSidebar = (newBug) => {
    this.props.dispatch(startUpdatingBug(newBug));
  }

  onNewSwimlaneClick = () => {
    if (this.props.onModalOpenClick)
      this.props.onModalOpenClick("createSwimlaneModal");
  }

  onDragEnd = (result) => {
    if (!result.destination) {
      return;
    }

    this.props.dispatch(reorderStatuses(result.source.index, result.destination.index));
  }

  openConfirmBugColumnCreationDialog = () => {
    this.setState({
      isConfirmationNeededForBugColumnDeletion: true
    });
  }

  closeConfirmBugColumnCreationDialog = () => {
    this.setState({
      isConfirmationNeededForBugColumnDeletion: false
    });
  }

  onDeleteSwimlaneIntention = () => {
    this.openConfirmBugColumnCreationDialog();
  }

  onConfirmBugDeletionDialogCancel = () => {
    this.closeConfirmBugColumnCreationDialog();
    this.handleMoreBugColumnOptionsPopoverClose();
  }

  onConfirmBugDeletionDialogConfirm = () => {
    this.props.dispatch(startDeletingSwimlane(this.state.columnToModifyName));
    this.closeConfirmBugColumnCreationDialog();
    this.handleMoreBugColumnOptionsPopoverClose();
  }

  render() {
    const { classes } = this.props;
    const { newBugPopoverAnchorEl } = this.state;
    const isNewBugPopoverOpen = Boolean(newBugPopoverAnchorEl);

    const { moreBugColumnOptionsAnchorEL } = this.state;
    const isMoreBugColumnOptionsOpen = Boolean(moreBugColumnOptionsAnchorEL);


    return (
      <div className="parent-relative" tabIndex="0" onKeyDown={this.onKeyPressed}>

        {this.props.waitingForBugUpdate ?
          <div className="loadinge-image-wrapper">
            <div className="loading-image-wrapper__background" />
            {/* <img className="loading-image" src={loadingSVG} /> */}
          </div>
          :
          ""
        }

        <div className="bugs-overview-header">
          <div className={classes.search}>
            <div className={classes.searchIcon}>
              <SearchIcon />
            </div>
            <InputBase
              placeholder="Filter bugs..."
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              onChange={this.onBugFilterInputChange}
            />
          </div>
          <Button
            variant="contained"
            color="primary"
            className="with-margin-left-auto"
            onClick={this.handleClick}>
            New bug
            </Button>
          <Button
            variant="contained"
            color="primary"
            className="margin-left"
            onClick={this.onNewSwimlaneClick}>
            New swimlane
            </Button>
        </div>
        <DragDropContext onDragEnd={this.onDragEnd}>
          <Droppable droppableId="droppable" direction="horizontal">
            {(provided, snapshot) => (
              <div className="bugs-overview" ref={provided.innerRef} {...provided.droppableProps}>
                {this.props.statuses.map((bugStatus, index) => (
                  <Draggable key={bugStatus.statusName} draggableId={bugStatus.statusName} index={index}>
                    {(provided, snapshot) => (
                      <BugsColumn
                        innerRef={provided.innerRef}
                        provided={provided}
                        bugStatus={bugStatus.statusName}
                        statusColor={bugStatus.statusColor}
                        bugs={this.props.bugsByStatus[bugStatus.statusName] ? this.props.bugsByStatus[bugStatus.statusName] : []}
                        onAddBug={this.createOnAddBugCallbackForStatus(bugStatus.statusName)}
                        onMoreOptions={this.createOnMoreOptionsCallbackForStatus(bugStatus.statusName)}
                        bugDragStarted={this.bugDragStarted}
                        onBugDrop={this.bugDropped}
                        isPossibleDropTarget={this.state.draggingBugFromStatus && this.state.draggingBugFromStatus !== bugStatus.statusName}
                      />
                    )}
                  </Draggable>
                )
                )}
              </div>
            )}
          </Droppable>
        </DragDropContext>

        <CreateBugPopover
          id="new-bug-popover"
          open={isNewBugPopoverOpen}
          anchorEl={newBugPopoverAnchorEl}
          onClose={this.handleNewBugPopoverClose}
          handleCreateNewBug={this.handleCreateNewBugFromPopover} />

        <MoreBugColumnOptionsPopover
          id="more-bug-column-options-popover"
          open={isMoreBugColumnOptionsOpen}
          anchorEl={moreBugColumnOptionsAnchorEL}
          onClose={this.handleMoreBugColumnOptionsPopoverClose}
          onDeleteSwimlaneIntention={this.onDeleteSwimlaneIntention} />

        <UnmountingDelayed show={this.props.activeBugToModifyID !== null} delay="300">
          <BugDetailsModal
            onBugEdit={this.onBugEditFromSidebar}
            mustClose={this.props.activeBugToModifyID == null} />
        </UnmountingDelayed>

        {this.state.isConfirmationNeededForBugColumnDeletion ?
          <ConfirmBugColumnDeletionDialog
            open={true}
            statusName={StringFormatters.ToNiceBugStatus(this.state.columnToModifyName)}
            onCancel={this.onConfirmBugDeletionDialogCancel}
            onConfirm={this.onConfirmBugDeletionDialogConfirm} />
          :
          null}

      </div>
    );
  }
}

const mapStateToProps = state => ({
  statuses: state.statuses,
  bugs: state.allBugs,
  bugsByStatus: state.bugsByStatus,
  waitingForBugUpdate: state.waitingForBugUpdate,
  activeBugToModifyID: state.activeBugToModifyID
});

export default withStyles(styles)(connect(mapStateToProps)(BugsOverview));
