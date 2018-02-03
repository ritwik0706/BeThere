package cfd.ram.attendance

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ArrayAdapter.createFromResource
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_prof_details.*
import kotlinx.android.synthetic.main.activity_add_student_details.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddStudentDetails : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    val database = FirebaseDatabase.getInstance()
    var myRef = database.reference

    var instituteList = ArrayList<String>()
    var proflist = ArrayList<String>()
    var courseList = ArrayList<String>()

    var instituteName:String?=null
    var profName:String?=null
    var courseCode:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_add_student_details)

        instituteList.add("Institutes")
        proflist.add("Professors")
        courseList.add("Course Code")

        LoadInsituteList()

        ivStudent.setOnClickListener {
            CheckPermission()
        }

        buStudentDone.setOnClickListener {
            myRef.child("Institute").child("Student").child(tvStudentName.text.toString().trim()).child(courseCode!!).setValue(PostStudentinfoToFirebase(tvStudentName.text.toString().trim(),
                    tvStudentRoll.text.toString().trim(),DownloadUrl,instituteName!!,profName!!,courseCode!!,mAuth!!.currentUser!!.email.toString()))

            finish()
        }
    }

    fun LoadInsituteList(){
        myRef.child("Institute").child("Institute").child("Professor Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        try {


                            var td = p0!!.value as HashMap<String, Any>

                            for (key in td.keys) {
                                instituteList.add(key)
                            }

                        } catch (ex: Exception) {
                        }
                    }

                })

        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,instituteList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        instituteSpinner.adapter=adapter

        instituteSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                instituteName=parent!!.getItemAtPosition(position).toString()

                LoadProfList()
            }

        }


    }

    fun LoadProfList(){

        if (instituteName!=null){
            myRef.child("Institute").child("Institute").child("Professor Info").child(instituteName)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {

                                var td = p0!!.value as HashMap<String, Any>

                                for (key in td.keys) {
                                    proflist.add(key)
                                }

                            } catch (ex: Exception) {
                            }
                        }

                    })

            var profAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,proflist)

            profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            ProfSpinner.adapter=profAdapter

            ProfSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    profName=parent!!.getItemAtPosition(position).toString()

                    LoadCourseList()
                }

            }
        }
    }

    fun LoadCourseList(){
        if (profName!=null){

            myRef.child("Institute").child("Professor").child(instituteName).child(profName)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {

                                var td = p0!!.value as HashMap<String, Any>

                                for (key in td.keys) {
                                    courseList.add(key)
                                }

                            } catch (ex: Exception) {
                            }
                        }

                    })

            var courseAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,courseList)

            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            courseSpinner.adapter=courseAdapter

            courseSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    courseCode=parent!!.getItemAtPosition(position).toString()
                }

            }
        }
    }

    var READIMAGE:Int=253

    fun CheckPermission(){
        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                return
            }
        }
        LoadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            READIMAGE->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    LoadImage()
                }else{
                    Toast.makeText(applicationContext,"Can't Grant Permission", Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    var PICK_IMAGE_CODE=123

    fun LoadImage(){
        var photoGallery= Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(photoGallery,PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==PICK_IMAGE_CODE && data!=null && resultCode== Activity.RESULT_OK){

            val selectedImage=data.data
            val filePathCol= arrayOf(MediaStore.Images.Media.DATA)
            var cursor=contentResolver.query(selectedImage,filePathCol,null,null,null)
            cursor.moveToFirst()
            val colIndex=cursor.getColumnIndex(filePathCol[0])
            val picturePath=cursor.getString(colIndex)
            cursor.close()

            UploadImage(BitmapFactory.decodeFile(picturePath))
        }
    }

    var DownloadUrl:String=""

    fun UploadImage(bitmap: Bitmap){

        val storage= FirebaseStorage.getInstance()
        val storageRef=storage.getReferenceFromUrl("gs://attendance-b61a4.appspot.com")
        val df= SimpleDateFormat("ddMMyyHHmmss")
        val dateObj= Date()
        val imgPath=SplitString(tvStudentName.text.toString())+"."+ df.format(dateObj)+ ".jpg"
        val imgRef=storageRef.child("Student/"+imgPath)
        var baos= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data=baos.toByteArray()
        val uploadTask=imgRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"Uploading Image Failed", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
                    DownloadUrl=taskSnapshot.downloadUrl!!.toString()
                    Picasso.with(this).load(DownloadUrl).into(ivStudent)
                }


    }

    fun SplitString(str:String):String{
        var split=str.split("@")
        return split[0]
    }
}


