import {
  RECEIVE_PROJECTS,
  RECEIVE_REFRESH_PROJECT,
  REQUEST_PROJECTS,
  REQUEST_REFRESH_PROJECT,
  SELECT_PROJECT
} from './actionTypes'

const initialState = {
  list: [],
  pageNumber: 0,
  pageSize: 15,
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case SELECT_PROJECT:
      return Object.assign({}, state, {
        loading: false,
        selectedId: action.projectId
      });
    case REQUEST_PROJECTS:
      return Object.assign({}, state, {
        loading: true,
        pageNumber: action.pageNumber,
        pageSize: action.pageSize,
        selectedId: null,
      });

    case RECEIVE_PROJECTS:
      return Object.assign({}, state, {
        loading: false,
        list: action.list,
        totalPages: action.totalPages,
        totalItems: action.totalItems,
        lastUpdated: action.receivedAt
      });

    case REQUEST_REFRESH_PROJECT:
      return Object.assign({}, state, {
        loading: true
      });

    case RECEIVE_REFRESH_PROJECT:
      return Object.assign({}, state, {
        loading: false
      });

    default:
      return state;
  }
}