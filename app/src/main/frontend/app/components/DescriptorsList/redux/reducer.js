import {
  RECEIVE_DESCRIPTORS,
  REQUEST_DESCRIPTORS,
  SELECT_DESCRIPTOR
} from './actionTypes'

const initialState = {
  list: [],
  loading: false
}

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_DESCRIPTORS:
      return Object.assign({}, state, {
        loading: true,
        selectedId: null
      });

    case RECEIVE_DESCRIPTORS:
      return Object.assign({}, state, {
        loading: false,
        list: action.list
      });
    case SELECT_DESCRIPTOR:
      return Object.assign({}, state, {
        loading: false,
        selectedId: action.descriptorId
      });

    default:
      return state;
  }
}