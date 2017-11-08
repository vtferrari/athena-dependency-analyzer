import React, {Component} from 'react';
import Table from 'react-bootstrap/lib/Table';
import '../style/App.css';

class ProjectsList extends Component {
  render() {
    return (
      <Table striped={true} hover={true}>
        <thead>
          <tr>
            <td>Name</td>
            <th>Branch</th>
            <th>SCM Url</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>2</td>
            <td>3</td>
          </tr>
          <tr>
            <td>4</td>
            <td>5</td>
            <td>6</td>
          </tr>
          <tr>
            <td>7</td>
            <td>8</td>
            <td>9</td>
          </tr>
        </tbody>
      </Table>
    );
  }
}

export default ProjectsList;
