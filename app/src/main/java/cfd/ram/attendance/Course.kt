package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 31-01-2018.
 */
class Course{
    var courseName:String?=null
    var attendance:String?=null

    constructor(courseName:String,attendance:String){
        this.courseName=courseName
        this.attendance=attendance
    }
}