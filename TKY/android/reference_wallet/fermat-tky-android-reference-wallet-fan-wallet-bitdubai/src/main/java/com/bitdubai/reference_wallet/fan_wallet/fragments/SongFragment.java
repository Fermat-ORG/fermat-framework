package com.bitdubai.reference_wallet.fan_wallet.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.BroadcasterNotificationType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.common.adapters.SongAdapter;
import com.bitdubai.reference_wallet.fan_wallet.common.models.SongItems;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;
import com.bitdubai.reference_wallet.fan_wallet.util.ManageRecyclerviewClick;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class SongFragment extends AbstractFermatFragment {
    //FermatManager
    private FanWalletSession fanwalletSession;
    private FanWalletPreferenceSettings fanWalletSettings;
    private ErrorManager errorManager;
    private FanWalletModule fanWalletModule;


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeContainer;
    String TAG="SONGFRAGMENT";
    View view;
    private Paint p = new Paint();
    DownloadThreadClass downloadthread;
    SyncThreadClass syncthread;
    final Handler myHandler = new Handler();
    final Handler myCancelHandler = new Handler();
   // MonitorThreadClass monitorThreadClass;
    private SongAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    String code;
    FermatBundle bundle;


    List<SongItems> items=new ArrayList();
    public static SongFragment newInstance(){

        return new SongFragment();
    }

    public SongFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            fanwalletSession = ((FanWalletSession) appSession);
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START SONG");

            try {
                fanWalletSettings =  fanwalletSession.getModuleManager().getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletPreferenceSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    fanwalletSession.getModuleManager().getSettingsManager().persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

    }

    void initvalues(){
        fanWalletModule=fanwalletSession.getModuleManager();
        compareViewAndDatabase();
        syncTokenlyAndUpdateThreads(true);

    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        if (swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);
        }
        Toast.makeText(getActivity(),"Connection Problem With External Platform",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateViewUIThred(FermatBundle bundle) {
            this.bundle = bundle;
        if (swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);
        }
        myHandler.post(myRunnablebundle);
    }

    final Runnable myRunnablebundle = new Runnable() {

        public void run() {
            updateViewForBroadcaster(bundle);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.tky_fan_wallet_song_fragment, container, false);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        lManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(lManager);
        adapter = new SongAdapter(items);
        recyclerView.setAdapter(adapter);
        initvalues();

        swipe_effect();


        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);

        recyclerView.addOnItemTouchListener(
                new ManageRecyclerviewClick(view.getContext(), new ManageRecyclerviewClick.OnItemClickListener() {
                    @Override

                    public void onItemClick(View view, int position) {

                        System.out.println("click position:" + position);
                        if (items.get(position).getStatus().equals(SongStatus.AVAILABLE.getFriendlyName()) ||items.get(position).getStatus().equals(SongStatus.DELETED.getFriendlyName())) {
                            askquestion(position, null, 1);
                        } else if (items.get(position).getStatus().equals(SongStatus.DOWNLOADED.getFriendlyName())) {
                            playsong();
                        } else if (items.get(position).getStatus().equals(SongStatus.DOWNLOADING.getFriendlyName())) {
                            askquestion(position, null, 2);
                        }
                    }
                })
        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //just for Demo
                /*(new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);

                    }
                }, 1500);*/
                syncTokenlyAndUpdateThreads(false);

                //
                // updatesonglist();

            }
        });



        return view;
    }


    void syncTokenlyAndUpdateThreads(boolean autosync){


        syncthread=new SyncThreadClass(autosync); // Firstthread
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // Above Api Level 13
        {
            // syncthread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            syncthread.execute();
        }
        else // Below Api Level 13
        {
            syncthread.execute();
        }

       /* monitorThreadClass=new MonitorThreadClass(); // Secondthread
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)// Above Api Level 13
        {
            monitorThreadClass.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else // Below Api Level 13
        {
            monitorThreadClass.execute();
        }*/

    }

    int searchInViewBySongId(UUID song_Id){

        for(int i=0;i<items.size();i++){
            if(items.get(i).getSong_id().equals(song_Id)){
                return i;
            }
        }
        return 0;
    }

    void searchInViewPosition(Song songOfBroadcast,UUID song_Id){
       int position;
       String songInfo;
       List<String> listComposerAndSongNameOnView=new ArrayList<>();
        for(SongItems songitems : items){
               if(!listComposerAndSongNameOnView.contains(songitems.getArtist_name()+"@#@#"+songitems.getSong_name())){
                   //   System.out.println("TKY_VIEW songs"+songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
                   listComposerAndSongNameOnView.add(songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
               }
        }
        songInfo=songOfBroadcast.getComposers()+"@#@#"+songOfBroadcast.getName();
        if(!listComposerAndSongNameOnView.contains(songInfo)){
            items.add(new SongItems(R.drawable.tky_tokenly_album, songOfBroadcast.getName(), songOfBroadcast.getComposers(), SongStatus.DOWNLOADING.getFriendlyName(),song_Id, 0, false));
            adapter.setFilter(items);

        }

    }

    void compareViewAndDatabase(){
        String databaseInfo;
        List<String> listComposerAndSongNameOnView=new ArrayList<>();
        List<WalletSong> songsInDatabase=new ArrayList<>();

        try {
            songsInDatabase=fanWalletModule.getAvailableSongs();
        } catch (CantGetSongListException e) {
            e.printStackTrace();
        }

        for(SongItems songitems : items){
            if(!listComposerAndSongNameOnView.contains(songitems.getArtist_name()+"@#@#"+songitems.getSong_name())){
             //   System.out.println("TKY_VIEW songs"+songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
                listComposerAndSongNameOnView.add(songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
            }
        }
        if(songsInDatabase.size()>listComposerAndSongNameOnView.size()){
            for (WalletSong walletitems :songsInDatabase){
                databaseInfo=walletitems.getComposers()+"@#@#"+walletitems.getName();
              //  System.out.println("TKY_WALLET songs"+walletitems.getComposers()+"@#@#"+walletitems.getName());
                if(!listComposerAndSongNameOnView.contains(databaseInfo)){
                    listComposerAndSongNameOnView.add("TKY_WALLET songs" + walletitems.getComposers() + "@#@#" + walletitems.getName());
                    //System.out.println("TKY_NOT in view" + walletitems.getComposers() + "@#@#" + walletitems.getName());
                    items.add(new SongItems(R.drawable.tky_tokenly_album, walletitems.getName(), walletitems.getComposers(), walletitems.getSongStatus().getFriendlyName(), walletitems.getSongId(), 0, false));
                    adapter.setFilter(items);
                }
            }

        }
    }

    void swipe_effect(){


   //     ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                return true;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                List<SongItems> original=new ArrayList<>();
                original.addAll(items);
                askquestion(position, original, 0);

            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.END;       // delete one in case you want just one direction
                if(items.get(position).getStatus()==SongStatus.DOWNLOADING.getFriendlyName() ||items.get(position).getStatus()==SongStatus.NOT_AVAILABLE.getFriendlyName()){
                    return 0;
                }else{
                    return makeMovementFlags(dragFlags, swipeFlags);
                }

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
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.tky_chat);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 1.75f*width ,(float) itemView.getTop() + 0.75f*width,(float) itemView.getRight() - width/4,(float)itemView.getBottom() - 0.75f*width);
                        c.drawBitmap(icon, null, icon_dest, p);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void askquestion(final int position, final List<SongItems> original, int whocallme){
        final UUID songid;
        if(whocallme==0) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
                dialogo1.setTitle("FanWallet");
                dialogo1.setMessage("Do you really want to delete '" + items.get(position).getSong_name() + "' from your device?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        deletesong(position);

                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        adapter.setFilter(original);
                    }
                });
                dialogo1.show();
        }else if(whocallme==1){
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
            dialogo1.setTitle("FanWallet");
            dialogo1.setMessage("Do you really want to download '" + items.get(position).getSong_name() + "'?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                    downloadsong(position);

                }
            });
            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //Nothing happen
                }
            });
            dialogo1.show();
        }else if(whocallme==2){
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
            dialogo1.setTitle("FanWallet");
            dialogo1.setMessage("Do you really want to Cancel the download of '" + items.get(position).getSong_name() + "'?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                    cancelsong(position);


                }
            });
            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //Nothing happen
                }
            });
            dialogo1.show();
        }
    }

    void deletesong(int position){

        try {
            fanWalletModule.deleteSong(items.get(position).getSong_id());
            items.get(position).setStatus(SongStatus.DELETED.getFriendlyName());
            items.get(position).setProgress(0);
            adapter.setFilter(items);
        } catch (CantDeleteSongException e) {
            errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (CantUpdateSongStatusException e) {
            errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

    }

    void downloadsong(int position){
        downloadthread = new DownloadThreadClass(position);
        downloadthread.execute();
    }

    void cancelsong(int position){
        try {
            if(downloadthread!=null){
                downloadthread.cancel(true);
            }
            else if(syncthread!=null){
                myCancelHandler.post(myRunnablecancel);
            }



        } catch (Exception e) {
            Log.v(TAG, "EXCEPTION" + e);
        }

    }

    final Runnable myRunnablecancel = new Runnable() {

        public void run() {
            fanwalletSession.getModuleManager().cancelDownload();
        }
    };

    public void updateViewForBroadcaster(FermatBundle bundle) {

        int position=0;

            try {
                System.out.println("TKY_Broad_Arrive:");

                if (bundle.contains(BroadcasterNotificationType.SONG_INFO.getCode())) {
                    System.out.println("TKY_BROAD_SONGINFO:"+ ((Song)bundle.getSerializable(BroadcasterNotificationType.SONG_INFO.getCode())).getName());
                    searchInViewPosition((Song) bundle.getSerializable(BroadcasterNotificationType.SONG_INFO.getCode()),
                            (UUID) bundle.getSerializable(BroadcasterNotificationType.SONG_ID.getCode()));
                }

                if (bundle.contains(BroadcasterNotificationType.DOWNLOAD_PERCENTAGE.getCode())) {
                    System.out.println("TKY_BROAD_DOWNLOAD_PERCENTAGE:"+bundle.getString(BroadcasterNotificationType.DOWNLOAD_PERCENTAGE.getCode()));
                    position=searchInViewBySongId((UUID) bundle.getSerializable(BroadcasterNotificationType.SONG_ID.getCode()));
                    updateprogress(position, bundle.getString(BroadcasterNotificationType.DOWNLOAD_PERCENTAGE.getCode()).split("%")[0]);
                }

                if (bundle.contains(BroadcasterNotificationType.DOWNLOAD_EXCEPTION.getCode())) {
                    System.out.println("TKY_BROAD_DOWNLOAD_EXCEPTION:"+bundle.getString(BroadcasterNotificationType.DOWNLOAD_EXCEPTION.getCode()));
                    position=searchInViewBySongId((UUID) bundle.getSerializable(BroadcasterNotificationType.SONG_ID.getCode()));
                    downloadproblem(position);
                }

                if (bundle.contains(BroadcasterNotificationType.SONG_CANCEL.getCode())) {
                    System.out.println("TKY_BROAD_SONG_CANCEL:"+bundle.getString(BroadcasterNotificationType.SONG_CANCEL.getCode()));
                    position=searchInViewBySongId((UUID) bundle.getSerializable(BroadcasterNotificationType.SONG_ID.getCode()));
                    cancelnotification(position);
                }


            } catch (IllegalAccessException e) {
                //errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                System.out.println("TKY_Error On update bundle:" + e);
                e.printStackTrace();
            }
        }



    void updateprogress(int position,String progress){
        System.out.println("Vista(" + position + "):" + progress);
        items.get(position).setProgressbarvissible(true);
        items.get(position).setProgress(Integer.valueOf(progress));
        if(progress.equals("100")){
            items.get(position).setProgressbarvissible(false);
            items.get(position).setStatus(SongStatus.DOWNLOADED.getFriendlyName());
          //  items.get(position).setSong_id();  //TODO
        }
        adapter.setFilter(items);
    }

    void cancelnotification(int position){
        items.get(position).setProgressbarvissible(false);
        items.get(position).setProgress(Integer.valueOf(0));
        items.get(position).setStatus(SongStatus.AVAILABLE.getFriendlyName());
        adapter.setFilter(items);
    }
    // TODO: 04/04/16 what happen here?
    void downloadproblem(int position){
        //
    }


    void playsong(){

    }


    /* AsyncTask
    Variable type
    Void for the parameters
    Float for the onprogressupdate
    Boolen for the  onPostExecute   */
    public class DownloadThreadClass extends AsyncTask<Void, Float, Boolean> {
    int position;
        UUID songId;
        Fan testfan=getTestFanIdentity();
        /**
         * parameters position
         */
        public DownloadThreadClass(int position) {
            this.position=position;
        }

        /**
         * Before start thread
         */
        @Override
        protected void onPreExecute() {

            items.get(position).setStatus("Downloading");
            items.get(position).setProgressbarvissible(true);
            songId=items.get(position).getSong_id();
            Log.v(TAG, "Before start download");
            adapter.setFilter(items);

        }

        /**
         * Se ejecuta después de "onPreExecute". Se puede llamar al hilo Principal con el método "publishProgress" que ejecuta el método "onProgressUpdate" en hilo Principal
         */
        @Override
        protected Boolean doInBackground(Void... variableNoUsada) {

            float progreso = 0.0f;

            while(items.get(position).getProgress()<100) {
                    if (isCancelled()) {
                       break;
                    }
                    try {
                        fanwalletSession.getModuleManager().downloadSong(songId,testfan.getMusicUser());
                    } catch (CantDownloadSongException e) {
                        e.printStackTrace();
                    } catch (CantUpdateSongStatusException e) {
                        e.printStackTrace();
                    } catch (CantUpdateSongDevicePathException e) {
                        e.printStackTrace();
                    }


            }

            return true;
        }



        /**
         * To update the view
         */
        @Override
        protected void onProgressUpdate(Float... porcentajeProgreso) {

        }


        /**
         * afte finish receive the value of doinbackground
         */

        protected void onPostExecute(Boolean ready) {
        }
        /**
         * when the method cancel is called
         */
        @Override
        protected void onCancelled() {
            fanwalletSession.getModuleManager().cancelDownload();
        }

    }

    /* AsyncTask
   Variable type
   Void for the parameters
   Float for the onprogressupdate
   Boolen for the  onPostExecute   */
    public class SyncThreadClass extends AsyncTask<Void, WalletSong, Boolean> {
        boolean autosync;


        /**
         * parameters position
         */
        public SyncThreadClass(boolean autosync) {
            this.autosync=autosync;
        }

        /**
         * Before start thread
         */
        @Override
        protected void onPreExecute() {

        }

        /**
         * Se ejecuta después de "onPreExecute". Se puede llamar al hilo Principal con el método "publishProgress" que ejecuta el método "onProgressUpdate" en hilo Principal
         */
        @Override
        protected Boolean doInBackground(Void... variableNoUsada) {

            try {
                Fan testfan=getTestFanIdentity();
                if(autosync) {
                    System.out.println("TKY_AutoSync ok");
                    fanwalletSession.getModuleManager().synchronizeSongs(testfan);
                }else{
                    System.out.println("TKY_ManualSync ok");
                    fanwalletSession.getModuleManager().synchronizeSongsByUser(testfan);
                }
            }catch (Exception e ){
                System.out.println("TKY_Error manual sync:" + e);
            }


            return true;
        }



        /**
         * To update the view
         */
        @Override
        protected void onProgressUpdate(WalletSong... walletitems) {


        }





        /**
         * afte finish receive the value of doinbackground
         */

        protected void onPostExecute(Boolean ready) {

            Log.v(TAG, "Game Over SyncThreadClass");

        }
        /**
         * when the method cancel is called
         */
        @Override
        protected void onCancelled() {

        }





    }

    //JUST FOR TEST


    private Fan getTestFanIdentity(){
        Fan fanIdentity = new Fan() {


            @Override
            public List<String> getConnectedArtists() {
                return null;
            }

            @Override
            public void addNewArtistConnected(String userName) {

            }

            @Override
            public String getArtistsConnectedStringList() {
                return null;
            }

            @Override
            public void addArtistConnectedList(String xmlStringList) {

            }

            @Override
            public String getTokenlyId() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public String getApiToken() {
                return null;
            }

            @Override
            public String getApiSecretKey() {
                return null;
            }

            @Override
            public UUID getId() {
                return null;
            }

            @Override
            public String getPublicKey() {
                return null;
            }

            @Override
            public byte[] getProfileImage() {
                return new byte[0];
            }

            @Override
            public void setNewProfileImage(byte[] imageBytes) {

            }


            @Override
            public ExternalPlatform getExternalPlatform() {
                return null;
            }

            @Override
            public MusicUser getMusicUser() {
                MusicUser hardocedUser = new MusicUser() {

                    @Override
                    public String getTokenlyId() {
                        return null;
                    }

                    @Override
                    public String getUsername() {
                        return "pereznator";
                    }

                    @Override
                    public String getEmail() {
                        return "darkpriestrelative@gmail.com";
                    }

                    @Override
                    public String getApiToken() {
                        return "Tvn1yFjTsisMHnlI";
                    }

                    @Override
                    public String getApiSecretKey() {
                        return "K0fW5UfvrrEVQJQnK27FbLgtjtWHjsTsq3kQFB6Y";
                    }
                };
                return hardocedUser;
            }

            @Override
            public String getUserPassword() {
                return null;
            }
        };
        return fanIdentity;
    }


    /* AsyncTask
  Variable type
  Void for the parameters
  Float for the onprogressupdate
  Boolen for the  onPostExecute   */

   /* public class MonitorThreadClass extends AsyncTask<Void, WalletSong, Boolean> {

        boolean unfinish=true;

        List<WalletSong> songlistofthread=new ArrayList<>();
        List<String> listComposerAndSongNameOnView=new ArrayList<>();
        *//**
         * parameters position
         *//*
        public MonitorThreadClass() {

        }

        *//**
         * Before start thread
         *//*
        @Override
        protected void onPreExecute() {

        }

        *//**
         * Se ejecuta después de "onPreExecute". Se puede llamar al hilo Principal con el método "publishProgress" que ejecuta el método "onProgressUpdate" en hilo Principal
         *//*
        @Override
        protected Boolean doInBackground(Void... variableNoUsada) {

            while(syncthread.unfinish){
                try {
                    songlistofthread=fanwalletmoduleManager.getFanWalletModule().getAvailableSongs();
                    compareViewAndDatabase(songlistofthread, items);
           //         System.out.println("TKY_Monitor ok");
                } catch (CantGetSongListException e) {
                    System.out.println("tky_monitorthread:"+e);;
                }
            }


            return true;
        }

        void compareViewAndDatabase(List<WalletSong> listAvailableSongs,List<SongItems> listSongInView ){
            String databaseInfo;

            for(SongItems songitems : listSongInView){
                if(!listComposerAndSongNameOnView.contains(songitems.getArtist_name()+"@#@#"+songitems.getSong_name())){
                    System.out.println("TKY_VIEW songs"+songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
                    listComposerAndSongNameOnView.add(songitems.getArtist_name()+"@#@#"+songitems.getSong_name());
                }
            }
            if(listAvailableSongs.size()>listComposerAndSongNameOnView.size()){
                for (WalletSong walletitems :listAvailableSongs){
                    databaseInfo=walletitems.getComposers()+"@#@#"+walletitems.getName();
                    System.out.println("TKY_WALLET songs"+walletitems.getComposers()+"@#@#"+walletitems.getName());
                    if(!listComposerAndSongNameOnView.contains(databaseInfo)){
                        listComposerAndSongNameOnView.add("TKY_WALLET songs"+walletitems.getComposers()+"@#@#"+walletitems.getName());
                        System.out.println("TKY_NOT in view"+walletitems.getComposers()+"@#@#"+walletitems.getName());
                        publishProgress(walletitems);
                    }
                }

            }
        }

        *//**
         * To update the view
         *//*
        @Override
        protected void onProgressUpdate(WalletSong... walletitems) {
            System.out.println("TKY_PUBLISHPROGRESS"+Arrays.toString(walletitems));
            items.add(new SongItems(R.drawable.tky_tokenly_album, walletitems[0].getName(), walletitems[0].getComposers(),SongStatus.DED.getCode(),walletitems[0].getSongId(),0,false));
            adapter.setFilter(items);
        }


        *//**
         * afte finish receive the value of doinbackground
         *//*

        protected void onPostExecute(Boolean ready) {

            swipeContainer.setRefreshing(false);
            Log.v(TAG, "Game Over MonitoringThreadClass");

        }
        *//**
         * when the method cancel is called
         *//*
        @Override
        protected void onCancelled() {

        }





    }*/





}
