package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragments.wallet_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;


public  class ContactsFragment extends FermatWalletFragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] countries;
    private String[] states;
    private String[] when;
    private String[] amount;
    private String[] cities;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static ContactsFragment newInstance() {
        ContactsFragment f = new ContactsFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{ "Céline Begnis", "Kimberly Brown","Juan Luis R. Pons", "Karina Rodríguez","Guillermo Villanueva","Lucia Alarcon De Zamacona", "Luis Fernando Molina", "Mariana Duyos", "Pedro Perrotta", "Simon Cushing","Stephanie Himonidis","Taylor Backus", "Ginny Kaltabanis","Piper Faust",   "Deniz Caglar","Helen Nisbet","Dea Vanagan",    "Tim Hunter",   "Madeleine Jordan","Kate Bryan","Victoria Gandit","Jennifer Johnson","Robert Wint",  "Kevin Helms","Teddy Truchot" ,"Hélène Derosier","John Smith",     "Caroline Mignaux","Guillaume Thery","Brant Cryder","Thomas Levy","Louis Stenz" };
        countries = new String[]{"United States","United States"  ,"United States",    "Canada"          ,"United States"       ,"Spain"                      ,"Hungary"            , "United States", "Turkey"         ,"United States", "United States"      ,"United States", "Mexico"          ,"United States",  "Australia",   "Brazil",      "United States",  "United States","United Kingdom",  "Germany",   "United States",  "United States",   "United States","Hungary",    "United States", "United States",  "United Kingdom","United States",   "United States"  ,"United States","Canda","United States",};
        states = new String[]{   "FL"           ,"CA",             "NY"               ,"AB",              "FL"               ,   "Andalusia"               ,   "Pest",                "NY",            "Istanbul"       ,"MS",            "NY",                 "UT",            "JAL",             "KY"             ,"WA",          "ES",          "NY",             "KY",           "London",          "Berlin",    "NY",             "NY",              "TX",           "Pest",       "NY",            "NV"             ,"London"       , "NY",              "OH",             "NY"      ,"ON"      ,"NY"     ,"La",       "ON"      ,"NY"};
        cities = new String[]{   "Miami"        ,"San Francisco"  ,"Buffalo"          ,"Calgary"         ,"Orlando"             ,"Cordoba",                    "Budapest",            "New York"      ,"Adair"          ,"Jackson",       "New York"           ,"Provo"         ,"Guadalajara"     ,"Lexington",      "Perth",       "Vitoria",     "New York",       "Louisville",   "Sutton",          "Berlin",    "New York",        "New York"      , "Dallas",       "Budapest",   "New York",      "Las Vegas",      "Croydon",       "New York",        "Pittsburgh"     ,"New York","Sudbury","New York","New Orleans","Sudbury","Kingston"};

        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
        };
        transactions_amounts = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},

        };

        transactions_whens = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},


        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_contacts, container, false);


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
                convertView.setTag(holder);



            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder country;
            ViewHolder state;
            ViewHolder city;

            ImageView profile_picture;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_contacts_list_item, parent, false);



                profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);

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
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 6:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 7:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 8:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 9:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 10:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 11:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 12:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 13:
                        profile_picture.setImageResource(R.drawable.mati_profile);
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
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 18:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 19:
                        profile_picture.setImageResource(R.drawable.kate_profile_picture);
                        break;
                    case 20:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 21:
                        profile_picture.setImageResource(R.drawable.jennifer_profile_picture);
                        break;
                    case 22:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 23:
                        profile_picture.setImageResource(R.drawable.kevin_profile_picture);
                        break;
                    case 24:
                        profile_picture.setImageResource(R.drawable.mati_profile);
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
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 30:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;
                    case 31:
                        profile_picture.setImageResource(R.drawable.mati_profile);
                        break;




                }

                city= new ViewHolder();
                city.text = (TextView) convertView.findViewById(R.id.city);
                city.text.setText(cities[groupPosition].toString());


                country = new ViewHolder();
                country.text = (TextView) convertView.findViewById(R.id.country);

                country.text.setText(countries[groupPosition].toString());


                state = new ViewHolder();
                state.text = (TextView) convertView.findViewById(R.id.state);

                state.text.setText(states[groupPosition].toString());


                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.contact_name);

                convertView.setTag(holder);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }




            holder.text.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class ViewHolder {
            TextView text;
        }
    }
}