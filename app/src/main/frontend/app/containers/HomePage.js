import React, {Component} from 'react';
import {Col, Row} from "antd";
import ScanButton from "../components/ScanButton/ScanButton";
import ProjectsList from "../components/ProjectsList/ProjectsList";
import DescriptorsList from "../components/DescriptorsList/DescriptorsList";
import ArtifactsList from "../components/ArtifactsList/ArtifactsList";

export default class HomePage extends Component {

  render() {
    return (
        <div>
          <Row className={"topButtons"}>
            <Col span={12}>
              <ScanButton/>
            </Col>
          </Row>
          <Row>
            <Col span={12}>
              <ProjectsList title={"Projects"}/>
            </Col>
            <Col span={12}>
              <Row>
                <Col span={24}>
                  <DescriptorsList title={"Descriptors"}/>
                </Col>
              </Row>
              <Row>
                <Col span={24}>
                  <ArtifactsList title={"Artifacts"}/>
                </Col>
              </Row>
            </Col>
          </Row>
        </div>
    )
  }
}