import React, {Component} from 'react';
import {Icon, Menu} from 'antd';
import {Link} from "react-router-dom";

export default class MainMenu extends Component {

  render() {
    return (
        <Menu theme="dark"
              defaultSelectedKeys={['home']}
              mode="inline">
          <Menu.Item key="home">
            <Link to="/">
              <Icon type="home"/>
              <span>Home</span>
            </Link>
          </Menu.Item>
          <Menu.Item key="scmApi">
            <Link to="/scmApi">
              <Icon type="api"/>
              <span>SCM API</span>
            </Link>
          </Menu.Item>
        </Menu>
    )
  }
}