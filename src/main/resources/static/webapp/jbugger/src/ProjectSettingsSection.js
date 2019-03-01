import React, { Component } from 'react'
import './ProjectSettingsSection.css';

import { Typography, Divider } from '@material-ui/core';

export default class ProjectSettingsSection extends Component {
    render() {
        return (
            <section className="project-settings-section-parent">
                <Typography variant="h4" style={{ display: 'inline-block' }}>
                    {this.props.sectionName}
                </Typography>
                <Divider />
                {this.props.children}
            </section>
        )
    }
}
