package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null

    var database=FirebaseDatabase.getInstance()
    var myRef=database.reference

    private var adapter:CourseAdapter?=null
    private var courseList=ArrayList<Course>()
    private var layoutManager:RecyclerView.LayoutManager?=null

    var studentName:String?=null
    var studentImage:String?=null
    var studentRoll:String?=null
    var studentDOB:String?=null
    var studentYear:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        if (!isUserLogin()){
            LogOut()
        }
        setContentView(R.layout.activity_student)

        adapter= CourseAdapter(courseList,this)
        layoutManager= LinearLayoutManager(this)


        rvCourses.layoutManager=layoutManager
        rvCourses.adapter=adapter

        LoadStudentDetails()

        fab_to_add_student_courses.setOnClickListener {
            var addCourse=Intent(this,AddStudentCourses::class.java)
            startActivity(addCourse)
        }

        ivStudentInfo.setOnClickListener {
            if (studentName=="" && studentRoll==""){
                var intent=Intent(this,AddStudentDetails::class.java)
                startActivity(intent)
            }else{
                var intent=Intent(this,StudentInfo::class.java)
                intent.putExtra("Name",studentName)
                intent.putExtra("Image",studentImage)
                intent.putExtra("Roll",studentRoll)
                intent.putExtra("DOB",studentDOB)
                intent.putExtra("Year",studentYear)

            }

        }

        ivStudentLogOut.setOnClickListener {
            LogOut()
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
                                    studentImage=student["image"] as String
                                    studentRoll=student["roll"] as String
                                    studentDOB=student["dob"] as String
                                    studentYear=student["year"] as String
                                }

                            }

                            if (studentImage!=""){
                                Picasso.with(this@StudentActivity).load(studentImage).into(ivStudentInfo)
                            }

                            LoadCourses()
                        }

                    }

                })
    }

    fun LoadCourses(){

        if (studentRoll!=null || studentRoll!=""){
            myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll!!)
                    .addValueEventListener(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            var td=p0!!.value as HashMap<String,Any>

                            courseList.clear()

                            for (key in td.keys){
                                var courseDetails=td[key] as HashMap<String,Any>

                                courseList.add(Course(courseDetails["courseCode"] as String,courseDetails["courseName"] as String))
                            }

                            adapter!!.notifyDataSetChanged()
                        }

                    })
        }
    }

    fun isUserLogin():Boolean{
        if (mAuth!!.currentUser!=null){
            return true
        }
        return false
    }

    fun SignOut(){
        var signout= Intent(this,CategorySelectionActivity::class.java)
        startActivity(signout)
        finish()
    }

    fun LogOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){

                        SignOut()

                    }
                }
    }
}
