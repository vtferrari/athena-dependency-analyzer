import axios from 'axios';
import {store} from '../store';

const initialState = {
  projects: [],
  loading: false
}

export const GET_DATA = 'GET_DATA';
export const SHOW_PROJECTS = 'SHOW_PROJECTS';
export const listProjects = ({
  type: GET_DATA,
  url: 'http://localhost:8080/api/v1/projects'
});

function showProjectsList(projects) {
  return {
    type: SHOW_PROJECTS,
    projects: projects
  }
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case GET_DATA:
      axios.get(action.url).then(
          response => store.dispatch(showProjectsList(response.data)));

      return {
        projects: [],
        loading: true
      };
    case SHOW_PROJECTS:
      return {
        projects: action.projects,
        loading: false
      };

    default:
      return state;
  }
}