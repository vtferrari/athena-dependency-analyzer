import React, {Component} from 'react';
import '../style/App.css';

class App extends Component {
  render() {
    return (
        <div>
          <link rel="stylesheet"
                href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
          <header>
            <h1>Athena Dependency Analyzer</h1>
          </header>
          <p class="text-right">By Netshoes</p>
        </div>
    );
  }
}

export default App;
