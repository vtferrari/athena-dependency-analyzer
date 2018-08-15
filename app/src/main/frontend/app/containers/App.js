import React, {Component} from 'react';
import MainMenu from "../components/MainMenu/MainMenu";
import {Col, Layout, Row} from 'antd';
import '../../style/App.css';
import {HashRouter, Route} from "react-router-dom";
import HomeContent from "./HomeContent";
import ArtifactsUsageContent from "./ArtifactsUsageContent";
import ScmApiContent from "./ScmApiContent";
import LoginLogoutButton from "../components/Authentication/LoginLogoutButton";

const {Header, Footer, Content, Sider} = Layout;

export default class App extends Component {
  render() {
    return (
        <HashRouter>
          <div>
            <Layout>
              <Header className="header">
                <Row>
                  <Col span={12}>
                    <h1>Athena Dependency Analyzer</h1>
                  </Col>
                  <Col span={12} align="right">
                    <LoginLogoutButton/>
                  </Col>
                </Row>
              </Header>
              <Layout>
                <Sider collapsed={true}>
                  <MainMenu/>
                </Sider>
                <Content>
                  <Route exact path="/" component={HomeContent}/>
                  <Route path="/artifactsUsage"
                         component={ArtifactsUsageContent}/>
                  <Route path="/scmApi" component={ScmApiContent}/>
                </Content>
              </Layout>
              <Footer className="footer">
                Powered By Netshoes
              </Footer>
            </Layout>
          </div>
        </HashRouter>
    );
  }
}