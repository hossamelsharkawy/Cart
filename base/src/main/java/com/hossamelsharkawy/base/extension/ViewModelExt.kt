package com.hossamelsharkawy.base.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


fun <T> Flow<T>.stateIn(demoViewModel: ViewModel, t: T) = this.stateIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(),
    initialValue = t
)

fun <T> Flow<T>.stateInShort(
    demoViewModel: ViewModel,
    t: T,
    stopTimeoutMillis: Long = 100
) =
    this.stateIn(
        scope = demoViewModel.viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = t
    )


fun <T> StateFlow<T>.stateInShort(
    demoViewModel: ViewModel,

    stopTimeoutMillis: Long = 100
) = this.stateIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
    initialValue = value
)

fun <T> Flow<T>.stateInLong(
    demoViewModel: ViewModel, t: T,
) = this.stateIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
    initialValue = t
)

fun <T> Flow<T>.stateInEver(demoViewModel: ViewModel, t: T) = this.stateIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = Long.MAX_VALUE),
    initialValue = t
)

fun <T> StateFlow<T>.stateInEver(demoViewModel: ViewModel) = this.stateIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = Long.MAX_VALUE),
    initialValue = value
)

/*****************************************************************************************/

fun <T> Flow<T>.shareIn(demoViewModel: ViewModel) = this.shareIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(),

    )

fun <T> Flow<T>.shareInShort(
    demoViewModel: ViewModel,
    stopTimeoutMillis: Long = 100
) = this.shareIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
)

fun <T> Flow<T>.shareInLong(demoViewModel: ViewModel) = this.shareIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
)

fun <T> Flow<T>.shareInEver(demoViewModel: ViewModel) = this.shareIn(
    scope = demoViewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = Long.MAX_VALUE),
)


inline fun <T> AppCompatActivity.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }


inline fun <T> AppCompatActivity.collect(
    viewModelState: ViewModelState<T>,
    crossinline block: (T) -> Unit
) =
    lifecycleScope.launch {
        viewModelState.flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }

inline fun <T> Fragment.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }


inline fun <T> Fragment.collect(viewModelState: ViewModelState<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        viewModelState.flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }


inline fun <T> Fragment.collect(
    viewModelShared: ViewModelShared<T>,
    crossinline block: (T) -> Unit
) =
    lifecycleScope.launch {
        viewModelShared.flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }


inline fun <T> Fragment.collectWithDrop(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collectWithDrop.lifecycle)
            .drop(1)
            .collect { action ->
                block.invoke(action)
            }
    }


inline fun <T> Fragment.collectLatest(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collectLatest.lifecycle)
            .collectLatest { action ->
                block.invoke(action)
            }
    }


/*inline fun <T> Fragment.collect(flow: SharedFlow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }*/

/*************************************************************************************/


suspend infix fun <T> Flow<T>?.collectIn(b: Loading<T>) {
    this?.let {
        b.load()
        catch {
            b.error(it.message)
        }.collect {
            b.success(it)
        }
    }
}

suspend inline fun <T> Flow<T>.collectWith(crossinline action: suspend () -> Loading<T>) {
    action.invoke().load()
    catch {
        action.invoke().error(it.message)
    }.collect {
        action.invoke().success(it)
    }
}

interface Loading<T> {
    fun load()
    fun finishLoad()
    fun error(string: String?)
    fun success(mItem: T?)
}

class LoadingList<T> : Loading<List<T>> {


    var itemsFlow = MutableStateFlow(arrayListOf<T>())
        private set


    var _loading = MutableStateFlow(false)
        private set

    var _error = MutableStateFlow<String?>(null)
        private set

    override fun load() {
        itemsFlow.value = arrayListOf()
        _loading.value = true
        _error.value = null
    }

    override fun finishLoad() {
        _loading.value = false
        _error.value = null
    }

    override fun error(string: String?) {
        _error.value = string
        _loading.value = false
    }

    override fun success(mItem: List<T>?) {
        finishLoad()
        itemsFlow.value = if (mItem.isNullOrEmpty()) {
            arrayListOf()
        } else {
            ArrayList(mItem)
        }
    }

}

/*************************************************************************************/


class ViewModelState<T>(
    value: T,
    function: (StateFlow<T>) -> StateFlow<T>
) {
    private var _flow = MutableStateFlow(value)
    var flow = function(_flow)


    var value
        get() = _flow.value
        set(value) {
            _flow.value = value
        }


    suspend fun emit(value: T) {
        _flow.emit(value)
    }


    fun update(function: (T) -> T) {
        _flow.update(function)
    }


}

class ViewModelShared<T>(
    function: (SharedFlow<T>) -> SharedFlow<T>
) {
    private var _flow = MutableSharedFlow<T>()
    var flow = function(_flow)

    suspend fun emit(value: T) {
        _flow.emit(value)
    }

    fun tryEmit(value: T) {
        _flow.tryEmit(value)
    }
}

fun <T> T.vmStateShort(viewModel: ViewModel) = ViewModelState(this, { it.stateInShort(viewModel) })

fun <T> T.vmStateLong(viewModel: ViewModel) =
    ViewModelState(this, { it.stateInLong(viewModel, this) })


fun <T> T.vmState(viewModel: ViewModel) = MutableStateFlow(this).stateInShort(viewModel)

fun <T> T.vmStateEver(viewModel: ViewModel) = ViewModelState(this, { it.stateInEver(viewModel) })

fun <T> T.vmSharedShort(viewModel: ViewModel) =
    ViewModelShared<T>(function = { it.shareInShort(viewModel) })


/*suspend inline fun <T> Flow<T>.collectWithLoading(loadingItems :LoadingItems<T>) {
    loadingItems.load()
    catch {
        loadingItems.error()

    }.collect {
        loadingItems.susses(it)
    }
}
*/


fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(block = block)