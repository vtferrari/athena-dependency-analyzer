import {
  RECEIVE_PROJECTS,
  RECEIVE_REFRESH_PROJECT,
  REQUEST_PROJECTS,
  REQUEST_REFRESH_PROJECT,
  REQUEST_REFRESH_PROJECT_FAILED,
  SELECT_PROJECT
} from './actionTypes'

const initialState = {
  list: [],
  pageNumber: 0,
  pageSize: 10,
  search: {},
  loading: false,
  refreshLoading: false,
  refreshError: false,
  refreshErrorMessage: null
};

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
        search: action.search
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
        loading: true,
        refreshLoading: true,
        refreshError: false,
        refreshErrorMessage: null
      });

    case RECEIVE_REFRESH_PROJECT:
      let refreshedProject = state.list.find(
          p => p.projectId === action.project.projectId);

      Object.assign(refreshedProject, action.project);

      return Object.assign({}, state, {
        loading: false,
        refreshLoading: false,
        selectedId: null
      });

    case REQUEST_REFRESH_PROJECT_FAILED:
      return Object.assign({}, state, {
        loading: false,
        refreshLoading: false,
        refreshError: true,
        refreshErrorMessage: action.message
      });

    default:
      return state;
  }
}