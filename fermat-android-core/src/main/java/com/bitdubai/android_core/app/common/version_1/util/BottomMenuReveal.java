package com.bitdubai.android_core.app.common.version_1.util;

import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.top_settings.AppStatusDialog;
import com.bitdubai.android_core.app.common.version_1.top_settings.AppStatusListener;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;

import java.lang.ref.WeakReference;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


/**
 * Created by mati on 2016.03.02..
 */
public class BottomMenuReveal {

    /**
     * Listeners
     */
    private AppStatusListener appStatusListener;

    /**
     *
     */
    private boolean hidden;
    private ViewGroup mRevealView;
    private WeakReference<FermatActivity> fermatActivity;
    private View.OnClickListener onClickListener;

    public BottomMenuReveal(final ViewGroup mRevealView, final FermatActivity activity) {
        this.hidden = false;
        this.mRevealView = mRevealView;
        this.fermatActivity = new WeakReference<FermatActivity>(activity);
    }

    public void buildMenuSettings(){
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
                            ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(800);

                    SupportAnimator animator_reverse = animator.reverse();

                    if (hidden) {
                        mRevealView.setVisibility(View.VISIBLE);
                        animator.start();
                        hidden = false;
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
        //view.findViewById(R.id.img_fermat_setting).setOnClickListener(onClickListener);


        backgroundShadow.setOnClickListener(onClickListener);


        ImageButton btn_fermat_apps_status = (ImageButton)mRevealView.findViewById(R.id.btn_fermat_apps_status);
        final AndroidCoreModule androidCoreModule = FermatSystemUtils.getAndroidCoreModule();

        if(appStatusListener==null){
            appStatusListener = new AppStatusListener(fermatActivity.get(),btn_fermat_apps_status);
        }

        try {
            AndroidCoreSettings androidCoreSettings = (AndroidCoreSettings) androidCoreModule.getSettingsManager().loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
            switch (androidCoreSettings.getAppsStatus()){
                case RELEASE:
                    btn_fermat_apps_status.setBackgroundResource(R.drawable.icon_relese);
                    break;
                case BETA:
                    btn_fermat_apps_status.setBackgroundResource(R.drawable.icons_beta);
                    break;
                case ALPHA:
                    btn_fermat_apps_status.setBackgroundResource(R.drawable.icons_alfa);
                    break;
                case DEV:
                    btn_fermat_apps_status.setBackgroundResource(R.drawable.icons_developer);
                    break;
                default:
                    btn_fermat_apps_status.setBackgroundResource(R.drawable.icon_relese);
                    break;
            }
        } catch (CantGetSettingsException | SettingsNotFoundException e) {
            btn_fermat_apps_status.setBackgroundResource(R.drawable.icon_relese);
            // e.printStackTrace();
        }
        btn_fermat_apps_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AppStatusDialog(fermatActivity.get(), androidCoreModule, appStatusListener).show();
            }
        });


        ImageButton btn_fermat_network = (ImageButton)mRevealView.findViewById(R.id.btn_fermat_network);
        btn_fermat_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fermatActivity.get().changeActivity(Activities.DESKTOP_SETTING_FERMAT_NETWORK.getCode(), "main_desktop");
            }
        });

    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void clear(){
        if(appStatusListener != null)
            appStatusListener.clear();
        if(mRevealView!=null)mRevealView.removeAllViews();
        fermatActivity.clear();
        onClickListener = null;
    }


}
