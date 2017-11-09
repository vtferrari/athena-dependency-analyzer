import {RECEIVE_PROJECTS, REQUEST_PROJECTS} from './actionTypes'
import axios from 'axios'

function requestProjects() {
  return {
    type: REQUEST_PROJECTS
  }
}

function receiveProjects(projects) {
  return {
    type: RECEIVE_PROJECTS,
    projects: projects,
    receivedAt: Date.now()
  }
}

export function listProjects() {
  return function (dispatch) {
    dispatch(requestProjects());
    axios.get('http://localhost:8080/api/v1/projects').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveProjects(response.data)));
  }
}