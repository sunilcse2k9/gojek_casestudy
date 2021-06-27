package com.gojek.casestudy.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojek.casestudy.model.Repository
import com.gojek.casestudy.model.Resource
import com.gojek.casestudy.network.ApiClient
import com.gojek.casestudy.network.repository.DataRepository
import com.gojek.casestudy.network.repository.SortingLogic
import com.gojek.casestudy.util.Utils
import com.target.casestudy.R
import com.gojek.casestudy.viewmodel.DataViewModel
import com.gojek.casestudy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var repositories = mutableListOf<Repository>()
  private val viewModel: DataViewModel by lazy {
    ViewModelProvider(
      this,
      ViewModelFactory(DataRepository(this, 2, ApiClient.build(), SortingLogic()))
    ).get(DataViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    rv_repositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rv_repositories.addItemDecoration(VerticalSpaceItemDecoration(Utils.convertDpToPixel(this, 2f).toInt()))

    viewModel.loadData(Utils.isInternetConnected(this))

    viewModel.resource.observe(this, Observer {
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
      viewModel.loadData(Utils.isInternetConnected(this))
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
  }
}
