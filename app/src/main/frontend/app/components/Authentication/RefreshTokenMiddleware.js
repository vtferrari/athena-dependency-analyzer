import {refreshToken} from "./AuthenticationActions";

function isExpired(expiresAt) {
  return Date.now() > expiresAt;
}

export default function jwt({dispatch, getState}) {
  return (next) => (action) => {
    let async = typeof action === 'function';
    if (async) {
      let state = getState();
      if (state.auth && state.auth.auth) {
        let auth = state.auth.auth;
        if (isExpired(Date.parse(auth.expiresAt))) {
          if (!auth.refreshTokenPromise) {
            return refreshToken(dispatch, auth.refreshToken).then(() => next(action));
          } else {
            return auth.refreshTokenPromise.then(() => next(action));
          }
        }
      }
    }
    return next(action);
  };
}