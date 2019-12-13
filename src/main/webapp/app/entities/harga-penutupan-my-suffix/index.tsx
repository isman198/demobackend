import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HargaPenutupanMySuffix from './harga-penutupan-my-suffix';
import HargaPenutupanMySuffixDetail from './harga-penutupan-my-suffix-detail';
import HargaPenutupanMySuffixUpdate from './harga-penutupan-my-suffix-update';
import HargaPenutupanMySuffixDeleteDialog from './harga-penutupan-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HargaPenutupanMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HargaPenutupanMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HargaPenutupanMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={HargaPenutupanMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={HargaPenutupanMySuffixDeleteDialog} />
  </>
);

export default Routes;
