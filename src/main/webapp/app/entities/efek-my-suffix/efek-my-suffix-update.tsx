import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './efek-my-suffix.reducer';
import { IEfekMySuffix } from 'app/shared/model/efek-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEfekMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEfekMySuffixUpdateState {
  isNew: boolean;
}

export class EfekMySuffixUpdate extends React.Component<IEfekMySuffixUpdateProps, IEfekMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { efekEntity } = this.props;
      const entity = {
        ...efekEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/efek-my-suffix');
  };

  render() {
    const { efekEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="demobackendApp.efek.home.createOrEditLabel">Create or edit a Efek</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : efekEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="efek-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="kodeEfekLabel" for="kodeEfek">
                    Kode Efek
                  </Label>
                  <AvField
                    id="efek-my-suffix-kodeEfek"
                    type="text"
                    name="kodeEfek"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      maxLength: { value: 20, errorMessage: 'This field cannot be longer than 20 characters.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="namaEfekLabel" for="namaEfek">
                    Nama Efek
                  </Label>
                  <AvField
                    id="efek-my-suffix-namaEfek"
                    type="text"
                    name="namaEfek"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      maxLength: { value: 100, errorMessage: 'This field cannot be longer than 100 characters.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="closingPriceLabel" for="closingPrice">
                    Closing Price
                  </Label>
                  <AvField
                    id="efek-my-suffix-closingPrice"
                    type="string"
                    className="form-control"
                    name="closingPrice"
                    validate={{
                      min: { value: 0, errorMessage: 'This field should be at least 0.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="closingDateLabel" for="closingDate">
                    Closing Date
                  </Label>
                  <AvField id="efek-my-suffix-closingDate" type="date" className="form-control" name="closingDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusGadaiLabel" check>
                    <AvInput id="efek-my-suffix-statusGadai" type="checkbox" className="form-control" name="statusGadai" />
                    Status Gadai
                  </Label>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/efek-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  efekEntity: storeState.efek.entity,
  loading: storeState.efek.loading,
  updating: storeState.efek.updating,
  updateSuccess: storeState.efek.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EfekMySuffixUpdate);
