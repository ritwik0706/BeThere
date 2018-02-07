package cfd.ram.attendance

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by RITWIK JHA on 31-01-2018.
 */
class CourseAdapter(private val courseList:ArrayList<Course>,private val context:Context):RecyclerView.Adapter<CourseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseAdapter.ViewHolder {
        val myView=LayoutInflater.from(context).inflate(R.layout.student_course_card,parent,false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder?, position: Int) {
        holder!!.BindItem(courseList[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun BindItem(course:Course){
            var name:TextView=itemView.findViewById(R.id.tvcourseName) as TextView
            var attendance:TextView=itemView.findViewById(R.id.tvAttendance) as TextView

            name.text=course.courseName
            attendance.text=course.attendance

            itemView.setOnClickListener {
                var intent=Intent(context,MarkAttendance::class.java)
                intent.putExtra("Code",course.courseName)
                context.startActivity(intent)
            }
        }
    }

}