import React, {Component} from 'react';
import {Tag} from "antd";
import './TechnologyIndicator.css';
import PropTypes from "prop-types";

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