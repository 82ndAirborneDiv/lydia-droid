package gov.cdc.stdtxguide;

import android.app.Application;
import android.content.SharedPreferences;

import com.pushwoosh.PushManager;

/**AppManager.java
 * lydia-droid
 *
 * Created by jason on 11/10/15.
 * Copyright (c) 2015 Informatics Research and Development Lab. All rights reserved.
 */
public class AppManager extends Application {
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static ConditionContent conditionContent;
    //public static SiteCatalystController sc;
    public static PushManager pushManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //set global instance of Shared Prefs and instantiate global editor
        pref = getApplicationContext().getSharedPreferences(STDTxGuidePreferences.PREFS_NAME, 0);
        editor =  pref.edit();

        //Create global instance of ConditionContent to reduce duplicate processing
        conditionContent = new ConditionContent(getApplicationContext());

        if(pref.getBoolean(STDTxGuidePreferences.FIRST_LAUNCH, true)){
            setDefaultPrefs();
            editor.putBoolean(STDTxGuidePreferences.FIRST_LAUNCH, false).commit();
        }

        //TODO Uncomment and implement SiteCatalyst for each page
        // Create SiteCatalystController instance and log App Launch event.
        //sc = new SiteCatalystController();
        //sc.trackAppLaunchEvent();



/*   TODO Uncomment when ready to add pushwoosh support
        pushManager = PushManager.getInstance(this);

        //Register for Pushwoosh
        if (pref.getBoolean(STDTxGuidePreferences.ALLOW_PUSH_NOTIFICATIONS, true)) {
            //Register for push!
            pushManager.registerForPushNotifications();
            try {
                pushManager.onStartup(this);
            } catch (Exception e) {
                //push notifications are not available or AndroidManifest.xml is not configured properly
            }

        }*/
    }
    private void setDefaultPrefs(){
        editor.putBoolean(STDTxGuidePreferences.ALLOW_PUSH_NOTIFICATIONS, true);
        editor.putBoolean(STDTxGuidePreferences.AGREED_TO_EULA, false);
        editor.commit();
    }
}

