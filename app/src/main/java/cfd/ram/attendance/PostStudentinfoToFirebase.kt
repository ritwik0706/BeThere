package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 03-02-2018.
 */
class PostStudentinfoToFirebase{
    var name:String?=null
    var roll:String?=null
    var image:String?=null
    var DOB:String?=null
    var year:String?=null
    var email:String?=null

    constructor(name:String,roll:String,image:String,dob:String,year:String,email:String){
        this.name=name
        this.roll=roll
        this.image=image
        this.email=email
        this.DOB=dob
        this.year=year
    }
}