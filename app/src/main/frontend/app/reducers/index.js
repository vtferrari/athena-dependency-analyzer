import {combineReducers} from 'redux'
import rateLimitPanelReducer from '../components/RateLimitPanel/redux/reducer';
import projectListReducer from '../components/ProjectsList/redux/reducer';
import descriptorsListReducer from '../components/DescriptorsList/redux/reducer';
import artifactsListReducer from '../components/ArtifactsList/redux/reducer';
import scanButtonReducer from '../components/ScanButton/redux/reducer';

const rootReducer = combineReducers({
  projects: projectListReducer,
  descriptors: descriptorsListReducer,
  artifacts: artifactsListReducer,
  scanButton: scanButtonReducer,
  rateLimit: rateLimitPanelReducer
});

export default rootReducer