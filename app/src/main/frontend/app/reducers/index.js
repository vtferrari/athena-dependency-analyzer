import {combineReducers} from 'redux'
import rateLimitPanelReducer from '../components/RateLimitPanel/redux/reducer';
import projectListReducer from '../components/ProjectsList/redux/reducer';
import descriptorsListReducer from '../components/DescriptorsList/redux/reducer';
import artifactsListReducer from '../components/ArtifactsList/redux/reducer';
import fullScanButtonReducer from '../components/FullScanButton/redux/reducer';
import fullAnalyzeButtonReducer from '../components/FullAnalyzeButton/redux/reducer';

const rootReducer = combineReducers({
  projects: projectListReducer,
  descriptors: descriptorsListReducer,
  artifacts: artifactsListReducer,
  fullScanButton: fullScanButtonReducer,
  fullAnalyzeButton: fullAnalyzeButtonReducer,
  rateLimit: rateLimitPanelReducer
});

export default rootReducer