import React, {Component} from 'react';
import ProjectsList from '../components/ProjectsList/ProjectsList';
import DescriptorsList from '../components/DescriptorsList/DescriptorsList';
import ArtifactsList from "../components/ArtifactsList/ArtifactsList";
import {Col, Layout, Row} from 'antd';
import '../../style/App.css';

const {Header, Footer, Content} = Layout;

export default class App extends Component {
  render() {
    return (
        <div>
          <Layout>
            <Header className={"header"}><h1>Athena Dependency Analyzer</h1>
            </Header>
            <Content>
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
            </Content>
            <Footer className={"footer"}>
              By Netshoes
            </Footer>
          </Layout>
        </div>
    );
  }
}