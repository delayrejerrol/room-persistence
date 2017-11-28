package com.example.jerrol.sampleroompersistence;

import android.app.Application;

import com.example.jerrol.sampleroompersistence.db.AppDatabase;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
