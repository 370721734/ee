package com.jarhero790.eub.message.presenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by silladus on 2018/7/6/0006.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class RxUtils {

    /**
     * 定义一个泛型ObservableTransformer以作复用
     * Observable.compose(applySchedulers())
     * 参考{@literal https://www.jianshu.com/p/e9e03194199e}
     */
    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 切换线程
     *
     * @param <T> Observable<T>
     * @return Observable<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) SCHEDULERS_TRANSFORMER;
    }
}
