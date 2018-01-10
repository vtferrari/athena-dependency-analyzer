import {
  RECEIVE_FULL_ANALYZE, REQUEST_FULL_ANALYZE,
  REQUEST_FULL_ANALYZE_FAILED
} from "./FullAnalyzeButtonActions";

const initialState = {
  loading: false,
  error: false,
  errorMessage: null
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_FULL_ANALYZE:
      return Object.assign({}, state, {
        loading: true,
        error: false,
        errorMessage: null
      });

    case RECEIVE_FULL_ANALYZE:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null
      });

    case REQUEST_FULL_ANALYZE_FAILED:
      return Object.assign({}, state, {
        loading: false,
        error: true,
        errorMessage: action.message
      });

    default:
      return state;
  }
}