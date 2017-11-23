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

function requestProjects(pageNumber, pageSize, search) {
  return {
    type: REQUEST_PROJECTS,
    pageNumber: pageNumber,
    pageSize: pageSize,
    search: search
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

export function listProjects(pageNumber, pageSize, search) {
  return function (dispatch) {
    dispatch(requestProjects(pageNumber, pageSize, search));
    let queryString = 'pageNumber=' + pageNumber + '&pageSize=' + pageSize;
    if (search && search.name) {
      queryString += '&name=' + search.name;
    }

    axios.get('/api/v1/projects?' + queryString).then(
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
    project: data,
    receivedAt: Date.now()
  }
}

export function refreshProject(projectId) {
  return function (dispatch) {
    dispatch(requestRefreshProject(projectId));
    axios.post('/api/v1/projects/' + projectId
        + '/refreshNow').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveRefreshProject(response.data)));
  }
}