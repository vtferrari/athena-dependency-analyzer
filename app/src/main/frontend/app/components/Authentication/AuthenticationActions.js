import axios from 'axios'
import config from '../../config/config.js'
import jwtDecode from 'jwt-decode'

export const SHOW_MODAL = 'SHOW_MODAL';
export const CLOSE_MODAL = 'CLOSE_MODAL';
export const TRY_AUTHENTICATE_FROM_SESSION = 'TRY_AUTHENTICATE_FROM_SESSION';
export const REQUEST_LOGIN = 'REQUEST_LOGIN';
export const REQUEST_LOGIN_FAILED = 'REQUEST_LOGIN_FAILED';
export const RECEIVE_LOGIN = 'RECEIVE_LOGIN';
export const REFRESHING_TOKEN = 'REFRESHING_TOKEN';
export const SAVE_REFRESH_TOKEN = 'SAVE_REFRESH_TOKEN';
export const DONE_REFRESH_TOKEN = 'DONE_REFRESH_TOKEN';
export const REFRESH_TOKEN_FAILED = 'REFRESH_TOKEN_FAILED';
export const LOGOUT = 'LOGOUT';

export function showModal() {
  return {
    type: SHOW_MODAL
  }
}

export function closeModal() {
  return {
    type: CLOSE_MODAL
  }
}

export function tryAuthenticateFromSession() {
  let sessionAuth = sessionStorage.getItem('auth');
  let auth = null;
  if (sessionAuth) {
    try {
      auth = JSON.parse(sessionAuth);
    } catch (e) {
      console.log(e);
    }
  }
  return {
    type: TRY_AUTHENTICATE_FROM_SESSION,
    logged: auth ? true : false,
    auth: auth
  }
}

function requestLogin() {
  return {
    type: REQUEST_LOGIN
  }
}

function createAuth(data) {
  let accessToken = data.access_token;
  let decodedToken = jwtDecode(accessToken);
  let expiresAt = new Date(Date.now());
  expiresAt.setSeconds(expiresAt.getSeconds() + data.expires_in - 60);

  let auth = {
    accessToken: accessToken,
    refreshToken: data.refresh_token,
    tokenType: data.token_type,
    scope: data.scope,
    expiresIn: data.expires_in,
    expiresAt: expiresAt.toISOString(),
    jti: data.jti,
    authorities: decodedToken.authorities,
    isAdmin: decodedToken.authorities.indexOf('ROLE_ADMIN') !== -1,
    username: decodedToken.user_name
  };
  return auth;
}

function receiveLogin(data) {
  let auth = createAuth(data);
  sessionStorage.setItem('auth', JSON.stringify(auth));

  return {
    type: RECEIVE_LOGIN,
    auth: auth,
    logged: true,
    receivedAt: Date.now()
  }
}

function requestLoginFailed(error) {
  let errorMessage;
  if (error.response && error.response.status === 400) {
    errorMessage = 'Invalid username or password';
  }
  else {
    errorMessage = error.toString();
    console.log('An error occurred.', error);
  }
  return {
    type: REQUEST_LOGIN_FAILED,
    message: errorMessage
  }
}

export function login(username, password) {
  return function (dispatch) {
    dispatch(requestLogin());

    let params = new URLSearchParams();
    params.append('grant_type', 'password');
    params.append('username', username);
    params.append('password', password);

    axios.post('/oauth/token', params, {
      auth: {
        username: config.clientId,
        password: config.clientSecret
      }
    })
    .then(response => dispatch(receiveLogin(response.data)))
    .catch(error => dispatch(requestLoginFailed(error)));
  }
}

const doneRefreshToken = {
  type: DONE_REFRESH_TOKEN
};

function requestRefreshTokenFailed(error) {
  let errorMessage;
  if (error.response && error.response.status === 400) {
    errorMessage = 'Unable to refresh token';
  }
  else {
    errorMessage = error.toString();
    console.log('An error occurred.', error);
  }
  sessionStorage.removeItem('auth');
  return {
    type: REFRESH_TOKEN_FAILED,
    message: errorMessage
  }
}

function saveRefreshToken(auth) {
  sessionStorage.setItem('auth', JSON.stringify(auth));

  return {
    type: SAVE_REFRESH_TOKEN,
    auth: auth,
    logged: true,
    receivedAt: Date.now()
  }
}

function refreshingToken(refreshTokenPromise) {
  return {
    type: REFRESHING_TOKEN,
    refreshTokenPromise
  }
}

export function refreshToken(dispatch, refreshToken) {
  let params = new URLSearchParams();
  params.append('grant_type', 'refresh_token');
  params.append('refresh_token', refreshToken);

  var refreshTokenPromise = axios.post('/oauth/token', params, {
    auth: {
      username: config.clientId,
      password: config.clientSecret
    }
  })
  .then(response => {
    dispatch(doneRefreshToken);

    let auth = createAuth(response.data);
    dispatch(saveRefreshToken(auth));

    return response.data ? Promise.resolve(auth)
        : Promise.reject({message: 'Could not refresh token'})
  })
  .catch(error => {
    dispatch(requestRefreshTokenFailed(error));

    return Promise.reject(error);
  });

  dispatch(refreshingToken(refreshTokenPromise));
  return refreshTokenPromise;
}

export function logout() {
  sessionStorage.removeItem('auth');
  return {
    type: LOGOUT,
    logged: false
  }
}