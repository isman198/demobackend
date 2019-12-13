import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEfekMySuffix, defaultValue } from 'app/shared/model/efek-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_EFEK_LIST: 'efek/FETCH_EFEK_LIST',
  FETCH_EFEK: 'efek/FETCH_EFEK',
  CREATE_EFEK: 'efek/CREATE_EFEK',
  UPDATE_EFEK: 'efek/UPDATE_EFEK',
  DELETE_EFEK: 'efek/DELETE_EFEK',
  RESET: 'efek/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEfekMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EfekMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: EfekMySuffixState = initialState, action): EfekMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EFEK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EFEK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EFEK):
    case REQUEST(ACTION_TYPES.UPDATE_EFEK):
    case REQUEST(ACTION_TYPES.DELETE_EFEK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EFEK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EFEK):
    case FAILURE(ACTION_TYPES.CREATE_EFEK):
    case FAILURE(ACTION_TYPES.UPDATE_EFEK):
    case FAILURE(ACTION_TYPES.DELETE_EFEK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EFEK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EFEK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EFEK):
    case SUCCESS(ACTION_TYPES.UPDATE_EFEK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EFEK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/efeks';

// Actions

export const getEntities: ICrudGetAllAction<IEfekMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EFEK_LIST,
  payload: axios.get<IEfekMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEfekMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EFEK,
    payload: axios.get<IEfekMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEfekMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EFEK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEfekMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EFEK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEfekMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EFEK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
