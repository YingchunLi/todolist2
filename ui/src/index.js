import React from 'react';
import ReactDOM from 'react-dom';

// routing
import {BrowserRouter, Route, Switch} from 'react-router-dom';

import './index.css';

import App from './App';
import TodoList from './TodoList';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={App}/>
      <Route exact path="/todos" component={TodoList}/>
    </Switch>
  </BrowserRouter>,
  document.getElementById('root'));
registerServiceWorker();
