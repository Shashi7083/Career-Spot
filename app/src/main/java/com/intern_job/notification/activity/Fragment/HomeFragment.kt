package com.intern_job.notification.activity.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.intern_job.notification.R
import com.intern_job.notification.activity.Database.HomeDBHelper
import com.intern_job.notification.activity.adapter.HomeRecyclerAdapter

import com.intern_job.notification.activity.model.homeRecyclerModel
import com.intern_job.notification.activity.util.ConnectionManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = "Shashi"
    private var param2: String? = null


    lateinit var recyclerView : RecyclerView
    lateinit var anime :ConstraintLayout
    lateinit var animation:com.airbnb.lottie.LottieAnimationView
    val companyList = arrayListOf<homeRecyclerModel>()
    var commonList = arrayListOf<homeRecyclerModel>()
    var fromSearchList = arrayListOf<homeRecyclerModel>()
    var compareCmpList = arrayListOf<homeRecyclerModel>()
    lateinit var adapter: HomeRecyclerAdapter
    lateinit var searchView :androidx.appcompat.widget.SearchView
    lateinit var homeDBHelper:HomeDBHelper





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        anime = view.findViewById(R.id.anime)
        animation = view.findViewById(R.id.animation_view)
        searchView = view.findViewById(R.id.searchView)


        adapter = HomeRecyclerAdapter(activity as Context,commonList)

        homeDBHelper = HomeDBHelper(activity as Context)



        //play animation in start
        animation.setAnimation(R.raw.loading)
        animation.repeatCount = LottieDrawable.INFINITE
        animation.playAnimation()
        anime.visibility= View.VISIBLE

        //Get data from Database
        val dbList = homeDBHelper.getAllHomeCompany()
        Log.e("dblist","${dbList.size}")

        if(dbList.size!=0) {

            recyclerView.adapter = adapter
            commonList.clear()
            commonList.addAll(dbList)
            adapter.notifyDataSetChanged()
            anime.visibility = View.GONE
            fromSearchList.clear()

            fromSearchList.addAll(dbList)


        }
       else{
           


            //check internet Connection
            if(ConnectionManager().checkConnectivity(activity as Context)==true) {

                if(companyList.size ==0){

                    animation.setAnimation(R.raw.loading)
                    animation.repeatCount = LottieDrawable.INFINITE
                    animation.playAnimation()
                    anime.visibility= View.VISIBLE

                    val queue = Volley.newRequestQueue(activity as Context)
                    val url =
                        "https://script.google.com/macros/s/AKfycbz-zexeZOBed6cONqY70wPi-ljym-QRUJDVJi1Rp3tdtGx2XEDuov2aipICZ-3PpRMHCw/exec"

                    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->


                        val data = response.getJSONArray("notification")

                        for (i in 0 until data.length()) {

                            val companyJsonObject = data.getJSONObject(i)

                            val notifyObject = homeRecyclerModel(
                                companyJsonObject.getString("sno").toInt(),
                                companyJsonObject.getString("company"),
                                companyJsonObject.getString("batch"),
                                companyJsonObject.getString("date")

                            )

                            companyList.add(notifyObject)

                            //Insert list in Database
                            val c = homeRecyclerModel(companyList.get(i).sno,companyList.get(i).company,companyList.get(i).batch,companyList.get(i).url)
                            fromSearchList.add(c)
                            val status = homeDBHelper.insertCompany(c)
                            //check inset success or not
                            if(status>-1){
                                    //Inserted
                            }
                            else{
                                //Not Inserted
                            }


                        }


                        recyclerView.adapter = adapter
                        commonList.clear()
                        commonList.addAll(companyList)
                        adapter.notifyDataSetChanged()
                        anime.visibility=View.GONE


                    }, { error ->
                        Toast.makeText(activity as Context, "Check Your Internet ", Toast.LENGTH_SHORT)
                            .show()
                    })
                    queue.add(jsonObjectRequest)


                }
                else{



                    recyclerView.adapter = adapter
                    commonList.clear()
                    commonList.addAll(companyList)
                    adapter.notifyDataSetChanged()
                }

            }
            else{
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("No Internet")
                dialog.setPositiveButton("Open Setting"){
                        text,listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    activity?.finish()

                }
                dialog.setNegativeButton("Cancel"){
                        text,listener->
                    // ActivityCompat.finishAffinity(requireActivity())

                    animation.setAnimation(R.raw.offline_internet)
                    animation.repeatCount = LottieDrawable.INFINITE
                    animation.playAnimation()
                    anime.visibility= View.VISIBLE
                }
                dialog.create()
                dialog.show()
            }

        }








//Implement Search View
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                commonList.clear()
                for(i in 0..fromSearchList.size-1){
                    if(subStr(fromSearchList.get(i).company,query) == true || subStr(fromSearchList.get(i).batch,query)==true || query.equals(fromSearchList.get(i).company)||query.equals(fromSearchList.get(i).batch)){
                        commonList.add(fromSearchList.get(i))
                    }
                }
                adapter.notifyDataSetChanged()
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                commonList.clear()
                for(i in 0..fromSearchList.size-1){
                    if(subStr(fromSearchList.get(i).company,newText) == true || subStr(fromSearchList.get(i).batch,newText)==true){
                        commonList.add(fromSearchList.get(i))

                    }
                }
                adapter.notifyDataSetChanged()
                return false
            }

        })




        //compare data base and online data
        if(ConnectionManager().checkConnectivity(activity as Context)==true) {

            if(companyList.size ==0){


                val queue = Volley.newRequestQueue(activity as Context)
                val url =
                    "https://script.google.com/macros/s/AKfycbz-zexeZOBed6cONqY70wPi-ljym-QRUJDVJi1Rp3tdtGx2XEDuov2aipICZ-3PpRMHCw/exec"

                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->


                    val data = response.getJSONArray("notification")

                    for (i in 0 until data.length()) {

                        val companyJsonObject = data.getJSONObject(i)

                        val notifyObject = homeRecyclerModel(
                            companyJsonObject.getString("sno").toInt(),
                            companyJsonObject.getString("company"),
                            companyJsonObject.getString("batch"),
                            companyJsonObject.getString("date")

                        )

                        compareCmpList.add(notifyObject)


                    }


                    if(compareCmpList.size!=0){

                        if(dbList.equals(compareCmpList)){
                          //  Toast.makeText(activity as Context, "equal", Toast.LENGTH_SHORT).show()
                        }
                        else{
                          //  Toast.makeText(activity as Context, "not equal", Toast.LENGTH_SHORT).show()
                            homeDBHelper.deleteAllHomeData()
                            for(i in 0 until compareCmpList.size){
                                val c = homeRecyclerModel(compareCmpList.get(i).sno,compareCmpList.get(i).company,compareCmpList.get(i).batch,compareCmpList.get(i).url)

                                val status = homeDBHelper.insertCompany(c)
                                //check inset success or not
                                if(status>-1){
                                    //Inserted
                                   // Toast.makeText(activity as Context, "insert successful", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    //Not Inserted
                                   // Toast.makeText(activity as Context, "not inserted", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }




                }, { error ->
                    Toast.makeText(activity as Context, "Check Your Internet ", Toast.LENGTH_SHORT)
                        .show()
                })
                queue.add(jsonObjectRequest)


            }


        }



        return view
    }


    fun subStr( name :   String?,str : String?): Boolean? {
        val result = name?.contains(""+str,ignoreCase = true)

        return result
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }



    }


}