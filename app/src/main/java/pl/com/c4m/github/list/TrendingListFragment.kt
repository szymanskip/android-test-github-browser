package pl.com.c4m.github.list

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_trending_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.com.c4m.github.GitHubActivity
import pl.com.c4m.github.Navigator
import pl.com.c4m.github.R

class TrendingListFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel { parametersOf(context as GitHubActivity) }
    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var navigator: Navigator

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigator = context as Navigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter(layoutInflater,
                onItemClick = { item ->
                    navigator.goToRepository(item.owner.login, item.name)
                })

        viewModel.repositories.observe(this, Observer { list ->
            repositoryAdapter.submitList(list)
        })
        viewModel.networkState.observe(this, Observer { state ->
            repositoryAdapter.networkState = state
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