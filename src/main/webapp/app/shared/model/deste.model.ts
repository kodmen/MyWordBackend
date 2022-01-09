import { IUser } from 'app/core/user/user.model';
import { IKart } from 'app/shared/model/kart.model';

export interface IDeste {
  id?: number;
  renk?: string;
  name?: string;
  internalUser?: IUser;
  kartlars?: IKart[];
}

export class Deste implements IDeste {
  constructor(public id?: number, public renk?: string, public name?: string, public internalUser?: IUser, public kartlars?: IKart[]) {}
}
