import React from 'react';
import { Typography } from '@material-ui/core';
import './BugDetailsSidebarSection.css';

class BugDetailsSidebarSection extends React.PureComponent {
    state = {
        editMode: false
    }

    componentDidMount() {

    }

    onEditClick = () => {
        if(this.props.onEditClick)
            this.props.onEditClick();
        this.setState({
            editMode: true
        })
    }

    onCancelEdit = () => {
        this.endEditMode();
    }

    onSave = () => {
        this.endEditMode();
        if(this.props.onSave){
            this.props.onSave();
        }
    }

    endEditMode = () => {
        this.setState({
            editMode: false
        })
    }

    render() {
        return (
            <section>
                <div className="flexbox-horizontal">
                    <Typography className="flex-grow" variant="subtitle2">{this.props.sectionName}</Typography>
                    {this.state.editMode ?
                        <React.Fragment>
                            <Typography className="sidebar__edit-button slight-padding-right" variant="subtitle2" onClick={this.onSave}>Save</Typography>
                            <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onCancelEdit}>Cancel</Typography>
                        </React.Fragment>
                        :
                        <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onEditClick}>Edit</Typography>
                    }
                </div>
                {this.state.editMode ?
                    this.props.renderEditControl()
                    :
                    <Typography className="sidebar__detail-info">
                        {this.props.initialData}
                    </Typography>
                }
            </section>
        )
    }
}

export default BugDetailsSidebarSection;