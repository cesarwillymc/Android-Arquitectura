
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.doapps.android.domain.utils.status.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread internal constructor(){
    private val result = MediatorLiveData<Resource<ResultType>>()

    private var dbSource: LiveData<ResultType>

    internal val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                GlobalScope.launch(Dispatchers.Main) {
                    fetchFromNetwork(dbSource)
                }
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        try{
            val response = createCall()
            result.removeSource(dbSource)
            saveResultAndReInit(response)
        }catch (e:Exception){
            loadError(e)
        }

    }
    private fun loadError(e:Exception){
        onFetchFailed()
        result.removeSource(dbSource)
        result.addSource(dbSource) { newData ->
            result.setValue(Resource.error(e.message.toString(), newData))
        }
    }
    @MainThread
    private fun saveResultAndReInit(response: RequestType) {
        GlobalScope.launch(Dispatchers.Main) {
            saveCallResult(response)
            //delay(100L)
            result.addSource(loadFromDb()) { newData -> result.setValue(Resource.success(newData)) }
        }
    }

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun createCall(): RequestType

    @MainThread
    protected abstract fun onFetchFailed()
}
