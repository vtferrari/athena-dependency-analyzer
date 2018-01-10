import React, {Component} from 'react';
import RateLimitCard from "../components/RateLimitCard/RateLimitCard";

export default class ScmApiContent extends Component {

  render() {
    return (
        <div>
          <RateLimitCard title={"Rate limit"}/>
        </div>
    )
  }
}