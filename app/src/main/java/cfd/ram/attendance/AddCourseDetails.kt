package cfd.ram.attendance

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_course_details.*
import kotlinx.android.synthetic.main.activity_professor.*
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class AddCourseDetails : AppCompatActivity() {

    private var buID:String?=null

    private var database=FirebaseDatabase.getInstance()
    private var myRef=database.reference

    var mBoolean:Boolean=false

    var mAuth:FirebaseAuth?=null

    var profInstitute:String?=null
    var profName:String?=null
    var longi:String?=null
    var lat:String?=null
    var attendance:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_add_course_details)

        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        if (p0!!.value!=null){
                            var td=p0.value as HashMap<String,Any>

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


                    }

                })

        buDoneCoureDetails.setOnClickListener {

            myRef.child("Institute").child("Professor").child(profInstitute).child(profName).child(cd_code.text.toString()).setValue(PostCourseInfoToFirebase(cd_name.text.toString().trim(),
                    cd_code.text.toString().trim(),buStart1.text.toString(),buEnd1.text.toString(),buStart2.text.toString(),buEnd2.text.toString(),
                    buStart3.text.toString(),buEnd3.text.toString(),buStart4.text.toString(),buEnd4.text.toString(),buStart5.text.toString(),buEnd5.text.toString(),
                    buStart6.text.toString(),buEnd6.text.toString(),longi!!,lat!!,"0"))

            finish()

        }

        buAddCoureLocation.setOnClickListener{
            var intent = Intent(this, CurrentLocation::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1){
            if (resultCode== Activity.RESULT_OK){
                longi=data!!.getStringExtra("Longi")
                lat=data.getStringExtra("Lati")
            }
        }
    }

    /*fun RetrieveAttendance(){

        myRef.child("Institute").child("Professor").child(profInstitute).child(profName).child(cd_code.text.toString()
               .addValueEventListener(object:ValueEventListener{
                   override fun onCancelled(p0: DatabaseError?) {
                   }

                   override fun onDataChange(p0: DataSnapshot?) {
                       var td=p0!!.value as HashMap<String,Any>

                       attendance=td["attendance"] as Int

                   }

               })

    }

    fun UpdateAttendance(){
        val currentDay=Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var weekDay=DateFormatSymbols().shortWeekdays[currentDay] as String
        val df= SimpleDateFormat("HH")
        val dateObj= Date()
        val datef= SimpleDateFormat("mm")
        val dobj= Date()
        var hour= df.format(dateObj).toString()
        var min=datef.format(dobj).toString()

        Toast.makeText(applicationContext,hour+":"+min,Toast.LENGTH_SHORT).show()

        if (weekDay.contains("Wed")){
            attendance += 1
        }else if (weekDay.contains("Mon")){
            attendance+=1
        }else if (weekDay.contains("Tue")){
            attendance+=1
        }else if (weekDay.contains("Thu")){
            attendance+=1
        }else if (weekDay.contains("Thu")){
            attendance+=1
        }else if (weekDay.contains("Fri")){
            attendance+=1
        }else if (weekDay.contains("Sat")){
            attendance+=1
        }

    }*/

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
