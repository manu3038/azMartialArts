import { IStudent } from 'app/shared/model//student.model';

export interface IPerformance {
    id?: number;
    rating?: string;
    students?: IStudent[];
}

export class Performance implements IPerformance {
    constructor(public id?: number, public rating?: string, public students?: IStudent[]) {}
}
