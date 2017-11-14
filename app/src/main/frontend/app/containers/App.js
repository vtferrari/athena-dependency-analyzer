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
          <header>
            <h1>Athena Dependency Analyzer</h1>
          </header>

          <Row>
            <Col md={6}>
              <ProjectsList title={"Projects"}/>
            </Col>
            <Col md={6}>
              <DescriptorsList title={"Descriptors"}/>
              <ArtifactsList title={"Artifacts"}/>
            </Col>
          </Row>

          <footer>
            <p className="text-right">By Netshoes</p>
          </footer>
        </div>
    );
  }
}