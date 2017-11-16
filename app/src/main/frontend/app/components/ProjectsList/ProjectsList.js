import React, {Component} from 'react';
import {FormattedTime} from 'react-intl';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Pagination, Panel, Table} from 'react-bootstrap';
import {connect} from 'react-redux'
import {listProjects, selectProject, refreshProject} from './redux/actions';
import {bindActionCreators} from 'redux'
import './ProjectsList.css';
import githubIcon from './github-icon.png';

export class ProjectsList extends Component {

  componentWillMount() {
    this.props.listProjects(this.props.pageNumber, this.props.pageSize);
  }

  handlePagination(pageNumber) {
    this.props.listProjects(pageNumber - 1, this.props.pageSize);
  }

  refreshProject(projectId) {
    this.props.refreshProject(projectId);
  }

  selectProject(projectId) {
    this.props.selectProject(projectId);
  }

  render() {
    let rows = [];
    for (let i in this.props.projects) {
      let item = this.props.projects[i];
      rows.push(<tr key={item.projectId}>
            <td>{item.name}</td>
            <td>{item.branch}</td>
            <td>
              <FormattedTime
                  value={item.lastCollectDate}
                  day="numeric"
                  month="numeric"
                  year="numeric"/>
            </td>
            <td>
              <a href={item.scmRepository.url} target={"_blank"}>
                <img src={githubIcon} height={16} title={item.scmRepository.url}
                     className={"action-btn"}/>
              </a>
              <a href={"#"}
                 onClick={this.refreshProject.bind(this, item.projectId)}
                 title={"Refresh"}>
                <span className={"glyphicon glyphicon-refresh action-btn"}
                      aria-hidden={true}/>
              </a>
              <a href={"#"}
                 onClick={this.selectProject.bind(this, item.projectId)}
                 title={"View descriptors"}>
                  <span className={"glyphicon glyphicon-zoom-in action-btn"}
                        aria-hidden={true}/>
              </a>
            </td>
          </tr>
      )
    }

    return (
        <Panel header={this.props.title && <h2>{this.props.title}</h2>}>
          <Table striped={true} hover={true} className={"projects"}>
            <thead>
            <tr>
              <th className={"col-md-5"}>Name</th>
              <th className={"col-md-2"}>Branch</th>
              <th className={"col-md-3"}>Last updated</th>
              <th className={"col-md-2"}/>
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
};

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
  return bindActionCreators({listProjects, selectProject, refreshProject}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ProjectsList)