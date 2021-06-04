package com.yousef.openweathermap.data.api.rx;

public interface RxApiCallback<P> {
    void onSuccess(P t);

    void onFailed(Throwable throwable);
}