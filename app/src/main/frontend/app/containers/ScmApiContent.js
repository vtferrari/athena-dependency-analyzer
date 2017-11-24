import React, {Component} from 'react';
import RateLimitPanel from "../components/RateLimitPanel/RateLimitPanel";

export default class ScmApiContent extends Component {

  render() {
    return (
        <div>
          <RateLimitPanel title={"Rate limit"}/>
        </div>
    )
  }
}