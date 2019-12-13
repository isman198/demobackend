import { Moment } from 'moment';
import { IHargaPenutupanMySuffix } from 'app/shared/model/harga-penutupan-my-suffix.model';

export interface IEfekMySuffix {
  id?: number;
  kodeEfek?: string;
  namaEfek?: string;
  closingPrice?: number;
  closingDate?: Moment;
  statusGadai?: boolean;
  hargaPenutupans?: IHargaPenutupanMySuffix[];
}

export const defaultValue: Readonly<IEfekMySuffix> = {
  statusGadai: false
};
