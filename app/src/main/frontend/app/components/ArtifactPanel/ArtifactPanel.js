import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Col, Panel, Row} from 'react-bootstrap';

export class ArtifactPanel extends Component {

  render() {
    let tableHeader;
    if (this.props.title != null) {
      tableHeader = <h2>{this.props.title}</h2>
    }

    return (
        <Panel header={tableHeader}>
          <Row>
            <Col md={12}>
              <b>Group Id:</b> {this.props.artifact
            && this.props.artifact.groupId}
            </Col>
            <Col md={12}>
              <b>Artifact Id:</b> {this.props.artifact
            && this.props.artifact.artifactId}
            </Col>
            <Col md={12}>
              <b>Version:</b> {this.props.artifact
            && this.props.artifact.version}
            </Col>
          </Row>
        </Panel>
    );
  }
}

ArtifactPanel.propTypes = {
  title: PropTypes.string,
  artifact: PropTypes.shape({
    groupId: PropTypes.string,
    artifactId: PropTypes.string,
    version: PropTypes.string
  })
};

