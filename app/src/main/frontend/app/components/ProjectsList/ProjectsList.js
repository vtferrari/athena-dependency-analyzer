import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Pagination, Panel, Table} from 'react-bootstrap';
import {connect} from 'react-redux'
import {listProjects, selectProject} from './redux/actions';
import {bindActionCreators} from 'redux'
import './ProjectsList.css';

export class ProjectsList extends Component {

  componentWillMount() {
    this.props.listProjects(this.props.pageNumber, this.props.pageSize);
  }

  handlePagination(pageNumber) {
    this.props.listProjects(pageNumber - 1, this.props.pageSize);
  }

  onClickProject(projectId) {
    this.props.selectProject(projectId);
  }

  render() {
    let rows = [];
    for (let i in this.props.projects) {
      let item = this.props.projects[i];
      rows.push(<tr key={item.projectId}
                    onClick={this.onClickProject.bind(this, item.projectId)}>
        <td>{item.name}</td>
        <td>{item.branch}</td>
        <td>{item.scmRepository.url}</td>
      </tr>)
    }

    return (
        <Panel header={this.props.title && <h2>{this.props.title}</h2>}>
          <Table striped={true} hover={true} className={"projects"}>
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
        </Panel>
    );
  }
}

ProjectsList.propTypes = {
  title: PropTypes.string
}

const mapStateToProps = (state) => {
  return {
    projects: state.projects.list,
    pageSize: state.projects.pageSize,
    pageNumber: state.projects.pageNumber,
    totalPages: state.projects.totalPages,
    loading: state.projects.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listProjects, selectProject}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ProjectsList)