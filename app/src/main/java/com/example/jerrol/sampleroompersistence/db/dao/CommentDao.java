package com.example.jerrol.sampleroompersistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jerrol.sampleroompersistence.db.entity.CommentEntity;

import java.util.List;

/**
 * Created by Jerrol on 11/28/2017.
 */

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comments WHERE productId = :productId")
    LiveData<List<CommentEntity>> loadComments(int productId);

    @Query("SELECT * FROM comments WHERE productId = :productId")
    List<CommentEntity> loadCommentsSync(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CommentEntity> products);
}
