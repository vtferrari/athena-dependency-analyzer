import {combineReducers} from 'redux'
import projectListReducer from '../components/ProjectsList/redux/reducer';
import artifactsListReducer from '../components/ArtifactsList/redux/reducer';

const rootReducer = combineReducers({
  projects: projectListReducer,
  artifacts: artifactsListReducer
});

export default rootReducer