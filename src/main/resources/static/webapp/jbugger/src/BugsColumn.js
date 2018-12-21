import React, { Component } from 'react';
import './BugsColumn.css';
import Bug from './Bug'
import BugShortOverview from './BugShortOverview'
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { Divider } from '@material-ui/core';
import BugsColumnHeader from './BugsColumnHeader';

const styles = theme => ({

  bugsContainer: {

  },

  bugGridItem: {
    marginBottom: "10px"
  },

  bugsColumnHeader: {
    marginBottom: "10px"
  }
})

class BugsColumn extends Component {

  state = {
  }

  componentDidMount() {

  }

  render() {
    const { classes } = this.props;
    return (
      <div className="bugs-column">
        <Grid
          container
          direction="column"
          alignItems="center"
          alignContent="center"
          className="bugsContainer">
          <Grid item className={classes.bugsColumnHeader}>
            <BugsColumnHeader status={this.props.bugStatus} headerColorClass={this.props.headerColorClass}/>
          </Grid>
          {this.props.bugs.map(
            (bug, index) =>
              <Grid item className={classes.bugGridItem} key={bug.id}>
                <BugShortOverview title={bug.title} id={bug.id} />
              </Grid>
          )}
        </Grid>
      </div>
    );
  }
}

//bugStatus
//bugs
//headerColorClass
export default withStyles(styles)(BugsColumn);
