package com.intern_job.notification.activity.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.intern_job.notification.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyFragment : Fragment() {
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

lateinit var jobTitle :TextView
lateinit var jobBatch :TextView
lateinit var applyNow :Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        val title :String? = arguments?.getString("title")
        val batch :String? = arguments?.getString("batch")
        val link :String?= arguments?.getString("link")

        jobTitle = view.findViewById(R.id.title)
        jobBatch = view.findViewById(R.id.batch)
        applyNow = view.findViewById(R.id.applyNow)

        jobTitle.text = title
        jobBatch.text = batch

        applyNow.setOnClickListener {
            val uri : Uri = Uri.parse(link)
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
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
         * @return A new instance of fragment CompanyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}