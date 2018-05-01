import React from 'react';
import ReactDOM from 'react-dom';
import renderer from "react-test-renderer";

import TodoList from './TodoList';

it('renders without todos', () => {
  fetch.once('[]');
  const div = document.createElement('div');
  ReactDOM.render(<TodoList />, div);
});

it('renders with todos', () => {
  const body = [{
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

it('snapshot test - without none-ok response', () => {
  fetch.once('some internal server error', {status: 500, statusText: 'Internal Server Error'});
  const component = renderer.create(
    <TodoList />
  ).toJSON();
  expect(component).toMatchSnapshot();
});

it('snapshot test - without none-json resonse', () => {
  fetch.once('some response not in json format');
  const component = renderer.create(
    <TodoList />
  ).toJSON();
  expect(component).toMatchSnapshot();
});

it('snapshot test - without todos', () => {
  fetch.once('[]');
  const component = renderer.create(
    <TodoList />
  ).toJSON();
  expect(component).toMatchSnapshot();
});

it('snapshot test - with todos', () => {
  const body = [{
    id:1,
    name:'milk',
    description: 'remember the milk',
    dueDate: '2018-12-31T00:00:00.000z',
    status: 'Pending'}
  ];
  fetch.once( JSON.stringify(body) );
  const component = renderer.create(
    <TodoList />
  ).toJSON();

  expect(component).toMatchSnapshot();
});