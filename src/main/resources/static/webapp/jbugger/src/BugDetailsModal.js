import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Typography } from '@material-ui/core';
import './BugDetailsModal.css';
import { closeModal } from './redux-stuff/actions/actionCreators';

class BugDetailsModal extends PureComponent {
    state = {

    }

    onModalClose = () => {
        this.props.dispatch(closeModal());
    }

    onAssignedToEditClick = () => {

    }

    render() {
        let extraClassIfOpen = this.props.open ? " modal-expanded" : "";
        return (
            <aside className={"modal-parent" + extraClassIfOpen}>
                {this.props.bug ?
                    <div className="sidebar">
                        <header className="modal__header">
                            <div className="header__bug-info">
                                <Typography variant="subtitle2" color="inherit">
                                    {this.props.bug.title}
                                </Typography>
                                <Typography variant="caption" color="inherit">
                                    #{this.props.bug.id}
                                </Typography>
                            </div>
                            <div className="header__vertical-separator" />
                            <a aria-label="Toggle sidebar" href="#" role="button" onClick={this.onModalClose} className="header__close-button">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 15 15">
                                    <path d="M9,7.5l5.83-5.91a.48.48,0,0,0,0-.69L14.11.15a.46.46,0,0,0-.68,0l-5.93,6L1.57.15a.46.46,0,0,0-.68,0L.15.9a.48.48,0,0,0,0,.69L6,7.5.15,13.41a.48.48,0,0,0,0,.69l.74.75a.46.46,0,0,0,.68,0l5.93-6,5.93,6a.46.46,0,0,0,.68,0l.74-.75a.48.48,0,0,0,0-.69Z">
                                    </path>
                                </svg>
                            </a>
                        </header >
                        <div className="sidebar__horizontal-separator" />
                        <main>
                            <section>
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Assigned to</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.assignedToName}
                                </Typography>
                            </section>
                            <div className="sidebar__horizontal-separator" />
                            <section className="with-margin-top">
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Severity</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.severity}
                                </Typography>
                            </section>
                            <div className="sidebar__horizontal-separator" />
                            <section className="with-margin-top">
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Revision</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.revision}
                                </Typography>
                            </section>
                            <div className="sidebar__horizontal-separator" />
                            <section className="with-margin-top">
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Target date</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.targetDate}
                                </Typography>
                            </section>
                            <div className="sidebar__horizontal-separator" />
                            <section className="with-margin-top">
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Description</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.description}
                                </Typography>
                            </section>
                            <div className="sidebar__horizontal-separator" />
                            <section className="with-margin-top">
                                <div className="flexbox-horizontal">
                                    <Typography className="flex-grow" variant="subtitle2">Labels</Typography>
                                    <Typography className="sidebar__edit-button" variant="subtitle2" onClick={this.onAssignedToEditClick}>Edit</Typography>
                                </div>
                                <Typography className="sidebar__detail-info">
                                    {this.props.bug.assignedTo}
                                </Typography>
                            </section>
                        </main>
                    </div>
                    : ""}
            </aside >
        )
    }
}

const mapStateToProps = state => ({
    bug: state.activeBugToModify
});

export default connect(mapStateToProps)(BugDetailsModal);