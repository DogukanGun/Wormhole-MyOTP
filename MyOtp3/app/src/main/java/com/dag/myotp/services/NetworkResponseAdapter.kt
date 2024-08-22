
import com.dag.myotp.services.NetworkResponseCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<S:Any>(
    private val successType:Type
) : CallAdapter<S, Call<S>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<S> {
        return NetworkResponseCall(call)
    }
}