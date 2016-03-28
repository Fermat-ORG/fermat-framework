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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.fan_wallet.common.adapters.SongAdapter;
import com.bitdubai.reference_wallet.fan_wallet.common.models.SongItems;
import com.bitdubai.reference_wallet.fan_wallet.preference_settings.FanWalletSettings;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;
import com.bitdubai.reference_wallet.fan_wallet.util.ManageRecyclerviewClick;

import java.util.ArrayList;
import java.util.List;

import sub_app.R;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class SongFragment extends AbstractFermatFragment {
    //FermatManager
    private FanWalletSession fanwalletSession;
    //   private FanWalletModuleManager fanwalletmoduleManager;
    private FanWalletSettings fanWalletSettings;
    private ErrorManager errorManager;


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeContainer;
    String TAG="SONGFRAGMENT";
    View view;
    private Paint p = new Paint();
    thread downloadthread;
    private SongAdapter adapter;
    private RecyclerView.LayoutManager lManager;


    List<SongItems> items=new ArrayList();
    public static SongFragment newInstance(){
        return new SongFragment();
    }

    public SongFragment(){
        items.add(new SongItems(R.drawable.td, "Let It Go", "Tatiana Moroz","Downloaded",0,false));
        items.add(new SongItems(R.drawable.td,"I Don't Know You Anymore","Tatiana Moroz","Pending",0,false));
        items.add(new SongItems(R.drawable.td,"Heart of Gold","Tatiana Moroz","Pending",0,false));
        items.add(new SongItems(R.drawable.md, "Memory Remain", "S&M","Downloaded",0,false));
        items.add(new SongItems(R.drawable.md,"Master of Puppets","S&M","Pending",0,false));
        items.add(new SongItems(R.drawable.dd, "One Last Time", "Metropolis Pt. 2: Scenes from a Memory","Downloaded",0,false));
        items.add(new SongItems(R.drawable.dd,"Fatal Tragedy","Metropolis Pt. 2: Scenes from a Memory","Pending",0,false));
        items.add(new SongItems(R.drawable.dd, " Overture 1928", "Metropolis Pt. 2: Scenes from a Memory", "Downloaded", 0, false));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            fanwalletSession = ((FanWalletSession) appSession);
            //fanwalletmoduleManager = fanwalletSession.getModuleManager();
            errorManager = appSession.getErrorManager();


            try {
                //    fanWalletSettings = fanwalletmoduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    //fanwalletmoduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.tky_fan_wallet_song_fragment,container,false);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        lManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(lManager);
        adapter = new SongAdapter(items);
        recyclerView.setAdapter(adapter);
        swipe_effect();

        recyclerView.addOnItemTouchListener(
                new ManageRecyclerviewClick(view.getContext(), new ManageRecyclerviewClick.OnItemClickListener() {
                    @Override

                    public void onItemClick(View view, int position) {

                        System.out.println("click position:"+position);
                        if (items.get(position).getStatus().equals("Pending")) {
                            askquestion(position, null, 1);
                        } else if (items.get(position).getStatus().equals("Downloaded")) {
                            playsong();
                        }else if(items.get(position).getStatus().equals("Downloading")){
                            askquestion(position,null,2);
                        }
                    }
                })
        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //just for Demo
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);

                    }
                }, 1500);

                //
                // updatesonglist();

            }
        });



    return view;
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
                final int swipeFlags = ItemTouchHelper.START;       // delete one in case you want just one direction
                if(items.get(position).getStatus()=="Downloading" ||items.get(position).getStatus()=="Pending"){
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
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.chat);
                        //Start draw left-top to right-bottom     RectF (left,top,right,bottom)
                        RectF icon_dest = new RectF((float) itemView.getLeft() + (width/4) ,(float) itemView.getTop() + 0.75f*width,(float) itemView.getLeft()+ 2f*width,(float)itemView.getBottom() -0.75f*width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.trash);
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


    void askquestion(final int position, final List<SongItems> original, int whocallme){
        if(whocallme==0) {
            if (items.get(position).getStatus() == "Downloaded") {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
                dialogo1.setTitle("FanWallet");
                dialogo1.setMessage("Do you really want to delete '" + items.get(position).getSong_name() + "' from your device?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                   //     items.remove(position);
                        items.get(position).setStatus("Pending");
                        items.get(position).setProgress(0);
                        adapter.setFilter(items);
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        adapter.setFilter(original);
                    }
                });
                dialogo1.show();
            }
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

                    try {
                        downloadthread.cancel(true);
                    } catch (Exception e) {
                        Log.v(TAG, "EXCEPTION"+e);
                    }

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


    void downloadsong(int position){
        downloadthread = new thread(position);
        downloadthread.execute();
    }

    void playsong(){

    }

    void updatesonglist(){
        swipeContainer.setRefreshing(false);

    }
    /* AsyncTask
    Variable type
    Void for the parameters
    Float for the onprogressupdate
    Boolen for the  onPostExecute   */
    public class thread extends AsyncTask<Void, Float, Boolean> {
    int position;

        public thread(int position) {
            this.position=position;
        }

        /**
         * Before start thread
         */
        @Override
        protected void onPreExecute() {
            items.get(position).setStatus("Downloading");
            items.get(position).setProgressbarvissible(true);
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
                        Thread.sleep((long) (1000));
                    } catch (InterruptedException e) {
                   //     cancel(true); //just in case something goes wrong
                   //     e.printStackTrace();
                    }

                    progreso = progreso+15;

                    publishProgress(progreso);


            }

            return true;
        }



        /**
         * To update the view
         */
        @Override
        protected void onProgressUpdate(Float... porcentajeProgreso) {

            Log.v(TAG, "tiempo:" + porcentajeProgreso[0] + "");

            items.get(position).setProgress(Math.round(porcentajeProgreso[0]));

            adapter.setFilter(items);
        }

        String crono(float tiempo){
            float horas,minutos;
            int segundos;
            String conteo="NADA";

            return conteo;
        }

        /**
         * afte finish receive the value of doinbackground
         */

        protected void onPostExecute(Boolean ready) {
            items.get(position).setStatus("Downloaded");
            items.get(position).setProgressbarvissible(false);
            Log.v(TAG, "Game Over thread");
            adapter.setFilter(items);
        }
        /**
         * when the method cancel is called
         */
        @Override
        protected void onCancelled() {
            items.get(position).setStatus("Pending");
            items.get(position).setProgress(0);
            items.get(position).setProgressbarvissible(false);
            Log.v(TAG, "Donwload canceled");
            adapter.setFilter(items);
        }





    }


}
