package com.ratushny.modulotech.presentation.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveData<T> : MutableLiveData<T>() {

    private val pending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(
            lifecycleOwner
        ) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }
}