import {RECEIVE_SCAN, REQUEST_SCAN, REQUEST_SCAN_FAILED} from './actionTypes'
import axios from 'axios'

function requestFullScan() {
  return {
    type: REQUEST_SCAN,
  }
}

function receiveFullScan(data) {
  return {
    type: RECEIVE_SCAN,
    projects: data,
    receivedAt: Date.now()
  }
}

function requestFullScanFailed(error) {
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
    type: REQUEST_SCAN_FAILED,
    message: errorMessage
  }
}

export function fullScan() {
  return function (dispatch) {
    dispatch(requestFullScan());
    axios.post('/api/v1/projects/scan')
    .then(response => dispatch(receiveFullScan(response.data)))
    .catch(error => dispatch(requestFullScanFailed(error)));
  }
}