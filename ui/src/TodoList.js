import React, {Component} from 'react';
import PropTypes from 'prop-types';

const TodoListTable = ({title, todos}) =>
    <table className="table table-striped table-hover">
      <caption>{title}</caption>
      <thead>
        <tr>
          <th>name</th>
          <th>description</th>
          <th>due date</th>
          <th>status</th>
        </tr>
      </thead>
      <tbody>
        {
          todos.map((todo, idx) =>
            <tr key={idx} >
              <td>{todo.name}</td>
              <td>
                {todo.description &&
                todo.description.split('\n')
                  .map((line,idx) => <div key={idx}>{line}</div>)
                }
                </td>
              <td>{todo.dueDate && new Date(todo.dueDate).toISOString()}</td>
              <td>{todo.status}</td>
            </tr>
          )
        }
      </tbody>
    </table>;

export class TodoList extends Component {
  state = {
    todos: [],
  };

  componentWillMount() {
    fetch('/api/todos').then(response => response.json())
      .then(json => this.setState({todos: json}));
  }

  render() {
    const {todos} = this.state;
    const pendingTodos = todos.filter(t => t.status === 'Pending');
    const doneTodos = todos.filter(t => t.status === 'Done');
    return (
      <div>
        <h2>Todo List</h2>
        <TodoListTable title="Pending todos" todos={pendingTodos} />
        <TodoListTable title="Done todos" todos={doneTodos} />
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
