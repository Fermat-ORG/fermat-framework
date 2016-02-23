package com.mati.bitcoin_exchange_widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.bitdubai.fermat_core.FermatSystem;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

public class CurrentMoodService extends Service {
	public static final String UPDATEMOOD = "UpdateMood";
	public static final String CURRENTMOOD = "CurrentMood";
	
	private String currentMood;
	private LinkedList<String> moods;
	private final FermatSystem fermatSystem;
	
	public CurrentMoodService(){
		this.moods = new LinkedList<String>();
		fermatSystem = FermatSystem.getInstance();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);
		
        Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "onStartCommand");

        updateMood(intent);
			
		stopSelf(startId);
		
		return START_STICKY;
	}

	private String getRandomMood() {
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		int pos = r.nextInt(moods.size());
		return moods.get(pos);
	}

	private void updateMood(Intent intent) {
        Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the intent " + intent);
        if (intent != null){
    		String requestedAction = intent.getAction();
            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the action " + requestedAction);
    		if (requestedAction != null && requestedAction.equals(UPDATEMOOD)){
	            this.currentMood = getRandomMood();

//				fermatSystem.startAndGetPluginVersion(new PluginVersionReference(
//						Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM,
//						Layers.PROVIDER,
//						Developers.BITDUBAI,
//						new Version()))



	            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

	            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the currentMood " + currentMood + " to widget " + widgetId);

	            AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
	            RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.bitcoin_widget);
	            views.setTextViewText(R.id.txt_base_price, currentMood);
	            appWidgetMan.updateAppWidget(widgetId, views);
	            
	            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "CurrentMood updated!");
    		}
        }
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
