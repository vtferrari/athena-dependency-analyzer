import {RECEIVE_RATE_LIMIT, REQUEST_RATE_LIMIT} from './RateLimitActions'

const initialState = {
  data: {},
  loading: false
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_RATE_LIMIT:
      return Object.assign({}, state, {
        loading: true,
      });

    case RECEIVE_RATE_LIMIT:
      return Object.assign({}, state, {
        loading: false,
        data: action.data
      });

    default:
      return state;
  }
}