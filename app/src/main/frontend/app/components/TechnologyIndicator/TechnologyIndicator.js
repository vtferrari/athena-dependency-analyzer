import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Tag} from "antd";
import './TechnologyIndicator.css';

export default class TechnologyIndicator extends Component {

  render() {
    let name = this.props.name;
    let color;
    switch (name.toLowerCase()) {
      case "redis":
        color = "red";
        break;
      case "mongodb":
        color = "green";
        break;
      case "kafka":
        color = "cyan";
        break;
      case "rabbitmq":
        color = "orange";
        break;
      case "oracle":
        color = "purple";
        break;
      case "mysql":
        color = "blue";
        break;
      case "cassandra":
        color = "blue";
        break;
    }
    return (<Tag color={color}>{name}</Tag>);
  }
}

TechnologyIndicator.propTypes = {
  name: PropTypes.string
};