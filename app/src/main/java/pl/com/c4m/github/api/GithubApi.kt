package pl.com.c4m.github.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val SORT_STARS = "stars"
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
        const val CREATED = "created"
        const val LANGUAGE = "language"
    }

    @GET("/search/repositories")
    fun getRepositories(
            @Query("q") query: String? = null,
            @Query("sort") sort: String? = null,
            @Query("order") order: String? = null,
            @Query("page") page: Int? = null,
            @Query("per_page") perPage: Int? = null
    ): Single<SearchResponse>
}
