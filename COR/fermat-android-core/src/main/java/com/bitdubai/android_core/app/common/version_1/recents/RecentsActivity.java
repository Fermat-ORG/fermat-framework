/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bitdubai.android_core.app.common.version_1.recents;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_android_api.engine.FermatRecentApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.wirelesspienetwork.overview.misc.Utilities;
import com.wirelesspienetwork.overview.model.OverviewAdapter;
import com.wirelesspienetwork.overview.views.Overview;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main Recents activity that is started from AlternateRecentsComponent.
 *
 * Created by Matias Furszyfer
 */
public class RecentsActivity extends Activity implements Overview.RecentsViewCallbacks, OverviewAdapter.Callbacks, RecentCallback<RecentApp> {
    private static final String TAG = "RecentsActivity";
    boolean mVisible;
    // Top level views
    Overview mRecentsView;

    List<FermatRecentApp> recents;
    private FermatTextView emptyView;

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For the non-primary user, ensure that the SystemSericesProxy is initialized

        // Initialize the widget host (the host id is static and does not change)

        Log.i(TAG,"onCreate");
        // Set the Recents layout
        setContentView(R.layout.recents);

        emptyView = (FermatTextView) findViewById(R.id.empty_text);

        emptyView.setVisibility(View.VISIBLE);

        mRecentsView = (Overview) findViewById(R.id.recents_view);
        mRecentsView.setCallbacks(this);
        mRecentsView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        // Register the broadcast receiver to handle messages when the screen is turned off
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(SearchManager.INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED);

        // Private API calls to make the shadows look better
        try {
            Utilities.setShadowProperty("ambientRatio", String.valueOf(1.5f));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Mark Recents as visible
        mVisible = true;

        Object[] objects = ((Object[]) getIntent().getSerializableExtra(ApplicationConstants.RECENT_APPS));
        List<RecentApp> models = new ArrayList<>();//Arrays.asList((RecentApp[])objects);

        RecentApp recentApp;
        for (Object object : objects) {
            recentApp = (RecentApp) object;
            if(recentApp.getFermatApp().getAppType() != FermatAppType.DESKTOP) {
                recentApp.getFermatApp().setBanner(selectBannerSwitch(recentApp.getPublicKey()));
                models.add(recentApp);
            }
        }
//        for(int i = 0; i < 4; ++i) {
//            Random random = new Random();
//            random.setSeed(i);
//            int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
//            models.add(new RecentApp("reference_wallet", new FermatApp() {
//                @Override
//                public String getAppName() {
//                    return "wallet";
//                }
//
//                @Override
//                public String getAppPublicKey() {
//                    return "reference_wallet";
//                }
//
//                @Override
//                public AppsStatus getAppStatus() {
//                    return AppsStatus.ALPHA;
//                }
//
//                @Override
//                public FermatAppType getAppType() {
//                    return FermatAppType.WALLET;
//                }
//
//                @Override
//                public byte[] getAppIcon() {
//                    return new byte[0];
//                }
//            },i));
//        }

        RecentsAdapter recentsAdapter = new RecentsAdapter(this,models);
        recentsAdapter.setCallbacks(this);
        recentsAdapter.setRecentCallback(this);

        mRecentsView.setTaskStack(recentsAdapter);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                stack.notifyDataSetInserted(new Integer(1), 2);
//            }
//        },2000);

        if(models.isEmpty()){
            emptyView.setText("Nothing to show");
            FermatAnimationsUtils.showEmpty(this,true,emptyView);
        }

    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
    }

    @Override
    public void onAllCardsDismissed() {
        emptyView.setText("Nothing to show");
        FermatAnimationsUtils.showEmpty(this,true,emptyView);
    }

    @Override
    public void onCardDismissed(int position) {

    }

    @Override
    public void onCardAdded(OverviewAdapter overviewAdapter, int i) {

    }

    @Override
    public void onCardRemoved(OverviewAdapter overviewAdapter, int i) {

    }

    @Override
    public void onItemClick(RecentApp item) {
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,item.getPublicKey());
//        resultIntent.putExtra(ApplicationConstants.INTENT_APP_TYPE, item.getFermatApp().getAppType());
//        // TODO Add extras or a data URI to this intent as appropriate.
//        setResult(Activity.RESULT_OK, resultIntent);
//
        try {
            FermatApplication.getInstance().getApplicationManager().openFermatApp(item.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onFirstElementAdded() {
        FermatAnimationsUtils.showEmpty(this, false, emptyView);
    }



    // metodo totalmente innecesario que será eliminado una vez que se puedan instalar las apps desde la store
    private int selectBannerSwitch(String key){
        int res = 0;

        try {
            switch (WalletsPublicKeys.valueOf(key)) {
                case CCP_REFERENCE_WALLET:
                    res = R.drawable.banner_bitcoin_wallet;
                    break;
                case BNK_BANKING_WALLET:
                    break;
                case CSH_MONEY_WALLET:
                    break;
                case CBP_CRYPTO_BROKER_WALLET:
                    res = R.drawable.banner_crypto_broker;
                    break;
                case CBP_CRYPTO_CUSTOMER_WALLET:
                    res = R.drawable.banner_crypto_customer_wallet;
                    break;
                case DAP_ISSUER_WALLET:
                    res = R.drawable.banner_asset_issuer_wallet;
                    break;
                case DAP_USER_WALLET:
                    res = R.drawable.banner_asset_user_wallet;
                    break;
                case DAP_REDEEM_WALLET:
                    res = R.drawable.banner_redeem_point_wallet;
                    break;
                case TKY_FAN_WALLET:
                    res = R.drawable.banner_tky;
                    break;
                default:
                    break;

            }
        }catch (IllegalArgumentException i){
            try {
                switch (SubAppsPublicKeys.valueOf(key)) {
                    case CHT_OPEN_CHAT:
                        break;
                    case CBP_BROKER_COMMUNITY:
                        break;
                    case CBP_BROKER_IDENTITY:
                        break;
                    case CBP_CUSTOMER_COMMUNITY:
                        break;
                    case CBP_CUSTOMER_IDENTITY:
                        break;
                    case CCP_COMMUNITY:
                        res = R.drawable.cryptou_community;
                        break;
                    case CCP_IDENTITY:
                        res = R.drawable.identity_banner;
                        break;
                    case CWP_PUBLISHER:
                        break;
                    case CWP_STORE:
                        break;
                    case DAP_COMMUNITY_ISSUER:
                        break;
                    case DAP_COMMUNITY_USER:
                        break;
                    case DAP_COMMUNITY_REDEEM:
                        break;
                    case DAP_IDENTITY_ISSUER:
                        break;
                    case DAP_IDENTITY_USER:
                        break;
                    case DAP_IDENTITY_REDEEM:
                        break;
                    case DAP_FACTORY:
                        break;
                    case PIP_DEVELOPER:
                        break;

                }
            }catch (Exception e){
                res = R.drawable.banner_bitcoin_wallet;
            }


        }

        return res;
    }
}
