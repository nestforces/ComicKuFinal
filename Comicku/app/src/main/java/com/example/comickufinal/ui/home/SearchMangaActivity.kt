package com.example.comickufinal.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.comickufinal.R
import com.example.comickufinal.adapter.AllContentPagedListAdapter
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.SearchMangaResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.ui.detail.DetailMangaActivity
import com.example.comickufinal.ui.viewmodel.AllContentViewModel
import kotlinx.android.synthetic.main.activity_search_manga.*

class SearchMangaActivity
    : AppCompatActivity(), OnSelectedMangaListener {

    private lateinit var allContentViewModel: AllContentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_manga)

        // progress bar
        searchMangaAct_progressBar.visibility = View.GONE

        // toolbar
        searchMangaAct_toolbar.title = ""
        setSupportActionBar(searchMangaAct_toolbar)
        searchMangaAct_toolbar.setNavigationOnClickListener {
            finish()
        }

        // adapter
        val adapter =
            AllContentPagedListAdapter<SearchMangaResponse.SearchMangaResponseItem>(this)
        searchMangaAct_recyclerView.adapter = adapter

        // search
        searchMangaAct_inputSearch.editText?.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                allContentViewModel.setSearchKeyword(textView.text.toString())
                searchMangaAct_progressBar.visibility = View.VISIBLE
                true
            } else {
                false
            }
        }

        // view model
        allContentViewModel = ViewModelProvider(this).get(AllContentViewModel::class.java)

        allContentViewModel.searchManga.observe(this) {
            adapter.submitList(it)
        }

        allContentViewModel.networkState.observe(this) {
            if (it == NetworkState.LOADED) {
                searchMangaAct_progressBar.visibility = View.GONE
            }
        }
    }

    override fun onSelectedManga(endpoint: String) {
        val intent = Intent(this, DetailMangaActivity::class.java)
        intent.putExtra(DetailMangaActivity.EXTRA_MANGA_ENDPOINT, endpoint)
        startActivity(intent)
    }
}