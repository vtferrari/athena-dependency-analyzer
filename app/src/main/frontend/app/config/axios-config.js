import axios from 'axios';

export default function configureAxios() {
  axios.defaults.baseURL = process.env.URL;
}