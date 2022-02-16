package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.chat

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.TopLvlChatBinding
import com.galaxy_techno.uKnow.movie.Movie
import com.galaxy_techno.uKnow.movie.MovieLoadStateAdapter
import com.galaxy_techno.uKnow.movie.MoviePagingDataAdapter
import com.galaxy_techno.uKnow.presentation.base.TopFragment
import com.galaxy_techno.uKnow.presentation.extension.displaySnack
import com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.orders.tabs.all.AllOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class TopChatFragment : TopFragment<TopLvlChatBinding>(TopLvlChatBinding::inflate) {

    private val movieAdapter: MoviePagingDataAdapter by lazy {
        MoviePagingDataAdapter(isDarkTheme(requireActivity())) { getItemClick(it) }.apply {
            this.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.orderAllRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.orderAllRecycler.adapter = this
            binding.orderAllRecycler.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }
    private val viewModel: AllOrdersViewModel by viewModels()
    private lateinit var movieList : List<Movie>

    override fun setupView() {
        super.setupView()
        shouldShowShimmer(true)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        binding.toolbar.topLvlToolbar.apply {
            title = getString(R.string.btn_nav_chat)
            setTitleTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        }

        binding.toolbar.menuSearch.setOnSearchClickListener {

        }
    }


    private fun shouldShowShimmer(flag: Boolean) {
        if (flag) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.orderAllRecycler.visibility = View.GONE
            binding.shimmerLayout.startShimmer()
        }else{
            binding.shimmerLayout.visibility = View.GONE
            binding.orderAllRecycler.visibility = View.VISIBLE
            binding.shimmerLayout.stopShimmer()
        }
    }

    override fun observe() {
        super.observe()
        viewModel.movies.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                delay(1000L)
                shouldShowShimmer(false)
                movieAdapter.submitData(it)
            }
        }
    }


    override fun setupListener() {
        super.setupListener()
//        binding.toolbar.menuProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_dash_to_profile)
//        }
    }

    private fun getItemClick(position: Int) {
        val item = movieAdapter.getClickItem(position)
        binding.root.displaySnack(item.toString())
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}