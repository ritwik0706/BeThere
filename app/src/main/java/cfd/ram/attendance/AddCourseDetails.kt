package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_course_details.*
import kotlinx.android.synthetic.main.activity_professor.*

class AddCourseDetails : AppCompatActivity() {

    private var buID:String?=null

    private var database=FirebaseDatabase.getInstance()
    private var myRef=database.reference

    var mAuth:FirebaseAuth?=null

    var profInstitute:String?=null
    var profName:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_add_course_details)

        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        var td=p0!!.value as HashMap<String,Any>

                        for (key in td.keys){
                            var prof=td[key] as HashMap<String,Any>

                            for (i in prof.keys){
                                var profDetails=prof[i] as HashMap<String,Any>

                                var email=profDetails["email"] as String

                                if (email==mAuth!!.currentUser!!.email){
                                    profName=profDetails["name"] as String
                                    profInstitute=profDetails["instituteName"] as String
                                }

                            }
                        }

                    }

                })

        buDoneCoureDetails.setOnClickListener {

            myRef.child("Institute").child("Professor").child(profInstitute).child(profName).child(cd_code.text.toString()).setValue(PostCourseInfoToFirebase(cd_name.text.toString().trim(),
                    cd_code.text.toString().trim(),buStart1.text.toString(),buEnd1.text.toString(),buStart2.text.toString(),buEnd2.text.toString(),
                    buStart3.text.toString(),buEnd3.text.toString(),buStart4.text.toString(),buEnd4.text.toString(),buStart5.text.toString(),buEnd5.text.toString(),
                    buStart6.text.toString(),buEnd6.text.toString()))

            finish()

        }

        buAddCoureLocation.setOnClickListener{
            var intent = Intent(this, CurrentLocation::class.java)
            intent.putExtra("Institute",profInstitute)
            intent.putExtra("Prof",profName)
            intent.putExtra("CourseCode",cd_code.text.toString())
            startActivity(intent)
        }
    }

    fun SetTimeStart1(hours:Int,min:Int){
        buStart1.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeStart2(hours:Int,min:Int){
        buStart2.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeStart3(hours:Int,min:Int){
        buStart3.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeStart4(hours:Int,min:Int){
        buStart4.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeStart5(hours:Int,min:Int){
        buStart5.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeStart6(hours:Int,min:Int){
        buStart6.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd1(hours:Int,min:Int){
        buEnd1.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd2(hours:Int,min:Int){
        buEnd2.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd3(hours:Int,min:Int){
        buEnd3.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd4(hours:Int,min:Int){
        buEnd4.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd5(hours:Int,min:Int){
        buEnd5.text=hours.toString()+ ":" +min.toString()
    }
    fun SetTimeEnd6(hours:Int,min:Int){
        buEnd6.text=hours.toString()+ ":" +min.toString()
    }

    fun Clock(view: View){
        buID=resources.getResourceName(view.id)

        val popTime=PopTime.newInstance(buID!!)
        val fm=fragmentManager

        popTime.show(fm,"Select Time")
    }
}
