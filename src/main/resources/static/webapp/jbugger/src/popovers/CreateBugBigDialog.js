import React, { Component } from 'react'
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, Typography, Divider } from '@material-ui/core';
import AccountCircle from '@material-ui/icons/AccountCircle';
import DateRange from '@material-ui/icons/DateRange';
import ShortText from '@material-ui/icons/ShortText';
import styled from 'styled-components';

export default class CreateBugBigDialog extends Component {
  state = {
    newBugTitle: "Unnamed bug"
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
      console.log(event);
    }
  }
  event
  render() {
    return (
      <Dialog
        disableBackdropClick
        disableEscapeKeyDown
        maxWidth=""
        aria-labelledby="confirmation-dialog-title"
        open={true}
      >
        <DialogContent>
          <div className="flexbox-vertical">
            <CustomTextAreaForBugTextInput
              title
              value={this.state.newBugTitle}
              onChange={this.handleStateValueChange('newBugTitle')}
              placeholder="Write a bug name"
              onKeyDown={this.onKeyDownOnInput} />

            <NewBugSection marginBottom>
              <CustomButtonForSelection>
                <AccountCircle className="color-gray small-margin-right" />
                <Typography variant="subtitle2">Not assigned</Typography>
              </CustomButtonForSelection>
              <CustomButtonForSelection marginLeft>
                <DateRange className="color-gray small-margin-right" />
                <Typography variant="subtitle2">No target date</Typography>
              </CustomButtonForSelection>
            </NewBugSection>

            <Divider />

            <NewBugSection alignedToUpperButtons marginTop>
              <ShortText className="color-gray small-margin-right" />
              <CustomTextAreaForBugTextInput
                placeholder="Description..." />
            </NewBugSection>

          </div>
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