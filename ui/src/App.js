import React, { Component } from 'react';
import './App.css';
import TodoList from './TodoList';

class App extends Component {
  render() {
    return (
      <div className="ui">
        <nav className="appbar">Todo List</nav>
        <nav className="boardbar">Dashboard</nav>
        <div className="todolists">
          <article className="todolist">
            <header>Pending todos</header>
            <ul>
              <li>Milk</li>
              <li>Grocery</li>
            </ul>
            <footer>Add new</footer>
          </article>

          <article className="todolist">
            <header>Done todos</header>
            <ul>
              <li>Gift for tony</li>
              <li>A very long todo which probably wrap two lines if not then adding more to the title</li>
              <li>Another todo item that is done</li>
            </ul>
            <footer>list footer</footer>
          </article>
        </div>
      </div>
    );
  }
}

export default App;
