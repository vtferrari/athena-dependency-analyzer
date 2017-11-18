import React from 'react';
import {addLocaleData, IntlProvider} from 'react-intl';
import {render} from 'react-dom';
import {store} from './store';
import {Provider} from 'react-redux';
import App from './containers/App';
import configureAxios from './config/axios-config'
import locale_en_US from 'react-intl/locale-data/en';
import locale_pt_BR from 'react-intl/locale-data/pt';
import registerServiceWorker from './registerServiceWorker';
import {LocaleProvider} from 'antd';
import enUS from 'antd/lib/locale-provider/en_US';

configureAxios();
addLocaleData([...locale_en_US, ...locale_pt_BR]);

render(
    <LocaleProvider locale={enUS}>
      <IntlProvider locale={navigator.language}>
        <Provider store={store}>
          <App/>
        </Provider>
      </IntlProvider>
    </LocaleProvider>
    , document.getElementById('root'));

registerServiceWorker();
