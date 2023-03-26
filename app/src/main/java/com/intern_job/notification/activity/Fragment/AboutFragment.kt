package com.intern_job.notification.activity.Fragment

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.intern_job.notification.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
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

    lateinit var s_github : ImageView
    lateinit var s_linkdin :ImageView
    lateinit var s_insta : ImageView
    lateinit var ss_github :ImageView
    lateinit var ss_linkdin:ImageView
    lateinit var ss_insta : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_about, container, false)

        s_github = view.findViewById(R.id.s_github)
        s_linkdin= view.findViewById(R.id.s_linkdin)
        s_insta = view.findViewById(R.id.s_insta)
        ss_github = view.findViewById(R.id.ss_github)
        ss_linkdin = view.findViewById(R.id.ss_linkdin)
        ss_insta = view.findViewById(R.id.ss_insta)


        s_github.setOnClickListener {
            val uri : Uri = Uri.parse("https://github.com/Shashi7083")
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
        ss_github.setOnClickListener {
            val uri : Uri = Uri.parse("https://github.com/surajks02")
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
        s_linkdin.setOnClickListener {
            val uri : Uri = Uri.parse("https://www.linkedin.com/in/shashi-ranjan-kumar-b44b26216/")
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }

        ss_linkdin.setOnClickListener {
            val uri : Uri = Uri.parse("https://www.linkedin.com/in/suraj-kr-suman/")
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
         }
        s_insta.setOnClickListener {
            val uri : Uri = Uri.parse("https://www.instagram.com/shashi.r.kumar/")
            val intent= Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }

        ss_insta.setOnClickListener {
            val uri : Uri = Uri.parse("https://www.instagram.com/_surajsuman_08/")
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
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}