import React, { PureComponent } from 'react'
import BarChart from './BarChart';
import PieChart from './PieChart/index';

import './Statistics.css';

import ProjectSettingsSection from './ProjectSettingsSection';

export default class Statistics extends PureComponent {

    state = {
        statisticsByLabels: [],
        activeLabelIndex: -1
    }

    componentDidMount() {
        fetch("http://localhost:8080/statistics/byLabels")
            .then((response) => response.json())
            .then(data => {
                this.setState({
                    statisticsByLabels: data
                })
            })
    }

    onMouseOverLabelName = (labelName, labelIndex) => event => {
        this.setState({
            activeLabelIndex: labelIndex
        })
    }

    onMouseLeaveLabel = () => {
        this.setState({
            activeLabelIndex: null
        })
    }

    render() {
        return (
            <div className="flexbox-vertical">
                <ProjectSettingsSection sectionName="By labels" horizontalContent>
                    <section className="pie-chart">
                        <PieChart
                            data={this.state.statisticsByLabels}
                            transitionDuration="0.2s"
                            expandOnHover
                            expandSize={2}
                            expandedIndex={this.state.activeLabelIndex}
                            controlledFromExterior={this.state.statisticsByLabels !== null} />
                    </section>
                    <section>
                        <div className="flexbox-vertical">
                            {this.state.statisticsByLabels.map((element, index) => {
                                return (
                                    <div onMouseOver={this.onMouseOverLabelName(element.title, index)} onMouseLeave={this.onMouseLeaveLabel}><span>
                                        {element.title}
                                    </span>
                                    </div>
                                )
                            })}
                        </div>
                    </section>
                </ProjectSettingsSection>

            </div>

        )
    }
}
