import { ITeacher } from 'app/shared/model//teacher.model';
import { IBeltLevel } from 'app/shared/model//belt-level.model';
import { IPerformance } from 'app/shared/model//performance.model';
import { ILocation } from 'app/shared/model//location.model';

export interface IStudent {
    id?: number;
    studentName?: string;
    dateOfBirth?: string;
    studentmobileNumber?: string;
    dateOfJoining?: string;
    aadharCardNumber?: string;
    parentName?: string;
    parentmobileNumber?: string;
    password?: string;
    totalFees?: number;
    paidFees?: number;
    dueFees?: number;
    imageContentType?: string;
    image?: any;
    studentemail?: string;
    parentemail?: string;
    teacher?: ITeacher;
    beltLevel?: IBeltLevel;
    performance?: IPerformance;
    location?: ILocation;
}

export class Student implements IStudent {
    constructor(
        public id?: number,
        public studentName?: string,
        public dateOfBirth?: string,
        public studentmobileNumber?: string,
        public dateOfJoining?: string,
        public aadharCardNumber?: string,
        public parentName?: string,
        public parentmobileNumber?: string,
        public password?: string,
        public totalFees?: number,
        public paidFees?: number,
        public dueFees?: number,
        public imageContentType?: string,
        public image?: any,
        public studentemail?: string,
        public parentemail?: string,
        public teacher?: ITeacher,
        public beltLevel?: IBeltLevel,
        public performance?: IPerformance,
        public location?: ILocation
    ) {}
}
