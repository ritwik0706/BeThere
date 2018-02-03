package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null

    var database=FirebaseDatabase.getInstance()
    var myRef=database.reference

    private var adapter:CourseAdapter?=null
    private var courseList=ArrayList<Course>()
    private var layoutManager:RecyclerView.LayoutManager?=null

    var studentName:String?=null
    var studentInstitute:String?=null
    var studentProfName:String?=null
    var studentImage:String?=null
    var courseCode:String?=null
    var studentRoll:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_student)

        adapter= CourseAdapter(courseList,this)
        layoutManager=LinearLayoutManager(this)


        rvCourses.layoutManager=layoutManager
        rvCourses.adapter=adapter

        LoadStudentDetails()

        ivStudentInfo.setOnClickListener {
            var intent=Intent(this,AddStudentDetails::class.java)
            startActivity(intent)
        }
    }

    fun LoadStudentDetails(){
        myRef.child("Institute").child("Student")
                .addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        var td=p0!!.value as HashMap<String,Any>

                        for (key in td.keys){
                            var course=td[key] as HashMap<String,Any>

                            for (i in course.keys){
                                var student=course[i] as HashMap<String,Any>

                                var stEmail=student["email"] as String

                                if (stEmail==mAuth!!.currentUser!!.email.toString()){
                                    studentName=student["name"] as String
                                    studentImage=student["image"] as String
                                    studentInstitute=student["insName"] as String
                                    studentProfName=student["profName"] as String
                                    courseCode=student["courseCode"] as String
                                    studentRoll=student["roll"] as String
                                }
                            }
                        }

                        LoadCourses()
                    }

                })
    }

    fun LoadCourses(){

        myRef.child("Institute").child("Professor").child(studentInstitute!!).child(studentProfName!!).child(courseCode!!)
                .addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                       var courseDetails=p0!!.value as HashMap<String,Any>

                        courseList.add(Course(courseDetails["courseCode"] as String,courseDetails["courseName"] as String))

                        adapter!!.notifyDataSetChanged()
                    }

                })
    }
}
