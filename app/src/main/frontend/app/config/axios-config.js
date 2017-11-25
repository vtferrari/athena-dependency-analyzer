import axios from 'axios';

function getBaseURL() {
  if (!sessionStorage.getItem("athena.baseURL")) {
    let baseUrl = window.location.origin + "/"
    sessionStorage.setItem("athena.baseURL", baseUrl);
    console.log("Axios BaseURL configured as",
        sessionStorage.getItem("athena.baseURL"));
  }
  return sessionStorage.getItem("athena.baseURL")
}

export default function configureAxios() {
  axios.defaults.baseURL = getBaseURL();
}

