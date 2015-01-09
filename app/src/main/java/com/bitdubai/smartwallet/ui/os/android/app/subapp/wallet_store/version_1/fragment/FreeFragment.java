package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_store.version_1.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class FreeFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;

    private int position;

    public static FreeFragment newInstance(int position) {
        FreeFragment f = new FreeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] company_names =
                {"Girl's wallet",
                        "Boy's wallet",
                        "Ladies",
                        "Young",
                        "Boca Junior's wallet",
                        "Carrefour's wallet",
                        "Gucci's wallet",
                        "Bank Itau's wallet",
                        "Mc donal's wallet",
                        "Van's wallet",
                        "Samsung's wallet",
                        "Popular Bank's wallet",
                        "Sony's wallet",
                        "BMW's wallet",
                        "HP's wallet",
                        "Billabong's wallet",
                        "Starbuck's wallet"

                };
        String[] company_addresses =
                {"by bitDubai",
                        "by bitDubai",
                        "by bitDubai",
                        "by bitDubai",
                        "by Boca Junios",
                        "by carrefour",
                        "by Gucci",
                        "by Bank Itau",
                        "by McDonals",
                        "by Vans",
                        "by Samsung",
                        "by bitDubai",
                        "by Sony",
                        "by BMW",
                        "by HP",
                        "by Billabong",
                        "by starbucks"

                };
        String[] company_horario = {"Free",
                "Free",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "$1.00 / month",
                "Free",
                "Free",
                "$1.00 / month",
                "Free",
                "$1.00 / month",
                "$1.00 / month"
        };
        String[] company_telefono =
                {"No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "0.15% transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "0.15% transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees",
                        "No transaction fees",
                        "No transaction fees",
                        "0.10% transaction fees"
                };

        String[] company_descriptions = {"Classic, redefined. See what's new at our Westchester location!.",
                "Smoking is allowed inside this clubby shop known for its hand-rolled cigars made ...",
                "Basic pizzeria serving a variety of NYC-style slices in a small, counter-service space.",
                "Pasta dishes, sandwiches & salads for take-out or eating in at this pint-sized Italian deli.",
                "Sophisticated Greek dishes, happy hour small plates & organic boutique wines in a ...",
                "Hair Salon, Day Spa",
                "Standard 24/7 diner with retro looks, basic fare & close proximity to Madison Square...",
                "Huge historic Jazz Age (1929) hotel offers over 900 rooms and suites across from Penn Station.",
                "Long-running chain serving signature donuts, breakfast sandwiches & a variety of coffee ...",
                "Home-away-from-home Australian pub offering grilled kangaroo, Aussie beers & rugby ...",
                "Counter-serve bakery/cafe chain serving sandwiches, salads & more, known for its ...",
                "Counter-serve setup offering deli sandwiches & wraps, hot entrees, salads & breakfast items.",
                "Counter-serve chain for ready-made sandwiches plus breakfast, coffee, soups & ...",
                "Casual counter-serve chain for build-your-own sandwiches & salads, with health-conscious ...",
                "Buzzy after-work-geared bar/eatery serving drinks & American fare in a cozy, ... ",
                "A bar pours mojitos up front & a brick-lined dining room in the back serves traditional ...",
                "Tiny, art-adorned coffeehouse pairing its brews with baked goods, sandwiches & a ...",

        };
        String[] company_sites = {"bitDubai.com",
                "bitDubai.com",
                "bitDubai.com",
                "bitDubai.com",
                "bitBubai.com",
                "carrefour.com",
                "gucci.com",
                "Bankitau.com",
                "mcdonals.com",
                "vans.com",
                "samsung.com",
                "bitDubai.com",
                "sony.com",
                "bmw.com",
                "HP",
                "billabong.com ",
                "starbucks.com"


        };

        String[] free_paid =
                {"free",
                        "free",
                        "free",
                        "paid",
                        "free",
                        "paid",
                        "free",
                        "paid",
                        "free",
                        "paid",
                        "paid",
                        "free",
                        "free",
                        "paid",
                        "free",
                        "paid",
                        "paid"
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

        String[] installed =
                {"true",
                        "true",
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


        if (mlist == null)
        {

            mlist = new ArrayList<App>();

            for (int i = 0; i < free_paid.length; i++) {

                if (free_paid[i] == "free") {
                    App item = new App();
                    item.title = company_sites[i];
                    item.description = company_descriptions[i];
                    item.picture = company_picture[i];
                    item.company = company_names[i];
                    item.Open_hours = company_horario[i];
                    item.Phone = company_telefono[i];
                    item.Address = company_addresses[i];
                    item.rate = (float) Math.random() * 5;
                    item.value = (int) Math.floor((Math.random() * (500 - 80 + 1))) + 80;
                    item.favorite = (float) Math.random() * 5;
                    item.timetoarraive = (float) Math.random() * 5;
                    item.sale = (float) Math.random() * 5;
                    item.installed = (installed[i]=="true") ? true : false;
                    mlist.add(item);
                }
            }
        }


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }

        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallet_store_activity_store_front_grid_item, mlist));



