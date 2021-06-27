package com.gojek.casestudy.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojek.casestudy.R
import com.gojek.casestudy.model.GitHubRepository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.network.ApiClient
import com.gojek.casestudy.repository.DataRepository
import com.gojek.casestudy.repository.SortingLogic
import com.gojek.casestudy.util.Utils
import com.gojek.casestudy.viewmodel.MainViewModel
import com.gojek.casestudy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var repositories = mutableListOf<GitHubRepository>()
  private val viewModel: MainViewModel by lazy {
    ViewModelProvider(
      this,
      ViewModelFactory(DataRepository(this, 2*60, ApiClient.build(), SortingLogic()))
    ).get(MainViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    rv_repositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rv_repositories.addItemDecoration(VerticalSpaceItemDecoration(Utils.convertDpToPixel(this, 2f).toInt()))

    viewModel.loadData(Utils.isInternetConnected(applicationContext))

    viewModel.resource.observe(this, {
      pull_refresh.isRefreshing = false

      when(it) {
        is Resource.Success -> {
          repositories.clear()
          repositories.addAll(it.data)
          rv_repositories.adapter = RepositoriesAdapter(repositories)
          pull_refresh.visibility = View.VISIBLE
          iv_menu.visibility = View.VISIBLE
          layer_error.visibility = View.GONE
          pb_loading.visibility = View.GONE
        }

        is Resource.Error -> {
          layer_error.visibility = View.VISIBLE
          pull_refresh.visibility = View.GONE
          pb_loading.visibility = View.GONE
          iv_menu.visibility = View.GONE
        }

        is Resource.Loading -> {
          pb_loading.visibility = View.VISIBLE
          pull_refresh.visibility = View.GONE
          layer_error.visibility = View.GONE
          iv_menu.visibility = View.GONE
        }
      }
    })

    btn_retry.setOnClickListener {
      viewModel.loadData(Utils.isInternetConnected(applicationContext))
    }

    iv_menu.setOnClickListener {
      val popupMenu = PopupMenu(this, iv_menu)
      popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)
      popupMenu.setOnMenuItemClickListener { menuItem ->
        if (menuItem?.itemId == R.id.sort_name) {
          viewModel.sortByNames(repositories)
        } else {
          viewModel.sortByStars(repositories)
        }

        true
      }

      popupMenu.show()
    }

    pull_refresh.setOnRefreshListener {
      pull_refresh.isRefreshing = true
      viewModel.loadData(Utils.isInternetConnected(applicationContext))
    }
  }
}
