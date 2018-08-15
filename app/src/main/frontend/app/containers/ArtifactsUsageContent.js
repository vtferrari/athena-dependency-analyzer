import React, {Component} from 'react';
import {Col, Row} from "antd";
import ArtifactsUsageList from "../components/ArtifactsUsageList/ArtifactsUsageList";

export default class ArtifactsUsageContent extends Component {

  render() {
    return (
        <div>
          <Row>
            <Col span={24}>
              <ArtifactsUsageList title="Artifact usages"/>
            </Col>
          </Row>
        </div>
    )
  }
}