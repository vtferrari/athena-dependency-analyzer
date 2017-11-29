import React, {Component} from 'react';
import {connect} from 'react-redux';
import * as PropTypes from "react/lib/ReactPropTypes";
import {listDescriptors, selectDescriptor} from './redux/actions';
import {bindActionCreators} from 'redux'
import {Collapse, Icon, Table} from 'antd';
import './DescriptorsList.css';
import UnstableVersionIndicator from "../UnstableVersionIndicator/UnstableVersionIndicator";

const Panel = Collapse.Panel;
const Column = Table.Column;

export class DescriptorsList extends Component {

  componentWillMount() {
    if (this.props.projectId) {
      this.props.listDescriptors(this.props.projectId);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.projectId && this.props.projectId !== nextProps.projectId) {
      this.props.listDescriptors(nextProps.projectId);
    }
  }

  onClickDescriptor(descriptorId) {
    this.props.selectDescriptor(descriptorId);
  }

  render() {
    return ( this.props.descriptors && this.props.descriptors.length > 0 &&
        <Collapse defaultActiveKey={['descriptors']}>
          <Panel header={this.props.title} key="descriptors">
            <Table dataSource={this.props.descriptors}
                   rowKey={record => record.id}
                   loading={this.props.loading} className={'descriptors'}>
              <Column
                  title="Group Id"
                  dataIndex="project.groupId"
                  key="project.groupId"
                  width="40%"
              />
              <Column
                  title="Artifact Id"
                  dataIndex="project.artifactId"
                  key="project.artifactId"
                  width="30%"
                  render={(text, record) => {
                    let result = [];
                    result.push(text);
                    result.push(" ");
                    if (record.unstableArtifacts
                        && record.unstableArtifacts.length > 0) {
                      result.push(<UnstableVersionIndicator
                          items={record.unstableArtifacts}
                          key={"u-" + record.id}/>);
                    }
                    return (<span>{result}</span>);
                  }}
              />
              <Column
                  title="Version"
                  dataIndex="project.version"
                  key="project.version"
                  width="20%"/>
              <Column
                  title="Actions"
                  key="action"
                  width="10%"
                  render={(text, record) => (
                      <span>
                        <a href={"#"}
                           onClick={this.onClickDescriptor.bind(
                               this, record.id)}
                           title={"View dependencies"}>
                          <Icon type="plus-circle-o" className={'action-btn'}/>
                        </a>
                      </span>
                  )}/>
            </Table>
          </Panel>
        </Collapse>
    )
  }
}

DescriptorsList.propTypes = {
  title: PropTypes.string
}

const mapStateToProps = (state) => {
  return {
    projectId: state.projects.selectedId,
    descriptors: state.projects.selectedId ? state.descriptors.list : [],
    loading: state.descriptors.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listDescriptors, selectDescriptor}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(DescriptorsList)