package pl.com.c4m.github

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import pl.com.c4m.github.api.GithubApi
import pl.com.c4m.github.details.RepositoryViewModel
import pl.com.c4m.github.list.TrendingViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single {
        Retrofit.Builder()
                .baseUrl(GithubApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubApi::class.java)
    }
    single { RepositoryInteractor(get()) }
    viewModel { (activity: GitHubActivity) -> TrendingViewModel(get(), Navigator(activity)) }
    viewModel { RepositoryViewModel(get()) }
}
