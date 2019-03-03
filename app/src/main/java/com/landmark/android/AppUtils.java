package com.landmark.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Response;

public class AppUtils {

    static double[][] multiples = new double[4][4];

    public AppUtils() {
        initializeValues();
    }
    //initialize array with 0's
    static void initializeValues(){
        multiples[0][0] = 0;
        multiples[0][1] = 0;
        multiples[0][2] = 0;
        multiples[0][3] = 0;
        multiples[1][0] = 0;
        multiples[1][1] = 0;
        multiples[1][2] = 0;
        multiples[1][3] = 0;
        multiples[2][0] = 0;
        multiples[2][1] = 0;
        multiples[2][2] = 0;
        multiples[2][3] = 0;
        multiples[3][0] = 0;
        multiples[3][1] = 0;
        multiples[3][2] = 0;
        multiples[3][3] = 0;

    }

    public void setMultiples(int row , int col, double val){
        multiples[row][col] = val;
    }

    public static double[][] getMultiples(){
        return multiples;
    }

    //To print the array
    public static void print(){
        for(int i=0;i<=3;i++)
        {
            for(int j=0;j<=3;j++)
            {
                System.out.print(multiples[i][j]+"\t");
            }
            System.out.println();
        }
    }

    //To check the response is success
    public static boolean isSuccessResponse(Response response){
        return response.isSuccessful() && response.body() != null;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static BigDecimal roundTo(BigDecimal amount, int digits) {
        return amount.setScale(digits, RoundingMode.DOWN);
    }

    public static int convertDoubleToInt(Double val){

        return (int) Math.round(val);
    }

}
