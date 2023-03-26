package com.intern_job.notification.activity.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.intern_job.notification.R
import com.intern_job.notification.activity.adapter.HomeRecyclerAdapter
import com.intern_job.notification.activity.adapter.TopCompanyAdaptar
import com.intern_job.notification.activity.model.homeRecyclerModel
import com.intern_job.notification.activity.model.topcompanyModel
import com.intern_job.notification.activity.util.ConnectionManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopCompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopCompanyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    lateinit var recyclerView : RecyclerView
    lateinit var anime : ConstraintLayout
    lateinit var animation:com.airbnb.lottie.LottieAnimationView
    val companyList = arrayListOf<topcompanyModel>()
    lateinit var adapter: TopCompanyAdaptar
    val duplicateList = arrayListOf<topcompanyModel>()



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_top_company, container, false)

        recyclerView = view.findViewById(R.id.topCompanyrecycler)

        anime = view.findViewById(R.id.anime1)
        animation = view.findViewById(R.id.animation_view1)

        adapter =  TopCompanyAdaptar(activity as Context ,duplicateList)



        if(ConnectionManager().checkConnectivity(activity as Context)==true) {

            if(companyList.size ==0){

                animation.setAnimation(R.raw.loading)
                animation.repeatCount = LottieDrawable.INFINITE
                animation.playAnimation()
                anime.visibility= View.VISIBLE

                val queue = Volley.newRequestQueue(activity as Context)
                val url =
                    "https://script.google.com/macros/s/AKfycbx7UQ8_r3UPM9b8lTh1-CfNNZrPNeuw8OC6MlSmNs-q2FxrwZ2ljGRDM4zTe3jNCMjftA/exec"

                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->


                    val data = response.getJSONArray("notification")

                    for (i in 0 until data.length()) {

                        val companyJsonObject = data.getJSONObject(i)

                        val notifyObject = topcompanyModel(
                            companyJsonObject.getString("sno").toInt(),
                            companyJsonObject.getString("company"),
                            companyJsonObject.getString("sector"),
                            companyJsonObject.getString("img"),
                            companyJsonObject.getString("carrier")

                        )

                        companyList.add(notifyObject)


                    }


                    recyclerView.adapter = adapter
                    duplicateList.clear()
                    duplicateList.addAll(companyList)
                    adapter.notifyDataSetChanged()
                    anime.visibility=View.GONE


                }, { error ->
                    Toast.makeText(activity as Context, "Check Your Internet ", Toast.LENGTH_SHORT)
                        .show()
                })
                queue.add(jsonObjectRequest)

            }
            else{


                adapter = TopCompanyAdaptar(requireContext(), duplicateList)
                recyclerView.adapter = adapter
                duplicateList.clear()
                duplicateList.addAll(companyList)
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
//
                animation.setAnimation(R.raw.offline_internet)
                animation.repeatCount = LottieDrawable.INFINITE
                animation.playAnimation()
                anime.visibility= View.VISIBLE
            }
            dialog.create()
            dialog.show()
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopCompanyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopCompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}