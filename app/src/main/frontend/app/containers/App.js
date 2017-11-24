import React, {Component} from 'react';
import MainMenu from "../components/MainMenu/MainMenu";
import {Layout} from 'antd';
import '../../style/App.css';
import Contents from "./Contents";

const {Header, Footer, Content, Sider} = Layout;

export default class App extends Component {
  render() {
    return (
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
                <Contents/>
              </Content>
            </Layout>
            <Footer className={"footer"}>
              By Netshoes
            </Footer>
          </Layout>
        </div>
    );
  }
}