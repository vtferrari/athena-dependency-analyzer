import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Panel, Table} from 'react-bootstrap';
import {connect} from 'react-redux'
import {listDescriptors, selectDescriptor} from './redux/actions';
import {bindActionCreators} from 'redux'
import './DescriptorsList.css';

export class DescriptorsList extends Component {

  componentWillMount() {
    if (this.props.projectId != undefined) {
      this.props.listDescriptors(this.props.projectId);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.projectId !== nextProps.projectId) {
      this.props.listDescriptors(nextProps.projectId);
    }
  }

  onClickDescriptor(descriptorId) {
    this.props.selectDescriptor(descriptorId);
  }

  render() {
    let rows = [];
    for (let i in this.props.descriptors) {
      let item = this.props.descriptors[i];
      rows.push(<tr
          key={item.id}
          onClick={this.onClickDescriptor.bind(this,
              item.id)}>
        <td>{item.groupId}</td>
        <td>{item.artifactId}</td>
        <td>{item.version}</td>
      </tr>)
    }

    return ( rows && rows.length > 0 &&
        <Panel header={this.props.title && <h2>{this.props.title}</h2>}>
          <Table striped={true} hover={true} className={"descriptors"}>
            <thead>
            <tr>
              <th>Group Id</th>
              <th>Artifact Id</th>
              <th>Version</th>
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

DescriptorsList.propTypes = {
  title: PropTypes.string
}

const mapStateToProps = (state) => {
  return {
    projectId: state.projects.selectedId,
    descriptors: state.descriptors.list,
    loading: state.descriptors.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listDescriptors, selectDescriptor}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(DescriptorsList)