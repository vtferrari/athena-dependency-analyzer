import {applyMiddleware, createStore} from 'redux';
import {createLogger} from 'redux-logger';
import thunkMiddleware from 'redux-thunk';
import jwt from './components/Authentication/RefreshTokenMiddleware';
import rootReducer from './reducers';

const loggerMiddleware = createLogger();

export const store = createStore(rootReducer,
    applyMiddleware(jwt, thunkMiddleware, loggerMiddleware));