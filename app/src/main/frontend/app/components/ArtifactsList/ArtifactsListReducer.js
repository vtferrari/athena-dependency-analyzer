import {RECEIVE_ARTIFACTS, REQUEST_ARTIFACTS} from './ArtifactsListActions'

const initialState = {
  list: [],
  loading: false
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_ARTIFACTS:
      return Object.assign({}, state, {
        loading: true
      });

    case RECEIVE_ARTIFACTS:
      return Object.assign({}, state, {
        loading: false,
        list: action.list
      });

    default:
      return state;
  }
}