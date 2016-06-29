package org.fermat.fermat_dap_android_sub_app_asset_user_community.common.header;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nerio on 07/03/16.
 */
public abstract class UserAbstractCommunityHeaderFactory<OCL extends View.OnClickListener> {

    protected ViewGroup mRootView;
    private View mHeader;
    protected OCL onClickListener;


    public static View constructHeader(ViewGroup view) {
        UserAssetCommunityHeaderFactory headerFactory = new UserAssetCommunityHeaderFactory(view);
        return headerFactory.obtainView();
    }

    public UserAbstractCommunityHeaderFactory(ViewGroup mRootView) {
        this.mRootView = mRootView;
        mRootView.setVisibility(View.VISIBLE);
        mRootView.setBackgroundColor(Color.parseColor(setParentBackgroundColor()));
        onClickListener = setOnClickListener();
        setUp();
    }

    public void setVisibility(int visibility) {
        mRootView.setVisibility(visibility);
    }

    protected LayoutInflater getLayoutInflater() {
        return (LayoutInflater) mRootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void setUp() {
        LayoutInflater layoutInflater = getLayoutInflater();
        mHeader = View.inflate(mRootView.getContext(), setLayoutId(), mRootView);
        constructHeader(layoutInflater, (ViewGroup) mHeader);
    }

    public View obtainView() {
        return mRootView;
    }

    protected View getHeader() {
        return mHeader;
    }

    public abstract void constructHeader(LayoutInflater layoutInflater, ViewGroup header);

    public abstract String setParentBackgroundColor();

    public abstract int setLayoutId();

    protected abstract OCL setOnClickListener();
}
