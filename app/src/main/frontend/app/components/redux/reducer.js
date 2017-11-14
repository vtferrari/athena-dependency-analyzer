import {RECEIVE_PROJECTS, REQUEST_PROJECTS} from './actionTypes'

const initialState = {
  projects: [],
  pageNumber: 0,
  pageSize: 15,
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_PROJECTS:
      return Object.assign({}, state, {
        pageNumber: action.pageNumber,
        pageSize: action.pageSize,
        loading: true
      });

    case RECEIVE_PROJECTS:
      return Object.assign({}, state, {
        loading: false,
        projects: action.projects,
        totalPages: action.totalPages,
        totalItems: action.totalItems,
        lastUpdated: action.receivedAt
      });

    default:
      return state;
  }
}