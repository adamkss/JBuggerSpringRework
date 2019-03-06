import React, { Component } from 'react'
import { Typography } from '@material-ui/core';
import Comment from './Comment';

export default class Comments extends Component {
    render() {
        return (
            <div>
                <div className="flexbox-horizontal">
                    <Typography className="flex-grow" variant="subtitle2">Comments</Typography>
                </div>
                {this.props.comments.lenght == 0 ?
                    <div className="flexbox-horizontal flexbox-justify-center">
                        <Typography variant="subtitle2" className="sidebar__detail-info">No comments.</Typography>
                    </div>
                    :
                    null
                }
                {this.props.comments.map(comment =>
                    <Comment
                        key={comment.id}
                        commentText={comment.comment}
                        author={comment.author.name}
                        createdDateTime={comment.createdDateTime} />
                )}
            </div>
        )
    }
}
