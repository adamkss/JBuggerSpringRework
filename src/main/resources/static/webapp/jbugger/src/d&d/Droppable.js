import React, { Component } from 'react';

export default class Droppable extends Component {

    state = {
    }

    componentDidMount() {

    }

    onDragOver = (event) => {
        if (this.props.onDragOver) {
            this.props.onDragOver(event);
        }
        event.preventDefault();
    }

    onDrop = (event, category) => {
        if(this.props.onDrop)
            this.props.onDrop(event.dataTransfer.getData("text"));
    }

    render() {
        const {children, onDragOver, onDrop, ...otherProps} = this.props;
        return (
            <div droppable onDragOver={this.onDragOver} onDrop={this.onDrop} {...otherProps}>
                {children}
            </div>
        );
    }
}
