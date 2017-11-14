import {RECEIVE_PROJECTS, REQUEST_PROJECTS, SELECT_PROJECT} from './actionTypes'
import axios from 'axios'

export function selectProject(projectId) {
  return {
    type: SELECT_PROJECT,
    projectId: projectId
  }
}

function requestProjects(pageNumber, pageSize) {
  return {
    type: REQUEST_PROJECTS,
    pageNumber: pageNumber,
    pageSize: pageSize
  }
}

function receiveProjects(data) {
  return {
    type: RECEIVE_PROJECTS,
    list: data.items,
    totalPages: data.totalPages,
    totalItems: data.totalItems,
    receivedAt: Date.now()
  }
}

export function listProjects(pageNumber, pageSize) {
  return function (dispatch) {
    dispatch(requestProjects(pageNumber, pageSize));
    axios.get('http://localhost:8080/api/v1/projects?pageNumber=' + pageNumber
        + '&pageSize=' + pageSize).then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveProjects(response.data)));
  }
}