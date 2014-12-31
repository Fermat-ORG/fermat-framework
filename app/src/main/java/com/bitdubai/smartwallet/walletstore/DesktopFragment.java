package com.bitdubai.smartwallet.walletstore;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.walletframework.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalia on 31/12/2014.
 */
public class DesktopFragment  extends android.app.Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private static int tabId;

    private int position;

    public static DesktopFragment newInstance(int position) {
        DesktopFragment f = new DesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        String[] installed =
                {"false",
                        "false",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "false",
                        "false",
                        "false",
                        "true",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false"
                };
        String[] company_names =
                {"Girl's wallet",
                        "Boy's wallet",
                        "Ladies",
                        "Young",
                        "Boca Junior's",
                        "Carrefour's",
                        "Gucci's",
                        "Bank Itau's",
                        "Mc donal's",
                        "Van's",
                        "Samsung's",
                        "Popular Bank's",
                        "Sony's",
                        "BMW's",
                        "HP's",
                        "Billabong's",
                        "Starbuck's"

                };


        String[] company_picture =
                {"wallet_store_cover_photo_girl",
                        "wallet_store_cover_photo_boy",
                        "wallet_store_cover_photo_lady",
                        "wallet_store_cover_photo_young",
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
        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.wallet_manger_desktop_wallet_front_grid_item, mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);

                return ;
            }
        });


        return gridView;
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
                convertView = inflater.inflate(R.layout.wallet_manger_desktop_wallet_front_grid_item, parent, false);
                holder = new ViewHolder();



                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.company);
             holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());



            switch (item.picture)
            {
                case "wallet_store_cover_photo_girl":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_pink);
                    holder.imageView.setTag("1");
                    break;
                case "wallet_store_cover_photo_boy":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_yellow);
                    holder.imageView.setTag("2");
                    break;
                case "wallet_store_cover_photo_lady":
                    holder.imageView.setImageResource(R.drawable.wallet_1);
                    holder.imageView.setTag("3");
                    break;
                case "wallet_store_cover_photo_young":
                    holder.imageView.setImageResource(R.drawable.wallet_2);
                    holder.imageView.setTag("4");
                    break;
                case "wallet_store_cover_photo_boca_juniors":
                    holder.imageView.setImageResource(R.drawable.icono_club_1);
                    holder.imageView.setTag("10");
                    break;
                case "wallet_store_cover_photo_carrefour":
                    holder.imageView.setImageResource(R.drawable.icono_retailer_1);
                    holder.imageView.setTag("7");
                    break;
                case "wallet_store_cover_photo_gucci":
                    holder.imageView.setImageResource(R.drawable.wallet_4);
                    holder.imageView.setTag("6");
                    break;
                case "wallet_store_cover_photo_bank_itau":
                    holder.imageView.setImageResource(R.drawable.icono_banco_1);
                    holder.imageView.setTag("8");
                    break;
                case "wallet_store_cover_photo_mcdonals":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_mcdonals);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_vans":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_vans);
                    break;
                case "wallet_store_cover_photo_samsung":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_samsung);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_bank_popular":
                    holder.imageView.setImageResource(R.drawable.icono_banco_2);
                    holder.imageView.setTag("9");
                    break;
                case "wallet_store_cover_photo_sony":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_sony);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_hp":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_hp);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_bmw":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bmw);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_billabong":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_billabong);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_starbucks":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_starbucks);
                    holder.imageView.setTag("11");
                    break;
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

