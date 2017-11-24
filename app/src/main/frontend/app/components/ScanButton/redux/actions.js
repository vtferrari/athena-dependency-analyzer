import {RECEIVE_SCAN, REQUEST_SCAN} from './actionTypes'
import axios from 'axios'

function requestFullScan() {
  return {
    type: REQUEST_SCAN
  }
}

function receiveFullScan(data) {
  return {
    type: RECEIVE_SCAN,
    receivedAt: Date.now()
  }
}

export function fullScan() {
  return function (dispatch) {
    dispatch(requestFullScan());
    axios.post('/api/v1/projects/scan').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveFullScan(response.data)));
  }
}