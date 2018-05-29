import React, { Component } from 'react';
import './App.css';
import TodoList from './TodoList';

class App extends Component {
  render() {
    return (
      <div className="ui">
        <nav className="appbar">Todo List</nav>
        <nav className="boardbar">Dashboard</nav>
        <TodoList />
      </div>
    );
  }
}

export default App;
