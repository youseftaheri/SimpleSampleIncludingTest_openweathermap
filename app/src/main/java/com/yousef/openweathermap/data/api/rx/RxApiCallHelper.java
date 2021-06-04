package com.yousef.openweathermap.data.api.rx;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxApiCallHelper {
    public RxApiCallHelper() {
    }

    public static <T> Disposable call(Observable<T> observable, final RxApiCallback<T> rxAPICallback) {
        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }


        if (rxAPICallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        rxAPICallback.onSuccess(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable != null) {
                            rxAPICallback.onFailed(throwable);
                        } else {
                            rxAPICallback.onFailed(new Exception("Error: Something went wrong in api call."));
                        }
                    }
                });

    }
}