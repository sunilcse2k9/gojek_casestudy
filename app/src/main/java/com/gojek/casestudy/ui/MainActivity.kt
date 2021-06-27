package com.gojek.casestudy.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.network.ApiClient
import com.gojek.casestudy.network.repository.NetworkRepository
import com.gojek.casestudy.util.Utils
import com.target.casestudy.R
import com.gojek.casestudy.viewmodel.DataViewModel
import com.gojek.casestudy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val viewModel: DataViewModel by lazy {
    ViewModelProvider(
      this,
      ViewModelFactory(NetworkRepository(this, 2, ApiClient.build()))
    ).get(DataViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    rv_repositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    viewModel.loadData(Utils.isInternetConnected(this))
    viewModel.resource.observe(this, Observer {
      when(it) {
        is Resource.Success -> {
          rv_repositories.adapter = RepositoriesAdapter(it.data)
        }

        is Resource.Error -> {

        }

        is Resource.Loading -> {

        }
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.credit_card -> {

        true
      }
      else -> false
    }
  }
}
