package com.example.comickufinal.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.comickufinal.R
import com.example.comickufinal.adapter.AllContentPagedListAdapter
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.ExploreResponse
import com.example.comickufinal.model.GenreMangaResponse
import com.example.comickufinal.model.MangaResponse
import com.example.comickufinal.model.PopularMangaResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.ui.detail.DetailMangaActivity
import com.example.comickufinal.ui.viewmodel.AllContentViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_all_content.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class AllContentActivity
    : AppCompatActivity(), OnSelectedMangaListener {
    object Type {
        const val ALL_EXPLORE = "Explore"
        const val ALL_POPULAR = "All Popular"
        const val ALL_MANGA = "All Manga"
        const val ALL_MANHUA = "All Manhua"
        const val ALL_MANHWA = "All Manhwa"
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_GENRE = "extra_genre"
        const val EXTRA_GENRE_ENDPOINT = "extra_genre_endpoint"
    }

    private lateinit var allContentViewModel: AllContentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_content)

        // title and genre endpoint
        var endpoint = ""
        val title = if (intent.getStringExtra(EXTRA_TYPE) != null) {
            intent.getStringExtra(EXTRA_TYPE)
        } else {
            endpoint = intent.getStringExtra(EXTRA_GENRE_ENDPOINT) ?: ""
            intent.getStringExtra(EXTRA_GENRE)
        }
        allContentAct_tvTitle.text = title

        // toolbar
        allContentAct_toolbar.title = ""
        setSupportActionBar(allContentAct_toolbar)
        allContentAct_toolbar.setNavigationOnClickListener {
            finish()
        }

        val container = findViewById(R.id.shimmer_content) as ShimmerFrameLayout
        container.startShimmer()

        // adapter
        val contentAdapter =
            when (title) {
                Type.ALL_POPULAR ->
                    AllContentPagedListAdapter<PopularMangaResponse.Manga>(this)
                Type.ALL_EXPLORE ->
                    AllContentPagedListAdapter<ExploreResponse.Manga>(this)
                Type.ALL_MANGA, Type.ALL_MANHUA, Type.ALL_MANHWA ->
                    AllContentPagedListAdapter<MangaResponse.Manga>(this)
                else ->
                    AllContentPagedListAdapter<GenreMangaResponse.Manga>(this)
            }
        allContentAct_recyclerView.adapter = contentAdapter

        // view model
        allContentViewModel = ViewModelProvider(this).get(AllContentViewModel::class.java)
        allContentViewModel.setType(title!!)

        @Suppress("UNCHECKED_CAST")
        when (title) {
            Type.ALL_POPULAR -> {
                allContentViewModel.popularManga.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<PopularMangaResponse.Manga>)
                        .submitList(it)
                }
            }
            Type.ALL_EXPLORE -> {
                allContentViewModel.explore.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<ExploreResponse.Manga>)
                        .submitList(it)
                }
            }
            Type.ALL_MANGA -> {
                allContentViewModel.manga.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<MangaResponse.Manga>)
                        .submitList(it)
                }
            }
            Type.ALL_MANHUA -> {
                allContentViewModel.manhua.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<MangaResponse.Manga>)
                        .submitList(it)
                }
            }
            Type.ALL_MANHWA -> {
                allContentViewModel.manhwa.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<MangaResponse.Manga>)
                        .submitList(it)
                }
            }
            else -> {
                allContentViewModel.setGenreEndpoint(endpoint)
                allContentViewModel.genreManga.observe(this) {
                    (contentAdapter as AllContentPagedListAdapter<GenreMangaResponse.Manga>)
                        .submitList(it)
                }
            }
        }

        allContentViewModel.networkState.observe(this) {
            if (!allContentViewModel.isListEmpty()) {
                contentAdapter.setNetworkState(it)
            }

            if (it == NetworkState.LOADED) {
                allContentAct_recyclerView.visibility = View.VISIBLE
                shimmer_content.visibility = View.GONE
            }

            if (it == NetworkState.END_OF_PAGE || it == NetworkState.NO_CONNECTION || it == NetworkState.TIMEOUT) {
                val toast:Toast = Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                val view = toast.view
                view?.background?.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                toast.show()
            }
        }
    }

    override fun onSelectedManga(endpoint: String) {
        val intent = Intent(this, DetailMangaActivity::class.java)
        intent.putExtra(DetailMangaActivity.EXTRA_MANGA_ENDPOINT, endpoint)
        startActivity(intent)
    }
}