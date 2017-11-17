import axios from 'axios';

function getBaseURL(){
  if(!sessionStorage.getItem("athena.baseURL")){
    sessionStorage.setItem("athena.baseURL", window.location.href);
    console.log("Axios BaseURL configured as", sessionStorage.getItem("athena.baseURL"));
  }
  return sessionStorage.getItem("athena.baseURL")
}

export default function configureAxios() {
  axios.defaults.baseURL = getBaseURL();
}

