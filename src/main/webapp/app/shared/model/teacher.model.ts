import { IStudent } from 'app/shared/model//student.model';

export interface ITeacher {
    id?: number;
    teacherName?: string;
    mobileNumber?: string;
    dateOfBirth?: string;
    email?: string;
    password?: string;
    desc?: string;
    students?: IStudent[];
}

export class Teacher implements ITeacher {
    constructor(
        public id?: number,
        public teacherName?: string,
        public mobileNumber?: string,
        public dateOfBirth?: string,
        public email?: string,
        public password?: string,
        public desc?: string,
        public students?: IStudent[]
    ) {}
}
