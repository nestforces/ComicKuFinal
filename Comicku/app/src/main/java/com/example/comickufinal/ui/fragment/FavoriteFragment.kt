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
import com.example.comickufinal.adapter.FavoriteAdapter
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.ui.detail.DetailMangaActivity
import com.example.comickufinal.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment(), OnSelectedMangaListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view model
        val homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        homeViewModel.favoriteManga.observe(requireActivity()) {
            // adapter
            val adapter = FavoriteAdapter(it)
            adapter.setOnSelectedMangaListener(this)
            view.favoriteFrag_recyclerView.adapter = adapter
        }
    }

    override fun onSelectedManga(endpoint: String) {
        val intent = Intent(requireContext(), DetailMangaActivity::class.java)
        intent.putExtra(DetailMangaActivity.EXTRA_MANGA_ENDPOINT, endpoint)
        startActivity(intent)
    }
}