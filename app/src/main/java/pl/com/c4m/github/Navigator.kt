package pl.com.c4m.github

import pl.com.c4m.github.details.RepositoryFragment

class Navigator(
        private val activity: GitHubActivity
) {
    fun goToRepository(owner: String, name: String) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, RepositoryFragment.newInstance(owner, name))
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }
}