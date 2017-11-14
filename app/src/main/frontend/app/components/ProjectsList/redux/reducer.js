import {RECEIVE_PROJECTS, REQUEST_PROJECTS, SELECT_PROJECT} from './actionTypes'

const initialState = {
  list: [],
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
        list: action.list,
        totalPages: action.totalPages,
        totalItems: action.totalItems,
        lastUpdated: action.receivedAt
      });
    case SELECT_PROJECT:
      return Object.assign({}, state, {
        loading: false,
        selectedId: action.projectId
      });

    default:
      return state;
  }
}