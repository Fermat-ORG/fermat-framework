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

import static android.widget.RelativeLayout.LayoutParams.*;

public class UsdBalanceFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private String[] tickets;

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
        tickets = new String[]{"usd_1", "usd_20","usd_5", "usd_10","usd_10", "usd_20", "usd_100", "usd_1", "usd_5", "usd_5", "usd_10", "usd_20"};

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.wallets_kids_fragment_usd_balance2, container, false); //Contains empty RelativeLayout
        marco = (ViewGroup)view.findViewById(R.id.marco);

        for (int i = 0; i < tickets.length; i++) {
            final ImageView imageTicket= new ImageView(container.getContext());


            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams( WRAP_CONTENT,
                    WRAP_CONTENT);
            int left_margin = 25;
            int top_margin = 25;
            //int right_margin = 20;
            //int bottom_margin = 20;

            if(i > 5)
                marginParams.setMargins(100 + (2* i), (20* i), 20, 20);

            else
                marginParams.setMargins(25 + (2* i), top_margin * + (2* i), 20, 20);

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
                                imageTicket.setImageResource(R.drawable.ar_bill_1_b);
                                v.setTag(22);
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
                                imageTicket.setImageResource(R.drawable.ar_bill_5_b);
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
                                imageTicket.setImageResource(R.drawable.ar_bill_10_b);
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
                                imageTicket.setImageResource(R.drawable.ar_bill_2_b);
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
                                imageTicket.setImageResource(R.drawable.ar_bill_100_b );
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
            RelativeLayout.LayoutParams layoutParams;


            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams( WRAP_CONTENT,
                    WRAP_CONTENT);

            switch ((Integer)v.getTag()) {
                case 1:

                    break;
                case 5:

                    break;
                case 10:
                    v.setVisibility(View.INVISIBLE);
                    imagen1= new ImageView(v.getContext());

                    imagen1.setTag(5);
                    imagen1.setImageResource(R.drawable.usd_5);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 5) {
                                imagen1.setImageResource(R.drawable.ar_bill_5_b);
                                v.setTag(52);
                            }
                            else{
                                imagen1.setImageResource(R.drawable.usd_5);
                                v.setTag(50);
                            }
                        }


                    });
                    //imagen1.setOnLongClickListener(this);
                    marginParams.setMargins(100, 100, 20, 20);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen1.setLayoutParams(layoutParams);
                    imagen1.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen1);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar

                    imagen2.setTag(5);
                    imagen2.setImageResource(R.drawable.usd_5);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 5) {
                                imagen2.setImageResource(R.drawable.ar_bill_5_b);
                                v.setTag(52);
                            }
                            else{
                                imagen2.setImageResource(R.drawable.usd_5);
                                v.setTag(5);
                            }
                        }


                    });
                    // imagen2.setOnLongClickListener(this);
                    marginParams.setMargins(110, 110, 20, 20);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen2.setLayoutParams(layoutParams);
                    imagen2.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen2);

                    break;
                case 20:
                    v.setVisibility(View.INVISIBLE);
                    imagen1= new ImageView(v.getContext());

                    imagen1.setTag(10);
                    imagen1.setImageResource(R.drawable.usd_10);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 10) {
                                imagen1.setImageResource(R.drawable.ar_bill_10_b);
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
                    marco.addView(imagen1);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar

                    imagen2.setTag(10);
                    imagen2.setImageResource(R.drawable.usd_10);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 10) {
                                imagen2.setImageResource(R.drawable.ar_bill_10_b);
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
                    marco.addView(imagen2);
                    break;
                case 50:

                    break;
                case 100:
                    v.setVisibility(View.INVISIBLE);
                    imagen1= new ImageView(v.getContext());

                    imagen1.setTag(50);
                    imagen1.setImageResource(R.drawable.usd_50);
                    imagen1.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 50) {
                                imagen1.setImageResource(R.drawable.ar_bill_50_b);
                                v.setTag(102);
                            }
                            else{
                                imagen1.setImageResource(R.drawable.usd_50);
                                v.setTag(10);
                            }
                        }


                    });
                    // imagen1.setOnLongClickListener(this);
                    imagen1.setOnTouchListener(new theTouchListener());
                    marginParams.setMargins(100, 100, 20, 20);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen1.setLayoutParams(layoutParams);

                    marco.addView(imagen1);

                    imagen2= new ImageView(v.getContext());
                    //Señalamos la imagen a mostrar

                    imagen2.setTag(50);
                    imagen2.setImageResource(R.drawable.usd_50);
                    imagen2.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if ((Integer)v.getTag() == 50) {
                                imagen2.setImageResource(R.drawable.ar_bill_50_b);
                                v.setTag(502);
                            }
                            else{
                                imagen2.setImageResource(R.drawable.usd_50);
                                v.setTag(50);
                            }
                        }


                    });
                    // imagen2.setOnLongClickListener(this);
                    marginParams.setMargins(120, 100, 20, 20);
                    layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    imagen2.setLayoutParams(layoutParams);
                    imagen2.setOnTouchListener(new theTouchListener());
                    marco.addView(imagen2);
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

                    return false;
                case MotionEvent.ACTION_POINTER_DOWN:
                    //oldDist = spacing(event);
                    //if (oldDist > 5f) {
                    //  savedMatrix.set(matrix);
                    //  midPoint(mid, event);
                    mode = ZOOM;

                    // }
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
                        RelativeLayout.LayoutParams layoutParams =
                                (RelativeLayout.LayoutParams) view.getLayoutParams();
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
                        int ticketId = (Integer)view.getTag();
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
                            case 12:
                                MyApplication.setTicketId( "usd_1");
                                break;
                            case 102:
                                MyApplication.setTicketId( "usd_10");
                                break;
                            case 52:
                                MyApplication.setTicketId( "usd_5");
                                break;
                            case 1002:
                                MyApplication.setTicketId( "usd_100");
                                break;
                            case 202:
                                MyApplication.setTicketId( "usd_20");
                                break;
                            case 502:
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

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }
}

