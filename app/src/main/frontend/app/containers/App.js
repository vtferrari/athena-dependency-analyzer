import React, {Component} from 'react';
import ProjectsList from '../components/ProjectsList/ProjectsList';
import DescriptorsList from '../components/DescriptorsList/DescriptorsList';
import ArtifactsList from "../components/ArtifactsList/ArtifactsList";
import {Col, Row} from 'react-bootstrap';
import '../../style/App.css';

export default class App extends Component {
  render() {
    return (
        <div>
          <link rel="stylesheet"
                href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
          <Row>
            <Col md={12}>
              <header>
                <h1>Athena Dependency Analyzer</h1>
              </header>
            </Col>
          </Row>
          <Row>
            <Col md={6}>
              <ProjectsList title={"Projects"}/>
            </Col>
            <Col md={6}>
              <Row>
                <Col md={12}>
                  <DescriptorsList title={"Descriptors"}/>
                </Col>
              </Row>
              <Row>
                <Col md={12}>
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