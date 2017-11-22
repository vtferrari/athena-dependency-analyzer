import React, {Component} from 'react';
import ProjectsList from '../components/ProjectsList/ProjectsList';
import DescriptorsList from '../components/DescriptorsList/DescriptorsList';
import ArtifactsList from "../components/ArtifactsList/ArtifactsList";
import {Col, Row} from 'antd';
import '../../style/App.css';

export default class App extends Component {
  render() {
    return (
        <div>
          <Row>
            <Col span={24}>
              <header>
                <h1>Athena Dependency Analyzer</h1>
              </header>
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

          <footer>
            <p className="text-right">By Netshoes</p>
          </footer>
        </div>
    );
  }
}