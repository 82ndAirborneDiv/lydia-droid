package gov.cdc.stdtxguide;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment {
    private CheckBox notificationCheckBox;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        notificationCheckBox = (CheckBox) view.findViewById(R.id.allowNotificationsCheckBox);
        notificationCheckBox.setChecked(AppManager.pref.getBoolean(STDTxGuidePreferences.ALLOW_PUSH_NOTIFICATIONS, true));
        notificationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppManager.editor.putBoolean(STDTxGuidePreferences.ALLOW_PUSH_NOTIFICATIONS, true).commit();
                    AppManager.sc.trackEvent(Constants.SC_EVENT_PUSH_NOTIFICATION_REGISTER, Constants.SC_PAGE_TITLE_SETTINGS, Constants.SC_SECTION_SETTINGS);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            AppManager.pushManager.registerForPushNotifications();
                        }
                    });
                } else {
                    AppManager.editor.putBoolean(STDTxGuidePreferences.ALLOW_PUSH_NOTIFICATIONS, false).commit();
                    AppManager.sc.trackEvent(Constants.SC_EVENT_PUSH_NOTIFICATION_UNREGISTER, Constants.SC_PAGE_TITLE_SETTINGS, Constants.SC_SECTION_SETTINGS);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            AppManager.pushManager.unregisterForPushNotifications();
                        }
                    });
                }
            }
        });
        AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_SETTINGS, Constants.SC_SECTION_SETTINGS);
        return view;
    }
}
