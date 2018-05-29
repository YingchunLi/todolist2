import React, {Component} from 'react';
import PropTypes from 'prop-types';

const TodoListGroup = ({title, todos}) =>
  <article className="todolist">
    <header>{title}</header>
    <ul>
      { todos.map((todo, idx) => <li key={idx}>{todo.name}</li>) }
    </ul>
    <footer>list footer</footer>
  </article>;

export class TodoList extends Component {
  state = {
    todos: [],
  };

  componentWillMount() {
    fetch('/api/todos')
      .then(response => {
        if (!response.ok) throw Error(`${response.status} (${response.statusText})`);
        return response;
      })
      .then(response => response.json())
      .then(json => this.setState({todos: json}))
      .catch(error => console.error(`failed to get todo list: ${error}`));
  }

  render() {
    const {todos} = this.state;
    const pendingTodos = todos.filter(t => t.status === 'Pending');
    const doneTodos = todos.filter(t => t.status === 'Done');
    return (
      <div className="todolists">
        <TodoListGroup todos={pendingTodos} title="Pending todos" />
        <TodoListGroup todos={doneTodos} title="Done todos" />
      </div>
    );
  }
}

TodoList.propTypes = {
  todos:  PropTypes.arrayOf(PropTypes.object),
};

TodoList.defaultProps = {
  todos: [],
};

export default TodoList;
