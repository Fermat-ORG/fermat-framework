package com.bitdubai.sub_app.wallet_manager.fragment.welcome_wizard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.adapter.WizardPageAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.wizard.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.04.13..
 */
public class WelcomeWizardFirstFragment extends AbstractFermatFragment {


    ViewPager viewPager;
    WizardPageAdapter wizardPageAdapter;
    private String TAG = "WelcomeFragment";
    int position = 0;
    Button btnGotIt;
    List<AbstractFermatFragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_base_layout, container, false);
        fragments.add(WelcomeWizardSecondFragment.newInstance());
        fragments.add(WelcomeWizardThridFragment.newInstance());
        fragments.add(WelcomeWizardFourthFragment.newInstance());

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_welcome);
        btnGotIt = (Button) view.findViewById(R.id.btn_got_it);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, String.format("Change to position %d - Position offset %f", position, positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                boolean isNext = WelcomeWizardFirstFragment.this.position <= position;
                WelcomeWizardFirstFragment.this.position = position;
                if (position == 0) {
                    showView(false, btnGotIt);
                    showView(true, btnGotIt);
                } else if (position > 0) {
                    showView(true, btnGotIt);
                    showView(true, btnGotIt);
                }
                if (position >= fragments.size() - 1)
                    btnGotIt.setText("Finish");
                else
                    btnGotIt.setText("Next >>");
                if (position > 0 && isNext) {
                    // Save last page before moving to the next slide
                    WizardPageListener lastPage =
                            (WizardPageListener) fragments.get(position - 1);
                    lastPage.savePage();
                    // notify fragment active
                    WizardPageListener page = (WizardPageListener) fragments.get(position);
                    //page.onActivated(getData());
                    //setWizardActivity(page.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing...
            }
        });

        WizardPageAdapter adapter = new WizardPageAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        position = 0;

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
        return view;
    }

    /**
     * Show or hide any view
     *
     * @param show true to show, otherwise false
     * @param view View to show or hide
     */
    public void showView(boolean show, View view) {
        if (view == null)
            return;
        Animation fade = AnimationUtils.loadAnimation(getActivity(), show ? R.anim.fade_in : R.anim.fade_out);
        view.setAnimation(fade);
        if (show && (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE))
            view.setVisibility(View.VISIBLE);
        else if (!show && (view.getVisibility() == View.VISIBLE))
            view.setVisibility(View.INVISIBLE);
        else
            return;
    }

    public static AbstractFermatFragment newInstance() {
        return new WelcomeWizardFirstFragment();
    }


}
