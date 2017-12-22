import {
  CLOSE_MODAL,
  LOGOUT,
  RECEIVE_LOGIN,
  REFRESH_TOKEN_FAILED,
  REQUEST_LOGIN,
  REQUEST_LOGIN_FAILED,
  SAVE_REFRESH_TOKEN,
  SHOW_MODAL,
  TRY_AUTHENTICATE_FROM_SESSION
} from './AuthenticationActions'

const initialState = {
  loading: false,
  error: false,
  logged: false,
  modalVisible: false,
  errorMessage: null,
  auth: null
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case SHOW_MODAL:
      return Object.assign({}, state, {
        modalVisible: true
      });

    case CLOSE_MODAL:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null,
        modalVisible: false
      });

    case TRY_AUTHENTICATE_FROM_SESSION:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null,
        auth: action.auth,
        logged: action.logged
      });

    case LOGOUT:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null,
        auth: null,
        modalVisible: false,
        logged: action.logged
      });

    case REQUEST_LOGIN:
      return Object.assign({}, state, {
        loading: true,
        error: false,
        errorMessage: null,
        auth: null
      });

    case RECEIVE_LOGIN:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null,
        logged: action.logged,
        auth: action.auth
      });

    case SAVE_REFRESH_TOKEN:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        errorMessage: null,
        logged: action.logged,
        auth: action.auth
      });

    case REFRESH_TOKEN_FAILED:
      return Object.assign({}, state, {
        loading: false,
        error: false,
        logged: false,
        errorMessage: null,
        auth: null
      });

    case REQUEST_LOGIN_FAILED:
      return Object.assign({}, state, {
        loading: false,
        error: true,
        logged: false,
        errorMessage: action.message
      });

    default:
      return state;
  }
}