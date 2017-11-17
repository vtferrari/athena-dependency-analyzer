import {
  RECEIVE_PROJECTS,
  RECEIVE_REFRESH_PROJECT,
  REQUEST_PROJECTS,
  REQUEST_REFRESH_PROJECT,
  SELECT_PROJECT
} from './actionTypes'
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
    axios.get('/api/v1/projects?pageNumber=' + pageNumber
        + '&pageSize=' + pageSize).then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveProjects(response.data)));
  }
}

function requestRefreshProject(projectId) {
  return {
    type: REQUEST_REFRESH_PROJECT,
    projectId: projectId
  }
}

function receiveRefreshProject(data) {
  return {
    type: RECEIVE_REFRESH_PROJECT,
    projectId: data.id,
    url: data.url,
    branch: data.branch,
    receivedAt: Date.now()
  }
}

export function refreshProject(projectId) {
  return function (dispatch) {
    dispatch(requestRefreshProject(projectId));
    axios.post('/api/v1/projects/' + projectId
        + '/refresh').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveRefreshProject(response.data)));
  }
}