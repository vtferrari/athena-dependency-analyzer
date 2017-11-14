import {RECEIVE_ARTIFACTS, REQUEST_ARTIFACTS} from './actionTypes'

const initialState = {
  list: [],
  project: null,
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_ARTIFACTS:
      return Object.assign({}, state, {
        loading: true
      });

    case RECEIVE_ARTIFACTS:
      return Object.assign({}, state, {
        loading: false,
        project: action.project,
        list: action.list
      });

    default:
      return state;
  }
}