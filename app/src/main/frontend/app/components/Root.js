import React from 'react';
import PropTypes from "react/lib/ReactPropTypes";
import {addLocaleData, IntlProvider} from 'react-intl';
import {Provider} from 'react-redux';
import { BrowserRouter as Router, Route } from 'react-router-dom'
import App from '../containers/App';
import configureAxios from '../config/axios-config'
import locale_en_US from 'react-intl/locale-data/en';
import locale_pt_BR from 'react-intl/locale-data/pt';
import registerServiceWorker from '../registerServiceWorker';
import {LocaleProvider} from 'antd';
import enUS from 'antd/lib/locale-provider/en_US';

configureAxios();
addLocaleData([...locale_en_US, ...locale_pt_BR]);

const Root = ({store}) => (
    <LocaleProvider locale={enUS}>
      <IntlProvider locale={navigator.language}>
        <Provider store={store}>
          <Router>
            <Route path="/" component={App} />
          </Router>
        </Provider>
      </IntlProvider>
    </LocaleProvider>
);

Root.propTypes = {
  store: PropTypes.object.isRequired
};

registerServiceWorker();

export default Root;