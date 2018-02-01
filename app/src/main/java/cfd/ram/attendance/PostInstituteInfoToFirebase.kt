package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 01-02-2018.
 */
class PostInstituteInfoToFirebase{
    var name:String?=null
    var email:String?=null
    var contact:String?=null
    var imgUrl:String?=null

    constructor(name:String,email:String,contact:String,imgUrl:String){
        this.name=name
        this.email=email
        this.contact=contact
        this.imgUrl=imgUrl
    }
}