package com.example.jerrol.sampleroompersistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.jerrol.sampleroompersistence.BasicApp;
import com.example.jerrol.sampleroompersistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class ProductListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public ProductListViewModel(Application application) {
        super(application);

        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.setValue(null);

        LiveData<List<ProductEntity>> products = ((BasicApp) application).getRepository()
                .getProducts();

        mObservableProducts.addSource(products, mObservableProducts::setValue);
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }
}
