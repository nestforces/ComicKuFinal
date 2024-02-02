package com.example.comickufinal.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.*
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.tools.Utilities
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_manga.view.*
import kotlinx.android.synthetic.main.item_manga_no_card.view.*
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_imageView
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvChapter
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvTitle
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvType
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvUpdateOn
import kotlinx.android.synthetic.main.item_manga.view.flag as flag1


class AllContentPagedListAdapter<T>(
    private val onSelectedMangaListener: OnSelectedMangaListener
) : PagedListAdapter<T, RecyclerView.ViewHolder>(ItemDiffCallback<T>()) {

    var favoriteMangaList: List<MangaResponse.Manga> = listOf()

    private val CONTENT_VIEW_TYPE = 1
    private val NETWORK_VIEW_TYPE = 2
    private var networkState: NetworkState? = null

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun <X> bindTo(item: X) {
            val circularProgressDrawable =
                Utilities.circularProgressDrawable(itemView.context)

            when (item) {
                is PopularMangaResponse.Manga -> {
                    itemView.mangaItem_tvTitle.text = item.title
                    itemView.mangaItem_tvType.text = item.type
                    itemView.mangaItem_tvUpdateOn.text = item.uploadOn

                    // background type
                    itemView.mangaItem_tvType.background =
                        Utilities.backgroundType(item.type, itemView.context)
                    // flag type
                    itemView.flag.background =
                        Utilities.flagType(item.type, itemView.context)

                    Glide.with(itemView.context)
                        .load(item.thumb)
                        .placeholder(circularProgressDrawable)
                        .into(itemView.mangaItem_imageView)

                    // listener
                    itemView.setOnClickListener {
                        onSelectedMangaListener.onSelectedManga(item.endpoint)
                    }
                }
                is MangaResponse.Manga -> {
                    itemView.mangaItem_tvTitle.text = item.title
                    itemView.mangaItem_tvType.text = item.type
                    itemView.mangaItem_tvChapter.text = item.chapter
                    itemView.mangaItem_tvUpdateOn.text = item.updatedOn

                    // background type
                    itemView.mangaItem_tvType.background =
                        Utilities.backgroundType(item.type, itemView.context)
                    // flag type
                    itemView.flag.background =
                        Utilities.flagType(item.type, itemView.context)

                    Glide.with(itemView.context)
                        .load(item.thumb)
                        .placeholder(circularProgressDrawable)
                        .into(itemView.mangaItem_imageView)

                    // listener
                    itemView.setOnClickListener {
                        onSelectedMangaListener.onSelectedManga(item.endpoint)
                    }
                }
                is ExploreResponse.Manga -> {
                    itemView.mangaItem_tvTitle.text = item.title
                    itemView.mangaItem_tvType.text = item.type
                    itemView.mangaItem_tvChapter.text = item.chapter
                    itemView.mangaItem_tvUpdateOn.text = item.updatedOn

                    // background type
                    itemView.mangaItem_tvType.background =
                        Utilities.backgroundType(item.type, itemView.context)
                    // flag type
                    itemView.flag.background =
                        Utilities.flagType(item.type, itemView.context)

                    Glide.with(itemView.context)
                        .load(item.thumb)
                        .placeholder(circularProgressDrawable)
                        .into(itemView.mangaItem_imageView)

                    // listener
                    itemView.setOnClickListener {
                        onSelectedMangaListener.onSelectedManga(item.endpoint)
                    }
                }
                is GenreMangaResponse.Manga -> {
                    itemView.mangaItem_tvTitle.text = item.title
                    itemView.mangaItem_tvType.text = item.type

                    // background type
                    itemView.mangaItem_tvType.background =
                        Utilities.backgroundType(item.type, itemView.context)
                    // flag type
                    itemView.flag.background =
                        Utilities.flagType(item.type, itemView.context)

                    Glide.with(itemView.context)
                        .load(item.thumb)
                        .placeholder(circularProgressDrawable)
                        .into(itemView.mangaItem_imageView)

                    // listener
                    itemView.setOnClickListener {
                        onSelectedMangaListener.onSelectedManga(item.endpoint)
                    }
                }
                is SearchMangaResponse.SearchMangaResponseItem -> {
                    itemView.mangaItem_tvTitle.text = item.title
                    itemView.mangaItem_tvType.text = item.type
                    itemView.mangaItem_tvUpdateOn.text = item.updatedOn

                    // background type
                    itemView.mangaItem_tvType.background =
                        Utilities.backgroundType(item.type, itemView.context)
                    // flag type
                    itemView.flag.background =
                        Utilities.flagType(item.type, itemView.context)

                    Glide.with(itemView.context)
                        .load(item.thumb)
                        .placeholder(circularProgressDrawable)
                        .into(itemView.mangaItem_imageView)

                    // listener
                    itemView.setOnClickListener {
                        onSelectedMangaListener.onSelectedManga(item.endpoint)
                    }
                }
            }
        }
    }

    inner class NetworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.loadingItem_progressBar.visibility = View.VISIBLE
            } else {
                itemView.loadingItem_progressBar.visibility = View.GONE
            }
        }
    }

    private class ItemDiffCallback<U> : DiffUtil.ItemCallback<U>() {
        override fun areItemsTheSame(oldItem: U, newItem: U): Boolean {
            return if (oldItem is PopularMangaResponse.Manga && newItem is PopularMangaResponse.Manga) {
                oldItem.endpoint == newItem.endpoint
            } else {
                false
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: U, newItem: U): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        return if (viewType == CONTENT_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.item_manga
, parent, false)
            ContentViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.item_loading, parent, false)
            NetworkViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CONTENT_VIEW_TYPE) {
            (holder as AllContentPagedListAdapter<*>.ContentViewHolder)
                .bindTo(getItem(position))
        } else {
            (holder as AllContentPagedListAdapter<*>.NetworkViewHolder).bindTo(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            CONTENT_VIEW_TYPE
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousNetwork = this.networkState
        val hadExtraRow = hasExtraRow()

        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())             // remove progress bar
            } else {
                notifyItemInserted(super.getItemCount())            // add progress bar
            }
        } else if (hasExtraRow && previousNetwork != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}