import React, { PureComponent } from 'react';
import './GenericModal.css';

export default class GenericModal extends PureComponent {
    state = {

    }

    render() {
        return (
            <div className="modal">
                <div className="modal__content">
                    {/* {this.props.render()} */}
                </div>
            </div>
        )
    }
}

