import { IDeste } from 'app/shared/model/deste.model';

export interface IKart {
  id?: number;
  onYuz?: string;
  arkaYuz?: string;
  onemSira?: number;
  tekDeste?: IDeste;
}

export class Kart implements IKart {
  constructor(public id?: number, public onYuz?: string, public arkaYuz?: string, public onemSira?: number, public tekDeste?: IDeste) {}
}
