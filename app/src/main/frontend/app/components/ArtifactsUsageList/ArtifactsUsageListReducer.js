import {
  CLOSE_PROJECTS,
  RECEIVE_ARTIFACTS_USAGE,
  RECEIVE_ARTIFACTS_USAGE_COUNT,
  RECEIVE_PROJECTS_BY_ARTIFACT,
  REQUEST_ARTIFACTS_USAGE,
  REQUEST_PROJECTS_BY_ARTIFACT
} from './ArtifactsUsageListActions'

const initialState = {
  list: [],
  pageNumber: 0,
  pageSize: 10,
  search: {},
  loading: false
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case REQUEST_ARTIFACTS_USAGE:
      return Object.assign({}, state, {
        loading: true,
        pageNumber: action.pageNumber,
        pageSize: action.pageSize,
        search: action.search
      });

    case RECEIVE_ARTIFACTS_USAGE:
      return Object.assign({}, state, {
        loading: false,
        list: action.list,
        lastUpdated: action.receivedAt
      });

    case RECEIVE_ARTIFACTS_USAGE_COUNT:
      return Object.assign({}, state, {
        loading: false,
        totalItems: action.totalItems,
        lastUpdated: action.receivedAt
      });

    case REQUEST_PROJECTS_BY_ARTIFACT:
      return Object.assign({}, state, {
        loading: true,
        showProjectsModal: false,
        selectedGroupId: action.groupId,
        selectedArtifactId: action.artifactId,
        selectedVersion: action.version
      });

    case RECEIVE_PROJECTS_BY_ARTIFACT:
      return Object.assign({}, state, {
        loading: false,
        showProjectsModal: true,
        projectsList: action.list
      });

    case CLOSE_PROJECTS:
      return Object.assign({}, state, {
        showProjectsModal: false,
        projectsList: null
      });

    default:
      return state;
  }
}