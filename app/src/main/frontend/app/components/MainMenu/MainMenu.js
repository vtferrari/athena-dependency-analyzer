import React, {Component} from 'react';
import {connect} from 'react-redux'
import {goToPage} from './redux/actions';
import {bindActionCreators} from 'redux'
import {Icon, Menu} from 'antd';

export class MainMenu extends Component {

  goTo(event) {
    this.props.goToPage(event.keyPath[0]);
  }

  render() {
    return (
        <Menu theme="dark"
              defaultSelectedKeys={['home']}
              mode="inline"
              onClick={this.goTo.bind(this)}>
          <Menu.Item key="home">
            <Icon type="home"/>
            <span>Home</span>
          </Menu.Item>
          <Menu.Item key="scmApi">
            <Icon type="api"/>
            <span>SCM API</span>
          </Menu.Item>
        </Menu>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    actualPage: state.menu.actualPage
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({goToPage}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(MainMenu)