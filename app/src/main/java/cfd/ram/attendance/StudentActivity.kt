package cfd.ram.attendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {

    private var adapter:CourseAdapter?=null
    private var courseList=ArrayList<Course>()
    private var layoutManager:RecyclerView.LayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        adapter= CourseAdapter(courseList,this)
        layoutManager=LinearLayoutManager(this)


        rvCourses.layoutManager=layoutManager
        rvCourses.adapter=adapter

        for (i in 1..9){
            val course=Course("Course $i","Attendance $i/9")
            courseList.add(course)
        }

        adapter!!.notifyDataSetChanged()


    }
}
