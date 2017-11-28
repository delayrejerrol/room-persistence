package com.example.jerrol.sampleroompersistence.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Jerrol on 11/24/2017.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
