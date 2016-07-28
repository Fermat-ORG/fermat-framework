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
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_core.FermatSystem;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure.CurrencyPairImpl;

public class CurrentMoodService extends Service {
    public static final String UPDATEMOOD = "UpdateMood";
    public static final String CURRENTMOOD = "CurrentMood";

    private String currentMood;
    private LinkedList<String> moods;
    private final FermatSystem fermatSystem;

    public CurrentMoodService() {
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
        if (intent != null) {
            String requestedAction = intent.getAction();
            Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the action " + requestedAction);
            if (requestedAction != null && requestedAction.equals(UPDATEMOOD)) {


                //this.currentMood = getRandomMood();

                CurrencyExchangeProviderFilterManager providerFilter = null;
                try {
                    providerFilter = (CurrencyExchangeProviderFilterManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(
                            Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM,
                            Layers.SEARCH,
                            Plugins.FILTER,
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
                RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.bitcoin_widget);


//				final CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
//				try {
//					Collection<CurrencyExchangeRateProviderManager> filteredProviders =
//							providerFilter.getProviderReferencesFromCurrencyPair(wantedCurrencyPair);
//
//					for (final CurrencyExchangeRateProviderManager p : filteredProviders) {
//						String providerName = p.getProviderName();
//
//						//UUID del provider, puedes capturarlo y luego obtener una referencia nueva a el usando providerFilter.getProviderReference(UUID)
//						UUID providerId = p.getProviderId();
//
//						Callable<ExchangeRate> callable = new Callable() {
//							@Override
//							public ExchangeRate call() throws Exception {
//								return p.getCurrentExchangeRate(wantedCurrencyPair);
//							}
//						};
//						Future<ExchangeRate> future = executorService.submit(callable);
//						//Tu exchangerate.
//						ExchangeRate rate = future.get();
//						views.setTextViewText(R.id.txt_base_price, String.valueOf(rate.getSalePrice()));
//					}
//				}catch (Exception e){
//					e.printStackTrace();
//				}


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
