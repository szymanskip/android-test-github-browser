package pl.com.c4m.github.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import pl.com.c4m.github.NetworkState
import pl.com.c4m.github.RepositoryInteractor
import pl.com.c4m.github.api.RepositoryListing

class TrendingViewModel(
        interactor: RepositoryInteractor
) : ViewModel() {

    private val factory = RepositoriesDataSourceFactory(interactor)

    val repositories: LiveData<PagedList<RepositoryListing>> =
            LivePagedListBuilder(factory,
                    PagedList.Config.Builder()
                            .setPageSize(20)
                            .setInitialLoadSizeHint(40)
                            .build())
                    .build()
    val networkState: LiveData<NetworkState> = switchMap(factory.dataSource) { it.networkState }
}