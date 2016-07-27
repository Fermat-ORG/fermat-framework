package com.bitdubai.android_core.app.common.version_1.util;

import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.settings_slider.SettingsCallback;
import com.bitdubai.android_core.app.common.version_1.settings_slider.SettingsItem;
import com.bitdubai.android_core.app.common.version_1.settings_slider.SettingsSlider;
import com.bitdubai.android_core.app.common.version_1.settings_slider.SettingsSliderProvisoryData;
import com.bitdubai.android_core.app.common.version_1.top_settings.AppStatusDialog;
import com.bitdubai.android_core.app.common.version_1.top_settings.AppStatusListener;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.callback.AppStatusCallbackChanges;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;

import java.lang.ref.WeakReference;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


/**
 * Created by mati on 2016.03.02..
 */
public class BottomMenuReveal implements SettingsCallback<SettingsItem> {


    /**
     *
     */
    private boolean hidden;
    private ViewGroup mRevealView;
    private WeakReference<FermatActivity> fermatActivity;
    private View.OnClickListener onClickListener;
    private SettingsSlider settingsSlider;
    private AppStatusCallbackChanges appStatusListener;
    AndroidCoreSettings androidCoreSettings;

    private boolean isStart;

    int cx;
    int cy;
    int radius;

    public BottomMenuReveal(final ViewGroup mRevealView, final FermatActivity activity) {
        this.hidden = true;
        this.mRevealView = mRevealView;
        this.isStart = false;
        this.fermatActivity = new WeakReference<FermatActivity>(activity);
    }

    public void buildMenuSettings() {
        final View backgroundShadow = fermatActivity.get().findViewById(R.id.background_shadow_container);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cx = (mRevealView.getLeft() + mRevealView.getRight());
                //                int cy = (mRevealView.getTop() + mRevealView.getBottom())/2;
                int cy = mRevealView.getBottom();

                int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


                    SupportAnimator animator =
                            ViewAnimationUtils.createCircularReveal(mRevealView.getChildAt(0), 0, cy, 0, radius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(650);
                    animator.setupStartValues();

                    SupportAnimator animator_reverse = animator.reverse();

                    if (hidden) {
                        mRevealView.setVisibility(View.VISIBLE);
                        animator.start();
                        hidden = false;
                        if (!isStart) {
                            onClickListener.onClick(null);
                            isStart = true;
                            onClickListener.onClick(null);
                        }
                    } else {
                        animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {
                                mRevealView.setVisibility(View.INVISIBLE);
                                hidden = true;

                            }

                            @Override
                            public void onAnimationCancel() {

                            }

                            @Override
                            public void onAnimationRepeat() {

                            }
                        });
                        animator_reverse.start();

                    }
                } else {
                    if (hidden) {
                        android.animation.Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, 0, cy, 0, radius);
                        mRevealView.setVisibility(View.VISIBLE);
                        FrameLayout frameLayout = (FrameLayout) fermatActivity.get().findViewById(R.id.container_main);
                        frameLayout.bringChildToFront(mRevealView);
                        FermatAnimationsUtils.showEmpty(fermatActivity.get().getApplicationContext(), true, backgroundShadow);
                        anim.start();
                        hidden = false;

                    } else {
                        android.animation.Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, 0, cy, radius, 0);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(android.animation.Animator animation) {
                                super.onAnimationEnd(animation);
                                mRevealView.setVisibility(View.INVISIBLE);
                                FermatAnimationsUtils.showEmpty(fermatActivity.get().getApplicationContext(), false, backgroundShadow);
                                hidden = true;
                            }
                        });
                        anim.start();

                    }
                }
            }
        };
        fermatActivity.get().findViewById(R.id.btn_settings_bottom_menu).setOnClickListener(onClickListener);
        backgroundShadow.setOnClickListener(onClickListener);
        initRecyclerview();
    }

    private void initRecyclerview() {
        settingsSlider = new SettingsSlider(fermatActivity.get(), SettingsSliderProvisoryData.getSettings());
        settingsSlider.setClickCallback(this);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void clear() {
        if (appStatusListener != null)
            appStatusListener.clear();
        if (mRevealView != null) mRevealView.removeAllViews();
        fermatActivity.clear();
        onClickListener = null;
    }


    @Override
    public void onItemClickListener(View view, SettingsItem item, int position, View... views) {
        switch (item.getSettingsType()) {
            case APP_STATUS:
                if (appStatusListener == null) {
                    appStatusListener = new AppStatusListener(fermatActivity.get(), (ImageButton) view, (FermatTextView) views[0]);
                }
                try {
                    new AppStatusDialog(fermatActivity.get(), FermatSystemUtils.getAndroidCoreModule(), appStatusListener).show();
                } catch (CantCreateProxyException e) {
                    e.printStackTrace();
                }
                break;
            case FERMAT_NETWORK:
                //fermatActivity.get().changeActivity(Activities.DESKTOP_SETTING_FERMAT_NETWORK.getCode(), "main_desktop");
                break;
            case BITCOIN_NETWORK:
                break;
            case PRIVATE_NETWORK:
                break;
            case RECENTS:
                fermatActivity.get().openRecentsScreen();
                break;
            case HELP:
                fermatActivity.get().changeActivity(Activities.DESKTOP_WIZZARD_WELCOME.getCode(), null);
                break;
            case REPORT:
//                if(androidCoreSettings==null){
//                    androidCoreSettings = new AndroidCoreSettings(AppsStatus.ALPHA);
//                }
//                //AndroidCoreSettings androidCoreSettings = FermatSystemUtils.getAndroidCoreModule().loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
//                int res= 0;
//                if(androidCoreSettings.isErrorReportEnabled()){
//                    res = R.drawable.icon_suport;
//                }else res = R.drawable.icon_suport_on;
//                view.setBackgroundResource(res);
//                try {
//                    FermatSystemUtils.getErrorManager().enabledErrorReport(!androidCoreSettings.isErrorReportEnabled());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                androidCoreSettings.setIsErrorReportEnabled(!androidCoreSettings.isErrorReportEnabled());
                fermatActivity.get().changeActivity(Activities.DESKTOP_SETTING_IMPORT_KEY.getCode(), null, null);
                break;
        }
    }

}
