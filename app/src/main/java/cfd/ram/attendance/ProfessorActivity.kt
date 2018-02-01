package cfd.ram.attendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_professor.*

class ProfessorActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_professor)

        adapter= ProfCourseAdapter(profCourseList,this)
        layoutManager= LinearLayoutManager(this)

        rvProfCourses.layoutManager=layoutManager
        rvProfCourses.adapter=adapter

        for (i in 1..9){
            var c=ProfCourses("Course$i","Course$i Code")
            profCourseList.add(c)
        }
        adapter!!.notifyDataSetChanged()
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

                                profName=profDetails["name"] as String
                                profEmail=profDetails["email"] as String
                                profImage=profDetails["imageURL"] as String
                                profInstitute=profDetails["instituteName"] as String
                                profRank=profDetails["rank"] as String

                            }
                        }


                    }

                })
    }
}
