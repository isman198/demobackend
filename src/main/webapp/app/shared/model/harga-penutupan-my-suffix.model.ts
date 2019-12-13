import { Moment } from 'moment';
import { IEfekMySuffix } from 'app/shared/model/efek-my-suffix.model';

export interface IHargaPenutupanMySuffix {
  id?: number;
  tanggal?: Moment;
  harga?: number;
  efek?: IEfekMySuffix;
}

export const defaultValue: Readonly<IHargaPenutupanMySuffix> = {};
