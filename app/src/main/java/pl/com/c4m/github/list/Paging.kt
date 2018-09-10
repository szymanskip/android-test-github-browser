package pl.com.c4m.github.list

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import pl.com.c4m.github.RepositoryInteractor
import pl.com.c4m.github.api.RepositoryListing

private const val LOG_TAG = "RepositoriesDataSource"

class RepositoriesDataSource(
        private val interactor: RepositoryInteractor
) : PageKeyedDataSource<Int, RepositoryListing>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RepositoryListing>) {

        // this needs to be done synchronously
        try {
            interactor.getTrending(1, params.requestedLoadSize)
                    .blockingGet()
                    .let {
                        callback.onResult(it, 1, 2)
                    }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error loading repositories", e)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryListing>) {
        interactor.getTrending(params.key, params.requestedLoadSize)
                .subscribe({
                    callback.onResult(it, params.key + 1)
                }, {
                    Log.e(LOG_TAG, "Error loading repositories", it)
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryListing>) {
        // not used
    }

}


class RepositoriesDataSourceFactory(
        private val interactor: RepositoryInteractor
) : DataSource.Factory<Int, RepositoryListing>() {

    val dataSource = MutableLiveData<RepositoriesDataSource>()

    override fun create(): DataSource<Int, RepositoryListing> {
        val source = RepositoriesDataSource(interactor)
        dataSource.postValue(source)
        return source
    }

}