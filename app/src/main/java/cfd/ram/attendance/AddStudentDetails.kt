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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_student_details.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddStudentDetails : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    val database = FirebaseDatabase.getInstance()
    var myRef = database.reference

    var name:String?=null
    var image:String?=null
    var roll:String?=null
    var year:String?=null
    var DOB:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_add_student_details)
        var b:Bundle=intent.extras
        name=b.getString("Name")
        image=b.getString("Image")
        roll=b.getString("Roll")
        year=b.getString("Year")
        DOB=b.getString("DOB")

        Picasso.with(this).load(image).into(ivStudent)
        /*tvStudentName.text=name.toString()
        tvStudentRoll.text=roll
        tvStudentDOB.text=DOB
        tvStudentYear.text=year*/

        ivStudent.setOnClickListener {
            CheckPermission()
        }

        buStudentDone.setOnClickListener {
            myRef.child("Institute").child("Student").child("Student Info").child(tvStudentRoll.text.toString().trim()).setValue(PostStudentinfoToFirebase(tvStudentName.text.toString().trim(),
                    tvStudentRoll.text.toString().trim(),DownloadUrl,tvStudentDOB.text.toString().trim(),tvStudentYear.text.toString().trim(),mAuth!!.currentUser!!.email.toString()))

            finish()
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
                    if (DownloadUrl!=""){
                        Picasso.with(this).load(DownloadUrl).into(ivStudent)
                    }
                }


    }

    fun SplitString(str:String):String{
        val split=str.split("@")
        return split[0]
    }
}


