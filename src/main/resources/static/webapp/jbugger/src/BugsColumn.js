import React, { Component } from 'react';
import './BugsColumn.css';
import BugShortOverview from './BugShortOverview'
import BugsColumnHeader from './BugsColumnHeader';

class BugsColumn extends Component {

  state = {
  }

  componentDidMount() {

  }

  render() {
    return (
      <div className="bugs-column">
        <div className="flexbox-vertical-centered full-height full-width">
          <BugsColumnHeader status={this.props.bugStatus} headerColorClass={this.props.headerColorClass} onAddBug={this.props.onAddBug} />
          <div className="flexbox-vertical-centered vertical-scroll-container left-right-padded-container full-width">
            {this.props.bugs.map(
              (bug) =>
                <BugShortOverview title={bug.title} id={bug.id} key={bug.id} />
            )}
          </div>
        </div>
      </div>
    );
  }
}

//bugStatus
//bugs
//headerColorClass
//onAddBug
export default (BugsColumn);
