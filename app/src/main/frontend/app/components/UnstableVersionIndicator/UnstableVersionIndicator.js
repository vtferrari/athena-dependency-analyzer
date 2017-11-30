import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Badge, Popover} from "antd";

export default class UnstableVersionIndicator extends Component {

  renderMany() {
    let summaries = [];
    this.props.artifacts.forEach((a, i) => {
      summaries.push(<p key={i}>{a.artifactId}: {a.report.summary}</p>)
    });

    if (this.props.showPopover) {
      return (<Popover key="many" content={summaries} trigger="click"><a
          href="#"><Badge
          count={this.props.artifacts.length}/></a></Popover>);
    }
    else {
      return (<Badge count={this.props.artifacts.length}/>);
    }
  }

  renderUnique() {
    let artifact = this.props.artifact;
    let summary = artifact.report.summary;
    let key = artifact.groupId + ":" + artifact.artifactId + ":"
        + artifact.version;

    if (this.props.showPopover) {
      return (
          <Popover key={key}
                   content={summary}><Badge dot={true}><span
              style={{marginRight: 6}}>{this.props.children}</span></Badge></Popover>);
    }
    else {
      return (
          <Badge dot={true}><span
              style={{marginRight: 6}}>{this.props.children}</span></Badge>);
    }
  }

  render() {
    let result = [];
    if (this.props.artifacts) {
      result.push(this.renderMany());
    }
    else {
      result.push(this.renderUnique())
    }

    return (<span>{result}</span>);
  }
}

let ArtifactVersionReport = {
  stable: PropTypes.bool,
  stableVersion: PropTypes.string,
  summary: PropTypes.string
};

let Artifact = {
  groupId: PropTypes.string,
  artifactId: PropTypes.string,
  version: PropTypes.string,
  report: PropTypes.shape(ArtifactVersionReport)
};

UnstableVersionIndicator.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node
  ]),
  showPopover: PropTypes.bool,
  artifact: PropTypes.shape(Artifact),
  artifacts: PropTypes.arrayOf(PropTypes.shape(Artifact))
};

UnstableVersionIndicator.defaultProps = {
  showPopover: true
};