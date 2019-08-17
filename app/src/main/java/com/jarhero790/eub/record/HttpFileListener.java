package com.jarhero790.eub.record;

import java.io.File;

public interface HttpFileListener {
    public void onProgressUpdate(int progress);

    public void onSaveSuccess(File file);

    public void onSaveFailed(File file, Exception e);

    public void onProcessEnd();
}