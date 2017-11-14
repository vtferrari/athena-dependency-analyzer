import {RECEIVE_PROJECTS, REQUEST_PROJECTS} from './actionTypes'
import axios from 'axios'

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
    projects: data.items,
    totalPages: data.totalPages,
    totalItems: data.totalItems,
    receivedAt: Date.now()
  }
}

export function listProjects(pageNumber, pageSize) {
  return function (dispatch) {
    dispatch(requestProjects(pageNumber,pageSize));
    axios.get('http://localhost:8080/api/v1/projects?pageNumber=' + pageNumber + '&pageSize=' + pageSize).then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveProjects(response.data)));
  }
}