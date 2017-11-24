import React, {Component} from 'react';
import {Icon, Menu} from 'antd';
import {Link,withRouter} from "react-router-dom";

export class MainMenu extends Component {

  render() {
    return (
        <Menu theme="dark"
              defaultSelectedKeys={["/"]}
              selectedKeys={[this.props.location.pathname]}
              mode="inline">
          <Menu.Item key="/">
            <Link to="/">
              <Icon type="home"/>
              <span>Home</span>
            </Link>
          </Menu.Item>
          <Menu.Item key="/scmApi">
            <Link to="/scmApi">
              <Icon type="api"/>
              <span>SCM API</span>
            </Link>
          </Menu.Item>
        </Menu>
    )
  }
}

export default withRouter(MainMenu);