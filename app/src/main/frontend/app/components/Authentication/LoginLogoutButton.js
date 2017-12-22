import React, {Component} from 'react';
import {Icon} from 'antd';
import LoginFormModal from "./LoginFormModal";
import {
  logout,
  showModal,
  tryAuthenticateFromSession
} from "./AuthenticationActions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";

export class LoginLogoutButton extends Component {

  showModal() {
    this.props.showModal();
  }

  logout() {
    this.props.logout();
  }

  componentWillMount() {
    this.props.tryAuthenticateFromSession();
  }

  render() {
    let result = [];
    if (this.props.logged) {
      result.push(<Icon type="user" key="logoutIcon"/>);
      result.push(' Welcome ' + this.props.username);
      result.push(<a href="#" key="logoutLink"
                     onClick={this.logout.bind(this)}>
        {' Logout'}
      </a>);
    }
    else {
      result.push(<a href="#" key="loginLink"
                     onClick={this.showModal.bind(this)}>
        <Icon type="user" key="loginIcon"/>{' Login'}
      </a>);
      result.push(<LoginFormModal key='loginFormModal'/>);
    }

    return (
        <div>
          {result}
        </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    logged: state.auth.logged,
    username: state.auth.logged ? state.auth.auth.username : null
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({showModal, logout, tryAuthenticateFromSession},
      dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginLogoutButton)