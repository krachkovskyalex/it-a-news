package com.krachkovsky.it_anews.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.it_anews.adapter.NewsAdapter
import com.krachkovsky.it_anews.databinding.FragmentListBinding
import com.krachkovsky.it_anews.models.NewsEverything
import com.krachkovsky.it_anews.models.PagingData
import com.krachkovsky.it_anews.retrofit.NewsService
import com.krachkovsky.it_anews.util.Constants.GENERAL_ERROR_MESSAGE
import com.krachkovsky.it_anews.util.Constants.QUERY_PAGE_SIZE
import com.krachkovsky.it_anews.util.Constants.RECYCLER_ITEM_SPACE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }
    private val adapter = NewsAdapter { article ->
        findNavController().navigate(
            NewsListFragmentDirections.actionNewsListFragmentToNewsArticleFragment(article.url)
        )
    }

    private var currentPage = 1

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNews()

        with(binding) {
//            toolbarList.menu
//                .findItem(R.id.action_search)
//                .let { it.actionView as SearchView }
//                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String): Boolean {
//                        return true
//                    }
//
//                    override fun onQueryTextChange(newText: String): Boolean {
//                        searchNews(newText)
//                        Log.d("AAA search newText", newText)
//                        return false
//                    }
//                })

            val layoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )
            recyclerList.adapter = adapter
            recyclerList.layoutManager = layoutManager
            recyclerList.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)
            recyclerList.addOnScrollListener(scrollListener)

            swipeRefreshList.setOnRefreshListener {
                adapter.submitList(emptyList())
                currentPage = 1
                loadNews()
                swipeRefreshList.isRefreshing = false
            }

        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                loadNews()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun loadNews() {
        NewsService.newsApi.getAllNews(
            page = currentPage,
            pageSize = QUERY_PAGE_SIZE
        )
            .enqueue(object : Callback<NewsEverything> {
                override fun onResponse(
                    call: Call<NewsEverything>,
                    response: Response<NewsEverything>
                ) {
                    if (response.isSuccessful) {
                        val newList = adapter.currentList
                            .dropLastWhile { it == PagingData.Loading }
                            .plus(response.body()?.articles?.map { PagingData.Content(it) }
                                .orEmpty())
                            .plus(PagingData.Loading)
                        adapter.submitList(newList)
                        currentPage++
                        Log.d("AAA onResponse", "Success ${response.body().toString()}")
                    } else {
                        handleErrors(response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE)
                    }
                }

                override fun onFailure(call: Call<NewsEverything>, t: Throwable) {
                    handleErrors(t.message ?: GENERAL_ERROR_MESSAGE)
                    Log.d("AAA onFailure", "Failure ${t.message}")
                }
            })
    }

//    private fun searchNews(query: String) {
//        NewsService.newsApi.getAllNews(
//            q = query,
//            page = currentPage,
//            pageSize = QUERY_PAGE_SIZE
//        )
//            .enqueue(object : Callback<NewsEverything> {
//                override fun onResponse(
//                    call: Call<NewsEverything>,
//                    response: Response<NewsEverything>
//                ) {
//                    if (response.isSuccessful) {
//                        val newList = adapter.currentList
//                            .dropLastWhile { it == PagingData.Loading }
//                            .plus(response.body()?.articles?.map { PagingData.Content(it) }
//                                .orEmpty())
//                            .plus(PagingData.Loading)
//                        adapter.submitList(newList)
//                        if (response.body()?.totalResults!! < currentPage * QUERY_PAGE_SIZE) {
//                            currentPage++
//                        }
//                        Log.d("AAA onResponse", "Success ${response.body().toString()}")
//                    } else {
//                        handleErrors(response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE)
//                        Log.d("AAA onResponse else", "Else Success ${response.body().toString()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsEverything>, t: Throwable) {
//                    handleErrors(t.message ?: GENERAL_ERROR_MESSAGE)
//                    Log.d("AAA onFailure", "Failure ${t.message}")
//                }
//            })
//    }

    private fun handleErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun RecyclerView.addHorizontalSpaceDecoration(space: Int) {
        addItemDecoration(
            object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position != 0 && position != parent.adapter?.itemCount) {
                        outRect.top = space
                    }
                }
            }
        )
    }
}