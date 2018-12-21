import React, { Component } from 'react';
import Bug from './Bug';
import StringFormatters from './utils/StringFormatters';
import BugsColumn from './BugsColumn'
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import { fade } from '@material-ui/core/styles/colorManipulator';
import './BugsOverview.css';
import { Divider, Button } from '@material-ui/core';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';

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

class BugsOverview extends Component {

  state = {
    isLoading: false,
    bugs: [],
    bugsByStatus: {},
    newBugPopoverAnchorEl: null
  }

  componentDidMount() {
    this.setState({
      isLoading: true
    })

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

  render() {
    const { classes } = this.props;
    const { newBugPopoverAnchorEl } = this.state;
    const open = Boolean(newBugPopoverAnchorEl);

    const newBugPopover = (
      <Popover
        id="new-bug-popover"
        open={open}
        anchorEl={newBugPopoverAnchorEl}
        onClose={this.handleNewBugPopoverClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      >
        <Typography className={classes.typography}>The content of the Popover.</Typography>
      </Popover>
    );


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
            wrap="wrap"
            className="bugs-overview"
            justify="center"
          >
            {Object.keys(this.state.bugsByStatus).map(bugStatus => (
              <Grid item key={bugStatus}>
                <BugsColumn bugStatus={StringFormatters.ToNiceBugStatus(bugStatus)} headerColorClass={`${bugStatus}-bug-status-color`} bugs={this.state.bugsByStatus[bugStatus]} />
              </Grid>
            )
            )}
          </Grid>
        </Grid>
        {newBugPopover}
      </Grid>
    );
  }
}
export default withStyles(styles)(BugsOverview);
