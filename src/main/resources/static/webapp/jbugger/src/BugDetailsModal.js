import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Typography, Input, Select, MenuItem, TextField } from '@material-ui/core';
import './BugDetailsModal.css';
import { closeModal, getUserNames } from './redux-stuff/actions/actionCreators';
import BugDetailsSidebarSection from './BugDetailsSidebarSection';

class BugDetailsModal extends PureComponent {
    state = {
        assignedToUsernameNew: null,
        severityNew: null,
        revisionNew: null,
        open: false,
        mustClose: false,
        targetDateNew: null,
        descriptionNew: null
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

    }

    handleChange = (field) => {
        return (event) => {
            this.setState({
                [field]: event.target.value
            })
        }

    }

    onEditClickAssignedTo = () => {
        this.props.dispatch(getUserNames());
    }

    onAssignedToSave = () => {
        if (this.state.assignedToNew != null && this.state.assignedToNew !== this.props.bug.assignedToUsername) {
            this.props.onBugEdit({
                ...this.props.bug,
                assignedToUsername: this.state.assignedToNew
            })
        }
    }

    onEditClickSeverity = () => {
        // TODO: Maybe load severities async if they are not hardcoded anymore
    }

    onSeveritySave = () => {
        if (this.state.severityNew != null && this.state.severityNew !== this.props.bug.severity) {
            this.props.onBugEdit({
                ...this.props.bug,
                severity: this.state.severityNew
            })
        }
    }

    onRevisionSave = () => {
        if (this.state.revisionNew != null && this.state.revisionNew !== this.props.bug.revision) {
            this.props.onBugEdit({
                ...this.props.bug,
                revision: this.state.revisionNew
            })
        }
    }

    onSaveGeneral = (propertyName) => {
        return () => {
            if (this.state[propertyName + "New"] != null && this.state[propertyName + "New"] !== this.props.bug[propertyName]) {
                this.props.onBugEdit({
                    ...this.props.bug,
                    [propertyName]: this.state[propertyName + "New"]
                })
            }
        }
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
                                onSave={this.onSaveGeneral('assignedToUsername')}
                                renderEditControl={() => {
                                    return (
                                        <Select
                                            value={this.state.assignedToUsernameNew || this.props.bug.assignedToUsername}
                                            onChange={this.handleChange('assignedToUsernameNew')}
                                        >
                                            {this.props.usernames.map(user =>
                                                <MenuItem key={user.username} value={user.username}>{user.username + "-" + user.name}</MenuItem>
                                            )}
                                        </Select>
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Severity"
                                initialData={this.props.bug.severity}
                                onEditClick={this.onEditClickSeverity}
                                onSave={this.onSaveGeneral('severity')}
                                renderEditControl={() => {
                                    return (
                                        <Select
                                            value={this.state.severityNew || this.props.bug.severity}
                                            onChange={this.handleChange('severityNew')}
                                        >
                                            {this.props.severities.map(severity =>
                                                <MenuItem key={severity} value={severity}>{severity}</MenuItem>
                                            )}
                                        </Select>
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Revision"
                                initialData={this.props.bug.revision}
                                onSave={this.onSaveGeneral('revision')}
                                renderEditControl={() => {
                                    return (
                                        <Input
                                        value={this.state.revisionNew || this.props.bug.revision}
                                        onChange={this.handleChange('revisionNew')} />
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Target date"
                                initialData={this.props.bug.targetDate}
                                onSave={this.onSaveGeneral('targetDate')}
                                renderEditControl={() => {
                                    return (
                                        <TextField
                                            type="date"
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                            value={this.state.targetDateNew || this.props.bug.targetDate}
                                            onChange={this.handleChange('targetDateNew')}
                                        />
                                    )
                                }} />
                            <div className="sidebar__horizontal-separator" />
                            <BugDetailsSidebarSection
                                sectionName="Description"
                                initialData={this.props.bug.description}
                                onSave={this.onSaveGeneral('description')}
                                renderEditControl={() => {
                                    return (
                                        <Input
                                        className="full-width"
                                        multiline
                                        value={this.state.descriptionNew || this.props.bug.description}
                                        onChange={this.handleChange('descriptionNew')} />
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
    usernames: state.usernames,
    severities: state.severities
});

export default connect(mapStateToProps)(BugDetailsModal);