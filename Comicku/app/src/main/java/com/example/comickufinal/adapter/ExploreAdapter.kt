package com.example.comickufinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.ExploreResponse
import com.example.comickufinal.model.RecommendedResponse
import com.example.comickufinal.tools.Utilities
import kotlinx.android.synthetic.main.item_manga.view.*
import kotlinx.android.synthetic.main.item_popular_manga.view.*


class ExploreAdapter :
    RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    private var onSelectedMangaListener: OnSelectedMangaListener? = null
    var exploreResponseList: List<ExploreResponse.Manga>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circularProgressDrawable =
            Utilities.circularProgressDrawable(itemView.context)

        fun bindTo(mangaResponse: ExploreResponse.Manga?) {
            mangaResponse?.let {
                itemView.mangaItem_tvTitle.text = it.title
                itemView.mangaItem_tvType.text = it.type
                itemView.mangaItem_tvChapter.text = it.chapter
                itemView.mangaItem_tvUpdateOn.text = it.updatedOn

                // background type
                itemView.mangaItem_tvType.background =
                    Utilities.backgroundType(it.type, itemView.context)

                itemView.flag.background =
                    Utilities.flagType(it.type, itemView.context)

                Glide.with(itemView.context)
                    .load(it.thumb)
                    .placeholder(circularProgressDrawable)
                    .into(itemView.mangaItem_imageView)

                
                itemView.setOnClickListener { _ ->
                    onSelectedMangaListener?.onSelectedManga(it.endpoint)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_manga, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(exploreResponseList?.get(position))
    }

    override fun getItemCount(): Int {
        return exploreResponseList?.size ?: 0
    }

    fun setOnSelectedMangaListener(onSelectedMangaListener: OnSelectedMangaListener) {
        this.onSelectedMangaListener = onSelectedMangaListener
    }
}