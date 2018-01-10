import axios from 'axios'

export const REQUEST_DESCRIPTORS = 'REQUEST_DESCRIPTORS';
export const RECEIVE_DESCRIPTORS = 'RECEIVE_DESCRIPTOR';
export const SELECT_DESCRIPTOR = 'SELECT_DESCRIPTOR';

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