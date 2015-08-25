package com.bitdubai.sub_app.wallet_store.fragment;

/**
 * MATIAS 13/5/2015
 */
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.SearchView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.sub_app.wallet_store.Model.App;
import com.bitdubai.sub_app.wallet_store.Model.ItemsBD;
import com.bitdubai.sub_app.wallet_store.Model.ViewHolder;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.List;



public class AllFragment extends FermatFragment implements SearchView.OnQueryTextListener {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private static int tabId;

    private int position;

    private SearchView mSearchView;

    AppListAdapter _adpatrer;
    GridView gridView;

    public static AllFragment newInstance(int position,SubAppsSession subAppsSession) {
        AllFragment f = new AllFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        tabId = position;
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        //inflater.inflate(R.menu.wallet_store_activity_wallet_menu, menu);
        inflater.inflate(R.menu.wallet_store_activity_wallet_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(searchItem,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        //mSearchView.setIconifiedByDefault(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if(id == R.id.action_search) {
            //Toast.makeText(getActivity(),"holaa431",Toast.LENGTH_LONG);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Mati
        //setHasOptionsMenu(true);
        //super.onCreateView();

        //clase que luego se cambiara por la llamada a la base de datos
        ItemsBD itemsBD = new ItemsBD();


        if (mlist == null)
        {
            //tab = 0 all, tab = 1 discount, tab = 2 regular, tab 3 =  cercanas
            mlist = new ArrayList<App>();

            if(tabId == 0) {
                //acá se carga la mList con todos los datos
                mlist=itemsBD.cargarDatosPorTipoApp(ItemsBD.ALL_WALLETS);
            }

            if(tabId == 1) {
                //aca van las de discount
                mlist=itemsBD.cargarDatosPorTipoApp(ItemsBD.DISCOUNT_WALLETS);
            }

            if(tabId == 2) {
                //aca van las regulars
                mlist=itemsBD.cargarDatosPorTipoApp(ItemsBD.REGULAR_WALLETS);
            }

            if(tabId == 3) {
                //se busca por cercania, en este caso vamos a suponer que estamos en la region 1
                int region =1;
                mlist=itemsBD.nearbyWalletsItems(region);
            }
        }



        gridView = new GridView(getActivity());
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }

        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        _adpatrer = new AppListAdapter(getActivity(), R.layout.wallet_store_activity_store_front_grid_item, mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);




/*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);

                return ;
            }
        });

*/
        return gridView;
    }



    //ACÁ HAY QUE IMPLEMENTAR EL SEARCH
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getActivity(),"Probando busqueda completa",Toast.LENGTH_SHORT).show();

        /*ItemsBD itemsBD = new ItemsBD();
        mlist=itemsBD.buscarPorNombreCompania(mlist,query);
        Toast.makeText(getActivity(),mlist.get(0).toString(),Toast.LENGTH_SHORT).show();
        _adpatrer.notifyDataSetChanged();

        */
        //_adpatrer.clear();
        //_adpatrer.addAll(mlist);

        //_adpatrer = new AppListAdapter(getActivity(), R.layout.wallet_store_activity_store_front_grid_item, mlist);
        //_adpatrer.notifyDataSetChanged();

        //_adpatrer.notifyAll();
        //gridView.setAdapter(_adpatrer);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getActivity(),"Probando cambio busqueda",Toast.LENGTH_SHORT).show();
        return false;
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





            holder.titleTextView.setText(item.getTitle());
            holder.companyTextView.setText(item.getCompany());
            holder.companyDescription.setText(item.getAddress());
            //holder.ratingBar.setRating(item.rate);
            holder.valueTextView.setText(  item.getValue() + " reviews");

            holder.openHours.setText(  item.getOpen_hours());

            if (item.isInstalled() )
            {
                holder.timeToArrive.setText( "Installed");
                holder.downloadIcon.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.timeToArrive.setText( "Download now");
                holder.downloadIcon.setVisibility(View.VISIBLE);
            }

         /*   holder.openHours.setTypeface(MyApplication.getDefaultTypeface());
            holder.timeToArrive.setTypeface(MyApplication.getDefaultTypeface());
            holder.titleTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyDescription.setTypeface(MyApplication.getDefaultTypeface());
            holder.valueTextView.setTypeface(MyApplication.getDefaultTypeface());*/

            if (item.getRate() >= 0)
            {
                holder.star1.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.getRate() >= 1)
            {
                holder.star2.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.getRate() >= 2)
            {
                holder.star3.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.getRate() >= 3)
            {
                holder.star4.setImageResource(R.drawable.grid_background_star_full);
            }
            if (item.getRate() >= 4)
            {
                holder.star5.setImageResource(R.drawable.grid_background_star_full);
            }

            if (item.getFavorite() > 2)
            {
                holder.favorite.setImageResource(R.drawable.grid_background_favorite);
            }
            else
            {
                holder.favorite.setImageResource(R.drawable.grid_background_not_favorite);
            }


            if (item.getSale() > 3)
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

            switch (item.getPicture())
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


    }

}

