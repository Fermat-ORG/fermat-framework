package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ChatWithContactFragment;

/**
 * Created by Natalia on 09/01/2015.
 */
public class ChatWithContactActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_chatovertrx);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChatWithContactFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());

        /* Custom Action Bar with Icon and Text */
      /* Custom Action Bar with Icon and Text */
        String[]  contacts = new String[]{"Céline Begnis", "Kimberly Brown", "Juan Luis R. Pons", "Karina Rodríguez"
                ,"Guillermo Villanueva","Lucia Alarcon De Zamacona", "Luis Fernando Molina", "Mariana Duyos", "Pedro Perrotta"
                , "Simon Cushing","Stephanie Himonidis","Taylor Backus", "Ginny Kaltabanis","Piper Faust","Deniz Caglar"
                ,"Helen Nisbet","Dea Vanagan","Tim Hunter","Madeleine Jordan","Kate Bryan","Victoria Gandit","Jennifer Johnson"
                ,"Robert Wint","Kevin Helms","Teddy Truchot","Hélène Derosier","John Smith","Caroline Mignaux","Guillaume Thery"
                ,"Brant Cryder","Thomas Levy","Louis Stenz" };
        int groupPosition = 0;
        String contact_name = MyApplication.getContact();

        for (int i = 0; i < contacts.length; i++) {

            if (contacts[i] == contact_name) {
                groupPosition = i;
            }
        }

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_chatcontact_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);


        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[groupPosition].toString());


        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture);
        switch (groupPosition) {
            case 0:
                profile_picture.setImageResource(R.drawable.celine_profile_picture);
                break;
            case 1:
                profile_picture.setImageResource(R.drawable.kimberly_profile_picture);
                break;
            case 2:
                profile_picture.setImageResource(R.drawable.juan_profile_picture);
                break;
            case 3:
                profile_picture.setImageResource(R.drawable.karina_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                break;
            case 5:
                profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                break;
            case 6:
                profile_picture.setImageResource(R.drawable.luis_profile_picture);
                break;
            case 7:
                profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                break;
            case 8:
                profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                break;
            case 9:
                profile_picture.setImageResource(R.drawable.simon_profile_picture);
                break;
            case 10:
                profile_picture.setImageResource(R.drawable.stephani_profile_picture);
                break;
            case 11:
                profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                break;
            case 12:
                profile_picture.setImageResource(R.drawable.ginny_profile_picture);
                break;
            case 13:
                profile_picture.setImageResource(R.drawable.piper_profile_picture);
                break;
            case 14:
                profile_picture.setImageResource(R.drawable.deniz_profile_picture);
                break;
            case 15:
                profile_picture.setImageResource(R.drawable.helen_profile_picture);
                break;
            case 16:
                profile_picture.setImageResource(R.drawable.dea_profile_picture);
                break;
            case 17:
                profile_picture.setImageResource(R.drawable.tim_profile_picture);
                break;
            case 18:
                profile_picture.setImageResource(R.drawable.madaleine_profile_picture);
                break;
            case 19:
                profile_picture.setImageResource(R.drawable.kate_profile_picture);
                break;
            case 20:
                profile_picture.setImageResource(R.drawable.victoria_profile_picture);
                break;
            case 21:
                profile_picture.setImageResource(R.drawable.jennifer_profile_picture);
                break;
            case 22:
                profile_picture.setImageResource(R.drawable.robert_profile_picture);
                break;
            case 23:
                profile_picture.setImageResource(R.drawable.kevin_profile_picture);
                break;
            case 24:
                profile_picture.setImageResource(R.drawable.teddy_profile_picture);
                break;
            case 25:
                profile_picture.setImageResource(R.drawable.helene_profile_picture);
                break;
            case 26:
                profile_picture.setImageResource(R.drawable.john_profile_picture);
                break;
            case 27:
                profile_picture.setImageResource(R.drawable.caroline_profile_picture);
                break;
            case 28:
                profile_picture.setImageResource(R.drawable.guillaume_profile_picture);
                break;
            case 29:
                profile_picture.setImageResource(R.drawable.brant_profile_picture);
                break;
            case 30:
                profile_picture.setImageResource(R.drawable.thomas_profile_picture);
                break;
            case 31:
                profile_picture.setImageResource(R.drawable.louis_profile_picture);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_framework_activity_sent_all_menu, menu);
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
