package com.essensol.techmeq;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }


    public static BigDecimal getRounded(BigDecimal num) {

        Log.e("getRounded", " "+num);

       BigDecimal decimal = num.subtract(num.setScale(0, RoundingMode.FLOOR)).movePointRight(num.scale());

        Log.e("decimal", " "+decimal);

        Log.e("remainder", " "+decimal.remainder(new BigDecimal(10)));

        BigDecimal rounded;
        if(decimal.remainder(new BigDecimal(10)).equals(BigDecimal.valueOf(0)))
        {
            rounded =num.setScale(1, BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_DOWN);

            Log.e("remainder", "True "+rounded);

        }
        else {
            rounded=num.setScale(3, BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP);

            Log.e("remainder", "else "+rounded);
        }
        Log.e("remainder", "return "+rounded);

        return rounded;
    }


}
