package com.example.comickufinal.ui.fragment

import android.R.layout
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.comickufinal.R
import com.example.comickufinal.adapter.ExploreAdapter
import com.example.comickufinal.adapter.GenreAdapter
import com.example.comickufinal.adapter.PopularAdapter
import com.example.comickufinal.adapter.RecommendedAdapter
import com.example.comickufinal.adapter.listener.OnSelectedGenreListener
import com.example.comickufinal.adapter.listener.OnSelectedMangaListener
import com.example.comickufinal.model.GenreResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.tools.Utilities
import com.example.comickufinal.ui.detail.DetailMangaActivity
import com.example.comickufinal.ui.home.AllContentActivity
import com.example.comickufinal.ui.home.SearchMangaActivity
import com.example.comickufinal.ui.viewmodel.HomeViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), View.OnClickListener, OnSelectedGenreListener,
    OnSelectedMangaListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private lateinit var genreAdapter: GenreAdapter<GenreResponse.Genre>
    private lateinit var popularAdapter: PopularAdapter
   private lateinit var recommendedAdapter: RecommendedAdapter
   private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var swipeRefreshHome: SwipeRefreshLayout? = null


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshHome = view.srl

        // adapter
        genreAdapter = GenreAdapter()
        popularAdapter = PopularAdapter()
        recommendedAdapter = RecommendedAdapter()
        exploreAdapter = ExploreAdapter()
        exploreAdapter.setOnSelectedMangaListener(this)
        recommendedAdapter.setOnSelectedMangaListener(this)
        genreAdapter.setOnSelectedGenreListener(this)
        popularAdapter.setOnSelectedMangaListener(this)

        // view model
        homeViewModel = requireActivity().let {
            ViewModelProvider(it).get(HomeViewModel::class.java)
        }

        val container = view.shimmer_popular as ShimmerFrameLayout
        container.startShimmer()

        val customLayout = layoutInflater.inflate(R.layout.custom_toast, cardview)

        val containergenre = view.shimmer_genre as ShimmerFrameLayout
        containergenre.startShimmer()


        // set name
        view.homeFrag_tvFullName.text = homeViewModel.firebaseAuth.currentUser?.displayName
        view.homeFrag_tvGreet.text = Utilities.greeting(requireContext())






        /*homeViewModel.recommended.observe(requireActivity()) {
            recommendedAdapter.recommendedResponseList = it
            list.add(
                CarouselItem(
                    imageDrawable = R.drawable.image_2
                )
            )
        }*/

        homeViewModel.popularManga.observe(requireActivity()) {
            popularAdapter.popularMangaResponseList = it
            view.homeFrag_rvPopular.adapter = popularAdapter
        }

            homeViewModel.recommended.observe(requireActivity()) {
                recommendedAdapter.recommendedResponseList = it
                view.homeFrag_rvRecommended.adapter = recommendedAdapter
            }

        homeViewModel.explore.observe(requireActivity()) {
            exploreAdapter.exploreResponseList = it
            view.homeFrag_rvExplore.adapter = exploreAdapter
        }

        homeViewModel.genre.observe(requireActivity()) {
            genreAdapter.genreList = it.subList(0, 9)
            view.homeFrag_rvGenre.adapter = genreAdapter
        }

        homeViewModel.networkState.observe(requireActivity()) {
            if (it == NetworkState.LOADED) {
                view.shimmer_popular.visibility = View.GONE
                view.shimmer_genre.visibility = View.GONE
                view.shimmer_recommended.visibility = View.GONE
                view.shimmer_explore.visibility = View.GONE
                homeFrag_rvPopular.visibility = View.VISIBLE
                homeFrag_rvGenre.visibility = View.VISIBLE
                homeFrag_rvRecommended.visibility = View.VISIBLE
                homeFrag_rvExplore.visibility = View.VISIBLE
            }

            if (it == NetworkState.NO_CONNECTION ) {
               val toast:Toast = Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                val view = toast.view
                view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                toast.show()
            }

            if (it == NetworkState.TIMEOUT) {
                val toast:Toast = Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                val view = toast.view
                view?.background?.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                toast.show()
            }


        }



        // button listener
        view.homeFrag_btnMorePopular.setOnClickListener(this)
        view.homeFrag_btnMoreGenre.setOnClickListener(this)
        view.homeFrag_btnSearch.setOnClickListener(this)
        view.homeFrag_btnMoreExplore.setOnClickListener(this)

        // country action (linear layout listener)
        view.homeFrag_llManga.setOnClickListener(this)
        view.homeFrag_llManhua.setOnClickListener(this)
        view.homeFrag_llManhwa.setOnClickListener(this)

        swipeRefreshHome!!.setOnRefreshListener {

            resetData()
            fetchData()
            swipeRefreshHome!!.isRefreshing = false
        }

    }

    private fun resetData() {
        homeViewModel.reset
    }

    private fun fetchData() {
        homeViewModel.popularManga.observe(requireActivity()) {
            popularAdapter.popularMangaResponseList = it
            view?.homeFrag_rvPopular?.adapter = popularAdapter
        }

        homeViewModel.recommended.observe(requireActivity()) {
            recommendedAdapter.recommendedResponseList = it
            view?.homeFrag_rvRecommended?.adapter = recommendedAdapter
        }

        homeViewModel.explore.observe(requireActivity()) {
            exploreAdapter.exploreResponseList = it
            view?.homeFrag_rvExplore?.adapter = exploreAdapter
        }

        homeViewModel.genre.observe(requireActivity()) {
            genreAdapter.genreList = it.subList(0, 9)
            view?.homeFrag_rvGenre?.adapter = genreAdapter
        }
    }


    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.homeFrag_btnMorePopular -> {
                    val intent = Intent(requireContext(), AllContentActivity::class.java)
                    intent.putExtra(
                        AllContentActivity.EXTRA_TYPE,
                        AllContentActivity.Type.ALL_POPULAR
                    )
                    startActivity(intent)
                }
                R.id.homeFrag_btnMoreExplore -> {
                    val intent = Intent(requireContext(), AllContentActivity::class.java)
                    intent.putExtra(
                        AllContentActivity.EXTRA_TYPE,
                        AllContentActivity.Type.ALL_EXPLORE
                    )
                    startActivity(intent)
                }
                R.id.homeFrag_btnMoreGenre -> {
                    if (view.homeFrag_btnMoreGenre.isChecked) {
                        // total semua genre ada 63, data yng kita perlukan hanya 56
                        // kenapa karena 7 endpoint lainnya bukan endpoint melainkan halaman website
                        genreAdapter.genreList = homeViewModel.genre.value?.subList(0, 44)
                    } else {
                        genreAdapter.genreList = homeViewModel.genre.value?.subList(0, 9)
                    }
                }
                R.id.homeFrag_btnSearch -> {
                    val intent = Intent(requireContext(), SearchMangaActivity::class.java)
                    startActivity(intent)
                }
                R.id.homeFrag_llManga -> {
                    val intent = Intent(requireContext(), AllContentActivity::class.java)
                    intent.putExtra(
                        AllContentActivity.EXTRA_TYPE,
                        AllContentActivity.Type.ALL_MANGA
                    )
                    startActivity(intent)
                }
                R.id.homeFrag_llManhua -> {
                    val intent = Intent(requireContext(), AllContentActivity::class.java)
                    intent.putExtra(
                        AllContentActivity.EXTRA_TYPE,
                        AllContentActivity.Type.ALL_MANHUA
                    )
                    startActivity(intent)
                }
                R.id.homeFrag_llManhwa -> {
                    val intent = Intent(requireContext(), AllContentActivity::class.java)
                    intent.putExtra(
                        AllContentActivity.EXTRA_TYPE,
                        AllContentActivity.Type.ALL_MANHWA
                    )
                    startActivity(intent)
                }
            }
        }
    }

    override fun onSelectedGenre(genre: String, genreEndpoint: String) {
        val intent = Intent(requireContext(), AllContentActivity::class.java)
        intent.putExtra(AllContentActivity.EXTRA_GENRE, genre)
        intent.putExtra(AllContentActivity.EXTRA_GENRE_ENDPOINT, genreEndpoint)
        startActivity(intent)
    }

    override fun onSelectedManga(endpoint: String) {
        val intent = Intent(requireContext(), DetailMangaActivity::class.java)
        intent.putExtra(DetailMangaActivity.EXTRA_MANGA_ENDPOINT, endpoint)
        startActivity(intent)   
    }
}