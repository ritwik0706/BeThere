package cfd.ram.attendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_professor.*

class ProfessorActivity : AppCompatActivity() {

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
}
