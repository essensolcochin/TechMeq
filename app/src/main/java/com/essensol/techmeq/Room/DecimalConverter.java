package com.essensol.techmeq.Room;

import android.arch.persistence.room.TypeConverter;

import java.math.BigDecimal;

public class DecimalConverter {

    @TypeConverter
    public BigDecimal fromLong(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP);
    }

    @TypeConverter
    public Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal(100)).longValueExact();
        }
    }
}
