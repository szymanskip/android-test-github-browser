package pl.com.c4m.github.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import pl.com.c4m.github.RepositoryInteractor
import pl.com.c4m.github.api.GithubApi
import pl.com.c4m.github.api.RepositoryListing
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TrendingViewModel : ViewModel() {

    private val interactor = RepositoryInteractor(
            Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GithubApi::class.java)
    )
    private val factory = RepositoriesDataSourceFactory(interactor)

    val repositories: LiveData<PagedList<RepositoryListing>> =
            LivePagedListBuilder(factory,
                    PagedList.Config.Builder()
                            .setPageSize(20)
                            .setInitialLoadSizeHint(40)
                            .build())
                    .build()
}