import React, { Component } from 'react';
import Bug from './Bug';
import './BugShortOverview.css';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { withRouter } from 'react-router-dom';

const styles = theme => ({

})

class BugShortOverview extends Component {

  constructor(props) {
    super(props);
    this.onBugClick = this.onBugClick.bind(this);
  }

  onBugClick() {
    this.props.history.push(`/bugs/bug/${this.props.id}`);
  }

  state = {
  }

  componentDidMount() {

  }

  render() {
    const { classes } = this.props;
    return (
      <div className="bug-short-overview" onClick={this.onBugClick}>
        <Grid
          container
          direction="column">
          <Grid item>
            <Typography variant="subtitle2" color="inherit">
              {this.props.title}
            </Typography>
          </Grid>
          <Grid item>
            <Typography variant="caption" color="inherit">
              #{this.props.id}
            </Typography>
          </Grid>
        </Grid>
      </div>
    );
  }
}

//title
//id
//severity

export default withStyles(styles)(withRouter(BugShortOverview));
