package com.example.comickufinal.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.DetailMangaResponse
import com.example.comickufinal.tools.Utilities
import kotlinx.android.synthetic.main.item_manga.view.*
import kotlinx.android.synthetic.main.item_manga_no_card.view.*
import kotlinx.android.synthetic.main.item_manga_no_card.view.flag
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_imageView
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvChapter
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvTitle
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvType
import kotlinx.android.synthetic.main.item_manga_no_card.view.mangaItem_tvUpdateOn


class FavoriteAdapter(private val favoriteMangaList: List<DetailMangaResponse>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var onSelectedMangaListener: OnSelectedMangaListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val circularProgressDrawable =
            Utilities.circularProgressDrawable(itemView.context)

        fun bindTo(favoriteManga: DetailMangaResponse) {
            itemView.mangaItem_tvTitle.text = favoriteManga.title
            itemView.mangaItem_tvType.text = favoriteManga.type
            itemView.mangaItem_tvChapter.text = favoriteManga.status
            itemView.mangaItem_tvUpdateOn.text = favoriteManga.author

            // background type
            itemView.mangaItem_tvType.background =
                Utilities.backgroundType(favoriteManga.type, itemView.context)
            itemView.flag.background =
                Utilities.flagType(favoriteManga.type, itemView.context)

            // set image view height
            val layoutParams = itemView.mangaItem_imageView.layoutParams
            layoutParams.width = itemView.context.resources.getDimension(R.dimen.thumbnail_fav_history_width).toInt()

            Glide.with(itemView.context)
                .load(favoriteManga.thumb)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(circularProgressDrawable)
                .into(itemView.mangaItem_imageView)

            itemView.setOnClickListener { _ ->
                onSelectedMangaListener?.onSelectedManga(favoriteManga.mangaEndpoint)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_manga_no_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(favoriteMangaList.get(position))
    }

    override fun getItemCount(): Int {
        return favoriteMangaList.size
    }

    fun setOnSelectedMangaListener(onSelectedMangaListener: OnSelectedMangaListener) {
        this.onSelectedMangaListener = onSelectedMangaListener
    }
}