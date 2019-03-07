import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import Dialog from '@material-ui/core/Dialog';

class CreateCommentDialog extends React.Component {

  state = {
    commentMessage: ""
  }

  handleCancel = () => {
    this.props.onCancel();
  };

  handleOk = () => {
    this.props.onDone(this.state.commentMessage);
  };

  onCommentMessageChange = (event) => {
    this.setState({
      commentMessage: event.target.value
    })
  }

  render() {

    return (
      <Dialog
        disableBackdropClick
        disableEscapeKeyDown
        maxWidth="md"
        aria-labelledby="confirmation-dialog-title"
        open={true}
      >
        <DialogTitle id="confirmation-dialog-title">New comment</DialogTitle>
        <DialogContent>
          <TextField
            type="text"
            multiline
            rowsMax={10}
            rows={10}
            placeholder="Write new comment here..."
            value={this.state.commentMessage}
            onChange={this.onCommentMessageChange} />
        </DialogContent>
        <DialogActions>
          <Button onClick={this.handleCancel} color="primary">
            Cancel
          </Button>
          <Button onClick={this.handleOk} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

export default CreateCommentDialog;