package OverflowMenuButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.customviews.clelia.shapeviews.R;


/**
 * Created by Clelia López on 3/8/16
 */
public class OverflowMenuView
        extends LinearLayout {

    /**
     * Attributes
     */
    private LinearLayout container;
    private CircleShape firstPoint;
    private CircleShape secondPoint;
    private CircleShape thirdPoint;

    private Context context;

    private int pointsColor = -1;
    private int backgroundColor = -1;

    boolean initializedByParsing = false;


    public OverflowMenuView(Context context) {
        super(context);

        this.context = context;

        initializeViews();
    }

    public OverflowMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        initializedByParsing = true;

        parseAttributes(attrs);

        initializeViews();
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.MenuButton);

        pointsColor = typedArray.getResourceId(R.styleable.MenuButton_pointColor, R.color.magenta);
        backgroundColor = typedArray.getResourceId(R.styleable.MenuButton_pointsBackgroundColor, R.color.green_light);

        typedArray.recycle();
    }

    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.expandible_menu_button, this, true);
        container = (LinearLayout) view.getChildAt(0);
        firstPoint = (CircleShape) container.getChildAt(0);
        secondPoint = (CircleShape) container.getChildAt(1);
        thirdPoint = (CircleShape) container.getChildAt(2);

        if(initializedByParsing) {
            setPointsColor(pointsColor);
            setBackground(backgroundColor);
        }
    }

    /**
     * View Constructor
     */
    private View buildView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.expandible_menu_button, this, true);

        container = (LinearLayout) view.getChildAt(0);
        replaceView(container.getChildAt(0), firstPoint);
        replaceView(container.getChildAt(1), secondPoint);
        replaceView(container.getChildAt(2), thirdPoint);

        return container;
    }

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void remove_View(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null)
            return;
        final int index = parent.indexOfChild(currentView);
        remove_View(currentView);
        remove_View(newView);
        parent.addView(newView, index);
    }

    // Points
    public void setPointsColor(int color) {
        int resolveColor = ContextCompat.getColor(context, color);
        firstPoint.setColor(resolveColor);
        secondPoint.setColor(resolveColor);
        thirdPoint.setColor(resolveColor);
    }

    // Background
    public void setBackground(int color) {
        backgroundColor = ContextCompat.getColor(context, color);
        container.setBackgroundColor(backgroundColor);
    }

    /**
     * Builder class
     */
    public static class Builder {
        OverflowMenuView menuButton;
        Context context;

        int color = -1;
        private int backgroundColor = -1;


        /**
         * Constructor
         *
         * @param context - User class context
         */
        public Builder(Context context) {
            this.context = context;
            menuButton = new OverflowMenuView(context);
        }

        public View build() {

            // Color
            if(color != -1)
                menuButton.setPointsColor(color);

            // Background
            if(backgroundColor != -1)
                menuButton.setBackground(color);

            return menuButton.buildView(context);
        }

        // Color
        public Builder setPointColor(int color) {
            this.color = color;
            return this;
        }

        // Background
        public Builder setPointsBackgroundColor(int color) {
            backgroundColor = color;
            return this;
        }
    }
}

