package cfd.ram.attendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_prof_details.*
import kotlinx.android.synthetic.main.activity_institute_info2.*
import kotlinx.android.synthetic.main.activity_prof_info.*
import kotlinx.android.synthetic.main.activity_professor.*

class ProfInfo : AppCompatActivity() {

    var mAuth: FirebaseAuth?=null

    var profName:String?=null
    var profEmail:String?=null
    var profRank:String?=null
    var profInstitute:String?=null
    var profImage:String?=null

    var database= FirebaseDatabase.getInstance()
    var myRef=database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_prof_info)

        LoadProfInfo()
    }

    fun LoadProfInfo(){

        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        if (p0!!.value!= null){
                            var td=p0!!.value as HashMap<String,Any>

                            for (key in td.keys){
                                var prof=td[key] as HashMap<String,Any>

                                for (i in prof.keys){
                                    var profDetails=prof[i] as HashMap<String,Any>

                                    profName=profDetails["name"] as String
                                    profEmail=profDetails["email"] as String
                                    profImage=profDetails["imageURL"] as String
                                    profInstitute=profDetails["instituteName"] as String
                                    profRank=profDetails["rank"] as String

                                    if (profEmail==mAuth!!.currentUser!!.email){
                                        tvProfName.text=profName
                                        tvProfInstitute.text=profInstitute
                                        tvProfEmail.text=profEmail
                                        tvProfRank.text=profRank
                                        if (profImage!=""){
                                            Picasso.with(this@ProfInfo).load(profImage).into(ivProfInfo)
                                        }
                                    }

                                }
                            }

                        }
                        }


                })
    }
}
