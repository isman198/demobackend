import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './efek-my-suffix.reducer';
import { IEfekMySuffix } from 'app/shared/model/efek-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEfekMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EfekMySuffixDetail extends React.Component<IEfekMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { efekEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Efek [<b>{efekEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="kodeEfek">Kode Efek</span>
            </dt>
            <dd>{efekEntity.kodeEfek}</dd>
            <dt>
              <span id="namaEfek">Nama Efek</span>
            </dt>
            <dd>{efekEntity.namaEfek}</dd>
            <dt>
              <span id="closingPrice">Closing Price</span>
            </dt>
            <dd>{efekEntity.closingPrice}</dd>
            <dt>
              <span id="closingDate">Closing Date</span>
            </dt>
            <dd>
              <TextFormat value={efekEntity.closingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="statusGadai">Status Gadai</span>
            </dt>
            <dd>{efekEntity.statusGadai ? 'true' : 'false'}</dd>
          </dl>
          <Button tag={Link} to="/entity/efek-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/efek-my-suffix/${efekEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ efek }: IRootState) => ({
  efekEntity: efek.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EfekMySuffixDetail);
