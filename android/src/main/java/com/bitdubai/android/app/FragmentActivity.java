package com.bitdubai.android.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.android.app.common.version_1.classes.PagerSlidingTabStrip;

import com.bitdubai.android.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopChatFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopHistoryFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopMapFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopProductsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopReviewsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopShopFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.ProfileCardFrontFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AvailableBalanceFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ChatWithContactFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ContactsFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.DiscountsFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.HomeFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.RefillFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.SendFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ShopFragment;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.App;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.SubApp;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_core.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;
import com.bitdubai.smartwallet.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by toshiba on 24/02/2015.
 */
public class FragmentActivity  extends Activity {


    public CharSequence Title;
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Activity activity;
    private Map<Fragments, Fragment> fragments;
    private AppRuntimeManager appRuntimeMiddleware;
    private AndroidOsAddonRoot Os;
    private CorePlatformContext platformContext;
    private ViewPager pager;

    private  TextView abTitle;
    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;
    private MainMenu mainMenumenu;
    private SideMenu sidemenu;
    private String walletStyle = "";
    private TabStrip tabs;
    private TitleBar titleBar; // Comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_app_activity_fragment);

        Platform platform = MyApplication.getPlatform();

        this.platformContext = platform.getCorePlatformContext();

        this.appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.APP_RUNTIME_MIDDLEWARE);

        this.app = appRuntimeMiddleware.getLastApp();
        this.subApp = appRuntimeMiddleware.getLastSubApp();
        this.activity = appRuntimeMiddleware.getLasActivity();


        // Fragment fragment = appRuntimeMiddleware.getLastFragment();
        MyApplication.setActivityId(activity.getType().getKey());


        this.tabs = activity.getTabStrip();
        this.fragments =activity.getFragments();
        this.titleBar = activity.getTitleBar();

        this.mainMenumenu= activity.getMainMenu();
        this.sidemenu = activity.getSideMenu();


        if(fragments.size() == 1){
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, Fragment>> efragments = this.fragments.entrySet().iterator();

            while (efragments.hasNext()) {
                Map.Entry<Fragments, Fragment> fragmentEntry = efragments.next();

                RuntimeFragment fragment = (RuntimeFragment) fragmentEntry.getValue();
                Fragments type = fragment.getType();
                switch (type) {
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS:
                        if (savedInstanceState == null) {
                            getFragmentManager().beginTransaction()
                                    .add(R.id.container, new ContactsFragment())
                                    .commit();
                        }
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT:
                        if (savedInstanceState == null) {
                            getFragmentManager().beginTransaction()
                                    .add(R.id.container, new ChatWithContactFragment())
                                    .commit();
                        }
                        break;
                    case CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE :
                        if (savedInstanceState == null) {
                            getFragmentManager().beginTransaction()
                                    .add(R.id.container, new AvailableBalanceFragment())
                                    .commit();
                        }
                        break;
                    case CWP_SHOP_MANAGER_MAIN:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL:
                        break;
                    case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                        break;
                    case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                        break;
                    case CWP_WALLET_STORE_MAIN:
                        break;
                    case CWP_WALLET_FACTORY_MAIN:
                        break;

                }
            }

        }


    /*    if(titleBar !=null){
            this.Title = activity.getTitleBar().getLabel();
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            this.abTitle = (TextView) findViewById(titleId);
            abTitle.setTextColor(Color.WHITE);
            abTitle.setTypeface(MyApplication.getDefaultTypeface());
            ActionBar actionBar = getActionBar();
            actionBar.setTitle(this.Title );
            //  actionBar.setIcon(R.drawable.store_icon);
            getActionBar().show();

            ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),null, getActionBar(), getResources(),this.abTitle, this.Title.toString());

            actionBar.setTitle(this.Title );
            getActionBar().show();

        }
        else
        {
            getActionBar().hide();
        }


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);*/

        MyApplication.setActivityProperties(this, getWindow(),getResources(),tabStrip,getActionBar(), titleBar,abTitle,Title);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        switch ( this.activity.getType()) {

            case CWP_SHELL_LOGIN:
                break;
            case CWP_SHOP_MANAGER_MAIN:

                break;
            case CWP_WALLET_MANAGER_MAIN:
                break;
            case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN:

                break;

            case CWP_WALLET_RUNTIME_STORE_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE:
                getMenuInflater().inflate(R.menu.wallet_framework_activity_available_balance_menu, menu);

            case CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS:
                getMenuInflater().inflate(R.menu.wallet_framework_activity_contacts_menu, menu);
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT:
                getMenuInflater().inflate(R.menu.wallet_framework_activity_sent_all_menu, menu);

            case CWP_WALLET_ADULTS_ALL_SHOPS:

                break;
            case CWP_WALLET_ADULTS_ALL_REFFILS:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                break;
            case CWP_WALLET_FACTORY_MAIN:
                break;
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
