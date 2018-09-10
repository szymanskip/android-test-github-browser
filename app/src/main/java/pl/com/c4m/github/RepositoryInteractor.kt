package pl.com.c4m.github

import io.reactivex.Single
import pl.com.c4m.github.api.GithubApi
import pl.com.c4m.github.api.RepositoryDetails
import pl.com.c4m.github.api.RepositoryListing
import java.text.SimpleDateFormat
import java.util.*

class RepositoryInteractor(
        private val githubApi: GithubApi
) {

    fun getTrending(page: Int, perPage: Int): Single<List<RepositoryListing>> {
        val weekAgo = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                .also { it.add(Calendar.DAY_OF_MONTH, -7) }
                .let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.US).format(it.time)
                }

        val query = listOf(
                "android",
                "${GithubApi.LANGUAGE}:java",
                "${GithubApi.LANGUAGE}:kotlin",
                "${GithubApi.CREATED}<$weekAgo"
        ).joinToString("+")

        return githubApi.getRepositories(
                query = query,
                sort = GithubApi.SORT_STARS,
                order = GithubApi.ORDER_DESC,
                page = page,
                perPage = perPage)
                .map { it.items }
    }

    fun getByName(owner: String, name: String): Single<RepositoryDetails> {
        return githubApi.getRepository(owner, name)
    }
}