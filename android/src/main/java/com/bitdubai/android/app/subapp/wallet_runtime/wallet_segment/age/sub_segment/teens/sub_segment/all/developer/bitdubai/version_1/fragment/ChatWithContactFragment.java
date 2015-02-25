package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 09/01/2015.
 */
public class ChatWithContactFragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] countries;
    private String[] states;
    private String[] cities;

    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static ChatWithContactFragment newInstance(int position) {
        ChatWithContactFragment f = new ChatWithContactFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.countries = new String[]{};
        this.states = new String[]{};
        this.cities = new String[]{};

        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        this.transactions = new String[][]{

                {},
        };
        this.transactions_amounts = new String[][]{

                {},
        };

        this.transactions_whens = new String[][]{

                {},
        };

        this.contacts = new String[]{"Céline Begnis", "Kimberly Brown", "Juan Luis R. Pons", "Karina Rodríguez"
                ,"Guillermo Villanueva","Lucia Alarcon De Zamacona", "Luis Fernando Molina", "Mariana Duyos", "Pedro Perrotta"
                , "Simon Cushing","Stephanie Himonidis","Taylor Backus", "Ginny Kaltabanis","Piper Faust","Deniz Caglar"
                ,"Helen Nisbet","Dea Vanagan","Tim Hunter","Madeleine Jordan","Kate Bryan","Victoria Gandit","Jennifer Johnson"
                ,"Robert Wint","Kevin Helms","Teddy Truchot","Hélène Derosier","John Smith","Caroline Mignaux","Guillaume Thery"
                ,"Brant Cryder","Thomas Levy","Louis Stenz" };
        int groupPosition = 0;
        String contact_name = MyApplication.getContact();

        for (int i = 0; i < this.contacts.length; i++) {

            if (this.contacts[i].equals(contact_name)) {
                groupPosition = i;
                break;
            }
        }

        final ViewGroup actionBarLayout = (ViewGroup) super.getActivity().getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_chat_with_contact_action_bar,
                null);
        MyApplication.changeColor(Color.parseColor("#F0E173"), super.getActivity().getResources());

        super.getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        super.getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        super.getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        super.getActivity().getActionBar().setCustomView(actionBarLayout);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_contact_chat, container, false);


        TextView userInput;
        TextView button;

        userInput = ((TextView)rootView.findViewById(R.id.edit_text));
        userInput.setTypeface(MyApplication.getDefaultTypeface());

        button = ((TextView)rootView.findViewById(R.id.send_message_button));
        button.setTypeface(MyApplication.getDefaultTypeface());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] contacts;
        private String[][] transactions;

        public ExpandableListAdapter(String[] contacts, String[][] transactions) {
            this.contacts = contacts;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return contacts.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return contacts[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return transactions[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolder holder;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                // convertView = inf.inflate(R.layout.wallets_teens_fragment_send_and_receive_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.city);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);



            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                View view;
                view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);

                TextView hours;
                TextView dates;
                TextView tv;

                switch (groupPosition) {

                    case 0:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);
                        dates = ((TextView)view.findViewById(R.id.date));
                        dates.setText("4 jan 2015");
                        dates.setTypeface(MyApplication.getDefaultTypeface());
                        break;

                    case 1:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hi, did you receive the payment ok?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:43");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 2:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hello, yes it's confirmed.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:50");

                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 3:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, but I wanted to tell you I've got a problem.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());



                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:52");

                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 4:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Since this morning that i don't have water or electricity.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:53");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 5:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Oh, I see, I will see what's happening.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:55");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 6:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, thanks you.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:56");

                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 7:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Tessa, there was a problem with one of the building's pipes and it's all flooded, so for security the suspended the light.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:43");

                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 8:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ah, maybe that's why the electricity company truck is parked outside.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());

                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20: 45");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 9:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Yes, that's why they are there");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:45");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 10:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("As you don't have water neither light to use the heating system.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:46");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 11:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("I suggest you staying the night in the hotel down the street, and don't worry i will pay for it.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:46");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 12:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, thanks you for your kindness.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:47");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                }
                return view;
            }
            return convertView;
        }
        private class ViewHolder {
            TextView text;
        }
    }
}