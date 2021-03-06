package com.example.jerrol.sampleroompersistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.jerrol.sampleroompersistence.db.AppDatabase;
import com.example.jerrol.sampleroompersistence.db.entity.CommentEntity;
import com.example.jerrol.sampleroompersistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(), productEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                mObservableProducts.postValue(productEntities);
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }

        return sInstance;
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }
}
