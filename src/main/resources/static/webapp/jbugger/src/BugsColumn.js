import React, { Component } from 'react';
import './BugsColumn.css';
import BugShortOverview from './BugShortOverview'
import BugsColumnHeader from './BugsColumnHeader';
import Draggable from './d&d/Draggable';
import Droppable from './d&d/Droppable';
import {moveBug} from './redux-stuff/actions/actionCreators';

import { connect } from 'react-redux';

class BugsColumn extends Component {

  state = {
  }

  componentDidMount() {

  }

  onDrop = (bugInfo) => {
    const [bugId, oldStatus] = bugInfo.split("-");
    this.props.dispatch(moveBug(bugId, oldStatus, this.props.bugStatus));
  }

  render() {
    return (
      <Droppable onDrop={this.onDrop} className="bugs-column">
        <div className="flexbox-vertical-centered full-height full-width">
          <BugsColumnHeader status={this.props.bugStatus} headerColorClass={this.props.headerColorClass} onAddBug={this.props.onAddBug} />
          <div className="flexbox-vertical-centered vertical-scroll-container left-right-padded-container full-width">
            {this.props.bugs.map(
              (bug) =>
                <Draggable key={bug.id} transferData={bug.id + "-" + this.props.bugStatus} onDragStart={() => { }}>
                  <BugShortOverview title={bug.title} id={bug.id} />
                </Draggable>
            )}
          </div>
        </div>
      </Droppable>
    );
  }
}

//bugStatus
//bugs
//headerColorClass
//onAddBug
export default connect()(BugsColumn);
