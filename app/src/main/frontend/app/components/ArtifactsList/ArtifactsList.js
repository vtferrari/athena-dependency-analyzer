import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Panel, Table} from 'react-bootstrap';
import {listArtifacts} from "./redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";

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
    let rows = [];
    for (let i in this.props.artifacts) {
      let item = this.props.artifacts[i];
      let icon = 'glyphicon-question-sign';
      switch (item.origin) {
        case 'PARENT':
          icon = 'glyphicon-arrow-up';
          break;
        case 'DEPENDENCIES_MANAGEMENT':
          icon = 'glyphicon-wrench';
          break;
        default:
          icon = 'glyphicon-arrow-down';
          break;
      }

      rows.push(<tr key={i}>
        <td>{item.groupId}</td>
        <td>{item.artifactId}</td>
        <td>{item.version}</td>
        <td><span className={'glyphicon ' + icon}
                  aria-hidden={true} title={item.origin}/>
        </td>
      </tr>)
    }

    return ( rows && rows.length > 0 &&
        <Panel header={<h2>Dependencies</h2>}>
          <Table striped={true} hover={true} className={'artifacts'}>
            <thead>
            <tr>
              <th className={"col-md-4"}>Group Id</th>
              <th className={"col-md-4"}>Artifact Id</th>
              <th className={"col-md-3"}>Version</th>
              <th className={"col-md-1"}>Origin</th>
            </tr>
            </thead>
            <tbody>
            {rows}
            </tbody>
          </Table>
        </Panel>
    );
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