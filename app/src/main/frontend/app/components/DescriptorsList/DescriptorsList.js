import React, {Component} from 'react';
import * as PropTypes from "react/lib/ReactPropTypes";
import {Panel, Table} from 'react-bootstrap';
import {connect} from 'react-redux'
import {listDescriptors, selectDescriptor} from './redux/actions';
import {bindActionCreators} from 'redux'
import './DescriptorsList.css';

export class DescriptorsList extends Component {

  componentWillMount() {
    if (this.props.projectId) {
      this.props.listDescriptors(this.props.projectId);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.projectId && this.props.projectId !== nextProps.projectId) {
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
      rows.push(<tr key={item.id}>
        <td>{item.project.groupId}</td>
        <td>{item.project.artifactId}</td>
        <td>{item.project.version}</td>
        <td className={"actions-buttons"}>
          <a href={"#"} onClick={this.onClickDescriptor.bind(this, item.id)}
             title={"View dependencies"}>
            <span className={"glyphicon glyphicon-zoom-in"}
                  aria-hidden={true}/>
          </a>
        </td>
      </tr>)
    }

    return ( rows && rows.length > 0 &&
        <Panel header={this.props.title && <h2>{this.props.title}</h2>}>
          <Table striped={true} hover={true} className={"descriptors"}>
            <thead>
            <tr>
              <th className={"col-md-4"}>Group Id</th>
              <th className={"col-md-4"}>Artifact Id</th>
              <th className={"col-md-3"}>Version</th>
              <th className={"col-md-1"}/>
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
    descriptors: state.projects.selectedId ? state.descriptors.list : [],
    loading: state.descriptors.loading
  }
};

function mapDispatchToProps(dispatch) {
  return bindActionCreators({listDescriptors, selectDescriptor}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(DescriptorsList)