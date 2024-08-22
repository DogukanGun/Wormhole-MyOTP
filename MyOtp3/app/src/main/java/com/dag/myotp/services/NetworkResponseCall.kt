package com.dag.myotp.services

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.UnsupportedOperationException
class NetworkResponseCall<S:Any>(
    private val delegate: Call<S>
) : Call<S> {

    override fun enqueue(callback: Callback<S>) {
        return delegate.enqueue(object :Callback<S>{
            override fun onResponse(call: Call<S>, response: Response<S>) {
                callback.onResponse(this@NetworkResponseCall, response)
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                callback.onResponse(this@NetworkResponseCall,Response.error(404,null))
            }

        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun clone(): Call<S> = NetworkResponseCall(delegate.clone())

    override fun execute(): Response<S> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()


}