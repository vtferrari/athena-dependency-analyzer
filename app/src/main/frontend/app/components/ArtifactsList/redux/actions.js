import {RECEIVE_ARTIFACTS, REQUEST_ARTIFACTS,} from './actionTypes'
import axios from 'axios'

function requestArtifacts(projectId) {
  return {
    type: REQUEST_ARTIFACTS,
    projectId: projectId
  }
}

function receiveArtifacts(data) {
  return {
    type: RECEIVE_ARTIFACTS,
    list: data.artifacts,
    project: data.project
  }
}

export function listArtifacts(projectId) {
  return function (dispatch) {
    dispatch(requestArtifacts(projectId));
    axios.get('http://localhost:8080/api/v1/projects/' + projectId
        + '/descriptors').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveArtifacts(response.data[0])));
  }
}