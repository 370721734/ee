package com.jarhero790.eub.message.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.ObservableTransformer;

public abstract class BasePresenter<V> {
    protected V view;

    public BasePresenter() {
    }

    public BasePresenter(@NonNull V view) {
        this.view = view;
    }

    protected final <E> AutoDisposeConverter<E> autoDisposable() {
        if (view instanceof LifecycleOwner) {
            return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                    .from((LifecycleOwner) view));
        } else {
            throw new IllegalStateException(
                    "The BaseView impl class must to implement the LifecycleOwner!");
        }
    }

    /**
     * 切换线程
     *
     * @param <T> Observable<T>
     * @return Observable<T>
     */
    protected <T> ObservableTransformer<T,T> applySchedulers(){
        return RxUtils.applySchedulers();
    }

}
