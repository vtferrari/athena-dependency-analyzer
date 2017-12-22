import axios from 'axios';
import {store} from '../store';

var authorizationHeader;

function getBaseURL() {
  if (!sessionStorage.getItem("athena.baseURL")) {
    let baseUrl = window.location.origin + "/";
    sessionStorage.setItem("athena.baseURL", baseUrl);
    console.log("Axios BaseURL configured as",
        sessionStorage.getItem("athena.baseURL"));
  }
  return sessionStorage.getItem("athena.baseURL")
}

export default function configureAxios() {
  axios.defaults.baseURL = getBaseURL();
  axios.interceptors.request.use(config => {
    if (authorizationHeader) {
      config.headers['Authorization'] = authorizationHeader;
    }
    return config;
  })
}

store.subscribe(() => {
  let state = store.getState();
  if (state.auth && state.auth.auth) {
    let auth = state.auth.auth;
    authorizationHeader = auth.tokenType + ' ' + auth.accessToken;
  }
  else {
    authorizationHeader = undefined;
  }
});