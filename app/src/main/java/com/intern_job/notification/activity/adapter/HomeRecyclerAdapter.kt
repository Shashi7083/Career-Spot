package com.intern_job.notification.activity.adapter

import android.annotation.SuppressLint
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
import com.intern_job.notification.R
import com.intern_job.notification.activity.Fragment.CompanyFragment
import com.intern_job.notification.activity.model.homeRecyclerModel

class HomeRecyclerAdapter(val context: Context, val list:ArrayList<homeRecyclerModel>) :RecyclerView.Adapter<HomeRecyclerAdapter.viewHolder>(){



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item_view,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
     val company = list.get(position)

        holder.company_name.text = company.company
        holder.batch.text = company.batch
       // holder.lastDate.text = company.lastDate


        holder.home_item.setOnClickListener {

            var args : Bundle = Bundle()
            args.putString("title",company.company)
            args.putString("batch",company.batch)
            args.putString("link",company.url)

            val fragmentManager : FragmentManager = (context as FragmentActivity).supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val  fragment = CompanyFragment()
            fragment.arguments=args
            fragmentTransaction.replace(R.id.frame,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        


    }

    override fun getItemCount(): Int {
        return list.size
    }
  class viewHolder(view : View):RecyclerView.ViewHolder(view) {
       val company_name: TextView = view.findViewById(R.id.company)
      val batch :TextView = view.findViewById(R.id.batch)
      val lastDate :TextView = view.findViewById(R.id.lastDate)
      val home_item :CardView = view.findViewById(R.id.home_item)
      val img_Fav : ImageView = view.findViewById(R.id.imgFav)
    }

}