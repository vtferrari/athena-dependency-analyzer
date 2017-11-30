import React, {Component} from 'react';
import {connect} from 'react-redux'
import {bindActionCreators} from 'redux'
import {Button, message} from 'antd';
import {fullAnalyze} from "./redux/actions";

export class FullAnalyzeButton extends Component {

  fullAnalyze() {
    this.props.fullAnalyze();
  }

  componentWillUpdate(nextProps) {
    if (this.props.loading && !nextProps.loading) {
      if (nextProps.error) {
        message.error(nextProps.errorMessage, 5);
      }
      else {
        message.success(
            "Full analyze requested for all projects... The analyze is running in the background...");
      }
    }
  }

  render() {
    return (
        <div>
          <Button loading={this.props.loading}
                  onClick={this.fullAnalyze.bind(this)}>
            Request full analyze
          </Button>
        </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    loading: state.fullAnalyzeButton.loading,
    error: state.fullAnalyzeButton.error,
    errorMessage: state.fullAnalyzeButton.errorMessage,
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({fullAnalyze}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(FullAnalyzeButton)