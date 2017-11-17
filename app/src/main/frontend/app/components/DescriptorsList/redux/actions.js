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
  return {
    type: RECEIVE_DESCRIPTORS,
    list: data,
    receivedAt: Date.now()
  }
}

export function listDescriptors(projectId) {
  return function (dispatch) {
    dispatch(requestDescriptors(projectId));
    axios.get('/api/v1/projects/' + projectId
        + '/descriptors').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveDescriptors(response.data)));
  }
}