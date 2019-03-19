import React, { Component } from 'react'
import Popover from '@material-ui/core/Popover';
import { Dialog, DialogContent, DialogActions, Button, Typography, Divider, List, ListItem, Input } from '@material-ui/core';
import AccountCircle from '@material-ui/icons/AccountCircle';
import DateRange from '@material-ui/icons/DateRange';
import ShortText from '@material-ui/icons/ShortText';
import ErrorOutline from '@material-ui/icons/ErrorOutline';
import Label from '@material-ui/icons/Label';
import Attachment from '@material-ui/icons/Attachment';
import styled from 'styled-components';
import { connect } from 'react-redux';
import { getUserNames } from '../redux-stuff/actions/actionCreators';

class CreateBugBigDialog extends Component {
  state = {
    bugTitle: "Unnamed bug",
    bugDescription: "",
    assignedToUser: {
      name: null,
      id: null,
      username: null
    },
    targetDate: null,
    severity: null,
    selectAssignedToUserPopoverAnchorEl: null,
    selectTargetDatePopoverAnchorEl: null,
    selectSeverityPopoverAnchorEl: null
  };

  handleStateValueChange = (nameOfStateElement) => (event) => {
    this.setState({
      [nameOfStateElement]: event.target.value
    })
  }

  onKeyDownOnInput = (event) => {
    if (event.keyCode == 13) {
      event.preventDefault();
      event.stopPropagation();
    }
  }

  onKeyDownDialog = (event) => {
    if (event.keyCode === 27) {
      this.props.handleCancel();
    }
  }

  onAssignUserButtonClick = (event) => {
    this.props.dispatch(getUserNames());
    this.setState({
      selectAssignedToUserPopoverAnchorEl: event.currentTarget
    })
  }

  onAssignToUserPopoverClose = () => {
    this.setState({
      selectAssignedToUserPopoverAnchorEl: null
    })
  }

  onTargetDateButtonClick = (event) => {
    this.setState({
      selectTargetDatePopoverAnchorEl: event.currentTarget
    })
  }

  onTargetDatePopoverClose = () => {
    this.setState({
      selectTargetDatePopoverAnchorEl: null
    })
  }

  onSeverityButtonClick = (event) => {
    this.setState({
      selectSeverityPopoverAnchorEl: event.currentTarget
    })
  }

  onSeverityPopoverClose = () => {
    this.setState({
      selectSeverityPopoverAnchorEl: null
    })
  }

  assignedToUserSelected = (selectedUser) => {
    this.setState({
      assignedToUser: { ...selectedUser }
    })
    this.onAssignToUserPopoverClose();
  }

  render() {
    return (
      <Dialog
        disableBackdropClick
        disableEscapeKeyDown
        maxWidth=""
        aria-labelledby="confirmation-dialog-title"
        open={true}
        onKeyDown={this.onKeyDownDialog}
      >
        <DialogContent>
          <div className="flexbox-vertical">
            <CustomTextAreaForBugTextInput
              title
              value={this.state.bugTitle}
              onChange={this.handleStateValueChange('bugTitle')}
              placeholder="Write a bug name"
              onKeyDown={this.onKeyDownOnInput} />

            <NewBugSection marginBottom>
              <CustomButtonForSelection
                onClick={this.onAssignUserButtonClick}>
                <AccountCircle className="color-gray small-margin-right" />
                <Typography variant="subtitle2">
                  {this.state.assignedToUser.name || "Not assigned"}
                </Typography>
              </CustomButtonForSelection>
              <CustomButtonForSelection marginLeft
                onClick={this.onTargetDateButtonClick}>
                <DateRange className="color-gray small-margin-right" />
                <Typography variant="subtitle2">
                  {this.state.targetDate || "No target date"}
                </Typography>
              </CustomButtonForSelection>
              <CustomButtonForSelection marginLeft
                onClick={this.onSeverityButtonClick}>
                <ErrorOutline className="color-gray small-margin-right" />
                <Typography variant="subtitle2">
                  {this.state.severity || "No severity selected"}
                </Typography>
              </CustomButtonForSelection>
            </NewBugSection>

            <Divider />

            <NewBugSection alignedToUpperButtons marginTop>
              <ShortText className="color-gray small-margin-right" />
              <CustomTextAreaForBugTextInput
                placeholder="Description..."
                onChange={this.handleStateValueChange('bugDescription')}
                value={this.state.bugDescription} />
            </NewBugSection>

            <NewBugSection alignedToUpperButtons marginTop>
              {/* TODO: Add labels here */}
              <CustomButtonForSelection>
                <Label className="color-gray small-margin-right" />
                <Typography variant="subtitle2">Add label</Typography>
              </CustomButtonForSelection>
            </NewBugSection>

            <NewBugSection alignedToUpperButtons marginTop>
              {/* TODO: Add the attachments here */}
              <CustomButtonForSelection>
                <Attachment className="color-gray small-margin-right" />
                <Typography variant="subtitle2">Add attachment</Typography>
              </CustomButtonForSelection>
            </NewBugSection>

          </div>

          <SelectAssignedToUserPopover
            open={Boolean(this.state.selectAssignedToUserPopoverAnchorEl)}
            anchorEl={this.state.selectAssignedToUserPopoverAnchorEl}
            onClose={this.onAssignToUserPopoverClose}
            selectableUsers={this.props.users}
            assignedToUserSelected={this.assignedToUserSelected} />

          <SelectTargetDatePopover
            open={Boolean(this.state.selectTargetDatePopoverAnchorEl)}
            anchorEl={this.state.selectTargetDatePopoverAnchorEl}
            onClose={this.onTargetDatePopoverClose} />

          <SelectSeverityPopover
            open={Boolean(this.state.selectSeverityPopoverAnchorEl)}
            anchorEl={this.state.selectSeverityPopoverAnchorEl}
            onClose={this.onSeverityPopoverClose} />

        </DialogContent>
        <DialogActions>
          <Button onClick={this.props.handleCancel} color="primary">
            Cancel
          </Button>
          <Button onClick={this.props.handleOk} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    )
  }
}

