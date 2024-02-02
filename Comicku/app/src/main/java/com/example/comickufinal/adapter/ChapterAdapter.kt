package com.example.comickufinal.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comickufinal.R
import com.example.comickufinal.adapter.listener.OnSelectedChapterListener
import com.example.comickufinal.model.DetailMangaResponse
import kotlinx.android.synthetic.main.item_chapter.view.*


class ChapterAdapter :
    RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    private var onSelectedChapterListener: OnSelectedChapterListener? = null
    var chapterList: List<DetailMangaResponse.Chapter>? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(chapter: DetailMangaResponse.Chapter?) {
            chapter?.let {
                itemView.chapterItem_tv.text = it.chapterTitle

                itemView.setOnClickListener { _ ->
                    onSelectedChapterListener?.onSelectedChapter(
                        it.chapterTitle,
                        it.chapterEndpoint
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_chapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(chapterList?.get(position))
    }

    override fun getItemCount(): Int {
        return chapterList?.size ?: 0
    }

    fun setOnSelectedChapterEndpoint(onSelectedChapterListener: OnSelectedChapterListener) {
        this.onSelectedChapterListener = onSelectedChapterListener
    }
}