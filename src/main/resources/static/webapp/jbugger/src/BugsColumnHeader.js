import React, { Component } from 'react';
import './BugsColumnHeader.css';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { Divider, IconButton } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import 'typeface-roboto';
import classNames from 'classnames';
import { purple, indigo, blueGrey } from '@material-ui/core/colors';

const styles = theme => ({
  coloredHeaderLine: {
    width: "340px",
    height: "3px",
    borderTopLeftRadius: "5px",
    borderTopRightRadius: "5px"
  }
})

class BugsColumnHeader extends Component {

  constructor(props) {
    super(props);
  }

  state = {
     
  }

  componentDidMount() {

  }

  render() {
    const { classes } = this.props;
    return (
      <Grid
        container
        direction="column">
        <Grid
          container
          direction="row"
          className="headerGrid"
          alignItems="center">
          <Grid item>
            <div className={classNames(classes.coloredHeaderLine, this.props.headerColorClass)} />
          </Grid>
          <Grid item className="flex-grow left-subtitle">
            <Typography variant="subtitle2" color="inherit" >
              {this.props.status}
            </Typography>
          </Grid>
          <Grid item>
            <IconButton
              color="inherit"
              aria-label="Open drawer"
              onClick={this.props.onAddBug}
            >
              <AddIcon />
            </IconButton>
          </Grid>
        </Grid>
        <Grid item>
          <Divider />
        </Grid>
      </Grid>

    );
  }
}

//status
//onAddBug
//headerColorClass
export default withStyles(styles, { withTheme: true })(BugsColumnHeader);
