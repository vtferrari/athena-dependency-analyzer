import {GO_TO_PAGE} from './actionTypes'

const initialState = {
  actualPage: "home"
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case GO_TO_PAGE:
      return Object.assign({}, state, {
        actualPage: action.page
      });

    default:
      return state;
  }
}