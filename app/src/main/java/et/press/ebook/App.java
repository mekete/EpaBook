package et.press.ebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.color.DynamicColors;

import et.press.ebook.config.EpaConfig;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return App.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(EpaConfig.DEBUG, "app launched");
        DynamicColors.applyToActivitiesIfAvailable(this);
        App.context = getApplicationContext();
    }
}
