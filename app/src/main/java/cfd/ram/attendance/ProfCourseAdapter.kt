package cfd.ram.attendance

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by RITWIK JHA on 01-02-2018.
 */
class ProfCourseAdapter(private val profCourseList:ArrayList<ProfCourses>,private val context: Context):RecyclerView.Adapter<ProfCourseAdapter.VHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProfCourseAdapter.VHolder {
        val myView=LayoutInflater.from(context).inflate(R.layout.prof_course_view_card,parent,false)

        return ProfCourseAdapter.VHolder(myView)
    }

    override fun getItemCount(): Int {
        return profCourseList.size
    }

    override fun onBindViewHolder(holder: VHolder?, position: Int) {
        holder!!.BindItem(profCourseList[position])
    }

    class VHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun BindItem(prof_course:ProfCourses){
            var name: TextView =itemView.findViewById(R.id.tvProfCourseName) as TextView
            var email: TextView =itemView.findViewById(R.id.tvProfCourseCode) as TextView

            name.text=prof_course.courseName
            email.text=prof_course.courseCode
        }

    }

}