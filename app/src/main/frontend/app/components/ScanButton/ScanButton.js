import React, {Component} from 'react';
import {connect} from 'react-redux'
import {fullScan} from './redux/actions';
import {bindActionCreators} from 'redux'
import {Button, message} from 'antd';

export class ScanButton extends Component {

  fullScan() {
    this.props.fullScan();
  }

  componentWillUpdate(nextProps) {
    if (this.props.loading && !nextProps.loading) {
      if (nextProps.error) {
        message.error(nextProps.errorMessage, 5);
      }
      else {
        message.success(
            "Scan requested for " + nextProps.projects.length
            + " projects... The scan is running in the background...");
      }
    }
  }

  render() {
    return (
        <div>
          <Button loading={this.props.loading}
                  onClick={this.fullScan.bind(this)}>
            Request full scan
          </Button>
        </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    loading: state.scanButton.loading,
    error: state.scanButton.error,
    errorMessage: state.scanButton.errorMessage,
    projects: state.scanButton.projects
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({fullScan}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ScanButton)