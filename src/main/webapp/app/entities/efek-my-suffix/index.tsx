import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EfekMySuffix from './efek-my-suffix';
import EfekMySuffixDetail from './efek-my-suffix-detail';
import EfekMySuffixUpdate from './efek-my-suffix-update';
import EfekMySuffixDeleteDialog from './efek-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EfekMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EfekMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EfekMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={EfekMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EfekMySuffixDeleteDialog} />
  </>
);

export default Routes;
