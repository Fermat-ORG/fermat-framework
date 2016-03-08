package com.mati.bitcoin_exchange_widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure.CurrencyPairImpl;

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

//	private String getRandomMood() {
//		Random r = new Random(Calendar.getInstance().getTimeInMillis());
//		int pos = r.nextInt(moods.size());
//		return moods.get(pos);
//	}

	private void updateMood(Intent intent) {
        Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the intent " + intent);
        if (intent != null){
    		String requestedAction = intent.getAction();
            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the action " + requestedAction);
    		if (requestedAction != null && requestedAction.equals(UPDATEMOOD)){
	            //this.currentMood = getRandomMood();

				CurrencyExchangeRateProviderManager currencyExchangeRateProviderManager = null;

				try {
					currencyExchangeRateProviderManager = (CurrencyExchangeRateProviderManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(
							 Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM,
							 Layers.PROVIDER,
							 Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA,
							 Developers.BITDUBAI,
							 new Version()));
				} catch (VersionNotFoundException e) {
					e.printStackTrace();
				} catch (CantStartPluginException e) {
					e.printStackTrace();
				}


				int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

	            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the currentMood " + currentMood + " to widget " + widgetId);

	            AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
	            RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.bitcoin_widget);





				CurrencyPair usdVefCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);
				try {
					ExchangeRate rate = currencyExchangeRateProviderManager.getCurrentExchangeRate(usdVefCurrencyPair);
					views.setTextViewText(R.id.txt_base_price, String.valueOf(rate.getSalePrice()));
				} catch (UnsupportedCurrencyPairException e) {
					e.printStackTrace();
				} catch (CantGetExchangeRateException e) {
					e.printStackTrace();
				}





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
