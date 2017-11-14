import {
  RECEIVE_DESCRIPTORS,
  REQUEST_DESCRIPTORS,
  SELECT_DESCRIPTOR
} from './actionTypes'
import axios from 'axios'

export function selectDescriptor(descriptorId) {
  return {
    type: SELECT_DESCRIPTOR,
    descriptorId: descriptorId
  }
}

function requestDescriptors(projectId) {
  return {
    type: REQUEST_DESCRIPTORS,
    projectId: projectId
  }
}

function receiveDescriptors(data) {
  let descriptors = data.map(d => d.project);
  return {
    type: RECEIVE_DESCRIPTORS,
    list: descriptors,
    receivedAt: Date.now()
  }
}

export function listDescriptors(projectId) {
  return function (dispatch) {
    dispatch(requestDescriptors(projectId));
    axios.get('http://localhost:8080/api/v1/projects/' + projectId
        + '/descriptors').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveDescriptors(response.data)));
  }
}