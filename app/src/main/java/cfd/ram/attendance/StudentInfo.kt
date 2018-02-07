package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_student_info.*

class StudentInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        var b:Bundle=intent.extras
        var name=b.getString("Name")
        var image=b.getString("Image")
        var roll=b.getString("Roll")
        var year=b.getString("Year")
        var DOB=b.getString("DOB")

        tvStudentInfoName.text=name
        tvStudentInfoDOB.text=DOB
        tvStudentInfoYear.text=year
        tvStudentInfoRoll.text=roll

        Picasso.with(this).load(image).into(ivStudentInfoImage)

        buStudentInfoEdit.setOnClickListener {
            var intent= Intent(this,AddStudentDetails::class.java)
            intent.putExtra("Name",name)
            intent.putExtra("Image",image)
            intent.putExtra("Roll",roll)
            intent.putExtra("DOB",DOB)
            intent.putExtra("Year",year)
            startActivity(intent)
        }
    }
}
