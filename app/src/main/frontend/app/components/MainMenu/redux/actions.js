import {GO_TO_PAGE} from './actionTypes'

function goTo(page) {
  return {
    type: GO_TO_PAGE,
    page: page
  }
}

export function goToPage(page) {
  return function (dispatch) {
    dispatch(goTo(page));
  }
}