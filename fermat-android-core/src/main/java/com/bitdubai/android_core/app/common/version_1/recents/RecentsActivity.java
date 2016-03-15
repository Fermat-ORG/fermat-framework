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
import android.view.View;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.FermatRecentApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.wirelesspienetwork.overview.misc.Utilities;
import com.wirelesspienetwork.overview.model.OverviewAdapter;
import com.wirelesspienetwork.overview.views.Overview;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main Recents activity that is started from AlternateRecentsComponent.
 */
public class RecentsActivity extends Activity implements Overview.RecentsViewCallbacks, OverviewAdapter.Callbacks, RecentCallback<RecentApp> {
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
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,item.getPublicKey());
        resultIntent.putExtra(ApplicationConstants.INTENT_APP_TYPE, item.getFermatApp().getAppType());
        // TODO Add extras or a data URI to this intent as appropriate.
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onFirstElementAdded() {
        FermatAnimationsUtils.showEmpty(this, false, emptyView);
    }



    // metodo totalmente innecesario que será eliminado una vez que se puedan instalar las apps desde la store
    private int selectBannerSwitch(String key){
        int res = 0;
        switch (key){
            case "reference_wallet":
                res = R.drawable.banner_bitcoin_wallet;
                break;
        }

        return res;
    }
}
