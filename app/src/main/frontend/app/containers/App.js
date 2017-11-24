import React, {Component} from 'react';
import MainMenu from "../components/MainMenu/MainMenu";
import {Layout} from 'antd';
import '../../style/App.css';
import {HashRouter, Route} from "react-router-dom";
import HomeContent from "./HomeContent";
import ScmApiContent from "./ScmApiContent";

const {Header, Footer, Content, Sider} = Layout;

export default class App extends Component {
  render() {
    return (
        <HashRouter>
          <div>
            <Layout>
              <Header className={"header"}>
                <h1>Athena Dependency Analyzer</h1>
              </Header>
              <Layout>
                <Sider collapsed={true}>
                  <MainMenu/>
                </Sider>
                <Content>
                  <Route exact path="/" component={HomeContent}/>
                  <Route path="/scmApi" component={ScmApiContent}/>
                </Content>
              </Layout>
              <Footer className={"footer"}>
                By Netshoes
              </Footer>
            </Layout>
          </div>
        </HashRouter>
    );
  }
}