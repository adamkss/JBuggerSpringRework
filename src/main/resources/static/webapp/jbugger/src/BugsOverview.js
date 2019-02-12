import React, { Component } from 'react';
import BugsColumn from './BugsColumn'
import CreateBugPopover from './popovers/CreateBugPopover'
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import './BugsOverview.css';
import { Button } from '@material-ui/core';
import BugDetailsModal from './BugDetailsModal';
import { connect } from 'react-redux';
import { createBug, filterBugs, getAllStatuses, closeModal } from './redux-stuff/actions/actionCreators';
import UnmountingDelayed from './UnmountingDelayed';

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
    newBugStatus: null,
    draggingBugFromStatus: null
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

  render() {
    const { classes } = this.props;
    const { newBugPopoverAnchorEl } = this.state;
    const open = Boolean(newBugPopoverAnchorEl);

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
        </div>
        <Grid
          container
          direction="row"
          wrap="nowrap"
          className="bugs-overview"
          justify=""
        >
          {this.props.statuses.map(bugStatus => (
            <Grid item key={bugStatus.statusName}>
              <BugsColumn bugStatus={bugStatus.statusName}
                headerColorClass={`${bugStatus.statusName}-bug-status-color`}
                bugs={this.props.bugsByStatus[bugStatus.statusName] ? this.props.bugsByStatus[bugStatus.statusName] : []}
                onAddBug={this.createOnAddBugCallbackForStatus(bugStatus.statusName)}
                bugDragStarted={this.bugDragStarted}
                onBugDrop={this.bugDropped}
                isPossibleDropTarget={this.state.draggingBugFromStatus && this.state.draggingBugFromStatus !== bugStatus.statusName}
              />
            </Grid>
          )
          )}
        </Grid>

        <CreateBugPopover
          id="new-bug-popover"
          open={open}
          anchorEl={newBugPopoverAnchorEl}
          onClose={this.handleNewBugPopoverClose}
          handleCreateNewBug={this.handleCreateNewBugFromPopover} />
        <UnmountingDelayed show={this.props.activeBugToModifyID !== null} delay="300">
          <BugDetailsModal mustClose={this.props.activeBugToModifyID == null}/>
        </UnmountingDelayed>

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
