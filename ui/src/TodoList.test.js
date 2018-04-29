import React from 'react';
import ReactDOM from 'react-dom';
import TodoList from './TodoList';

it('renders without todos', () => {
  fetch.once('[]');
  const div = document.createElement('div');
  ReactDOM.render(<TodoList />, div);
});

it('renders with todos', () => {
  let body = [{
    id:1,
    name:'milk',
    description: 'remember the milk',
    dueDate: '2018-12-31T00:00:00.000z',
    status: 'Pending'}
  ];
  fetch.once( JSON.stringify(body) );
  const div = document.createElement('div');
  ReactDOM.render(<TodoList />, div);
});