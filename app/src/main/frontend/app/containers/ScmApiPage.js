import React, {Component} from 'react';
import RateLimitPanel from "../components/RateLimitPanel/RateLimitPanel";

export default class ScmApiPage extends Component {

  render() {
    return (
        <div>
          <RateLimitPanel title={"Rate limit"}/>
        </div>
    )
  }
}