/*

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), StoreActivity.class);
                startActivity(intent);

                return ;
            }
        });
*/

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
                convertView = inflater.inflate(R.layout.wallet_store_activity_store_front_grid_item, parent, false);
                holder = new ViewHolder();

                holder.star1= (ImageView) convertView.findViewById(R.id.star_1);
                holder.star2= (ImageView) convertView.findViewById(R.id.star_2);
                holder.star3= (ImageView) convertView.findViewById(R.id.star_3);
                holder.star4= (ImageView) convertView.findViewById(R.id.star_4);
                holder.star5= (ImageView) convertView.findViewById(R.id.star_5);

                holder.star1.setAdjustViewBounds(true);
                holder.star2.setAdjustViewBounds(true);
                holder.star3.setAdjustViewBounds(true);
                holder.star4.setAdjustViewBounds(true);
                holder.star5.setAdjustViewBounds(true);

                holder.sale = (ImageView) convertView.findViewById(R.id.sale);
                holder.favorite = (ImageView) convertView.findViewById(R.id.favorite);

                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
                holder.companyDescription = (TextView) convertView.findViewById(R.id.company_description);
                //holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
                holder.valueTextView = (TextView) convertView.findViewById(R.id.value_text_view);

                holder.openHours = (TextView) convertView.findViewById(R.id.open_hours);
                holder.timeToArrive = (TextView) convertView.findViewById(R.id.time_to_arrive);

                holder.downloadIcon = (ImageView) convertView.findViewById(R.id.download);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }





            holder.titleTextView.setText(item.title);
            holder.companyTextView.setText(item.company);
            holder.companyDescription.setText(item.Address);
            //holder.ratingBar.setRating(item.rate);
            holder.valueTextView.setText(  item.value + " reviews");

            holder.openHours.setText(  item.Open_hours);

            if (item.installed )
            {
                holder.timeToArrive.setText( "Installed");
                holder.downloadIcon.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.timeToArrive.setText( "Download now");
                holder.downloadIcon.setVisibility(View.VISIBLE);
            }




            holder.openHours.setTypeface(MyApplication.getDefaultTypeface());
            holder.timeToArrive.setTypeface(MyApplication.getDefaultTypeface());
            holder.titleTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyDescription.setTypeface(MyApplication.getDefaultTypeface());
            holder.valueTextView.setTypeface(MyApplication.getDefaultTypeface());

            if (item.rate >= 0)
            {
                holder.star1.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.rate >= 1)
            {
                holder.star2.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.rate >= 2)
            {
                holder.star3.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.rate >= 3)
            {
                holder.star4.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.rate >= 4)
            {
                holder.star5.setImageResource(R.drawable.grid_background_star_full);
            }

            if (item.favorite > 2)
            {
                holder.favorite.setImageResource(R.drawable.grid_background_favorite);
            }
            else
            {
                holder.favorite.setImageResource(R.drawable.grid_background_not_favorite);
            }


            if (item.sale > 3)
            {
                holder.sale.setImageResource(R.drawable.transparent);
            }
            else
            {
                holder.sale.setImageResource(R.drawable.grid_background_sale_flipped);
            }

            // favorite and sale icons set to invisible

            holder.favorite.setVisibility(View.INVISIBLE);
            holder.sale.setVisibility(View.INVISIBLE);

            switch (item.picture)
            {
                case "wallet_store_cover_photo_girl":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_girl);
                    break;
                case "wallet_store_cover_photo_boy":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_boy);
                    break;
                case "wallet_store_cover_photo_lady":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_lady);
                    break;
                case "wallet_store_cover_photo_young":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_young);
                    break;
                case "wallet_store_cover_photo_boca_juniors":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_boca_juniors);
                    break;
                case "wallet_store_cover_photo_carrefour":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_carrefour);
                    break;
                case "wallet_store_cover_photo_gucci":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_gucci);
                    break;
                case "wallet_store_cover_photo_bank_itau":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bank_itau);
                    break;
                case "wallet_store_cover_photo_mcdonals":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_mcdonals);
                    break;
                case "wallet_store_cover_photo_vans":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_vans);
                    break;
                case "wallet_store_cover_photo_samsung":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_samsung);
                    break;
                case "wallet_store_cover_photo_bank_popular":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bank_popular);
                    break;
                case "wallet_store_cover_photo_sony":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_sony);
                    break;
                case "wallet_store_cover_photo_hp":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_hp);
                    break;
                case "wallet_store_cover_photo_bmw":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bmw);
                    break;
                case "wallet_store_cover_photo_billabong":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_billabong);
                    break;
                case "wallet_store_cover_photo_starbucks":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_starbucks);
                    break;



            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {


            public ImageView star1;
            public ImageView star2;
            public ImageView star3;
            public ImageView star4;
            public ImageView star5;

            public ImageView sale;
            public ImageView favorite;

            public TextView openHours;
            public TextView timeToArrive;
            public TextView Phone;
            public TextView Address;

            public ImageView imageView;

            public TextView titleTextView;

            public TextView companyTextView;

            public TextView companyDescription;

            public RatingBar ratingBar;

            public TextView valueTextView;

            public ImageView downloadIcon;
        }


    }

}

