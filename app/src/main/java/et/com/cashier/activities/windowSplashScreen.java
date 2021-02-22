package et.com.cashier.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import et.com.cashier.R;
import et.com.cashier.model.DeviceInformation;
import et.com.cashier.network.retrofit.pojo.Configuration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;

public class windowSplashScreen extends AppCompatActivity implements LocationListener
{
    private static final int PERMISSION_REQUEST_CODE = 200;
    private et.com.cashier.model.Location location;
    public static DeviceInformation deviceInformation;


    protected boolean gps_enabled, network_enabled;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_splash_screen);

        deviceInformation = new DeviceInformation();
        init();
    }
    private void init()
    {
        if(checkPermission())
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            deviceInformation.setImeiNumber(getDeviceIMEI());
            loginWindow();
        }
        else
        {
            requestPermission();
        }
    }
    private boolean checkPermission()
    {
        int requestInternet = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int requestNetworkState = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int requestPhoneState = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int requestAccessFine = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int requestAccessCoarse = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return requestInternet == PackageManager.PERMISSION_GRANTED &&
                requestNetworkState == PackageManager.PERMISSION_GRANTED &&
                requestPhoneState == PackageManager.PERMISSION_GRANTED &&
                requestAccessFine == PackageManager.PERMISSION_GRANTED &&
                requestAccessCoarse == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{
                INTERNET, ACCESS_NETWORK_STATE, READ_PHONE_STATE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION },
                PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0)
                {
                    boolean internetAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean networkStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneStateAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean accessFineAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean accessCoarseAccepted = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if(internetAccepted && networkStateAccepted && phoneStateAccepted && accessFineAccepted && accessCoarseAccepted)
                    {

                    }
                    else {
                        View rootLayout = findViewById(R.id.splashRootLayout);
                        Snackbar snackbar = Snackbar
                                .make(rootLayout, Html.fromHtml("<B>Permission denied</B><Br/>you can not proceed unless permissions are granted"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            showMessageOKCancel("You need to grant all permissions", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{
                                                        INTERNET, ACCESS_NETWORK_STATE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                PERMISSION_REQUEST_CODE);
                                    }
                                }
                            });
                            return;
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(windowSplashScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void onLocationChanged(Location location)
    {
        this.location.setLatitude(location.getLatitude());
        this.location.setLongitude(location.getLongitude());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }
    @Override
    public void onProviderEnabled(String provider)
    {

    }
    @Override
    public void onProviderDisabled(String provider)
    {

    }
    @SuppressLint("MissingPermission")
    private String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
    private void loginWindow()
    {
        Intent intent = new Intent(windowSplashScreen.this, windowLogin.class);
        intent.putExtra("DeviceInformation", (Serializable) deviceInformation);

        startActivity(intent);
    }
}