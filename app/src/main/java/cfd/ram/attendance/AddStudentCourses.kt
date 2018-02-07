package cfd.ram.attendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_student_courses.*
import java.util.ArrayList
import java.util.HashMap

class AddStudentCourses : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    val database = FirebaseDatabase.getInstance()
    var myRef = database.reference

    var instituteList = ArrayList<String>()
    var proflist = ArrayList<String>()
    var courseList = ArrayList<String>()

    var instituteName:String?=null
    var profName:String?=null
    var studentName:String?=null
    var studentRoll:String?=null
    var courseCode:String?=null
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
    var longi:String?=null
    var lat:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_add_student_courses)

        instituteList.add("Institutes")
        proflist.add("Professors")
        courseList.add("Course Code")

        LoadInsituteList()

        LoadStudentDetails()

        buAddStudentCourse.setOnClickListener {
            myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll!!).child(courseCode!!).setValue(PostStudentCourseToFirebase(instituteName!!,profName!!,courseCode!!,mAuth!!.currentUser!!.email.toString(),courseName!!,
                    monStart!!,monEnd!!,tuStart!!,tuEnd!!,wedStart!!,wedEnd!!,thursStart!!,thursEnd!!,friStart!!,friEnd!!,satStart!!,satEnd!!,longi!!,lat!!,"0"))

            finish()
        }
    }

    fun LoadInsituteList(){
        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        try {


                            var td = p0!!.value as HashMap<String, Any>

                            for (key in td.keys) {
                                instituteList.add(key)
                            }

                        } catch (ex: Exception) {
                        }
                    }

                })

        var adapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,instituteList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        instituteSpinner.adapter=adapter

        instituteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                instituteName=parent!!.getItemAtPosition(position).toString()

                LoadProfList()
            }

        }


    }

    fun LoadProfList(){

        if (instituteName!=null){
            myRef.child("Institute").child("Institute").child("Professor Info").child(instituteName)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {

                                var td = p0!!.value as HashMap<String, Any>

                                for (key in td.keys) {
                                    proflist.add(key)
                                }

                            } catch (ex: Exception) {
                            }
                        }

                    })

            var profAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,proflist)

            profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            ProfSpinner.adapter=profAdapter

            ProfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    profName=parent!!.getItemAtPosition(position).toString()

                    LoadCourseList()
                }

            }
        }
    }

    fun LoadCourseList(){
        if (profName!=null){

            myRef.child("Institute").child("Professor").child(instituteName).child(profName)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {

                                var td = p0!!.value as HashMap<String, Any>

                                for (key in td.keys) {
                                    courseList.add(key)
                                }

                            } catch (ex: Exception) {
                            }
                        }

                    })

            var courseAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,courseList)

            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            courseSpinner.adapter=courseAdapter

            courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    courseCode=parent!!.getItemAtPosition(position).toString()

                    myRef.child("Institute").child("Professor").child(instituteName).child(profName)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError?) {
                                }

                                override fun onDataChange(p0: DataSnapshot?) {

                                    try {

                                        var td = p0!!.value as HashMap<String, Any>

                                        for (key in td.keys) {
                                            courseList.add(key)

                                            var course=td[key] as HashMap<String,Any>

                                            if (courseCode==course["courseCode"] as String){
                                                courseName=course["courseName"] as String
                                                monStart=course["monStart"] as String
                                                monEnd=course["monEnd"] as String
                                                tuStart=course["tuStart"] as String
                                                tuEnd=course["tuEnd"] as String
                                                wedStart=course["wedStart"] as String
                                                wedEnd=course["wedEnd"] as String
                                                thursStart=course["thursStart"] as String
                                                thursEnd=course["thursEnd"] as String
                                                friStart=course["friStart"] as String
                                                friEnd=course["friEnd"] as String
                                                satStart=course["satStart"] as String
                                                satEnd=course["satEnd"] as String
                                                longi=course["longitude"] as String
                                                lat=course["latitude"] as String
                                            }
                                        }

                                    } catch (ex: Exception) {
                                    }
                                }

                            })
                }

            }
        }
    }

    fun LoadStudentDetails(){
        myRef.child("Institute").child("Student").child("Student Info")
                .addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        if (p0!!.value!=null){
                            var td=p0!!.value as HashMap<String,Any>

                            for (key in td.keys){
                                var student=td[key] as HashMap<String,Any>

                                var stEmail=student["email"] as String

                                if (stEmail==mAuth!!.currentUser!!.email.toString()){
                                    studentName=student["name"] as String
                                    studentRoll=student["roll"] as String
                                }

                            }
                        }
                    }

                })
    }
}
