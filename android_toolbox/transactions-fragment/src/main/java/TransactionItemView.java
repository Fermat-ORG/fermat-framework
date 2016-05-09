import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.customviews.clelia.transactionsfragment.R;

import TransactionsFragment.Custom.CaviarTextView;
import TransactionsFragment.Utility.RippleDrawableGenerator;
import TransactionsFragment.Utility.ScreenUnitConverter;
import TransactionsFragment.Utility.ViewManager;

/**
 * Created by Clelia López 3/13/16
 */
public class TransactionItemView
    extends CardView {

    /**
     * Attributes
     */
    private CaviarTextView toTextView;
    private CaviarTextView toValueTextView;
    private CaviarTextView fromTextView;
    private CaviarTextView fromValueTextView;
    private View firstLine;
    private CaviarTextView dateTextView;
    private ImageView stateImageView;
    private CaviarTextView stateTextView;
    private CaviarTextView noteTextView;
    private View secondLine;
    private CaviarTextView signTextView;
    private CaviarTextView unitTextView;
    private CaviarTextView amountTextView;

    //private int backgroundColor = -1;
    //private Drawable backgroundDrawable = null;
    //int rippleColor = -1;

    private Drawable completeDrawable = null;
    private Drawable pendingDrawable = null;
    private boolean initializedByParsing = false;

    // To
    private int toText = -1;
    private int toTextSize = -1;
    private int toTextColor = -1;
    private int toValueTextSize = -1;
    private int toValueTextColor = -1;

    // From
    private int fromText = -1;
    private int fromTextSize = -1;
    private int fromTextColor = -1;
    private int fromValueTextSize = -1;
    private int fromValueTextColor = -1;

    // Date
    private int dateTextSize = -1;
    private int dateTextColor = -1;

    // State
    private int stateTextSize = -1;
    private int stateTextColor = -1;

    // Note
    private int noteTextSize = -1;
    private int noteTextColor = -1;

    // Amount
    private int amountTextSize = -1;
    private int amountTextColor = -1;

    // Background
    private int backgroundColor = -1;
    private int backgroundDrawable = -1;

    // Ripple Color
    private int rippleColor = -1;

    private Context context;

    public TransactionItemView(Context context) {
        super(context);

        this.context = context;

        initializeView();
    }

    public TransactionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initializedByParsing = true;

        parseAttributes(attrs);

        initializeView();
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.TransactionItemView);

        toTextSize = typedArray.getInteger(R.styleable.TransactionItemView_toTextSize, 16);
        toTextColor = typedArray.getColor(R.styleable.TransactionItemView_toTextColor, ContextCompat.getColor(context, R.color.cyan_500));
        toValueTextSize = typedArray.getInteger(R.styleable.TransactionItemView_toValueTextSize, 16);
        toValueTextColor = typedArray.getColor(R.styleable.TransactionItemView_toValueTextColor, ContextCompat.getColor(context, R.color.black));

        fromTextSize = typedArray.getInteger(R.styleable.TransactionItemView_fromTextSize, 16);
        fromTextColor = typedArray.getColor(R.styleable.TransactionItemView_fromTextColor, ContextCompat.getColor(context, R.color.cyan_500));
        fromValueTextSize = typedArray.getInteger(R.styleable.TransactionItemView_fromValueTextSize, 16);
        fromValueTextColor = typedArray.getColor(R.styleable.TransactionItemView_fromValueTextColor, ContextCompat.getColor(context, R.color.black));

        dateTextSize = typedArray.getInteger(R.styleable.TransactionItemView_dateTextSize, 15);
        dateTextColor = typedArray.getColor(R.styleable.TransactionItemView_dateTextColor, ContextCompat.getColor(context, R.color.cyan_500));

        stateTextSize = typedArray.getInteger(R.styleable.TransactionItemView_stateTextSize, 15);
        stateTextSize = typedArray.getColor(R.styleable.TransactionItemView_stateTextColor, ContextCompat.getColor(context, R.color.black));

        noteTextSize = typedArray.getInteger(R.styleable.TransactionItemView_noteTextSize, 17);
        noteTextSize = typedArray.getColor(R.styleable.TransactionItemView_noteTextColor, ContextCompat.getColor(context, R.color.teal_400));

        amountTextSize = typedArray.getInteger(R.styleable.TransactionItemView_amountTextSize, 17);
        amountTextSize = typedArray.getColor(R.styleable.TransactionItemView_amountTextColor, ContextCompat.getColor(context, R.color.red_200));

        pendingDrawable = typedArray.getDrawable(R.styleable.TransactionItemView_pendingDrawable);
        completeDrawable = typedArray.getDrawable(R.styleable.TransactionItemView_completeDrawable);

        typedArray.recycle();
    }

    public void initializeView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView cardView = (CardView)inflater.inflate(R.layout.transaction_item, this);
        cardView.setCardElevation(ScreenUnitConverter.convertValueTo(context, 4, ScreenUnitConverter.Unit.PIXELS));
        cardView.setClickable(true);

        LinearLayout generalContainer = (LinearLayout) cardView.getChildAt(0);

        if(rippleColor == -1)
            generalContainer.setBackground(RippleDrawableGenerator.generate(context, R.color.translucent, R.color.green_a700));
        else
            generalContainer.setBackground(RippleDrawableGenerator.generate(context, R.color.translucent, rippleColor));

        RelativeLayout toFromContainerRelativeLayout = (RelativeLayout) generalContainer.getChildAt(0);
        LinearLayout toContainerLinearLayout = (LinearLayout) toFromContainerRelativeLayout.getChildAt(0);
        toTextView = (CaviarTextView) toContainerLinearLayout.getChildAt(0);
        toValueTextView = (CaviarTextView) toContainerLinearLayout.getChildAt(1);
        LinearLayout fromContainerLinearLayout = (LinearLayout) toFromContainerRelativeLayout.getChildAt(1);
        fromTextView = (CaviarTextView) fromContainerLinearLayout.getChildAt(0);
        fromValueTextView = (CaviarTextView) fromContainerLinearLayout.getChildAt(1);
        firstLine = generalContainer.getChildAt(1);
        RelativeLayout dateStatusNoteContainerRelativeLayout = (RelativeLayout) generalContainer.getChildAt(2);
        LinearLayout dateStateLinearLayout = (LinearLayout) dateStatusNoteContainerRelativeLayout.getChildAt(0);
        dateTextView = (CaviarTextView) dateStateLinearLayout.getChildAt(0);
        LinearLayout stateLinearLayout = (LinearLayout) dateStateLinearLayout.getChildAt(1);
        stateImageView = (ImageView) stateLinearLayout.getChildAt(0);
        stateTextView = (CaviarTextView) stateLinearLayout.getChildAt(1);
        noteTextView = (CaviarTextView) dateStatusNoteContainerRelativeLayout.getChildAt(1);
        secondLine = generalContainer.getChildAt(3);
        LinearLayout amountLinearLayout = (LinearLayout) generalContainer.getChildAt(4);
        signTextView = (CaviarTextView) amountLinearLayout.getChildAt(0);
        unitTextView = (CaviarTextView) amountLinearLayout.getChildAt(1);
        amountTextView = (CaviarTextView) amountLinearLayout.getChildAt(2);

        if(initializedByParsing) {
            toTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, toTextSize);
            toTextView.setTextColor(toTextColor);
            toValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, toValueTextSize);
            toValueTextView.setTextColor(toValueTextColor);

            fromTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fromTextSize);
            fromTextView.setTextColor(fromTextColor);
            fromValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fromValueTextSize);
            fromValueTextView.setTextColor(fromValueTextColor);

            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, dateTextSize);
            dateTextView.setTextColor(dateTextColor);

            stateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, stateTextSize);
            stateTextView.setTextColor(stateTextColor);

            noteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, noteTextSize);
            noteTextView.setTextColor(noteTextColor);

            amountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, amountTextSize);
            amountTextView.setTextColor(amountTextColor);
        } else {
            completeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_transaction_complete);
            pendingDrawable = ContextCompat.getDrawable(context, R.drawable.ic_transaction_pending);
        }
    }

    public TransactionItemView buildView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView cardView = (CardView)inflater.inflate(R.layout.transaction_item, this);
        cardView.setCardElevation(ScreenUnitConverter.convertValueTo(context, 4, ScreenUnitConverter.Unit.PIXELS));
        cardView.setClickable(true);

        LinearLayout generalContainer = (LinearLayout) cardView.getChildAt(0);

        if(rippleColor == -1)
            generalContainer.setBackground(RippleDrawableGenerator.generate(context, R.color.translucent, R.color.green_a700));
        else
            generalContainer.setBackground(RippleDrawableGenerator.generate(context, R.color.translucent, rippleColor));

        /*if(backgroundColor != -1)
            generalContainer.getRootView().setBackgroundColor(backgroundColor);
        if(backgroundDrawable != null)
            generalContainer.getRootView().setBackground(backgroundDrawable);*/

        RelativeLayout toFromContainerRelativeLayout = (RelativeLayout) generalContainer.getChildAt(0);
        LinearLayout toContainerLinearLayout = (LinearLayout) toFromContainerRelativeLayout.getChildAt(0);
        ViewManager.replaceView(toContainerLinearLayout.getChildAt(0), toTextView);
        ViewManager.replaceView(toContainerLinearLayout.getChildAt(1), toValueTextView);
        LinearLayout fromContainerLinearLayout = (LinearLayout) toFromContainerRelativeLayout.getChildAt(1);
        ViewManager.replaceView(fromContainerLinearLayout.getChildAt(0), fromTextView);
        ViewManager.replaceView(fromContainerLinearLayout.getChildAt(1), fromValueTextView);
        ViewManager.replaceView(generalContainer.getChildAt(1), firstLine);
        RelativeLayout dateStatusNoteContainerRelativeLayout = (RelativeLayout) generalContainer.getChildAt(2);
        LinearLayout dateStateLinearLayout = (LinearLayout) dateStatusNoteContainerRelativeLayout.getChildAt(0);
        ViewManager.replaceView(dateStateLinearLayout.getChildAt(0), dateTextView);
        LinearLayout stateLinearLayout = (LinearLayout) dateStateLinearLayout.getChildAt(1);
        ViewManager.replaceView(stateLinearLayout.getChildAt(0), stateImageView);
        ViewManager.replaceView(stateLinearLayout.getChildAt(1), stateTextView);
        ViewManager.replaceView(dateStatusNoteContainerRelativeLayout.getChildAt(1), noteTextView);
        ViewManager.replaceView(generalContainer.getChildAt(3), secondLine);
        LinearLayout amountLinearLayout = (LinearLayout) generalContainer.getChildAt(4);
        ViewManager.replaceView(amountLinearLayout.getChildAt(0), signTextView);
        ViewManager.replaceView(amountLinearLayout.getChildAt(1), unitTextView);
        ViewManager.replaceView(amountLinearLayout.getChildAt(2), amountTextView);

        return this;
    }


    enum Views {toTextView, toValueTextView, fromTextView, fromValueTextView,
        firstLine, dateTextView, stateImageView, stateTextView, noteTextView,
        secondLine, signTextView, unitTextView, amountTextView }

    /**
     * Set text
     * @param text - value, resource string
     * @param view - view hierarchy
     */
    private void setText(int text, Views view) {
        CaviarTextView textView = null;
        switch (view) {
            case toTextView: textView = toTextView; break;
            case toValueTextView: textView = toValueTextView; break;
            case fromTextView: textView = fromTextView; break;
            case fromValueTextView: textView = fromValueTextView; break;
            case dateTextView: textView = dateTextView; break;
            case stateTextView: textView = stateTextView; break;
            case noteTextView: textView = noteTextView; break;
            case signTextView: textView = signTextView; break;
            case unitTextView: textView = unitTextView; break;
            case amountTextView: textView = amountTextView; break;
        }
        if(textView != null)
            textView.setText(context.getString(text));
    }

    /**
     * Set text
     * @param text - value, string type
     * @param view - view hierarchy
     */
    private void setText(String text, Views view) {
        CaviarTextView textView = null;
        switch (view) {
            case toTextView: textView = toTextView; break;
            case toValueTextView: textView = toValueTextView; break;
            case fromTextView: textView = fromTextView; break;
            case fromValueTextView: textView = fromValueTextView; break;
            case dateTextView: textView = dateTextView; break;
            case stateTextView: textView = stateTextView; break;
            case noteTextView: textView = noteTextView; break;
            case signTextView: textView = signTextView; break;
            case unitTextView: textView = unitTextView; break;
            case amountTextView: textView = amountTextView; break;
        }
        if(textView != null)
            textView.setText(text);
    }

    /**
     * Set size
     * @param size - value
     * @param view - view hierarchy
     */
    private void setTextSize(int size, Views view) {
        CaviarTextView textView = null;
        switch (view) {
            case toTextView: textView = toTextView; break;
            case toValueTextView: textView = toValueTextView; break;
            case fromTextView: textView = fromTextView; break;
            case fromValueTextView: textView = fromValueTextView; break;
            case dateTextView: textView = dateTextView; break;
            case stateTextView: textView = stateTextView; break;
            case noteTextView: textView = noteTextView; break;
            case signTextView: textView = signTextView; break;
            case unitTextView: textView = unitTextView; break;
            case amountTextView: textView = amountTextView; break;
        }
        if(textView != null)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set color
     * @param color - value
     * @param view - view hierarchy
     */
    public void setTextColor(int color, Views view) {
        CaviarTextView textView = null;
        switch (view) {
            case toTextView: textView = toTextView; break;
            case toValueTextView: textView = toValueTextView; break;
            case fromTextView: textView = fromTextView; break;
            case fromValueTextView: textView = fromValueTextView; break;
            case dateTextView: textView = dateTextView; break;
            case stateTextView: textView = stateTextView; break;
            case noteTextView: textView = noteTextView; break;
            case signTextView: textView = signTextView; break;
            case unitTextView: textView = unitTextView; break;
            case amountTextView: textView = amountTextView; break;
        }
        if(textView != null)
            textView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setLinesColor(int color) {
        int resolvedColor = ContextCompat.getColor(context, color);
        firstLine.setBackgroundColor(resolvedColor);
        secondLine.setBackgroundColor(resolvedColor);
    }

    // Background
    public void setColor(int color) {
        backgroundColor = ContextCompat.getColor(context, color);
    }

    public void setDrawable(int drawable) {
        //backgroundDrawable = ContextCompat.getDrawable(context, drawable);
    }

    // Icons
    public void setStateImageViewDrawables(int complete, int pending) {
        completeDrawable = ContextCompat.getDrawable(context, complete);
        pendingDrawable = ContextCompat.getDrawable(context, pending);
    }

    // Ripple color
    public void setRippleColor(int color) {
        rippleColor = color;
    }


    // Getters
    public CaviarTextView getToValueTextView() {
        return toValueTextView;
    }

    public CaviarTextView getFromValueTextView() {
        return fromValueTextView;
    }

    public CaviarTextView getDateTextView() {
        return dateTextView;
    }

    public ImageView getStateImageView() {
        return stateImageView;
    }

    public CaviarTextView getStateTextView() {
        return stateTextView;
    }

    public CaviarTextView getNoteTextView() {
        return noteTextView;
    }

    public CaviarTextView getSignTextView() {
        return signTextView;
    }

    public CaviarTextView getUnitTextView() {
        return unitTextView;
    }

    public CaviarTextView getAmountTextView() {
        return amountTextView;
    }

    public Drawable getCompleteDrawable() {
        return completeDrawable;
    }

    public Drawable getPendingDrawable() {
        return pendingDrawable;
    }
}
