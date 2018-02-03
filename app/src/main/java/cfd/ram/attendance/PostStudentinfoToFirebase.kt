package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 03-02-2018.
 */
class PostStudentinfoToFirebase{
    var name:String?=null
    var roll:String?=null
    var image:String?=null
    var insName:String?=null
    var profName:String?=null
    var courseCode:String?=null
    var email:String?=null

    constructor(name:String,roll:String,image:String,insName:String,profName:String,courseCode:String,email:String){
        this.name=name
        this.roll=roll
        this.image=image
        this.insName=insName
        this.profName=profName
        this.courseCode=courseCode
        this.email=email
    }
}