import React, { Component } from 'react';
import Bug from './Bug';
import './BugShortOverview.css';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { bugClicked } from './redux-stuff/actions/actionCreators';
import LabelShort from './LabelShort';

class BugShortOverview extends Component {

  constructor(props) {
    super(props);
    this.onBugClick = this.onBugClick.bind(this);
  }

  onBugClick() {
    // this.props.history.push(`/bugs/bug/${this.props.id}`);
    this.props.dispatch(bugClicked(this.props.id));
  }

  state = {
  }

  componentDidMount() {

  }

  render() {
    const isThisTheSelectedBug = this.props.currentlySelectedBugID == this.props.id;

    return (
      <div className={"bug-short-overview"} onClick={this.onBugClick}>
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
          <section className="labels">
            {this.props.labels.map(label =>
              <LabelShort
                key={label.labelName}
                text={label.labelName}
                backgroundColor={label.backgroundColor}
                smallMarginBottom />
            )}
          </section>
        </Grid>
        {isThisTheSelectedBug ?
          <div className="selected-indicator-wrapper bug-short-info__with-moving-background">
          </div>
          :
          null}
      </div>
    );
  }
}

//title
//id
//severity

const mapStateToProps = (state) => {
  return {
    currentlySelectedBugID: state.activeBugToModifyID
  }
}
export default connect(mapStateToProps)(withRouter(BugShortOverview));
