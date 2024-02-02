package com.example.comickufinal.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedGenreListener
import com.example.comickufinal.model.DetailMangaResponse
import com.example.comickufinal.model.GenreResponse
import kotlinx.android.synthetic.main.item_genre.view.*


class GenreAdapter<T> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onSelectedGenreListener: OnSelectedGenreListener? = null
    var genreList: List<T>? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun <X> bindTo(genre: X?) {
            if (genre is GenreResponse.Genre) {
                itemView.genreItem_button.text = genre.genre_name

                val layoutParam = itemView.genreItem_button.layoutParams
                if (layoutParam is GridLayoutManager.LayoutParams) {
                    layoutParam.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParam.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParam.setMargins(5)
                }

                itemView.setOnClickListener {
                    onSelectedGenreListener?.onSelectedGenre(genre.genre_name, genre.genre_name)
                }
            }

            if (genre is DetailMangaResponse.Genre) {
                itemView.genreItem_button.text = genre.genreName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GenreAdapter<*>.GenreViewHolder).bindTo(genreList?.get(position))
    }

    override fun getItemCount(): Int {
        return genreList?.size ?: 0
    }

    fun setOnSelectedGenreListener(onSelectedGenreListener: OnSelectedGenreListener) {
        this.onSelectedGenreListener = onSelectedGenreListener
    }
}