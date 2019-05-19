package com.beardie.udm_cats.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.beardie.udm_cats.R
import com.beardie.udm_cats.navigation.INavigation

class ProfileFragment : Fragment() {

  companion object {
    fun newInstance() = ProfileFragment()
  }

  private lateinit var backButton: ImageView
  private lateinit var title: TextView

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.profile_fragment, container, false)
    backButton = view.findViewById(R.id.iv_back_button)
    backButton.setOnClickListener { (activity as INavigation).navigateBack() }
    title = view.findViewById(R.id.tv_login)
    return view
  }
}