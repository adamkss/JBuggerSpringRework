import React, { Component } from 'react';

export default class Draggable extends Component {

    state = {
    }

    componentDidMount() {

    }

    onDragStart = (event, transferData) => {
        if(this.props.onDragStart)
            this.props.onDragStart(event,transferData);

        event.dataTransfer.setData("text/plain", transferData);
    }

    render() {
        const {children, transferData, onDragStart, ...otherProps} = this.props;
        return (
            <div draggable onDragStart={(e) => this.onDragStart(e, transferData)} {...otherProps} >
                {children}
            </div>
        );
    }
}
