import { IStudent } from 'app/shared/model//student.model';

export interface IBeltLevel {
    id?: number;
    level?: string;
    students?: IStudent[];
}

export class BeltLevel implements IBeltLevel {
    constructor(public id?: number, public level?: string, public students?: IStudent[]) {}
}
