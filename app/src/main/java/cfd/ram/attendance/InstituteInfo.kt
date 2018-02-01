package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_institute.*
import kotlinx.android.synthetic.main.activity_institute_info.*
import kotlinx.android.synthetic.main.activity_institute_info2.*

class InstituteInfo : AppCompatActivity() {

    var mAuth: FirebaseAuth?=null

    var database= FirebaseDatabase.getInstance()
    var myRef=database.reference

    var imgUrl:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth=FirebaseAuth.getInstance()
        setContentView(R.layout.activity_institute_info2)

        myRef.child("Institute").child("Institute").child("Institute Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        var td=p0!!.value as HashMap<String,Any>

                        for (key in td.keys){
                            if (key==mAuth!!.currentUser!!.uid){
                                var ins=td[key] as HashMap<String,Any>

                                imgUrl=ins["imgUrl"] as String
                                tvInstiName.text=ins["name"] as String
                                tvInstiEmail.text=ins["email"] as String
                                tvInstiContact.text=ins["contact"] as String
                                if (imgUrl!=""){
                                    Picasso.with(this@InstituteInfo).load(imgUrl).into(ivInsti)
                                }
                            }
                        }
                    }

                })

        buEditInstiInfo.setOnClickListener {
            var addinfo=Intent(this,AddInstituteInfo::class.java)
            startActivity(addinfo)
            finish()
        }


    }
}
