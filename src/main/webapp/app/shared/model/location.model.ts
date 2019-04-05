import { IStudent } from 'app/shared/model//student.model';

export interface ILocation {
    id?: number;
    locationName?: string;
    locationAddress?: string;
    students?: IStudent[];
}

export class Location implements ILocation {
    constructor(public id?: number, public locationName?: string, public locationAddress?: string, public students?: IStudent[]) {}
}
