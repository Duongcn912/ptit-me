package com.proptit.nghiadv.diemthiptit.common;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.proptit.nghiadv.diemthiptit.application.BaseApplication;

public class ConnectivityReceiver extends BroadcastReceiver {

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        // get connectivity manager from system service
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return;
        }
        //get network info

    }


    /**
     *
     * @return connect status
     */
    public static boolean isConnected() {
        try {

            //get network info
            ConnectivityManager cm = (ConnectivityManager) BaseApplication.getmInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == cm) {
                return false;
            }
            //get network info
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        }catch (Exception e) {
            return false;
        }
    }


}
