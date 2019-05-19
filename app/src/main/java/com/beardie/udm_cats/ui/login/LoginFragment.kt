package com.beardie.udm_cats.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beardie.udm_cats.R
import com.beardie.udm_cats.di.ViewModelFactory
import com.beardie.udm_cats.navigation.INavigation
import com.beardie.udm_cats.ui.map.MapFragment
import com.beardie.udm_cats.view_models.login.LoginViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var viewModel: LoginViewModel

  companion object {
    fun newInstance() = LoginFragment()
  }

  private lateinit var loginEditText: EditText
  private lateinit var passwordEditText: EditText
  private lateinit var loginButton: Button

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.login_fragment, container, false)
    loginEditText = view.findViewById(R.id.ed_login)
    passwordEditText = view.findViewById(R.id.ed_password)
    loginButton = view.findViewById(R.id.btn_login)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    loginButton.setOnClickListener {
      viewModel.login(loginEditText.text.toString(), passwordEditText.text.toString()).observe(this,
        Observer {
          if (it != null) {
            if (viewModel.isLogging)
              (activity as INavigation).navigateToWithReplace(MapFragment.newInstance())
            else
              Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
          }
        })
    }
  }


  override fun onStart() {
    super.onStart()
  }

  override fun onStop() {
    super.onStop()
  }
}