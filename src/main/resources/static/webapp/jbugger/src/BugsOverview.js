import React, { Component } from 'react';
import StringFormatters from './utils/StringFormatters';
import BugsColumn from './BugsColumn'
import CreateBugPopover from './popovers/CreateBugPopover'
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import './BugsOverview.css';
import { Button } from '@material-ui/core';

import {connect} from 'react-redux';

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
    },
    flexGrow: 1
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

const mapBugsToObjectByStatus = function (bugs) {
  const bugsByStatus = {};
  bugs.forEach(bug => {
    if (!bugsByStatus[bug.status]) {
      bugsByStatus[bug.status] = [];
    }
    bugsByStatus[bug.status].push(bug);
  })
  return bugsByStatus;
}

const addBugByStatus = function (oldBugsByStatus, newBug){
  let newBugByStatus = {...oldBugsByStatus};
  newBugByStatus[newBug.status] = [...newBugByStatus[newBug.status], newBug];
  return newBugByStatus;
}

class BugsOverview extends Component {

  state = {
    isLoading: false,
    bugs: [],
    bugsByStatus: {},
    newBugPopoverAnchorEl: null,
    newBugStatus: null
  }

  loadAllBugs = () => {
    fetch('http://localhost:8080/bugs')
      .then((response) => response.json())
      .then((data) => {
        this.setState({
          bugs: data,
          bugsByStatus: mapBugsToObjectByStatus(data),
          isLoading: false
        })
      })
  }

  addNewBugToComponentState = newBug => {
    this.setState(oldState => ({
      bugs: [...oldState.bugs, newBug],
      bugsByStatus: addBugByStatus(oldState.bugsByStatus, newBug)
    }))
  }

  componentDidMount() {
    this.setState({
      isLoading: true
    })

    this.loadAllBugs();
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

    fetch('http://localhost:8080/bugs', {
      method: "POST",
      body: JSON.stringify(newBugWithStatus),
      headers: {
        'Content-Type': 'application/json'
      },
    })
      .then((response) => response.json())
      .then((createdBug) => this.addNewBugToComponentState(createdBug));
    this.handleNewBugPopoverClose();
  }

  render() {
    const { classes } = this.props;
    const { newBugPopoverAnchorEl } = this.state;
    const open = Boolean(newBugPopoverAnchorEl);

    return (
      <Grid
        container
        direction="column"
      >
        <Grid item>
          <div className="bugsOverviewHeader">
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
              />
            </div>
            <Button
              variant="contained"
              color="primary"
              className="with-margin-left"
              onClick={this.handleClick}>
              New bug
            </Button>
          </div>
        </Grid>
        <Grid item>
          <Grid
            container
            spacing={16}
            direction="row"
            wrap="nowrap"
            className="bugs-overview"
            justify=""
          >
            {Object.keys(this.state.bugsByStatus).map(bugStatus => (
              <Grid item key={bugStatus}>
                <BugsColumn bugStatus={StringFormatters.ToNiceBugStatus(bugStatus)} headerColorClass={`${bugStatus}-bug-status-color`} bugs={this.state.bugsByStatus[bugStatus]} onAddBug={this.createOnAddBugCallbackForStatus(bugStatus)} />
              </Grid>
            )
            )}
          </Grid>
        </Grid>
        <CreateBugPopover
          id="new-bug-popover"
          open={open}
          anchorEl={newBugPopoverAnchorEl}
          onClose={this.handleNewBugPopoverClose}
          handleCreateNewBug={this.handleCreateNewBugFromPopover} />
      </Grid>
    );
  }
}

const mapStateToProps = state => ({
  // bugs: state.allBugs
});

export default withStyles(styles)(connect(mapStateToProps)(BugsOverview));
