import React from 'react';
import styled from 'styled-components';

const ParentDiv = styled.div`
    height: 20px;
    border-radius: 7px;
    background-color: ${props => props.backgroundColor || "orange"};
    color: ${props => {
        if (props.backgroundColor) {
            const red = parseInt(props.backgroundColor.substring(1, 2), 16);
            const green = parseInt(props.backgroundColor.substring(3, 2), 16);
            const blue = parseInt(props.backgroundColor.substring(5, 2), 16);
            if ((red * 0.299 + green * 0.587 + blue * 0.114) < 186)
                return "#ffffff";
        }
        return "#000000";
    }}
    font-size: 0.78rem;
    font-family: "Roboto", "Helvetica", "Arial", sans-serif;
    font-weight: 500;
    line-height: 1.57;
    letter-spacing: 0.00714em;  
    padding-left: 3px;
    padding-right: 3px;
    padding-top: 1px;
    padding-bottom: 1px;
    margin-left: 3px;
    cursor: ${props => props.selectable ? "pointer" : "initial"};
    ${props => props.selected && "border: solid 2px #4a148c;"}
    ${props => props.selected && "box-sizing: content-box;"}
`;


export default class LabelShort extends React.PureComponent {

    render() {
        const { text, ...restProps } = this.props;
        return (
            <ParentDiv {...restProps}>
                {text}
            </ParentDiv>
        )
    }
}