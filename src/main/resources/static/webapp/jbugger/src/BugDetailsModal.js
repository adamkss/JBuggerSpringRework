import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Typography, Input, Select, MenuItem } from '@material-ui/core';
import './BugDetailsModal.css';
import { closeModal, getUserNames } from './redux-stuff/actions/actionCreators';
import BugDetailsSidebarSection from './BugDetailsSidebarSection';

class BugDetailsModal extends PureComponent {
    state = {
        assignedToEditMode: false,
        age: 10,
        assignableUsers: [],
        open: false,
        mustClose: false
    }

    onModalClose = () => {
        this.props.dispatch(closeModal());
    }

    componentDidMount() {
        setTimeout(() => {
            this.setState({
                open: true
            })
        })
    }

    onAssignedToEditClick = () => {
        this.setState({
            assignedToEditMode: true
        })
    }

    handleChange = (event) => {
        this.setState({
            age: event.target.value
        })
    }

    onEditClickAssignedTo = () => {
        this.props.dispatch(getUserNames());
    }

    render() {
        let extraClassIfOpen = this.state.open && !this.props.mustClose ? " modal-expanded" : "";
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
                            <a aria-label="Toggle sidebar" href="#" role="button" onClick={this.onModalClose} className="header__close-button" tabIndex="-1">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 15 15">
                                    <path d="M9,7.5l5.83-5.91a.48.48,0,0,0,0-.69L14.11.15a.46.46,0,0,0-.68,0l-5.93,6L1.57.15a.46.46,0,0,0-.68,0L.15.9a.48.48,0,0,0,0,.69L6,7.5.15,13.41a.48.48,0,0,0,0,.69l.74.75a.46.46,0,0,0,.68,0l5.93-6,5.93,6a.46.46,0,0,0,.68,0l.74-.75a.48.48,0,0,0,0-.69Z">
                                    </path>
                                </svg>
                            </a>
                        </header >
                        <div className="sidebar__horizontal-separator" />
                        <main>
                            <BugDetailsSidebarSection
                                sectionName="Assigned to"
                                initialData={this.props.bug.assignedToName}
                                onEditClick={this.onEditClickAssignedTo}
                                renderEditControl={() => {
                                    return (
                                        <Select
                                            value={this.state.age}
                                            onChange={this.handleChange}
                                        >
                                            {this.props.usernames.map(user =>
                                                <MenuItem value={user.username}>{user.username}</MenuItem>
                                            )}
                                        </Select>
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Severity"
                                initialData={this.props.bug.severity}
                                renderEditControl={() => {
                                    return (
                                        <Input />
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Revision"
                                initialData={this.props.bug.revision}
                                renderEditControl={() => {
                                    return (
                                        <Input />
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Target date"
                                initialData={this.props.bug.targetDate}
                                renderEditControl={() => {
                                    return (
                                        <Input />
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Description"
                                initialData={this.props.bug.description}
                                renderEditControl={() => {
                                    return (
                                        <Input />
                                    )
                                }} />
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
    bug: state.activeBugToModify,
    usernames: state.usernames
});

export default connect(mapStateToProps)(BugDetailsModal);