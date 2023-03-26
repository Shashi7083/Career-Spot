package com.intern_job.notification.activity.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern_job.notification.R
import com.intern_job.notification.activity.Fragment.CompanyFragment
import com.intern_job.notification.activity.Fragment.TopCompanyPageFragment
import com.intern_job.notification.activity.model.topcompanyModel


class TopCompanyAdaptar(val context: Context,val list :ArrayList<topcompanyModel>) : RecyclerView.Adapter<TopCompanyAdaptar.viewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.top_company_item_view,parent,false)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val company = list.get(position)

        holder.company_name.text = company.companyName
        holder.sector.text = company.sector

        //Picasso.get().load(company.img).error(R.drawable.cognizant).into(holder.imgView)
        Glide.with(context).load(company.img).error(R.drawable.applogo).into(holder.imgView)


        holder.top_cmpny.setOnClickListener {


          if(company.carrier!=null) {
              var args: Bundle = Bundle()

              args.putString("link", company.carrier)

              val fragmentManager: FragmentManager =
                  (context as FragmentActivity).supportFragmentManager
              val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
              val fragment = TopCompanyPageFragment()
              fragment.arguments = args
              fragmentTransaction.replace(R.id.frame, fragment)
              fragmentTransaction.addToBackStack(null)
              fragmentTransaction.commit()
          }
        }

    }

    override fun getItemCount(): Int {
      return list.size
    }


    class viewHolder(view: View): RecyclerView.ViewHolder(view){
          val imgView :ImageView = view.findViewById(R.id.imageView)
        val company_name :TextView = view.findViewById(R.id.company_name)
        val sector :TextView = view.findViewById(R.id.textView2)
        val top_cmpny :CardView = view.findViewById(R.id.top_cmpny)

    }

}