import {RECEIVE_RATE_LIMIT, REQUEST_RATE_LIMIT} from './actionTypes'
import axios from 'axios'

function requestRateLimit() {
  return {
    type: REQUEST_RATE_LIMIT,
  }
}

function receiveRateLimit(data) {
  return {
    type: RECEIVE_RATE_LIMIT,
    data: data,
    receivedAt: Date.now()
  }
}

export function getRateLimit() {
  return function (dispatch) {
    dispatch(requestRateLimit());
    axios.get('/api/v1/scms/rateLimit').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveRateLimit(response.data)));
  }
}