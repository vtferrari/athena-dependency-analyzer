import React from 'react';
import {render} from 'react-dom';
import {store} from './store';
import {Provider} from 'react-redux';
import App from './containers/App';
import registerServiceWorker from './registerServiceWorker';

render(
    <Provider store={store}>
      <App/>
    </Provider>
    , document.getElementById('root'));

registerServiceWorker();
