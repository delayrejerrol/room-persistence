package com.example.jerrol.sampleroompersistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jerrol.sampleroompersistence.db.entity.ProductEntity;
import com.example.jerrol.sampleroompersistence.model.Product;

import java.util.List;

/**
 * Created by Jerrol on 11/28/2017.
 */

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Query("SELECT * FROM products WHERE id = :productId")
    LiveData<ProductEntity> loadProduct(int productId);

    @Query("SELECT * FROM products WHERE id = :productId")
    ProductEntity loadProductSynct(int productId);
}
