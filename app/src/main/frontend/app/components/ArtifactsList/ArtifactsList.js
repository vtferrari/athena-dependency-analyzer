import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Col, Row, Table} from 'react-bootstrap';
import {listArtifacts} from "./redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";

export class ArtifactsList extends Component {

  componentWillMount() {
    if (this.props.projectId != null) {
      this.props.listArtifacts(this.props.projectId);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.projectId !== nextProps.projectId) {
      this.props.listArtifacts(nextProps.projectId);
    }
  }
  render() {


    let rows = [];
    for (let i in this.props.artifacts) {
      let item = this.props.artifacts[i];
      rows.push(<tr key={i}>
        <td>{item.groupId}</td>
        <td>{item.artifactId}</td>
        <td>{item.version}</td>
        <td>{item.origin}</td>
      </tr>)
    }

    let tableHeader;
    if (this.props.title != null) {
      tableHeader = <h2>{this.props.title}</h2>
    }

    return (
        <div>
          {tableHeader}
          <Table striped={true} hover={true} className={"artifacts"}>
            <thead>
            <tr>
              <th>Group Id</th>
              <th>Artifact Id</th>
              <th>Version</th>
              <th>Origin</th>
            </tr>
            </thead>
            <tbody>
            {rows}
            </tbody>
          </Table>
        </div>
    );
  }
}

ArtifactsList.propTypes = {
  title: PropTypes.string
}

const mapStateToProps = (state) => {
  return {
    projectId: state.projects.selectedId,
    project: state.artifacts.project,
    artifacts: state.artifacts.list,
    loading: state.artifacts.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listArtifacts: listArtifacts}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ArtifactsList)