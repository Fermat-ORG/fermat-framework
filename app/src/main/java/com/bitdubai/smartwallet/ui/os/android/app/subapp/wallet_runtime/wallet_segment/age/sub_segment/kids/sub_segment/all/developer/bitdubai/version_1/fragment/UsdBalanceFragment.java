package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.util.FloatMath;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;

import java.io.Serializable;
import java.util.ArrayList;

import static android.widget.RelativeLayout.LayoutParams.*;

public class UsdBalanceFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    private ArrayList<TicketPosition> mTicketsList;
    private String[] tickets;
    private int cantTickets = 0;
    //Definimos el marco por el cual podemos arrastrar la imagen
    private ViewGroup marco;
    //Definimos la imagen que vasmo arrastrar
    private ImageView imagen;
    //Variables para centrar la imagen bajo el dedo
    private int xDelta;
    private int yDelta;

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    float oldDist = 1f;

    public static UsdBalanceFragment newInstance(int position) {
        UsdBalanceFragment f = new UsdBalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tickets = new String[]{"usd_1","usd_1","usd_10","usd_100","usd_10","usd_20","usd_5","usd_10"};

        position = getArguments().getInt(ARG_POSITION);
        mTicketsList = new ArrayList<TicketPosition>();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.wallets_kids_fragment_usd_balance2, container, false); //Contains empty RelativeLayout
        marco = (ViewGroup)view.findViewById(R.id.marco);
        cantTickets = tickets.length;
        for (int i = 0; i < tickets.length; i++) {
            final ImageView imageTicket= new ImageView(container.getContext());
            imageTicket.setId(i);

            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams( WRAP_CONTENT,
                    WRAP_CONTENT);
            int left_margin = 25;
            int top_margin = 25;
            //int right_margin = 20;
            //int bottom_margin = 20;

            if(i > 5)
                marginParams.setMargins(100 + (2* i), (20* i), -50, -50);

            else
                marginParams.setMargins(25 + (2* i), top_margin * + (2* i), -50, -50);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
            imageTicket.setLayoutParams(layoutParams);


            switch (tickets[i]) {
                case "usd_1":
                    MyApplication.setTicketId( "usd_1");
                    imageTicket.setTag(1);
                    imageTicket.setImageResource(R.drawable.usd_1);

                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            MyApplication.setTicketId( "usd_1");
                            if ((Integer)v.getTag() == 1) {
                                imageTicket.setImageResource(R.drawable.usd_1_b);
                                v.setTag(12);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_1);
                                v.setTag(1);
                            }
                        }

                    });

                    break;
                case "usd_5":
                    MyApplication.setTicketId( "usd_5");
                    imageTicket.setTag(5);
                    imageTicket.setImageResource(R.drawable.usd_5);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            MyApplication.setTicketId( "usd_5");
                            if ((Integer)v.getTag() == 5) {
                                imageTicket.setImageResource(R.drawable.usd_5_b);
                                v.setTag(52);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_5);
                                v.setTag(5);
                            }
                        }

                    });

                    break;
                case "usd_10":
                    MyApplication.setTicketId( "usd_10");
                    imageTicket.setTag(10);
                    imageTicket.setImageResource(R.drawable.usd_10);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            MyApplication.setTicketId( "usd_10");
                            if ((Integer)v.getTag() == 10) {
                                imageTicket.setImageResource(R.drawable.usd_10_b);
                                v.setTag(102);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_10);
                                v.setTag(10);
                            }
                        }

                    });

                    break;
                case "usd_20":
                    MyApplication.setTicketId( "usd_20");
                    imageTicket.setTag(20);
                    imageTicket.setImageResource(R.drawable.usd_20);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            MyApplication.setTicketId( "usd_20");
                            if ((Integer)v.getTag() == 20) {
                                imageTicket.setImageResource(R.drawable.usd_20_b);
                                v.setTag(202);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_20);
                                v.setTag(20);
                            }
                        }

                    });


                    break;
                case "usd_100":
                    MyApplication.setTicketId( "usd_100");
                    imageTicket.setTag(100);
                    imageTicket.setImageResource(R.drawable.usd_100);
                    imageTicket.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 100) {
                                imageTicket.setImageResource(R.drawable.usd_100_b );
                                v.setTag(1002);
                            }
                            else{
                                imageTicket.setImageResource(R.drawable.usd_100);
                                v.setTag(100);
                            }
                        }

                    });


                    break;

            }
            imageTicket.setOnLongClickListener(new theLongClickListener());
            imageTicket.setOnTouchListener(new theTouchListener());
            marco.addView(imageTicket);

            TicketPosition item2 = new TicketPosition();
            item2.Id = imageTicket.getId();
            item2.ticketId = (Integer)imageTicket.getTag();
            item2.leftMargin = layoutParams.leftMargin;
            item2.topMargin = layoutParams.topMargin ;
            item2.rightMargin = layoutParams.rightMargin;
            item2.bottomMargin =  layoutParams.bottomMargin;
            mTicketsList.add(item2);
        }


        return view;
    }

    private final class theLongClickListener implements OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {

            //add two image and remove de current image
            ClipData clipData = ClipData.newPlainText("", "");

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            //start the drag - contains the data to be dragged,
            //   metadata for this data and callback for drawing shadow
            v.startDrag(clipData, shadowBuilder, v, 0);
//        we're dragging the shadow so make the view invisible

            final ImageView imagen1;
            final ImageView imagen2;

            TicketPosition item1 = getTicket(v.getId());
            TicketPosition item2;
            RelativeLayout.LayoutParams layoutParams;

            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams( WRAP_CONTENT,
                    WRAP_CONTENT);

            switch ((Integer)v.getTag()) {
                case 1:
                case 12:
                    break;
                case 5:
                case 52:

                    break;
                case 10:
                case 102:

                    imagen1= new ImageView(v.getContext());
                    cantTickets++;
                    imagen1.setId(cantTickets);

                    imagen1.setTag(5);
                    imagen1.setImageResource(R.drawable.usd_5);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 5) {
                                imagen1.setImageResource(R.drawable.usd_5_b);
                                v.setTag(52);
                            }
                            else{
                                imagen1.setImageResource(R.drawable.usd_5);
                                v.setTag(5);
                            }
                        }


                    });

                    marginParams.setMargins(item1.leftMargin,item1.topMargin,item1.rightMargin,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen1.setLayoutParams(layoutParams);
                    imagen1.setOnLongClickListener(new theLongClickListener());
                    imagen1.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen1);

                    item2 = new TicketPosition();
                    item2.Id = cantTickets;
                    item2.ticketId = (Integer)imagen1.getTag();
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar
                    cantTickets++;
                    imagen2.setId(cantTickets);
                    imagen2.setTag(5);
                    imagen2.setImageResource(R.drawable.usd_5);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 5) {
                                imagen2.setImageResource(R.drawable.usd_5_b);
                                v.setTag(52);
                            }
                            else{
                                imagen2.setImageResource(R.drawable.usd_5);
                                v.setTag(5);
                            }
                        }


                    });

                    marginParams.setMargins(item1.leftMargin + 25,item1.topMargin,item1.rightMargin + 25,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen2.setLayoutParams(layoutParams);
                    imagen2.setOnLongClickListener(new theLongClickListener());
                    imagen2.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen2);

                    item2 = new TicketPosition();
                    item2.Id = cantTickets;
                    item2.ticketId = (Integer)imagen2.getTag();
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);
                    v.setVisibility(View.INVISIBLE);
                    mTicketsList.remove(item1);
                    break;
                case 20:
                case 202:
                    imagen1= new ImageView(v.getContext());
                    cantTickets++;
                    imagen1.setId(cantTickets);
                    imagen1.setTag(10);
                    imagen1.setImageResource(R.drawable.usd_10);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 10) {
                                imagen1.setImageResource(R.drawable.usd_10_b);
                                v.setTag(102);
                            }
                            else{
                                imagen1.setImageResource(R.drawable.usd_10);
                                v.setTag(10);
                            }
                        }


                    });
                    imagen1.setOnLongClickListener(new theLongClickListener());
                    imagen1.setOnTouchListener(new theTouchListener());
                    marginParams.setMargins(item1.leftMargin,item1.topMargin,item1.rightMargin,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen1.setLayoutParams(layoutParams);
                    marco.addView(imagen1);

                    item2 = new TicketPosition();
                    item2.Id = imagen1.getId();
                    item2.ticketId = 10;
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar
                    cantTickets++;
                    imagen2.setId(cantTickets);
                    imagen2.setTag(10);
                    imagen2.setImageResource(R.drawable.usd_10);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 10) {
                                imagen2.setImageResource(R.drawable.usd_10_b);
                                v.setTag(102);
                            }
                            else{
                                imagen2.setImageResource(R.drawable.usd_10);
                                v.setTag(10);
                            }
                        }


                    });
                    imagen2.setOnLongClickListener(new theLongClickListener());

                    imagen2.setOnTouchListener(new theTouchListener());
                    marginParams.setMargins(item1.leftMargin + 25,item1.topMargin + 25,item1.rightMargin,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen2.setLayoutParams(layoutParams);

                    item2 = new TicketPosition();
                    item2.Id = imagen2.getId();
                    item2.ticketId = 10;
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);
                    marco.addView(imagen2);

                    v.setVisibility(View.INVISIBLE);
                    mTicketsList.remove(item1);
                    break;
                case 50:
                case 502:
                    break;
                case 100:
                case 1002:
                    imagen1= new ImageView(v.getContext());
                    cantTickets++;
                    imagen1.setId(cantTickets);
                    imagen1.setTag(50);
                    imagen1.setImageResource(R.drawable.usd_50);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 50) {
                                imagen1.setImageResource(R.drawable.usd_50_b);
                                v.setTag(502);
                            }
                            else{
                                imagen1.setImageResource(R.drawable.usd_50);
                                v.setTag(50);
                            }
                        }


                    });
                    imagen1.setOnLongClickListener(new theLongClickListener());
                    imagen1.setOnTouchListener(new theTouchListener());
                    marginParams.setMargins(item1.leftMargin,item1.topMargin,item1.rightMargin,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen1.setLayoutParams(layoutParams);

                    marco.addView(imagen1);

                    item2 = new TicketPosition();
                    item2.Id = imagen1.getId();
                    item2.ticketId = (Integer)imagen1.getTag();
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar
                    cantTickets++;
                    imagen2.setId(cantTickets);
                    imagen2.setTag(50);
                    imagen2.setImageResource(R.drawable.usd_50);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 50) {
                                imagen2.setImageResource(R.drawable.usd_50_b);
                                v.setTag(502);
                            }
                            else{
                                imagen2.setImageResource(R.drawable.usd_50);
                                v.setTag(50);
                            }
                        }


                    });
                    imagen2.setOnLongClickListener(new theLongClickListener());
                    marginParams.setMargins(item1.leftMargin + 25,item1.topMargin + 25,item1.rightMargin,item1.bottomMargin);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen2.setLayoutParams(layoutParams);
                    imagen2.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen2);

                    item2 = new TicketPosition();
                    item2.Id = imagen2.getId();
                    item2.ticketId = (Integer)imagen2.getTag();
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin ;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin =  layoutParams.bottomMargin;
                    mTicketsList.add(item2);

                    v.setVisibility(View.INVISIBLE);
                    mTicketsList.remove(item1);
                    break;
            }


            return false;
        }

    }

    public static boolean esPar(int numero) {
        //Si el resto de numero entre 2 es 0, el numero es par
        if (numero % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }




    private final class theTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {

            int ticketId = (Integer)view.getTag();
            int Id = (Integer)view.getId();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) view.getLayoutParams();
            //Recogemos las coordenadas del dedo
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            //Dependiendo de la accion recogida..
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                //Al tocar la pantalla
                case MotionEvent.ACTION_DOWN:
                    //Recogemos los parametros de la imagen que hemos tocado
                    RelativeLayout.LayoutParams Params =
                            (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - Params.leftMargin;
                    yDelta = Y - Params.topMargin;
                    mode = DRAG;
                    return false;
                case MotionEvent.ACTION_UP:
                    //Al levantar el dedo simplemento mostramos un mensaje
                    //Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
//verify if image another other

                    joinTickets(Id,layoutParams,ticketId, view);
                    //add ticket position to array list
                    addTickets(Id,layoutParams,ticketId);

                    return false;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 5f) {
                        //  savedMatrix.set(matrix);
                        //  midPoint(mid, event);
                        mode = ZOOM;

                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    //No hace falta utilizarlo
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Al mover el dedo vamos actualizando

                    if (mode == DRAG) { //movement of first finger
                        //los margenes de la imagen para
                        //crear efecto de arrastrado

                        layoutParams.leftMargin = X - xDelta;
                        layoutParams.topMargin = Y - yDelta;
                        layoutParams.alignWithParent = false;
                        //Qutamos un poco de margen para
                        //que la imagen no se deforme
                        //al llegar al final de la pantalla y pueda ir más allá
                        //probar también el codigo omitiendo estas dos líneas
                        layoutParams.rightMargin = -50;
                        layoutParams.bottomMargin = -50;
                        //le añadimos los nuevos
                        //parametros para mover la imagen
                        view.setLayoutParams(layoutParams);




                    }
                    else if (mode == ZOOM) { //pinch zooming
                        //set ticket id

                        switch (ticketId) {
                            case 1:
                                MyApplication.setTicketId( "usd_1");
                                break;
                            case 10:
                                MyApplication.setTicketId( "usd_10");
                                break;
                            case 5:
                                MyApplication.setTicketId( "usd_5");
                                break;
                            case 100:
                                MyApplication.setTicketId( "usd_100");
                                break;
                            case 20:
                                MyApplication.setTicketId( "usd_20");
                                break;
                            case 50:
                                MyApplication.setTicketId( "usd_50");
                                break;
                        }
                        float newDist = spacing(event);

                        if (newDist > 5f) {
                            //"open fragmen pop up";
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                ft.remove(prev);
                            }
                            ft.addToBackStack(null);

                            // Create and show the dialog.
                            DialogFragment newFragment = TicketFragment.newInstance(1);
                            newFragment.show(ft, "dialog");

                        }
                    }

                    return false;


            }
            //Se podría decir que 'dibujamos'
            //la posición de la imagen en el marco.
            marco.invalidate();
            return true;
        }
    }

    private void joinTickets(int id,RelativeLayout.LayoutParams layoutParams, int ticketId, View view)
    {
        TicketPosition itemTicket = getTicket(id);
        for (int i = 0; i < mTicketsList.size(); i++) {
            TicketPosition item = mTicketsList.get(i);
            double posi1 = (item.leftMargin * 45) /100.0;
            double posi2 = (item.rightMargin * 5) /100.0;
            int left =0;
            int top = layoutParams.topMargin - item.topMargin;
            if (layoutParams.leftMargin > item.leftMargin)
                left = ( layoutParams.leftMargin - item.leftMargin);
            else
                left = (  item.leftMargin -layoutParams.leftMargin);
            int right = (item.rightMargin - layoutParams.rightMargin);
            //eveluate if resta negativa, no usarla.
            if((left <= posi1 && right ==0))
            {
                int idTicket = item.ticketId;
                int idTicket2 = ticketId;
                //verify if ticket equal to another ticket value and join in a another to couble value
                switch (item.ticketId) {
                    case 12:
                        idTicket = 1;
                        break;
                    case 102:
                        idTicket = 10;
                        break;
                    case 52:
                        idTicket = 5;
                        break;
                    case 1002:
                        idTicket = 100;
                        break;
                    case 202:
                        idTicket = 20;
                        break;
                    case 502:
                        idTicket = 50;
                        break;
                }

                switch (ticketId) {
                    case 12:
                        idTicket2 = 1;
                        break;
                    case 102:
                        idTicket2 = 10;
                        break;
                    case 52:
                        idTicket2 = 5;
                        break;
                    case 1002:
                        idTicket2 = 100;
                        break;
                    case 202:
                        idTicket2 = 20;
                        break;
                    case 502:
                        idTicket2 = 50;
                        break;
                }
//if the same value but diferent ticket
                if ((idTicket2 == idTicket) && (id != item.Id))
                {

                    final ImageView imagen1;
                    TicketPosition item2;
                    ImageView imagen2 = (ImageView)((RelativeLayout)view.getParent()).findViewById(item.Id);
                    ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams( WRAP_CONTENT,
                            WRAP_CONTENT);
                    switch (idTicket2) {
                        case 1:

                            break;
                        case 10:
                            imagen1= new ImageView(view.getContext());
                            cantTickets++;
                            imagen1.setId(cantTickets);
                            mTicketsList.remove(item);
                            view.setVisibility(View.INVISIBLE);
                            imagen1.setTag(20);
                            imagen1.setImageResource(R.drawable.usd_20);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 20) {
                                        imagen1.setImageResource(R.drawable.usd_20_b);
                                        v.setTag(202);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_20);
                                        v.setTag(20);
                                    }
                                }


                            });
                            imagen1.setOnLongClickListener(new theLongClickListener());
                            imagen1.setOnTouchListener(new theTouchListener());

                            marginParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,-50,-50);
                            layoutParams = new RelativeLayout.LayoutParams(marginParams);
                            imagen1.setLayoutParams(layoutParams);
                            marco.addView(imagen1);

                            item2 = new TicketPosition();
                            item2.Id = cantTickets;
                            item2.ticketId = 20;
                            item2.leftMargin = layoutParams.leftMargin;
                            item2.topMargin = layoutParams.topMargin ;
                            item2.rightMargin = layoutParams.rightMargin;
                            item2.bottomMargin =  layoutParams.bottomMargin;
                            mTicketsList.add(item2);


                            if(imagen2 != null)
                                marco.removeView(imagen2);

                            mTicketsList.remove(item);
                            mTicketsList.remove(itemTicket);
                            break;
                        case 5:
                            imagen1= new ImageView(view.getContext());
                            cantTickets++;
                            imagen1.setId(cantTickets);
                            view.setVisibility(View.INVISIBLE);

                            imagen1.setTag(10);
                            imagen1.setImageResource(R.drawable.usd_10);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 10) {
                                        imagen1.setImageResource(R.drawable.usd_10_b);
                                        v.setTag(102);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_10);
                                        v.setTag(10);
                                    }
                                }


                            });
                            imagen1.setOnLongClickListener(new theLongClickListener());

                            imagen1.setOnTouchListener(new theTouchListener());
                            marginParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, -50, -50);
                            layoutParams = new RelativeLayout.LayoutParams(marginParams);
                            imagen1.setLayoutParams(layoutParams);

                            item2 = new TicketPosition();
                            item2.Id = cantTickets;
                            item2.ticketId = 10;
                            item2.leftMargin = layoutParams.leftMargin;
                            item2.topMargin = layoutParams.topMargin ;
                            item2.rightMargin = layoutParams.rightMargin;
                            item2.bottomMargin =  layoutParams.bottomMargin;
                            mTicketsList.add(item2);

                            if(imagen2 != null)
                                marco.removeView(imagen2);
                            marco.addView(imagen1);

                            mTicketsList.remove(item);
                            mTicketsList.remove(itemTicket);
                            break;
                        case 100:
                            break;
                        case 20:

                            break;
                        case 50:
                            imagen1= new ImageView(view.getContext());
                            cantTickets++;
                            imagen1.setId(cantTickets);

                            view.setVisibility(View.INVISIBLE);
                            if(imagen2 != null)
                                imagen2.setVisibility(View.INVISIBLE);

                            imagen1.setTag(100);
                            imagen1.setImageResource(R.drawable.usd_100);
                            imagen1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    if ((Integer)v.getTag() == 100) {
                                        imagen1.setImageResource(R.drawable.usd_10_b);
                                        v.setTag(1002);
                                    }
                                    else{
                                        imagen1.setImageResource(R.drawable.usd_100);
                                        v.setTag(100);
                                    }
                                }


                            });
                            imagen1.setOnLongClickListener(new theLongClickListener());
                            imagen1.setOnTouchListener(new theTouchListener());
                            marginParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,-50,-50);
                            layoutParams = new RelativeLayout.LayoutParams(marginParams);
                            imagen1.setLayoutParams(layoutParams);

                            item2 = new TicketPosition();
                            item2.Id = cantTickets;
                            item2.ticketId = 100;
                            item2.leftMargin = layoutParams.leftMargin;
                            item2.topMargin = layoutParams.topMargin ;
                            item2.rightMargin = layoutParams.rightMargin;
                            item2.bottomMargin =  layoutParams.bottomMargin;
                            mTicketsList.add(item2);

                            if(imagen2 != null)
                                marco.removeView(imagen2);
                            marco.addView(imagen1);
                            mTicketsList.remove(item);
                            mTicketsList.remove(itemTicket);
                            break;
                    }


                }


            }
        }
    }


    private TicketPosition getTicket(int id) {

        TicketPosition item1 = new  TicketPosition();
        for (int i = 0; i < mTicketsList.size(); i++) {
            TicketPosition item = mTicketsList.get(i);
            if (item.Id == id) {
                item1= item;
            }
        }

        return item1;
    }
    private void addTickets(int id,RelativeLayout.LayoutParams layoutParams, int ticketId) {
        boolean _update = false;
        if (mTicketsList.size() > 0) {
            for (int i = 0; i < mTicketsList.size(); i++) {
                TicketPosition item = mTicketsList.get(i);

                if (item.ticketId == ticketId) {
                    //update
                    TicketPosition item2 = new TicketPosition();
                    item2.Id = id;
                    item2.ticketId = ticketId;
                    item2.leftMargin = layoutParams.leftMargin;
                    item2.topMargin = layoutParams.topMargin;
                    item2.rightMargin = layoutParams.rightMargin;
                    item2.bottomMargin = layoutParams.bottomMargin;
                    break;
                }
            }


        }

    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    public class TicketPosition implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;
        public int Id;
        public int ticketId;

        public int leftMargin;

        public int topMargin;

        public int rightMargin;

        public int bottomMargin;



    }
}

