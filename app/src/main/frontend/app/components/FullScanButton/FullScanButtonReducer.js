import {RECEIVE_SCAN, REQUEST_SCAN, REQUEST_SCAN_FAILED} from './FullScanButtonActions'

const initialState = {
  loading: false,
  error: false,
  projects: [],
  errorMessage: null
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_SCAN:
      return Object.assign({}, state, {
        loading: true,
        error: false,
        errorMessage: null
      });

    case RECEIVE_SCAN:
      return Object.assign({}, state, {
        loading: false,
        projects: action.projects,
        error: false,
        errorMessage: null
      });

    case REQUEST_SCAN_FAILED:
      return Object.assign({}, state, {
        loading: false,
        error: true,
        projects: [],
        errorMessage: action.message
      });

    default:
      return state;
  }
}