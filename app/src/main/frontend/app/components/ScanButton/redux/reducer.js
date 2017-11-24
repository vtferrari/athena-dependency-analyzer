import {RECEIVE_REFRESH_ALL, REQUEST_REFRESH_ALL} from './actionTypes'

const initialState = {
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_REFRESH_ALL:
      return Object.assign({}, state, {
        loading: true
      });

    case RECEIVE_REFRESH_ALL:
      return Object.assign({}, state, {
        loading: false
      });

    default:
      return state;
  }
}