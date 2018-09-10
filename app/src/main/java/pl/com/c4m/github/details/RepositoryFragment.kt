package pl.com.c4m.github.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_repository.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.com.c4m.github.R
import java.util.*

class RepositoryFragment : Fragment() {

    companion object {

        private const val ARG_OWNER = "owner"
        private const val ARG_NAME = "name"

        fun newInstance(owner: String, name: String): RepositoryFragment {
            return RepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_OWNER, owner)
                    putString(ARG_NAME, name)
                }
            }
        }
    }

    private val viewModel: RepositoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setName(arguments!!.getString(ARG_OWNER), arguments!!.getString(ARG_NAME))

        viewModel.repository.observe(this, Observer { details ->
            if (details != null) {
                repositoryContentLayout.isVisible = true
                repositoryProgressBar.isVisible = false

                repositoryNameView.text = details.fullName
                repositoryDescriptionView.text = details.description

                val formattedTime = details.updatedAt.let {
                    DateUtils.getRelativeTimeSpanString(it.time, Date().time, DateUtils.MINUTE_IN_MILLIS)
                }
                repositoryLastUpdateView.text = getString(R.string.last_updated, formattedTime)

                repositoryWatchesCountView.text = details.watchersCount.toString()
                repositoryStarsCountView.text = details.stargazersCount.toString()
                repositoryForksCountView.text = details.forksCount.toString()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }
}