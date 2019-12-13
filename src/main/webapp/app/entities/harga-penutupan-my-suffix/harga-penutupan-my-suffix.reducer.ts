import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHargaPenutupanMySuffix, defaultValue } from 'app/shared/model/harga-penutupan-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_HARGAPENUTUPAN_LIST: 'hargaPenutupan/FETCH_HARGAPENUTUPAN_LIST',
  FETCH_HARGAPENUTUPAN: 'hargaPenutupan/FETCH_HARGAPENUTUPAN',
  CREATE_HARGAPENUTUPAN: 'hargaPenutupan/CREATE_HARGAPENUTUPAN',
  UPDATE_HARGAPENUTUPAN: 'hargaPenutupan/UPDATE_HARGAPENUTUPAN',
  DELETE_HARGAPENUTUPAN: 'hargaPenutupan/DELETE_HARGAPENUTUPAN',
  RESET: 'hargaPenutupan/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHargaPenutupanMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type HargaPenutupanMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: HargaPenutupanMySuffixState = initialState, action): HargaPenutupanMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HARGAPENUTUPAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HARGAPENUTUPAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HARGAPENUTUPAN):
    case REQUEST(ACTION_TYPES.UPDATE_HARGAPENUTUPAN):
    case REQUEST(ACTION_TYPES.DELETE_HARGAPENUTUPAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HARGAPENUTUPAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HARGAPENUTUPAN):
    case FAILURE(ACTION_TYPES.CREATE_HARGAPENUTUPAN):
    case FAILURE(ACTION_TYPES.UPDATE_HARGAPENUTUPAN):
    case FAILURE(ACTION_TYPES.DELETE_HARGAPENUTUPAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HARGAPENUTUPAN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_HARGAPENUTUPAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HARGAPENUTUPAN):
    case SUCCESS(ACTION_TYPES.UPDATE_HARGAPENUTUPAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HARGAPENUTUPAN):
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

const apiUrl = 'api/harga-penutupans';

// Actions

export const getEntities: ICrudGetAllAction<IHargaPenutupanMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HARGAPENUTUPAN_LIST,
    payload: axios.get<IHargaPenutupanMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IHargaPenutupanMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HARGAPENUTUPAN,
    payload: axios.get<IHargaPenutupanMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHargaPenutupanMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HARGAPENUTUPAN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHargaPenutupanMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HARGAPENUTUPAN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHargaPenutupanMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HARGAPENUTUPAN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
