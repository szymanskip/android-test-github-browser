package pl.com.c4m.github.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_trending_list.*
import pl.com.c4m.github.R

class TrendingListFragment : Fragment() {

    private lateinit var viewModel: TrendingViewModel
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
                .get(TrendingViewModel::class.java)
        repositoryAdapter = RepositoryAdapter(layoutInflater)

        viewModel.repositories.observe(this, Observer { list ->
            repositoryAdapter.submitList(list)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trending_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trendingRecyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}