package cfd.ram.attendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_institute.*

class InstitutePage : AppCompatActivity() {

    private var adapter:ProfInstiPageAdapter?=null
    private var profList=ArrayList<Professor>()
    private var layoutManager: RecyclerView.LayoutManager?=null

    var mAuth:FirebaseAuth?=null

    var database=FirebaseDatabase.getInstance()
    var myRef=database.reference

    var imgUrl:String=""
    var instituteName:String?=null
    var instituteEmail:String=""
    var instituteContact:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        if (!isUserLogin()){
            LogOut()
        }
        setContentView(R.layout.activity_institute)

        adapter= ProfInstiPageAdapter(profList,this)
        layoutManager= LinearLayoutManager(this)


        rvProfs.layoutManager=layoutManager
        rvProfs.adapter=adapter


        LoadInsInfo()


        institute_info.setOnClickListener {
            if (imgUrl!="" && instituteContact!="" && instituteEmail!="" && instituteName!=""){
                val instiInfo=Intent(this,InstituteInfo::class.java)
                startActivity(instiInfo)
            }else{
                var insInfo=Intent(this, AddInstituteInfo::class.java)
                startActivity(insInfo)
            }
        }

        fab_to_add_prof_details.setOnClickListener {
            val profDetails=Intent(this,AddProfDetails::class.java)
            startActivity(profDetails)
        }
        ivInstituteLogout.setOnClickListener {
            LogOut()
        }
    }

    fun LoadInsInfo(){

        myRef.child("Institute").child("Institute").child("Institute Info")
                .addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        if (p0!!.value!=null){
                            var td=p0!!.value as HashMap<String,Any>

                            for (key in td.keys){
                                if (key==mAuth!!.currentUser!!.uid){
                                    var ins=td[key] as HashMap<String,Any>

                                    insName.text=ins["name"] as String
                                    imgUrl=ins["imgUrl"] as String
                                    var insName=ins["name"] as String
                                    instituteEmail=ins["email"] as String
                                    instituteContact=ins["contact"] as String
                                    if (imgUrl!="") {
                                        Picasso.with(this@InstitutePage).load(imgUrl).into(institute_info)
                                    }

                                    instituteName=insName
                                }

                            }
                            LoadProfessor()
                        }
                        }


                })
    }

    fun LoadProfessor(){

        if (instituteName!=null){
            myRef.child("Institute").child("Institute").child("Professor Info").child(instituteName)
                    .addValueEventListener(object: ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            try {

                                profList.clear()

                                var td= p0!!.value as HashMap<String,Any>

                                for (key in td.keys){
                                    var prof=td[key] as HashMap<String,Any>


                                    profList.add(Professor(prof["name"] as String,
                                            prof["email"] as String))

                                    adapter!!.notifyDataSetChanged()

                                }
                            }catch (ex:Exception){ }




                        }

                    })
        }

    }

    fun isUserLogin():Boolean{
        if (mAuth!!.currentUser!=null){
            return true
        }
        return false
    }

    fun SignOut(){
        var signout= Intent(this,CategorySelectionActivity::class.java)
        startActivity(signout)
        finish()
    }

    fun LogOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){

                        SignOut()

                    }
                }
    }
}
