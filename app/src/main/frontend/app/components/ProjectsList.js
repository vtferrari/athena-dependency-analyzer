import React, {Component} from 'react';
import {Pagination, Table} from 'react-bootstrap';
import {connect} from 'react-redux'
import {listProjects} from './redux/actions';
import {bindActionCreators} from 'redux'

export class ProjectsList extends Component {

  componentWillMount() {
    this.props.listProjects(this.props.pageNumber, this.props.pageSize);
  }

  handlePagination(pageNumber) {
    this.props.listProjects(pageNumber - 1, this.props.pageSize);
  }

  render() {
    var rows = [];
    for (var i in this.props.projects) {
      var item = this.props.projects[i];
      rows.push(<tr key={item.projectId}>
        <td>{item.name}</td>
        <td>{item.branch}</td>
        <td>{item.scmRepository.url}</td>
      </tr>)
    }

    return (
        <div>
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
          <div className={"text-center"}>
            <Pagination activePage={this.props.pageNumber + 1}
                        items={this.props.totalPages} maxButtons={5}
                        first={true}
                        prev={true}
                        next={true}
                        last={true}
                        onSelect={this.handlePagination.bind(this)}/>
          </div>
        </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    projects: state.projectListReducer.projects,
    pageSize: state.projectListReducer.pageSize,
    pageNumber: state.projectListReducer.pageNumber,
    totalPages: state.projectListReducer.totalPages,
    loading: state.projectListReducer.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listProjects}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ProjectsList)