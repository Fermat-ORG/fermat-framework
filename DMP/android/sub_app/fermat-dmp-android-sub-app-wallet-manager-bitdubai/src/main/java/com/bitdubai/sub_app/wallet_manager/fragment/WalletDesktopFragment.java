package com.bitdubai.sub_app.wallet_manager.fragment;

/**
 * Created by Natalia on 22/04/2015.
 */

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalia on 31/12/2014.
 */
public class WalletDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private static int tabId;

    private int position;
    Typeface tf;


    //private SearchView mSearchView;

    public static WalletDesktopFragment newInstance(int position) {
        WalletDesktopFragment f = new WalletDesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        setHasOptionsMenu(true);
        String[] installed =
                {"false",
                        "false",
                        "false",
                        "true",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false"
                };
        String[] company_names =
                {"Girls' wallet",
                        "Boys' wallet",
                        "Ladies' wallet",
                        "Bitcoin Reference Wallet",
                        "Boca Juniors' wallet",
                        "Carrefour's wallet",
                        "Gucci's wallet",
                        "Bank Itau's wallet",
                        "Mc donals' wallet",
                        "Vans' wallet",
                        "Samsung's wallet",
                        "Popular Bank's wallet",
                        "Sony's wallet",
                        "BMW's wallet",
                        "HP's wallet",
                        "Billabong's wallet",
                        "Starbucks' wallet"

                };


        String[] company_picture =
                {"wallet_store_cover_photo_girl",
                        "wallet_store_cover_photo_boy",
                        "wallet_store_cover_photo_lady",
                        "wallet_store_cover_fermat",
                        "wallet_store_cover_photo_boca_juniors",
                        "wallet_store_cover_photo_carrefour",
                        "wallet_store_cover_photo_gucci",
                        "wallet_store_cover_photo_bank_itau",
                        "wallet_store_cover_photo_mcdonals",
                        "wallet_store_cover_photo_vans",
                        "wallet_store_cover_photo_samsung",
                        "wallet_store_cover_photo_bank_popular",
                        "wallet_store_cover_photo_sony",
                        "wallet_store_cover_photo_bmw",
                        "wallet_store_cover_photo_hp",
                        "wallet_store_cover_photo_billabong",
                        "wallet_store_cover_photo_starbucks"

                };

        mlist = new ArrayList<App>();


        for (int i = 0; i < installed.length; i++) {
            if (installed[i] == "true") {
                App item = new App();

                item.picture = company_picture[i];
                item.company = company_names[i];
                item.rate = (float) Math.random() * 5;
                item.value = (int) Math.floor((Math.random() * (500 - 80 + 1))) + 80;
                item.favorite = (float) Math.random() * 5;
                item.timetoarraive = (float) Math.random() * 5;
                item.sale = (float) Math.random() * 5;
                item.installed = true;
                mlist.add(item);
            }
        }

        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);


     /*    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);

                return ;
            }
        });*/


        return gridView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //if(id == R.id.action_search){
        //    Toast.makeText(getActivity(), "holaa", Toast.LENGTH_LONG);
        //}

        return super.onOptionsItemSelected(item);
    }


    public class App implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;

        public String title;

        public String description;

        public String picture;

        public String company;

        public String Open_hours;

        public String Address;

        public String Phone;

        public float rate;

        public int value;

        public float favorite;

        public float sale;

        public float timetoarraive;

        public boolean installed;

    }



    public class AppListAdapter extends ArrayAdapter<App> {


        public AppListAdapter(Context context, int textViewResourceId, List<App> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            App item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);
                holder = new ViewHolder();



                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.company);
            holder.companyTextView.setTypeface(tf,Typeface.BOLD);


            LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.wallet_3);
            switch (item.picture)
            {

                case "wallet_store_cover_fermat":
                    holder.imageView.setImageResource(R.drawable.fermat);
                    holder.imageView.setTag("WalletBitcoinActivity|4");
                    linearLayout.setTag("WalletBitcoinActivity|4");

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).setScreen("WalletBitcoinActivity");
                            ((FermatScreenSwapper) getActivity()).changeScreen();

                        }
                    });

                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).setScreen("WalletBitcoinActivity");
                            ((FermatScreenSwapper) getActivity()).changeScreen();

                        }
                    });

                    break;

                //Prototype
                /*
                 case "wallet_store_cover_photo_girl":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_pink);

                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    linearLayout.setTag("CPWWRWAKAV1M|1");

                    break;
                case "wallet_store_cover_photo_boy":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_yellow);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    linearLayout.setTag("CPWWRWAKAV1M|2");
                    break;
                case "wallet_store_cover_photo_lady":
                    holder.imageView.setImageResource(R.drawable.wallet_1);
                    holder.imageView.setTag("AdultsActivity|3");
                    linearLayout.setTag("AdultsActivity|3");
                    break;
                case "wallet_store_cover_photo_young":
                    holder.imageView.setImageResource(R.drawable.wallet_2);
                    holder.imageView.setTag("WalletBitcoinActivity|4");
                    break;
                    case "wallet_store_cover_photo_boca_juniors":
                    holder.imageView.setImageResource(R.drawable.icono_club_1);
                    holder.imageView.setTag("AdultsActivity|10");
                    break;
                case "wallet_store_cover_photo_carrefour":
                    holder.imageView.setImageResource(R.drawable.icono_retailer_1);
                    holder.imageView.setTag("AdultsActivity|7");
                    break;
                case "wallet_store_cover_photo_gucci":
                    holder.imageView.setImageResource(R.drawable.wallet_4);
                    holder.imageView.setTag("AdultsActivity|6");
                    break;
                case "wallet_store_cover_photo_bank_itau":
                    holder.imageView.setImageResource(R.drawable.icono_banco_1);
                    holder.imageView.setTag("AdultsActivity|8");
                    break;
                case "wallet_store_cover_photo_mcdonals":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_mcdonals);
                    holder.imageView.setTag("AdultsActivity|11");
                    break;
                case "wallet_store_cover_photo_vans":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_vans);
                    break;
                case "wallet_store_cover_photo_samsung":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_samsung);
                    holder.imageView.setTag("AdultsActivity|12");
                    break;
                case "wallet_store_cover_photo_bank_popular":
                    holder.imageView.setImageResource(R.drawable.icono_banco_2);
                    holder.imageView.setTag("AdultsActivity|9");
                    break;
                case "wallet_store_cover_photo_sony":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_sony);
                    holder.imageView.setTag("AdultsActivity|13");
                    break;
                case "wallet_store_cover_photo_hp":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_hp);
                    holder.imageView.setTag("AdultsActivity|14");
                    break;
                case "wallet_store_cover_photo_bmw":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bmw);
                    holder.imageView.setTag("AdultsActivity|15");
                    break;
                case "wallet_store_cover_photo_billabong":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_billabong);
                    holder.imageView.setTag("AdultsActivity|16");
                    break;
                case "wallet_store_cover_photo_starbucks":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_starbucks);
                    holder.imageView.setTag("AdultsActivity|17");
                    break;
                */


            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {



            public ImageView imageView;
            public TextView companyTextView;


        }

    }




}

