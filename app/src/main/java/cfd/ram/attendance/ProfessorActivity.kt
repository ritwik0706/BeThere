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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_institute.*
import kotlinx.android.synthetic.main.activity_prof_info.*
import kotlinx.android.synthetic.main.activity_professor.*

class ProfessorActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null

    var profName:String?=null
    var profEmail:String?=null
    var profRank:String?=null
    var profInstitute:String?=null
    var profImage:String?=null

    var database=FirebaseDatabase.getInstance()
    var myRef=database.reference

    private var adapter:ProfCourseAdapter?=null
    private var profCourseList=ArrayList<ProfCourses>()
    private var layoutManager: RecyclerView.LayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth=FirebaseAuth.getInstance()
        setContentView(R.layout.activity_professor)

        adapter= ProfCourseAdapter(profCourseList,this)
        layoutManager= LinearLayoutManager(this)

        rvProfCourses.layoutManager=layoutManager
        rvProfCourses.adapter=adapter

        LoadProfInfo()

        fab_add_prof_course.setOnClickListener {
            var profCourseInfo=Intent(this,AddCourseDetails::class.java)
            startActivity(profCourseInfo)
        }

        prof_info.setOnClickListener {
            var intent= Intent(this,ProfInfo::class.java)
            startActivity(intent)
        }
    }

    fun LoadCourses(){

            myRef.child("Institute").child("Professor").child(profInstitute).child(profName)
                    .addValueEventListener(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {
                                if (p0!!.value!=null){

                                    profCourseList.clear()

                                    var td=p0!!.value as HashMap<String,Any>

                                    for (key in td.keys){
                                        var course=td[key] as HashMap<String,Any>

                                        var courseCode=course["courseCode"] as String
                                        var courseName=course["courseName"] as String

                                        profCourseList.add(ProfCourses(courseName,courseCode))
                                    }
                                    adapter!!.notifyDataSetChanged()
                                }
                            }catch (ex:Exception){}
                        }

                    })


    }

    fun LoadProfInfo(){

        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        var td=p0!!.value as HashMap<String,Any>

                        for (key in td.keys){
                            var prof=td[key] as HashMap<String,Any>

                            for (i in prof.keys){
                                var profDetails=prof[i] as HashMap<String,Any>

                                var name=profDetails["name"] as String
                                profEmail=profDetails["email"] as String
                                profImage=profDetails["imageURL"] as String
                                profInstitute=profDetails["instituteName"] as String
                                profRank=profDetails["rank"] as String

                                if (profEmail==mAuth!!.currentUser!!.email){
                                    profName=name
                                    tvProf.text=name
                                    if (profImage!=""){
                                        Picasso.with(this@ProfessorActivity).load(profImage).into(prof_info)
                                    }
                                }

                            }
                        }
                        LoadCourses()
                    }

                })
    }
}
