package com.example.comickufinal.ui.detail

import android.R.layout
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.comickufinal.R
import com.example.comickufinal.adapter.ChapterAdapter
import com.example.comickufinal.adapter.GenreAdapter
import com.example.comickufinal.adapter.listener.OnSelectedChapterListener
import com.example.comickufinal.model.DetailMangaResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.tools.Utilities
import com.example.comickufinal.ui.viewmodel.DetailViewModel
import com.example.custom.showCustomToast
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_detail_manga.*
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.fragment_account.*


class DetailMangaActivity : AppCompatActivity(), OnSelectedChapterListener {
    companion object {
        const val EXTRA_MANGA_ENDPOINT = "extra_endpoint"
    }

    private lateinit var mangaEndpoint: String
    private lateinit var chapterHistory: DetailMangaResponse.Chapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manga)

        // set endpoint
        mangaEndpoint = intent.getStringExtra(EXTRA_MANGA_ENDPOINT) ?: ""

        // toolbar
        detailMangaAct_toolbar.title = ""
        setSupportActionBar(detailMangaAct_toolbar)
        detailMangaAct_toolbar.setNavigationOnClickListener {
            finish()
        }

        // set default state button checked
        detailMangaAct_btnFavorite.isChecked = false

        // adapter
        val genreAdapter = GenreAdapter<DetailMangaResponse.Genre>()
        val chapterAdapter = ChapterAdapter()
        chapterAdapter.setOnSelectedChapterEndpoint(this)

        // layout manager
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP
        detailMangaAct_rvGenre.layoutManager = flexBoxLayoutManager

        // view model
        val detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.setMangaEndpoint(mangaEndpoint)

        detailViewModel.detailManga.observe(this) {
            detailMangaAct_tvTitle.text = it.title
            detailMangaAct_tvStatus.text = it.status
            detailMangaAct_tvAuthor.text = it.author
            detailMangaAct_tvSynopsis.text = it.synopsis
            detailMangaAct_tvType.text = it.type
            detailMangaAct_tvType.background =
                Utilities.backgroundType(it.type, this)

            // image view
            Glide.with(this)
                .load(it.thumb)
                .placeholder(R.drawable.icons8_no_image_100)
                .into(detailMangaAct_imageView)

            Glide.with(this)
                .load(it.thumb)
                .placeholder(R.drawable.icons8_no_image_100)
                .into(detailMangaAct_imageViewBackground)

            // recycler view
            genreAdapter.genreList = it.genreList
            detailMangaAct_rvGenre.adapter = genreAdapter

            chapterAdapter.chapterList = it.chapter.subList(0, 5)
            detailMangaAct_rvChapter.adapter = chapterAdapter
        }

        detailViewModel.favoriteMangaByEndpoint.observe(this) {
            detailMangaAct_btnFavorite.isChecked = it
        }

        detailViewModel.historyMangaByEndpoint.observe(this) {
            chapterHistory = if (it != null) {
                val historyTitle = it["chapter"]
                val historyEndpoint = it["lastChapter"]

                DetailMangaResponse.Chapter(historyEndpoint!!, historyTitle!!)
            } else {
                detailViewModel.detailManga.value!!.chapter.lastOrNull()!!
            }
        }

        detailViewModel.networkState.observe(this) {
            if (it == NetworkState.LOADING) {
                detailMangaAct_llContent.visibility = View.GONE
                detailMangaAct_progressBar.visibility = View.VISIBLE
            }

            if (it == NetworkState.LOADED) {
                detailMangaAct_llContent.visibility = View.VISIBLE
                detailMangaAct_progressBar.visibility = View.GONE
            }

            if (it == NetworkState.TIMEOUT) {
                val toast:Toast = Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                val view = toast.view
                view?.background?.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                toast.show()
            }
        }

        // button listener
        detailMangaAct_btnAllChapter.setOnClickListener {
            val intent = Intent(this, AllChapterActivity::class.java)
            intent.putExtra(AllChapterActivity.EXTRA_MANGA_ENDPOINT, mangaEndpoint)
            startActivity(intent)
        }

        detailMangaAct_btnFavorite.setOnClickListener {
            if (detailMangaAct_btnFavorite.isChecked) {
                Toast(this).showCustomToast (
                    "Added to Favorite",
                    this
                )
                detailViewModel.setFavoriteManga(detailViewModel.detailManga.value!!)
            } else {
                Toast(this).showCustomToast (
                    "Deleted from Favorite",
                    this
                )
                detailViewModel.deleteFavoriteManga(detailViewModel.detailManga.value!!)
            }
        }

        detailMangaAct_btnContinueReading.setOnClickListener {
            val intent = Intent(this, ChapterActivity::class.java)
            intent.putExtra(ChapterActivity.EXTRA_MANGA_ENDPOINT, mangaEndpoint)
            intent.putExtra(ChapterActivity.EXTRA_TITLE, chapterHistory.chapterTitle)
            intent.putExtra(ChapterActivity.EXTRA_CHAPTER_ENDPOINT, chapterHistory.chapterEndpoint)

            startActivity(intent)
        }
    }

    override fun onSelectedChapter(title: String, chapterEndpoint: String) {
        val intent = Intent(this, ChapterActivity::class.java)
        intent.putExtra(ChapterActivity.EXTRA_TITLE, title)
        intent.putExtra(ChapterActivity.EXTRA_CHAPTER_ENDPOINT, chapterEndpoint)
        intent.putExtra(ChapterActivity.EXTRA_MANGA_ENDPOINT, mangaEndpoint)

        startActivity(intent)
    }
}