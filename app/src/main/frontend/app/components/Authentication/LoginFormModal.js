import React, {Component} from 'react';
import {connect} from 'react-redux'
import {closeModal, login} from './AuthenticationActions';
import {bindActionCreators} from 'redux'
import {Alert, Button, Form, Icon, Input, Modal} from 'antd';

const FormItem = Form.Item;

export class LoginFormModal extends Component {

  closeModal() {
    this.props.closeModal();
  }

  login(e) {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.props.login(values['userName'], values['password']);
      }
    });
  }

  render() {
    const {getFieldDecorator} = this.props.form;
    return (
        <Modal visible={this.props.modalVisible} title="Login"
               onOk={this.login.bind(this)}
               onCancel={this.closeModal.bind(this)}
               footer={[
                 <Button key="back"
                         onClick={this.closeModal.bind(this)}>Cancel</Button>,
                 <Button key="submit" type="primary"
                         loading={this.props.loading}
                         onClick={this.login.bind(this)}>
                   Login
                 </Button>
               ]}
        >
          {this.props.error ?
              <Alert message={this.props.errorMessage} showIcon={true} type="error"/> : null}
          <Form onSubmit={this.login.bind(this)} className="login-form">
            <FormItem>
              {getFieldDecorator('userName', {
                rules: [{
                  required: true,
                  message: 'Please input your username!'
                }],
              })(
                  <Input prefix={<Icon type="user"
                                       style={{color: 'rgba(0,0,0,.25)'}}/>}
                         placeholder="Username"/>
              )}
            </FormItem>
            <FormItem>
              {getFieldDecorator('password', {
                rules: [{
                  required: true,
                  message: 'Please input your Password!'
                }],
              })(
                  <Input prefix={<Icon type="lock"
                                       style={{color: 'rgba(0,0,0,.25)'}}/>}
                         type="password" placeholder="Password"/>
              )}
            </FormItem>
          </Form>
        </Modal>
    )
  }
}

const WrappedLoginForm = Form.create()(LoginFormModal);

const mapStateToProps = (state) => {
  return {
    modalVisible: state.auth.modalVisible,
    loading: state.auth.loading,
    error: state.auth.error,
    errorMessage: state.auth.errorMessage
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({login, closeModal}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(WrappedLoginForm)