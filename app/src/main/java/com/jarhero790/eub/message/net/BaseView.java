package com.jarhero790.eub.message.net;

public interface BaseView {
    void onProgress();
    void onError(Throwable e);
    void onComplete();
}
