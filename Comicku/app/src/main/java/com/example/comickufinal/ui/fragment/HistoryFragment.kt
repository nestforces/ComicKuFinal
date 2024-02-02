package com.example.comickufinal.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.comickufinal.R
import com.example.comickufinal.adapter.HistoryAdapter
import com.example.comickufinal.adapter.listener.OnSelectedHistoryListener
import com.example.comickufinal.ui.detail.ChapterActivity
import com.example.comickufinal.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryFragment : Fragment(), OnSelectedHistoryListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view model
        val homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        homeViewModel.historyManga.observe(requireActivity()) {
            val adapter = HistoryAdapter(it)
            adapter.setOnSelectedHistoryListener(this)
            view.historyFrag_recyclerView.adapter = adapter
        }
    }

    override fun onSelectedHistory(history: Map<String, String>) {
        val intent = Intent(requireContext(), ChapterActivity::class.java)
        intent.putExtra(ChapterActivity.EXTRA_TITLE, history["chapter"])
        intent.putExtra(ChapterActivity.EXTRA_CHAPTER_ENDPOINT, history["lastChapter"])
        intent.putExtra(ChapterActivity.EXTRA_MANGA_ENDPOINT, history["mangaEndpoint"])

        startActivity(intent)
    }


}