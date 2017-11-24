import React, {Component} from 'react';
import {connect} from 'react-redux'
import HomePage from "./HomePage";
import ScmApiPage from "./ScmApiPage";

export class ContentsArea extends Component {

  render() {
    return (
        <div>
          <div key={"homeContent"} hidden={this.props.actualPage !== "home"}>
            <HomePage/>
          </div>
          <div key={"apiContent"} hidden={this.props.actualPage !== "scmApi"}>
            <ScmApiPage/>
          </div>
        </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    actualPage: state.menu.actualPage
  }
};

export default connect(mapStateToProps)(ContentsArea)