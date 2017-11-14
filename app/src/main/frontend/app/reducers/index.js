import {combineReducers} from 'redux'
import projectListReducer from '../components/ProjectsList/redux/reducer';
import descriptorsListReducer from '../components/DescriptorsList/redux/reducer';
import artifactsListReducer from '../components/ArtifactsList/redux/reducer';

const rootReducer = combineReducers({
  projects: projectListReducer,
  descriptors: descriptorsListReducer,
  artifacts: artifactsListReducer
});

export default rootReducer