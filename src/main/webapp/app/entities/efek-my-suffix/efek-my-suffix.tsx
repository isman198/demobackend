import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './efek-my-suffix.reducer';
import { IEfekMySuffix } from 'app/shared/model/efek-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEfekMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EfekMySuffix extends React.Component<IEfekMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { efekList, match } = this.props;
    return (
      <div>
        <h2 id="efek-my-suffix-heading">
          Efeks
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Efek
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Kode Efek</th>
                <th>Nama Efek</th>
                <th>Closing Price</th>
                <th>Closing Date</th>
                <th>Status Gadai</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {efekList.map((efek, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${efek.id}`} color="link" size="sm">
                      {efek.id}
                    </Button>
                  </td>
                  <td>{efek.kodeEfek}</td>
                  <td>{efek.namaEfek}</td>
                  <td>{efek.closingPrice}</td>
                  <td>
                    <TextFormat type="date" value={efek.closingDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{efek.statusGadai ? 'true' : 'false'}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${efek.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${efek.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${efek.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ efek }: IRootState) => ({
  efekList: efek.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EfekMySuffix);
