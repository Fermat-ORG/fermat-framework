package com.bitdubai.sub_app.wallet_factory.fragment.version_3.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.common.MyApplication;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.CommunityFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.DesktopFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ProfileFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ContactsFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.BusProvider;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.FragmentEvent;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.FragmentsEnum;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils.SmartFragmentStatePagerAdapter;
import com.squareup.otto.Subscribe;

/**
 * Created by Nicolas on 30/04/2015.
 */
public class FactoryActivity extends FragmentActivity {

    private static final CharSequence TITLE = "Wallet Factory";

    private SmartFragmentStatePagerAdapter mAdapterViewPager;
    private ViewPager mViewPager;

    private static ArrayMap<Integer, Fragment> mCurrentEntries = new ArrayMap<Integer, Fragment>();
    private static ArrayMap<Integer, Fragment> mRemovedEntries = new ArrayMap<Integer, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the UI from res/layout/wallet_factory_activity_version_3.xml
        setContentView(R.layout.wallet_factory_activity_version_3);
        MyApplication.setActivityId("FactoryActivity");

        createFragments();

        // The {@link ViewPager} that will host the section contents.
        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        mAdapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapterViewPager);

        // Attach the page change listener inside the activity
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(FactoryActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(TITLE);
            actionBar.setIcon(R.drawable.ic_action_factory);
        }
    }

    private void createFragments() {
        mCurrentEntries.put(FragmentsEnum.PROFILE.ordinal(), ProfileFragment.newInstance(0, "Profile"));
        mCurrentEntries.put(FragmentsEnum.DESKTOP.ordinal(), DesktopFragment.newInstance(1, "Desktop"));
        mCurrentEntries.put(FragmentsEnum.CONTACTS.ordinal(), ContactsFragment.newInstance(2, "Contacts"));
        mCurrentEntries.put(FragmentsEnum.COMMUNITY.ordinal(), CommunityFragment.newInstance(3, "Community"));
    }

    private void addToRemovedEntriesList(FragmentsEnum key, Fragment f) {
        mRemovedEntries.put(key.ordinal(), f);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    // you need to register the activity
    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    //public void onDeleteOrAddCommunityFragment(ProfileFragment.ProfileFragmentEvent event) {
    public void onDeleteOrAddFragment(FragmentEvent event) {
        if (mCurrentEntries.size() == 1) {
            Toast.makeText(this, "You need to have at least one page.", Toast.LENGTH_LONG).show();
        } else {
            FragmentsEnum msg = event.message;
            Fragment nf;
            if (mCurrentEntries.containsKey(msg.ordinal())) {
                nf = mCurrentEntries.remove(msg.ordinal());
                addToRemovedEntriesList(msg, nf);
            } else {
                nf = mRemovedEntries.remove(msg.ordinal());
                mCurrentEntries.put(msg.ordinal(), nf);
            }
            mAdapterViewPager.notifyDataSetChanged();
        }
    }

    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return mCurrentEntries.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return mCurrentEntries.valueAt(position);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return (String) mCurrentEntries.valueAt(position).getArguments().get("title");
        }

        //this is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

    }

}
