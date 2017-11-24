import React, {Component} from 'react';
import {FormattedTime} from 'react-intl';
import {connect} from 'react-redux'
import * as PropTypes from "react/lib/ReactPropTypes";
import {getRateLimit} from './redux/actions';
import {bindActionCreators} from 'redux'
import {Col, Collapse, Row, Spin} from "antd";

const Panel = Collapse.Panel;

export class RateLimitPanel extends Component {
  componentWillMount() {
    this.props.getRateLimit();
  }

  render() {
    let rate, core, search, graphql;
    let resetRateFormatted, resetCoreFormatted
    let resetSearchFormatted, resetGraphqlFormatted;

    if (this.props.rateLimit) {
      if (this.props.rateLimit.rate) {
        rate = this.props.rateLimit.rate;
        resetRateFormatted = <FormattedTime value={rate.reset}/>;
      }
      if (this.props.rateLimit.resources) {
        core = this.props.rateLimit.resources.core;
        resetCoreFormatted = <FormattedTime value={core.reset}/>;

        search = this.props.rateLimit.resources.search;
        resetSearchFormatted = <FormattedTime value={search.reset}/>;

        graphql = this.props.rateLimit.resources.graphql;
        resetGraphqlFormatted = <FormattedTime value={graphql.reset}/>;
      }
    }

    return (
        <Collapse defaultActiveKey={['rateLimit']}>
          <Panel header={this.props.title} key="rateLimit">
            <Spin spinning={this.props.loading}>
              <Row>
                <h2>Summarized</h2>
              </Row>
              <Row>
                <Col offset={1}>
                  <b>Limit:</b> {rate && rate.limit}
                </Col>
              </Row>
              <Row>
                <Col offset={1}>
                  <b>Remaining:</b> {rate && rate.remaining}
                </Col>
              </Row>
              <Row>
                <Col offset={1}>
                  <b>Will be reset at:</b> {resetRateFormatted}
                </Col>
              </Row>
              <Row>
                <h2>By resource type</h2>
              </Row>
              <Row type="flex" justify="space-around" align="middle">
                <Col span={6}>
                  <Row>
                    <h3>Core</h3>
                  </Row>
                  <Row>
                    <b>Limit:</b> {core && core.limit}
                  </Row>
                  <Row>
                    <b>Remaining:</b> {core && core.remaining}
                  </Row>
                  <Row>
                    <b>Will be reset at:</b> {resetCoreFormatted}
                  </Row>
                </Col>
                <Col span={6}>
                  <Row>
                    <h3>Search</h3>
                  </Row>
                  <Row>
                    <b>Limit:</b> {search && search.limit}
                  </Row>
                  <Row>
                    <b>Remaining:</b> {search && search.remaining}
                  </Row>
                  <Row>
                    <b>Will be reset at:</b> {resetSearchFormatted}
                  </Row>
                </Col>
                <Col span={6}>
                  <Row>
                    <h3>GraphQL</h3>
                  </Row>
                  <Row>
                    <b>Limit:</b> {core && graphql.limit}
                  </Row>
                  <Row>
                    <b>Remaining:</b> {core && graphql.remaining}
                  </Row>
                  <Row>
                    <b>Will be reset at:</b> {resetGraphqlFormatted}
                  </Row>
                </Col>
              </Row>
            </Spin>
          </Panel>
        </Collapse>
    )
  }
}

RateLimitPanel.propTypes = {
  title: PropTypes.string
};

const mapStateToProps = (state) => {
  return {
    rateLimit: state.rateLimit.data,
    loading: state.rateLimit.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({getRateLimit}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(RateLimitPanel)