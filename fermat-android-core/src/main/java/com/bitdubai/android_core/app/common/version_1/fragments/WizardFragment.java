package com.bitdubai.android_core.app.common.version_1.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Wizard Dialog Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class WizardFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    private static final String TAG = "WizardFragment";
    /**
     * FLAGS
     */
    private boolean isAttached;

    /**
     * DATA
     */
    private Wizard wizard;
    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
    private int position = -1;
    /**
     * UI
     */
    private View rootView;
    private ViewPager viewPager;
    private FermatTextView back;
    private FermatTextView next;
    /**
     * ARGUMENTS
     */
    private Object[] args;

    /**
     * Set Wizard FragmentsEnumType
     *
     * @param wizard
     */
    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenTheme);
        setupFragments();
        if (fragments == null || fragments.size() == 0) {
            // nothing to see here...
            dismiss();
            return;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    /*
    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(R.style.FullScreenDialog, theme);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.runtime_app_wizard_fragment, container, false);
        if (fragments != null && fragments.size() > 0) {
            Log.i(TAG, String.format("Wizard Pages: %d", fragments.size()));
            // load ui
            viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
            viewPager.setPageTransformer(true, new DepthPageTransformer());
            //WizardPageAdapter adapter = new WizardPageAdapter(getChildFragmentManager(), fragments);
            //viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            position = 0;
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.i(TAG, String.format("Change to position %d - Position offset %f", position, positionOffset));
                }

                @Override
                public void onPageSelected(int position) {
                    boolean isNext = WizardFragment.this.position <= position;
                    WizardFragment.this.position = position;
                    if (position == 0) {
                        showView(false, back);
                        showView(true, next);
                    } else if (position > 0) {
                        showView(true, back);
                        showView(true, next);
                    }
                    if (position >= fragments.size() - 1)
                        next.setText("Finish");
                    else
                        next.setText("Next >>");
                    if (position > 0 && isNext) {
                        // Save last page before moving to the next slide
                        WizardPageListener page =
                                (WizardPageListener) fragments.get(position - 1);
                        page.savePage();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // do nothing...
                }
            });

            back = (FermatTextView) rootView.findViewById(R.id.back);
            next = (FermatTextView) rootView.findViewById(R.id.next);

            if (position == 0 && back != null)
                back.setVisibility(View.INVISIBLE);

            if (fragments.size() > 1 && next != null) {
                next.setText("Next >>");
                next.setVisibility(View.VISIBLE);
            } else if (next != null) {
                next.setText("Finish");
                next.setVisibility(View.VISIBLE);
            }
            // Setting up listeners
            if (next != null)
                next.setOnClickListener(this);
            if (back != null)
                back.setOnClickListener(this);

        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    private void setupFragments() {
        if (wizard != null) {
            for (WizardPage page : wizard.getPages()) {
                switch (page.getType()) {
                    case CWP_WALLET_FACTORY_CREATE_STEP_1:
                        //fragments.add(new CreateWalletFragment());
                        break;
                    case CWP_WALLET_FACTORY_CREATE_STEP_2:
                        //fragments.add(new SetupNavigationFragment());
                        break;
                    case CWP_WALLET_PUBLISHER_PUBLISH_STEP_1:
                        //fragments.add(PublishFactoryProjectStep1.newInstance(args));
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                doBack();
                break;
            case R.id.next:
                doNext();
                break;
            default:
                break;
        }
    }

    /**
     * Perform back event
     */
    public void doBack() {
        if (position > 0 && viewPager != null)
            viewPager.setCurrentItem(position - 1);
    }

    /**
     * Move to next slide or finish this wizard
     */
    public void doNext() {
        if (position >= fragments.size() - 1) {
            // validate all fragments before finish
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            dialog.show();
            new Thread() {
                @Override
                public void run() {
                    int posFail = -1;
                    for (int x = 0; x < fragments.size(); x++) {
                        try {
                            WizardPageListener page =
                                    (WizardPageListener) fragments.get(x);
                            if (!page.validate()) {
                                posFail = x;
                                break;
                            }
                        } catch (Exception ex) {
                            posFail = x;
                            Log.getStackTraceString(ex);
                            break;
                        }
                    }
                    final int pos = posFail;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                if (pos == -1) {
                                    // close this wizard
                                    dismiss();
                                } else if (pos > -1) {
                                    if (viewPager != null) {
                                        viewPager.setCurrentItem(pos);
                                        Toast.makeText(getActivity(), "Something is missing, please review this step.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                }
            }.run();
        } else if (position < (fragments.size() - 1)) {
            // move to the next slide
            if (viewPager != null) {
                viewPager.setCurrentItem(position + 1);
            }
        }
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
        if (!isAttached)
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

    /**
     * Setting arguments like session, module, etc..
     *
     * @param args Object... Arguments
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }
}
