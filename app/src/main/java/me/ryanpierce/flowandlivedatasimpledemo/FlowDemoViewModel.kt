package me.ryanpierce.flowandlivedatasimpledemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

class FlowDemoViewModel(useCase: UseCase) : ViewModel() {

    val userLiveData: LiveData<User> = useCase.userFlow.asLiveData()

    companion object {
        val FACTORY = singleArgumentViewModelFactory(::FlowDemoViewModel)
    }
}

fun <T : ViewModel, A> singleArgumentViewModelFactory(
    constructor: (A) -> T
): (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}