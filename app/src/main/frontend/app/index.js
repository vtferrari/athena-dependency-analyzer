import React from 'react';
import {addLocaleData, IntlProvider} from 'react-intl';
import {render} from 'react-dom';
import {store} from './store';
import {Provider} from 'react-redux';
import App from './containers/App';
import locale_en_US from 'react-intl/locale-data/en';
import locale_pt_BR from 'react-intl/locale-data/pt';
import registerServiceWorker from './registerServiceWorker';

addLocaleData([...locale_en_US, ...locale_pt_BR]);

render(
    <IntlProvider locale={navigator.language}>
      <Provider store={store}>
        <App/>
      </Provider>
    </IntlProvider>
    , document.getElementById('root'));

registerServiceWorker();
