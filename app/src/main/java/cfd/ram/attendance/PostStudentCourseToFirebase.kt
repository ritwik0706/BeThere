package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 04-02-2018.
 */
class PostStudentCourseToFirebase{
    var insName:String?=null
    var profName:String?=null
    var courseCode:String?=null
    var email:String?=null
    var courseName:String?=null
    var monStart:String?=null
    var monEnd:String?=null
    var tuStart:String?=null
    var tuEnd:String?=null
    var wedStart:String?=null
    var wedEnd:String?=null
    var thursStart:String?=null
    var thursEnd:String?=null
    var friStart:String?=null
    var friEnd:String?=null
    var satStart:String?=null
    var satEnd:String?=null
    var longitude:String?=null
    var latitude:String?=null
    var attendance:String?=null

    constructor(insName:String,profName:String,courseCode:String,email:String,courseName:String,monStart:String,monEnd:String,tuStart:String,tuEnd:String,wedStart:String,
                wedEnd:String,thursStart:String,thursEnd:String,friStart:String,friEnd:String,satStart:String,satEnd:String,longitude:String,latitude:String,attendance:String){
        this.insName=insName
        this.profName=profName
        this.courseCode=courseCode
        this.email=email
        this.courseName=courseName
        this.monStart=monStart
        this.monEnd=monEnd
        this.tuStart=tuStart
        this.tuEnd=tuEnd
        this.wedStart=wedStart
        this.wedEnd=wedEnd
        this.thursStart=thursStart
        this.thursEnd=thursEnd
        this.friStart=friStart
        this.friEnd=friEnd
        this.satStart=satStart
        this.satEnd=satEnd
        this.longitude=longitude
        this.latitude=latitude
        this.attendance=attendance
    }
}