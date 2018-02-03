package cfd.ram.attendance

import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_add_course_details.*

/**
 * Created by RITWIK JHA on 02-02-2018.
 */

class PopTime:DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var myView=inflater!!.inflate(R.layout.pop_time,container,false)

        var buDone=myView.findViewById(R.id.buDone) as Button
        var tp=myView.findViewById<TimePicker>(R.id.tp)

        var buID=arguments.getString("ID")

        buDone.setOnClickListener {

            val courseDetails=activity as AddCourseDetails

            when(buID){
                "cfd.ram.attendance:id/buStart1"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart1(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart1(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buStart2"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart2(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart2(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buStart3"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart3(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart3(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buStart4"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart4(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart4(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buStart5"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart5(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart5(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buStart6"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeStart6(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeStart6(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd1"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd1(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd1(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd2"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd2(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd2(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd3"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd3(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd3(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd4"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd4(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd4(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd5"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd5(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd5(tp.currentHour,tp.currentMinute)
                    }
                }
                "cfd.ram.attendance:id/buEnd6"->{
                    if (Build.VERSION.SDK_INT>=23){
                        courseDetails.SetTimeEnd6(tp.hour,tp.minute)
                    }else{
                        courseDetails.SetTimeEnd6(tp.currentHour,tp.currentMinute)
                    }
                }

            }

            this.dismiss()

        }


        return myView
    }

    companion object {
        fun newInstance(id:String):PopTime{
            var args=Bundle()
            args.putString("ID",id)

            val frag=PopTime()
            frag.arguments=args

            return frag
        }
    }
}