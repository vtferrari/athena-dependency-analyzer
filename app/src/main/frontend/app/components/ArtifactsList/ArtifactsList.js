import React, {Component} from 'react';
import PropTypes from "prop-types";
import {listArtifacts} from "./ArtifactsListActions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {Card, Icon, Table} from 'antd';
import UnstableVersionIndicator from "../UnstableVersionIndicator/UnstableVersionIndicator";

const Column = Table.Column;

export class ArtifactsList extends Component {

  componentWillMount() {
    if (this.props.descriptorId && this.props.projectId) {
      this.props.listArtifacts(this.props.projectId, this.props.descriptorId);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.descriptorId && nextProps.projectId
        && ((this.props.descriptorId !== nextProps.descriptorId)
            || (this.props.projectId !== nextProps.projectId))) {
      this.props.listArtifacts(nextProps.projectId, nextProps.descriptorId);
    }
  }

  render() {
    let groupIds = this.props.artifacts.map(
        artifact => artifact.groupId).filter(
        (value, index, self) => self.indexOf(value) === index);

    let groupIdColumnFilter = groupIds.map(groupId => {
      return {
        text: groupId,
        value: groupId
      }
    });

    return (this.props.artifacts && this.props.artifacts.length > 0 &&
        <Card title={this.props.title}>
          <Table dataSource={this.props.artifacts}
                 rowKey={record => record.id}
                 loading={this.props.loading} className={'artifacts'}
                 size="small">
            <Column
                title="Group Id"
                dataIndex="groupId"
                key="groupId"
                width="40%"
                filters={groupIdColumnFilter}
                onFilter={(value, record) => record.groupId.indexOf(value)
                    === 0}
            />
            <Column
                title="Artifact Id"
                dataIndex="artifactId"
                key="artifactId"
                width="30%"
            />
            <Column
                title="Version"
                dataIndex="version"
                key="version"
                width="20%"
                render={(text, record) => {
                  let result = [];
                  if (record.report && !record.report.stable) {
                    result.push(<UnstableVersionIndicator key={record.id}
                                                          artifact={record}
                                                          version={text}>{text}</UnstableVersionIndicator>);
                  }
                  else {
                    result.push(text);
                  }
                  return result;
                }}
            />
            <Column
                title="Origin"
                dataIndex="origin"
                key="origin"
                width="10%"
                render={(text, record) => {
                  let icon = 'glyphicon-question-sign';
                  switch (record.origin) {
                    case 'PARENT':
                      icon = 'arrow-up';
                      break;
                    case 'DEPENDENCIES_MANAGEMENT':
                      icon = 'tool';
                      break;
                    default:
                      icon = 'arrow-down';
                      break;
                  }
                  return (<Icon type={icon} className={'action-btn'}
                                title={record.origin}/>
                  );
                }}
            />
          </Table>
        </Card>
    )
  }
}

ArtifactsList.propTypes = {
  title: PropTypes.string
};

const mapStateToProps = (state) => {
  return {
    projectId: state.projects.selectedId,
    descriptorId: state.descriptors.selectedId,
    artifacts: state.projects.selectedId && state.descriptors.selectedId
        ? state.artifacts.list : [],
    loading: state.artifacts.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listArtifacts: listArtifacts}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ArtifactsList)