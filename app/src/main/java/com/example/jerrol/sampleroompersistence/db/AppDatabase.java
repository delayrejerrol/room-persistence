package com.example.jerrol.sampleroompersistence.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.jerrol.sampleroompersistence.AppExecutors;
import com.example.jerrol.sampleroompersistence.db.converter.DateConverter;
import com.example.jerrol.sampleroompersistence.db.dao.CommentDao;
import com.example.jerrol.sampleroompersistence.db.dao.ProductDao;
import com.example.jerrol.sampleroompersistence.db.entity.CommentEntity;
import com.example.jerrol.sampleroompersistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Jerrol on 11/24/2017.
 */

@Database(entities = {ProductEntity.class, CommentEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract ProductDao productDao();

    public abstract CommentDao commentDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            addDelay();

                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<ProductEntity> products = DataGenerator.generateProducts();
                            List<CommentEntity> comments = DataGenerator.generateCommentsForProducts(products);

                            insertData(database, products, comments);

                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<ProductEntity> products,
                                   final List<CommentEntity> comments) {
        database.runInTransaction(() -> {
            database.productDao().insertAll(products);
            database.commentDao().insertAll(comments);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) { }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
