package com.example.comickufinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.RecommendedResponse
import com.example.comickufinal.tools.Utilities
import kotlinx.android.synthetic.main.item_popular_manga.view.*


class RecommendedAdapter :
    RecyclerView.Adapter<RecommendedAdapter.ViewHolder>() {

    private var onSelectedMangaListener: OnSelectedMangaListener? = null
    var recommendedResponseList: List<RecommendedResponse.Manga>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circularProgressDrawable =
            Utilities.circularProgressDrawable(itemView.context)

        fun bindTo(mangaResponse: RecommendedResponse.Manga?) {
            mangaResponse?.let {
                itemView.simpleMangaItem_tvTitle.text = it.title




                Glide.with(itemView.context)
                    .load(it.thumb)
                    .placeholder(circularProgressDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(itemView.simpleMangaItem_imageView)

                itemView.setOnClickListener { _ ->
                    onSelectedMangaListener?.onSelectedManga(it.endpoint)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recomended_manga, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(recommendedResponseList?.get(position))
    }

    override fun getItemCount(): Int {
        return recommendedResponseList?.size ?: 0
    }

    fun setOnSelectedMangaListener(onSelectedMangaListener: OnSelectedMangaListener) {
        this.onSelectedMangaListener = onSelectedMangaListener
    }
}