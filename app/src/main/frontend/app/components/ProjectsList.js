import React, {Component} from 'react';
import Table from 'react-bootstrap/lib/Table';
import {listProjects} from './ProjectsListReducer';
import {store} from '../store';
import '../../style/App.css';

export default class ProjectsList extends Component {

  constructor(props) {
    super(props);

    store.subscribe(() => {
      this.setState({
        items: store.getState().projectListReducer.projects
      });
    });
  }

  componentWillMount() {
    store.dispatch(listProjects);
  }

  render() {
    var rows = [];
    for (var i in this.state.items) {
      var item = this.state.items[i];
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
            <th>SCM Url</th>
          </tr>
          </thead>
          <tbody>
          {rows}
          </tbody>
        </Table>
    );
  }
}
