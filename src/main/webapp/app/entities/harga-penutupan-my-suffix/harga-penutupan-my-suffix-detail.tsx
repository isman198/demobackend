import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './harga-penutupan-my-suffix.reducer';
import { IHargaPenutupanMySuffix } from 'app/shared/model/harga-penutupan-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHargaPenutupanMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HargaPenutupanMySuffixDetail extends React.Component<IHargaPenutupanMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { hargaPenutupanEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            HargaPenutupan [<b>{hargaPenutupanEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tanggal">Tanggal</span>
            </dt>
            <dd>
              <TextFormat value={hargaPenutupanEntity.tanggal} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="harga">Harga</span>
            </dt>
            <dd>{hargaPenutupanEntity.harga}</dd>
            <dt>Efek</dt>
            <dd>{hargaPenutupanEntity.efek ? hargaPenutupanEntity.efek.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/harga-penutupan-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/harga-penutupan-my-suffix/${hargaPenutupanEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ hargaPenutupan }: IRootState) => ({
  hargaPenutupanEntity: hargaPenutupan.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HargaPenutupanMySuffixDetail);
