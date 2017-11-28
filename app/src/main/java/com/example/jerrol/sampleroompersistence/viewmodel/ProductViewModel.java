package com.example.jerrol.sampleroompersistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.jerrol.sampleroompersistence.BasicApp;
import com.example.jerrol.sampleroompersistence.DataRepository;
import com.example.jerrol.sampleroompersistence.db.entity.CommentEntity;
import com.example.jerrol.sampleroompersistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class ProductViewModel extends AndroidViewModel {

    private final LiveData<ProductEntity> mObservableProduct;

    public ObservableField<ProductEntity> product = new ObservableField<>();

    private final int mProductId;

    private final LiveData<List<CommentEntity>> mObservableComments;

    public ProductViewModel(@NonNull Application application, DataRepository repository, final int productId) {
        super(application);
        mProductId = productId;

        mObservableComments = repository.loadComments(mProductId);
        mObservableProduct = repository.loadProduct(mProductId);
    }

    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public void setProduct(ProductEntity product) {
        this.product.set(product);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mProductId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProductViewModel(mApplication, mRepository, mProductId);
        }
    }
}
