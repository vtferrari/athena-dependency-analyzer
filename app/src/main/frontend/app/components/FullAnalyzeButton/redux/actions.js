import {
  RECEIVE_FULL_ANALYZE,
  REQUEST_FULL_ANALYZE,
  REQUEST_FULL_ANALYZE_FAILED
} from "./actionTypes";
import axios from 'axios'

function requestFullAnalyze() {
  return {
    type: REQUEST_FULL_ANALYZE,
  }
}

function receiveFullAnalyze() {
  return {
    type: RECEIVE_FULL_ANALYZE,
    receivedAt: Date.now()
  }
}

function requestFullAnalyzeFailed(error) {
  let errorMessage;
  if (error.response && error.response.data && error.response.data.message) {
    errorMessage = error.response.data.message;
    console.log('An error occurred.', errorMessage);
  }
  else {
    errorMessage = error.toString();
    console.log('An error occurred.', error);
  }
  return {
    type: REQUEST_FULL_ANALYZE_FAILED,
    message: errorMessage
  }
}

export function fullAnalyze() {
  return function (dispatch) {
    dispatch(requestFullAnalyze());
    axios.post('/api/v1/projects/dependencies/analyze')
    .then(response => dispatch(receiveFullAnalyze()))
    .catch(error => dispatch(requestFullAnalyzeFailed(error)));
  }
}