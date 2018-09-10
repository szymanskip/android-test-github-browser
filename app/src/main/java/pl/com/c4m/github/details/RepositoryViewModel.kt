package pl.com.c4m.github.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.com.c4m.github.RepositoryInteractor
import pl.com.c4m.github.api.RepositoryDetails

class RepositoryViewModel(
        private val interactor: RepositoryInteractor
) : ViewModel() {

    private val name = MutableLiveData<Pair<String, String>>()
    val repository: LiveData<RepositoryDetails> = switchMap(name) { (owner, name) ->
        LiveDataReactiveStreams.fromPublisher(interactor.getByName(owner, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable())
    }

    fun setName(owner: String, name: String) {
        this.name.value = Pair(owner, name)
    }
}