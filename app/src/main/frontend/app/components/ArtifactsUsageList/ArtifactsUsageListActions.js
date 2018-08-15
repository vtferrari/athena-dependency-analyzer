import axios from 'axios'

export const REQUEST_ARTIFACTS_USAGE = 'REQUEST_ARTIFACTS_USAGE';
export const RECEIVE_ARTIFACTS_USAGE = 'RECEIVE_ARTIFACTS_USAGE';
export const RECEIVE_ARTIFACTS_USAGE_COUNT = 'RECEIVE_ARTIFACTS_USAGE_COUNT';
export const REQUEST_PROJECTS_BY_ARTIFACT = 'REQUEST_PROJECTS_BY_ARTIFACT';
export const RECEIVE_PROJECTS_BY_ARTIFACT = 'RECEIVE_PROJECTS_BY_ARTIFACT';
export const CLOSE_PROJECTS = 'CLOSE_PROJECTS';

function requestArtifactsUsage(pageNumber, pageSize, search) {
  return {
    type: REQUEST_ARTIFACTS_USAGE,
    pageNumber: pageNumber,
    pageSize: pageSize,
    search: search
  }
}

function receiveArtifactsUsage(data) {
  return {
    type: RECEIVE_ARTIFACTS_USAGE,
    list: data,
    receivedAt: Date.now()
  }
}

function receiveArtifactsUsageCount(data) {
  return {
    type: RECEIVE_ARTIFACTS_USAGE_COUNT,
    totalItems: data,
    receivedAt: Date.now()
  }
}

export function listArtifactsUsage(pageNumber, pageSize, search) {
  return function (dispatch) {
    dispatch(requestArtifactsUsage(pageNumber, pageSize, search));
    let queryString = 'pageNumber=' + pageNumber + '&pageSize=' + pageSize;
    if (search && search.groupId) {
      queryString += '&groupId=' + search.groupId;
    }
    if (search && search.artifactId) {
      queryString += '&artifactId=' + search.artifactId;
    }
    if (search && search.version) {
      queryString += '&version=' + search.version;
    }
    axios.get('/api/v1/artifacts?' + queryString).then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveArtifactsUsage(response.data)));

    axios.get('/api/v1/artifacts/count?' + queryString)
    .then(
        response => dispatch(receiveArtifactsUsageCount(response.data))).catch(
        error => console.log('An error occurred.', error));
  }
}

function requestProjectsByArtifact(groupId, artifactId, version) {
  return {
    type: REQUEST_PROJECTS_BY_ARTIFACT,
    groupId: groupId,
    artifactId: artifactId,
    version: version
  }
}

function receiveProjectsByArtifact(data) {
  return {
    type: RECEIVE_PROJECTS_BY_ARTIFACT,
    list: data,
    receivedAt: Date.now()
  }
}

export function listProjectsByArtifact(groupId, artifactId, version) {
  return function (dispatch) {
    dispatch(requestProjectsByArtifact(groupId, artifactId, version));
    axios.get('/api/v1/artifacts/' + groupId + '/' + artifactId + '/' + version
        + '/projects').then(
        response => response,
        error => console.log('An error occurred.', error)
    ).then(response => dispatch(receiveProjectsByArtifact(response.data)));
  }
}

export function closeProjects() {
  return {
    type: CLOSE_PROJECTS
  }
}