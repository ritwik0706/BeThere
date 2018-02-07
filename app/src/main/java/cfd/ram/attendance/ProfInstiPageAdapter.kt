package cfd.ram.attendance

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by RITWIK JHA on 31-01-2018.
 */
class ProfInstiPageAdapter(private val profList:ArrayList<Professor>,private val context:Context):RecyclerView.Adapter<ProfInstiPageAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProfInstiPageAdapter.MyViewHolder{
        val myView=LayoutInflater.from(context).inflate(R.layout.prof_card,parent,false)

        return MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return profList.size
    }

    override fun onBindViewHolder(holder: ProfInstiPageAdapter.MyViewHolder, position: Int) {
        holder.BindItem(profList[position])
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun BindItem(prof:Professor){
            var name:TextView=itemView.findViewById(R.id.profName) as TextView
            var email:TextView=itemView.findViewById(R.id.profEmailId) as TextView

            name.text=prof.profName
            email.text=prof.profEmail

        }
    }

}


