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
            <Col span={6} className="col1">
              <FullAnalyzeButton/>
            </Col>
            <Col span={6} className="col2">
              <FullScanButton/>
            </Col>
          </Row>
          <Row>
            <Col span={12}>
              <ProjectsList title="Projects"/>
            </Col>
            <Col span={12}>
              <Row>
                <Col span={24}>
                  <DescriptorsList title="Descriptors"/>
                </Col>
              </Row>
              <Row>
                <Col span={24}>
                  <ArtifactsList title="Artifacts"/>
                </Col>
              </Row>
            </Col>
          </Row>
        </div>
    )
  }
}