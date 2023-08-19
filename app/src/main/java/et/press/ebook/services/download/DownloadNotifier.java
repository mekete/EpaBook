package et.press.ebook.services.download;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.content.ContextCompat;

import et.press.book.R;
import et.press.ebook.config.EpaConfig;

public class DownloadNotifier {
    private final NotificationManager notificationManager;
    private final Notification.Builder builder;
    private final Context context;
    private final int notificationId;

    public DownloadNotifier(Context context, int notificationId) {
        this.context = context;
        this.notificationId = notificationId;
        notificationManager = ContextCompat.getSystemService(context, NotificationManager.class);
        builder = getNotificationBuilder();
    }

    public void showNotification() {
        createNotificationChannel();
        notifyNow();
    }

    @SuppressLint("DefaultLocale")
    public void setProgress(int current, int max) {
        builder.setProgress(max, current, false)
                .setContentTitle(String.format("Downloading... %d/%d", current, max));
        notifyNow();
    }

    public void complete() {
        builder.setOngoing(false)
                .setProgress(0, 0, false)
                .setContentTitle("Download completed.");
        notifyNow();
    }

    private void notifyNow() {
        notificationManager.notify(notificationId, builder.build());
    }

    private Notification.Builder getNotificationBuilder() {
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_library)
                .setOngoing(true)
                .setContentTitle("Downloading started...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(EpaConfig.NOTIF_DOWNLOAD_CHANNEL_NAME);
        }
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        return builder;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(
                    EpaConfig.NOTIF_DOWNLOAD_CHANNEL_NAME,
                    EpaConfig.NOTIF_DOWNLOAD_CHANNEL_NAME,
                    importance
            );
            notificationManager.createNotificationChannel(channel);
        }
    }
}
