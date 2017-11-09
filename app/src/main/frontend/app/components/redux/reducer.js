import {RECEIVE_PROJECTS, REQUEST_PROJECTS} from './actionTypes'

const initialState = {
  projects: [],
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_PROJECTS:
      return Object.assign({}, state, {
        loading: true
      });

    case RECEIVE_PROJECTS:
      return Object.assign({}, state, {
        loading: false,
        projects: action.projects,
        lastUpdated: action.receivedAt
      });

    default:
      return state;
  }
}