import React, {Component} from 'react';
import {connect} from 'react-redux'
import {fullScan} from './redux/actions';
import {bindActionCreators} from 'redux'
import {Button} from 'antd';

export class RefreshAllButton extends Component {

  fullScan() {
    this.props.fullScan();
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
    loading: state.refreshAllButton.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({fullScan}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(RefreshAllButton)