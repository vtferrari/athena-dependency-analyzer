import React, {Component} from 'react';
import {Col, Row} from "antd";
import FullScanButton from "../components/FullScanButton/FullScanButton";
import ProjectsList from "../components/ProjectsList/ProjectsList";
import DescriptorsList from "../components/DescriptorsList/DescriptorsList";
import ArtifactsList from "../components/ArtifactsList/ArtifactsList";
import FullAnalyzeButton from "../components/FullAnalyzeButton/FullAnalyzeButton";

export default class HomeContent extends Component {

  render() {
    return (
        <div>
          <Row className="topButtons">
            <Col span={12} className="col1">
              <FullAnalyzeButton/>
            </Col>
            <Col span={12} className="col2">
              <FullScanButton/>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <ProjectsList title="Projects"/>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Row>
                <Col span={12}>
                  <DescriptorsList title="Dependency Management Descriptors"/>
                </Col>
                <Col span={12}>
                  <ArtifactsList title="Artifacts"/>
                </Col>
              </Row>
            </Col>
          </Row>
        </div>
    )
  }
}