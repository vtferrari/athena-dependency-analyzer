import {combineReducers} from 'redux'
import rateLimitPanelReducer from '../components/RateLimitPanel/redux/reducer';
import menuReducer from '../components/MainMenu/redux/reducer';
import projectListReducer from '../components/ProjectsList/redux/reducer';
import descriptorsListReducer from '../components/DescriptorsList/redux/reducer';
import artifactsListReducer from '../components/ArtifactsList/redux/reducer';
import refreshAllButtonReducer from '../components/ScanButton/redux/reducer';

const rootReducer = combineReducers({
  menu: menuReducer,
  projects: projectListReducer,
  descriptors: descriptorsListReducer,
  artifacts: artifactsListReducer,
  refreshAllButton: refreshAllButtonReducer,
  rateLimit: rateLimitPanelReducer
});

export default rootReducer