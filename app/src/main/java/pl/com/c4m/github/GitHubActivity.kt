package pl.com.c4m.github

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.com.c4m.github.list.TrendingListFragment

class GitHubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, TrendingListFragment())
                    .commitNow()
        }
    }
}