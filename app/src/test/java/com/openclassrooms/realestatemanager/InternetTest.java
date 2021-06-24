package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowNetworkInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class InternetTest {

    private Context context;
    private ShadowNetworkInfo shadowNetworkInfo;
    private ConnectivityManager connectivityManager;

    @Test
    public void checkIsInternetAvailableFalse_NoInternet() throws Exception{
        this.setUpContextInternet();
        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assertFalse(Utils.isInternetAvailable(context));
    }

    @Test
    public void checkIsInternetAvailableTrue_InternetOn() throws Exception{
        this.setUpContextInternet();
        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertTrue(Utils.isInternetAvailable(context));
    }

    private void setUpContextInternet(){
        context = getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowNetworkInfo = shadowOf(connectivityManager.getActiveNetworkInfo());
    }
}
