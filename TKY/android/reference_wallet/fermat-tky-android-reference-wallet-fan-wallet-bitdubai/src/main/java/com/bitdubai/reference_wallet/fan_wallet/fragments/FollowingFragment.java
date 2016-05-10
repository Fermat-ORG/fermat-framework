package com.bitdubai.reference_wallet.fan_wallet.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.common.adapters.FollowingAdapter;
import com.bitdubai.reference_wallet.fan_wallet.common.models.FollowingItems;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;
import com.bitdubai.reference_wallet.fan_wallet.util.ManageRecyclerviewClick;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FollowingFragment extends AbstractFermatFragment implements SearchView.OnQueryTextListener {

    //FermatManager
    private FanWalletSession fanwalletSession;
    private FanWalletModule fanWalletModuleManager;
    private FanWalletPreferenceSettings fanWalletSettings;
    private ErrorManager errorManager;



    RecyclerView recyclerView;
    View view;
    private Paint p = new Paint();
    private FollowingAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<FollowingItems> items=new ArrayList<>();
    List<Fan> fanList=new ArrayList<>();
    Bot artistBot;
    final Handler myHandler = new Handler();


    public static FollowingFragment newInstance(){
        return new FollowingFragment();
    }
    public FollowingFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fanwalletSession = ((FanWalletSession) appSession);
            fanWalletModuleManager =  fanwalletSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START FOLLOWING");


            try {
                fanWalletSettings =  fanWalletModuleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletPreferenceSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    fanWalletModuleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tky_fan_wallet_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true; // Return true to expand action view
                    }
                });
    }

    void loaditems(){

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // Above Api Level 13
        {
            new BotRequester(view).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            syncThread.execute();
        }
        else // Below Api Level 13
        {
            new BotRequester(view).execute();
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.tky_fan_wallet_following_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        lManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(lManager);
        loaditems();
        adapter = new FollowingAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundResource(R.drawable.fanwallet_background_viewpager);
        swipe_effect();

        recyclerView.addOnItemTouchListener(
                new ManageRecyclerviewClick(view.getContext(), new ManageRecyclerviewClick.OnItemClickListener() {
                    @Override

                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(items.get(position).getURL()));
                        startActivity(intent);

                    }
                })
        );




        return view;
    }

    List<FollowingItems> reload(){
        List<FollowingItems> data=new ArrayList<>();
        data.addAll(items);
        return data;
    }

    void refreshAdapter(boolean noFollowing){
        if(noFollowing){
            Toast.makeText(view.getContext(),"Your are not following artist",Toast.LENGTH_SHORT).show();
        }else {
            adapter.setFilter(items);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(!query.isEmpty()) {
            final List<FollowingItems> filteredModelList = filter(items, query);
            adapter.setFilter(filteredModelList);
        }else{
            adapter.setFilter(reload());
        }
        return true;
    }


    void swipe_effect(){

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                return true;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                List<FollowingItems> original=new ArrayList<>();
                original.addAll(items);
                ask(position, original);
                adapter.setFilter(original);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.START;       // delete one in case you want just one direction
                return makeMovementFlags(dragFlags, swipeFlags);

                //   return  items.get(position).getStatus()=="Downloading"? 0:makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.tky_trash);
                        //Start draw left-top to right-bottom     RectF (left,top,right,bottom)
                        RectF icon_dest = new RectF((float) itemView.getLeft() + (width/4) ,(float) itemView.getTop() + 0.75f*width,(float) itemView.getLeft()+ 2f*width,(float)itemView.getBottom() -0.75f*width);
                        c.drawBitmap(icon,null,icon_dest,p);


                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.tky_chat);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 1.75f*width ,(float) itemView.getTop() + 0.75f*width,(float) itemView.getRight() - width/4,(float)itemView.getBottom() - 0.75f*width);
                        c.drawBitmap(icon,null,icon_dest,p);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

   void ask(int position, final List<FollowingItems> original){
       AlertDialog.Builder dialog1 = new AlertDialog.Builder(view.getContext());
       dialog1.setTitle("FanWallet");
       dialog1.setMessage("Do you really want to to chat with '" + items.get(position).getUsername() + "'?");
       dialog1.setCancelable(false);
       dialog1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialogo1, int id) {
               //     items.remove(position);
               adapter.setFilter(original);
               toChat();
           }
       });
       dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialogo1, int id) {
               adapter.setFilter(original);
           }
       });
       dialog1.show();
       
   }

    // TODO: 23/03/16  
    void toChat(){
        //TODO: to implement
    }
    // TODO: 01/04/16
    void getMyConnection(){
        //TODO: to implement
    }


    private List<FollowingItems> filter(List<FollowingItems> models, String query) {
        query = query.toLowerCase();

        final List<FollowingItems> filteredModelList = new ArrayList<>();
        for (FollowingItems model : models) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private class BotRequester extends AsyncTask <Void, Void, Boolean> {

        View view;
        boolean noFollowing =false;
        public BotRequester(View view){
            this.view = view;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... notUsingObject) {
            try {
                try {
                    fanList= fanWalletModuleManager.listIdentitiesFromCurrentDeviceUser();
                    if(fanList.size()==0){
                        noFollowing =true;
                    }
                    for(Fan artistUsername:fanList){
                        List<String> connectedArtistTKYUsername = artistUsername.getConnectedArtists();
                        for(String botUsername : connectedArtistTKYUsername){
                            artistBot= fanWalletModuleManager.getBotBySwapbotUsername(botUsername);
                            System.out.println(
                                    "tky_artistBot:" + artistBot);
                            items.add(new FollowingItems(convertUrlToBMP(artistBot.getLogoImageDetails().originalUrl()),
                                   //extractLinks(artistBot.getDescription())[0], artistBot.getUserName()));
                                    artistBot.getBotUrl(),
                                    artistBot.getName(),
                                    artistBot.getDescriptionHtml()));

                        }
                    }

                    } catch (CantGetBotException e) {
                    errorManager.reportUnexpectedUIException(
                            UISource.VIEW,
                            UnexpectedUIExceptionSeverity.NOT_IMPORTANT,
                            e);
                } catch (CantConnectWithTokenlyException e) {
                    //TODO: Miguel, implement here how to notify to the user that tokenly website is not available
                }

            } catch (CantListFanIdentitiesException e) {
                    System.out.println("tky_loaditem_fanidentity_exception:"+e);
                    e.printStackTrace();
                }

            return null;
        }




        public  String[] extractLinks(String text) {
            List<String> links = new ArrayList<String>();
            Matcher m = Patterns.WEB_URL.matcher(text);
            while (m.find()) {
                String url = m.group();
             //   Log.d("TKY_", "URL extracted: " + url);
                links.add(url);
            }

            return links.toArray(new String[links.size()]);
        }

        Bitmap convertUrlToBMP(String imageUrl){
            URL url;
            Bitmap bmp=null;
            try {
                url = new URL(imageUrl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Boolean ready) {

                refreshAdapter(noFollowing);

        }
    }

}
