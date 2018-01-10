import {combineReducers} from 'redux'
import authenticationReducer from '../components/Authentication/AuthenticationReducer';
import rateLimitPanelReducer from '../components/RateLimitPanel/RateLimitReducer';
import projectListReducer from '../components/ProjectsList/ProjectsListReducer';
import descriptorsListReducer from '../components/DescriptorsList/DescriptorsListReducer';
import artifactsListReducer from '../components/ArtifactsList/ArtifactsListReducer';
import fullScanButtonReducer from '../components/FullScanButton/FullScanButtonReducer';
import fullAnalyzeButtonReducer from '../components/FullAnalyzeButton/FullAnalyzeButtonReducer';

const rootReducer = combineReducers({
  auth: authenticationReducer,
  projects: projectListReducer,
  descriptors: descriptorsListReducer,
  artifacts: artifactsListReducer,
  fullScanButton: fullScanButtonReducer,
  fullAnalyzeButton: fullAnalyzeButtonReducer,
  rateLimit: rateLimitPanelReducer
});

export default rootReducer