const mapStateToProps = state => ({
  severities: state.severities,
  statuses: state.statuses,
  users: state.usernames
})

export default connect(mapStateToProps)(CreateBugBigDialog);

const CustomTextAreaForBugTextInput = styled.textarea`
  ${props => props.title ? "height: 40px;" : "height: 80px;"}
  width: 600px;
  border-color: transparent;
  border-radius: 5px;
  transition: border-color 300ms;
  resize: none;
  font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Helvetica,Arial,sans-serif;
  font-size: ${props => props.title ? "24px" : "16px"};
  font-weight: ${props => props.title ? "500" : "200"};
  line-height: ${props => props.title ? "32px" : "20px"};
  ${props => props.title ? "overflow: hidden;" : ""}
  margin-bottom: 8px;

  &:hover{
    border-color: #d5dce0;
  }
  
  &:focus{
    outline: none;
    border-color: #d5dce0;
  }
`;

const CustomButtonForSelection = styled.div`
  height: 30px;
  padding: 5px;
  border: solid 1px transparent;
  transition: border-color 300ms;
  border-radius: 10px;
  display:flex;
  flex-direction:row;
  justify-content:center;
  align-items:center;
  align-content:center;
  cursor: pointer;
  margin-left: ${props => props.marginLeft ? "8px;" : ""};

  &:hover{
    border-color: #d5dce0;
  }
`

const NewBugSection = styled.div`
  padding-left: ${props => props.alignedToUpperButtons ? "5px" : ""};
  display: flex;
  flex-direction: row;
  margin-bottom: ${props => props.marginBottom ? "8px" : ""};
  margin-top: ${props => props.marginTop ? "8px" : ""};
`

class SelectAssignedToUserPopover extends React.PureComponent {

  state = {
    filterString: ""
  }

  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      event.stopPropagation();
      event.preventDefault();
      this.props.onClose();
    }
  }

  getOnUserSelected = (user) => () => {
    this.props.assignedToUserSelected(user);
  }

  render() {
    const filterStringUppercase = this.state.filterString.toUpperCase();
    let filteredUsers = this.props.selectableUsers.filter((user) => {
      if (user.username.toUpperCase().includes(filterStringUppercase)
        || user.name.toUpperCase().includes(filterStringUppercase))
        return true;

      return false;
    })

    return (
      <Popover
        open={this.props.open}
        anchorEl={this.props.anchorEl}
        onClose={this.props.onClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        onKeyDown={this.onKeyDown}
      >
        <div style={{ maxHeight: "300px" }} className="flexbox-vertical-centered">
          <Input
            placeholder="Search text..."
            autoFocus
            value={this.state.filterString}
            onChange={(event) => {
              this.setState({
                filterString: event.target.value
              })
            }}
            style={{ width: "100%" }}
          />

          {filteredUsers.length > 0 ?
            <List component="nav" style={{ overflow: "auto" }}>
              {filteredUsers.map(selectableUser =>
                <ListItem
                  button
                  onClick={this.getOnUserSelected(selectableUser)}
                  style={{ cursor: "pointer" }}
                  key={selectableUser.id}>
                  {selectableUser.username}-{selectableUser.name}
                </ListItem>)}
            </List>
            :
            <Typography variant="subtitle2">
              No users.
          </Typography>
          }
        </div>
      </Popover>
    )
  }
}

class SelectTargetDatePopover extends React.PureComponent {

  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      event.stopPropagation();
      event.preventDefault();
      this.props.onClose();
    }
  }

  render() {
    return (
      <Popover
        open={this.props.open}
        anchorEl={this.props.anchorEl}
        onClose={this.props.onClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        onKeyDown={this.onKeyDown}
      >
        <div
          style={{ padding: "10px" }}>

        </div>
      </Popover>
    )
  }
}

class SelectSeverityPopover extends React.PureComponent {

  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      event.stopPropagation();
      event.preventDefault();
      this.props.onClose();
    }
  }

  render() {
    return (
      <Popover
        open={this.props.open}
        anchorEl={this.props.anchorEl}
        onClose={this.props.onClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        onKeyDown={this.onKeyDown}
      >
        <div
          style={{ padding: "10px" }}>

        </div>
      </Popover>
    )
  }
}