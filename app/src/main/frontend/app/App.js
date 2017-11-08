import React, {Component} from 'react';
import ProjectsList from './ProjectsList';
import Col from 'react-bootstrap/lib/Col';
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

          <Col md={12}>
            <ProjectsList/>
          </Col>

          <footer>
            <p className="text-right">By Netshoes</p>
          </footer>
        </div>
    );
  }
}

export default App;
