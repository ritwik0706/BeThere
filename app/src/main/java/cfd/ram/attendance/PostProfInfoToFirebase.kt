package cfd.ram.attendance

/**
 * Created by RITWIK JHA on 01-02-2018.
 */
class PostProfInfoToFirebase{
    var name:String?=null
    var email:String?= null
    var imageURL:String?=null
    var rank:String?=null
    var instituteName:String?=null

    constructor(name:String,email:String,imageURL:String,rank:String,instituteName:String){
        this.name=name
        this.email=email
        this.imageURL=imageURL
        this.rank=rank
        this.instituteName=instituteName
    }
}