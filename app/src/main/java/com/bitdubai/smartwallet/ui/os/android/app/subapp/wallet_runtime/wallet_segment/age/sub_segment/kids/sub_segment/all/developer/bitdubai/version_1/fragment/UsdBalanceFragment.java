package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.bitdubai.smartwallet.R;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;




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
        tickets = new String[]{"usd_1", "usd_1", "usd_1", "usd_5", "usd_10", "usd_20", "usd_100", "Mariana Duyos", "Pedro Perrotta", "Simon Cushing", "Stephanie Himonidis", "Taylor Backus", "Ginny Kaltabanis", "Piper Faust", "Deniz Caglar", "Helen Nisbet", "Dea Vanagan", "Tim Hunter", "Madeleine Jordan", "Kate Bryan", "Victoria Gandit", "Jennifer Johnson", "Robert Wint", "Kevin Helms", "Teddy Truchot", "Hélène Derosier", "John Smith", "Caroline Mignaux", "Guillaume Thery", "Brant Cryder", "Thomas Levy", "Louis Stenz"};

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        final ImageView imageTicket;
        view = inflater.inflate(R.layout.wallets_kids_fragment_usd_balance2, container, false); //Contains empty RelativeLayout
        marco = (ViewGroup)view.findViewById(R.id.marco);
        ImageView money = (ImageView) view.findViewById(R.id.ticket1);
        imageTicket = money;
        money.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                imageTicket.setImageResource(R.drawable.usd_10);
            }
        });
        // set the listener to the dragging data
        money.setOnTouchListener(new theTouchListener());

         money = (ImageView) view.findViewById(R.id.ticket2);
        money.setOnTouchListener(new theTouchListener());

         money = (ImageView) view.findViewById(R.id.ticket3);
        money.setOnTouchListener(new theTouchListener());

         money = (ImageView) view.findViewById(R.id.ticket4);
        money.setOnTouchListener(new theTouchListener());

    /*    money = (ImageView) view.findViewById(R.id.ticket5);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket6);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket7);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket8);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket9);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket10);
        money.setOnTouchListener(new theTouchListener());

        money = (ImageView) view.findViewById(R.id.ticket11);
        money.setOnTouchListener(new theTouchListener());*/

        return view;
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
                    //Recogemos los parametros de la imagen que hemo tocado
                    RelativeLayout.LayoutParams Params =
                            (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - Params.leftMargin;
                    yDelta = Y - Params.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    //Al levantar el dedo simplemento mostramos un mensaje
                    //Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
                    return false;
                case MotionEvent.ACTION_POINTER_DOWN:
                    //No hace falta utilizarlo
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    //No hace falta utilizarlo
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Al mover el dedo vamos actualizando
                    //los margenes de la imagen para
                    //crear efecto de arrastrado
                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    //Qutamos un poco de margen para
                    //que la imagen no se deforme
                    //al llegar al final de la pantalla y pueda ir más allá
                    //probar también el codigo omitiendo estas dos líneas
                   // layoutParams.rightMargin = -50;
                   // layoutParams.bottomMargin = -50;
                    //le añadimos los nuevos
                    //parametros para mover la imagen
                    view.setLayoutParams(layoutParams);
                    break;
            }
            //Se podría decir que 'dibujamos'
            //la posición de la imagen en el marco.
            marco.invalidate();
            return true;
        }
    }
}


   /* private final class dragTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class dropListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                 case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                  /*  View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) owner;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                   /* break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // v.setBackgroundDrawable(normalShape);
                /*    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) owner;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                 /*   break;
                default:
                    break;
            }
            return true;
        }

    }*/


