import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Icon} from "antd";

export default class UnstableVersionIndicator extends Component {

  render() {
    let result = [];
    if (this.props.items) {
      result.push(<Icon type="exclamation-circle" key={"unique"}/>);
    }
    else {
      let key = this.props.groupId + ":" + this.props.artifactId + ":"
          + this.props.version;
      result.push(<Icon type="exclamation-circle" key={"icon-" + key}
                        title={this.props.report.summary}/>)
    }

    return (<span>{result}</span>);
  }
}

UnstableVersionIndicator.propTypes = {
  groupId: PropTypes.string,
  artifactId: PropTypes.string,
  version: PropTypes.string,
  report: PropTypes.shape({
    stable: PropTypes.bool,
    stableVersion: PropTypes.string,
    summary: PropTypes.string,
  }),
  items: PropTypes.arrayOf(PropTypes.shape({
    groupId: PropTypes.string,
    artifactId: PropTypes.string,
    version: PropTypes.string,
    report: PropTypes.shape({
      stable: PropTypes.bool,
      stableVersion: PropTypes.string,
      summary: PropTypes.string,
    })
  }))
};
