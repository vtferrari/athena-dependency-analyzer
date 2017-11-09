import React, {Component} from 'react';
import Table from 'react-bootstrap/lib/Table';
import {connect} from 'react-redux'
import {listProjects} from './redux/actions';
import {bindActionCreators} from 'redux'

export class ProjectsList extends Component {

  componentWillMount() {
    this.props.listProjects();
  }

  render() {
    var rows = [];
    for (var i in this.props.projects) {
      var item = this.props.projects[i];
      rows.push(<tr key={item.projectId}>
        <td>{item.scmRepository.name}</td>
        <td>{item.branch}</td>
        <td>{item.scmRepository.url}</td>
      </tr>)
    }

    return (
        <Table striped={true} hover={true}>
          <thead>
          <tr>
            <th>Name</th>
            <th>Branch</th>
            <th>SCM URL</th>
          </tr>
          </thead>
          <tbody>
          {rows}
          </tbody>
        </Table>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    projects: state.projectListReducer.projects,
    loading: state.projectListReducer.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listProjects}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ProjectsList)