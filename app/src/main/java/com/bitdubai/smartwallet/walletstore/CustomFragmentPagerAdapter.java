package com.bitdubai.smartwallet.walletstore;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * FragmentPagerAdapter.
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    /** {@link PageItem} �̃��X�g. */
    private ArrayList<PageItem> mList;
    
    /**
     * �R���X�g���N�^.
     * @param fm {@link FragmentManager}
     */
    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<PageItem>();
    }
    
    @Override
    public Fragment getItem(int position) {
        PageItem item = mList.get(position);
        if (PageItem.RELATIVE == item.fragmentKind) {
            // RelativeLayout �� Fragment
            return new RecommendFragment();
        }
        // GridView �� Fragment
        GridViewFragment gridViewFragment = new GridViewFragment();
        // Bundle ���쐬
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", item.appList);
        gridViewFragment.setArguments(bundle);
        return gridViewFragment;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).title;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
    
    /**
     * �A�C�e����ǉ�����.
     * @param item {@link PageItem}
     */
    public void addItem(PageItem item) {
        mList.add(item);
    }
    
}
