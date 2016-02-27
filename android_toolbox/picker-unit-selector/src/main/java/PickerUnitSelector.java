import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.customviews.fermat.pickerunitselector.R;

import picker_unit_selector.CaviarEditText;
import picker_unit_selector.CaviarTextView;
import picker_unit_selector.CaviarSpinner;


/**
 * Created by Clelia López on 2/13/16
 */
public class PickerUnitSelector
        extends View {

    /**
     * Attributes
     */
    private View lineTopView;
    private LinearLayout pickerLinearLayout;
    private CaviarTextView labelTextView;
    private CaviarEditText editorEditText;
    private View lineMiddleView;
    private FrameLayout selectorSpinner;
    private View lineBottomView;

    private static int spinnerWidth;
    private static int spinnerHeight;

    private Context context;

    /**
     * Class Constructor
     */
    public PickerUnitSelector(Context context) {
        super(context);
        this.context = context;

        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.picker_units_selector, null);

        lineTopView = view.getChildAt(0);

        pickerLinearLayout = (LinearLayout) view.getChildAt(1);
        labelTextView = (CaviarTextView) pickerLinearLayout.getChildAt(0);
        editorEditText = (CaviarEditText) pickerLinearLayout.getChildAt(1);
        lineMiddleView = pickerLinearLayout.getChildAt(2);
        selectorSpinner = (FrameLayout) pickerLinearLayout.getChildAt(3);

        lineBottomView = view.getChildAt(2);
    }

    /**
     * View Constructor
     */
    public View buildView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.picker_units_selector, null);
        replaceView(view.getChildAt(0), lineTopView);
        replaceView(view.getChildAt(1), pickerLinearLayout);
        replaceView(view.getChildAt(2), lineBottomView);

        return view;
    }

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }


    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }


    // Label
    public void setLabelText(int text) {
        labelTextView.setText(context.getText(text));
    }

    public void setLabelTextSize(int size) {
        labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setLabelTextColor(int color) {
        labelTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setLabelBackgroundColor(int color) {
        labelTextView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setLabelBackgroundDrawable(int drawable) {
        labelTextView.setBackground(ContextCompat.getDrawable(context, drawable));
    }


    // Editor
    public void setEditorHint(int hint) {
        editorEditText.setHint(context.getText(hint));
    }

    public void setEditorHintColor(int color) {
        editorEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }

    public void setEditorTextSize(int size) {
        editorEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setEditorTextColor(int color) {
        editorEditText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setEditorBackgroundColor(int color) {
        editorEditText.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setEditorBackgroundDrawable(int drawable) {
        editorEditText.setBackground(ContextCompat.getDrawable(context, drawable));
    }


    // General Picker
    public void setLinesColor(int color) {
        lineTopView.setBackgroundColor(ContextCompat.getColor(context, color));
        lineBottomView.setBackgroundColor(ContextCompat.getColor(context, color));
        lineMiddleView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setPickerBackgroundColor(int color) {
        pickerLinearLayout.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setPickerBackgroundDrawable(int drawable) {
        pickerLinearLayout.setBackground(ContextCompat.getDrawable(context, drawable));
    }


    // Setting spinner
    public void setSpinner(CaviarSpinner spinner) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.spinner, SpinnerFragment.newInstance(spinner, editorEditText));
        fragmentTransaction.commit();
    }


    public static class Builder extends CaviarSpinner.Builder {

        PickerUnitSelector pickerUnitSelector;
        CaviarSpinner spinner;
        Context context;

        // Label
        int labelText = -1;
        int labelTextSize = -1;
        int labelTextColor = -1;
        int labelBackgroundColor = -1;
        int labelBackgroundDrawable = -1;

        // Editor
        int editorHint = -1;
        int editorHintColor = -1;
        int editorTextSize = -1;
        int editorTextColor = -1;
        int editorBackgroundColor = -1;
        int editorBackgroundDrawable = -1;

        // General Picker
        int separatorLinesColor = -1;
        int pickerBackgroundColor = -1;
        int pickerBackgroundDrawable = -1;

        /**
         * Constructor
         *
         * @param context - User class context
         */
        public Builder(Context context) {
            super(context);
            this.context = context;
            pickerUnitSelector = new PickerUnitSelector(context);
            spinner = new CaviarSpinner(context);
        }

        //@Override
        public View build() {

            // Label
            if(labelText != -1)
                pickerUnitSelector.setLabelText(labelText);

            if(labelTextSize != -1)
                pickerUnitSelector.setLabelTextSize(labelTextSize);

            if(labelTextColor != -1)
                pickerUnitSelector.setLabelTextColor(labelTextColor);

            if(labelBackgroundColor != -1)
                pickerUnitSelector.setLabelBackgroundColor(labelBackgroundColor);

            if(labelBackgroundDrawable != -1)
                pickerUnitSelector.setLabelBackgroundDrawable(labelBackgroundDrawable);


            // Editor
            if(editorHint != -1)
                pickerUnitSelector.setEditorHint(editorHint);

            if(editorHintColor != -1)
                pickerUnitSelector.setEditorHintColor(editorHintColor);

            if(editorTextSize != -1)
                pickerUnitSelector.setEditorTextSize(editorTextSize);

            if(editorTextColor != -1)
                pickerUnitSelector.setEditorTextColor(editorTextColor);

            if(editorBackgroundColor != -1)
                pickerUnitSelector.setEditorBackgroundColor(editorBackgroundColor);

            if(editorBackgroundDrawable != -1)
                pickerUnitSelector.setEditorBackgroundDrawable(editorBackgroundDrawable);


            // General Picker
            if(separatorLinesColor != -1)
                pickerUnitSelector.setLinesColor(separatorLinesColor);

            if(pickerBackgroundColor != -1)
                pickerUnitSelector.setPickerBackgroundColor(pickerBackgroundColor);

            if(pickerBackgroundDrawable != -1)
                pickerUnitSelector.setPickerBackgroundDrawable(pickerBackgroundDrawable);

            spinner = getSpinner();
            pickerUnitSelector.setSpinner(spinner);

            return pickerUnitSelector.buildView(context);
        }


        public Builder setUpSpinner() {
            setSpinnerWidth(165);
            setSpinnerHeight(80);
            setSpinnerBackgroundColor(R.color.black);
            setSpinnerGravity(Gravity.CENTER);
            setSpinnerTextColor(R.color.white);
            setItemTextColor(R.color.white);
            setItemBackgroundColor(R.color.orange_dark);
            setItemGravity(Gravity.CENTER);
            setSpinnerListBackgroundColor(R.color.black_translucent);
            setNumberOfItemsOverSpinner(1);
            return this;
        }

        // Label
        public Builder setLabelText(int text) {
            labelText = text;
            return  this;
        }

        public Builder setLabelTextSize(int size) {
            labelTextSize = size;
            return  this;
        }

        public Builder setLabelTextColor(int color) {
            labelTextColor= color;
            return  this;
        }

        public Builder setLabelBackgroundColor(int color) {
            labelBackgroundColor = color;
            return  this;
        }

        public Builder setLabelBackgroundDrawable(int drawable) {
            labelBackgroundDrawable = drawable;
            return  this;
        }


        // Editor
        public Builder setEditorHint(int text) {
            editorHint = text;
            return  this;
        }

        public Builder setEditorHintTextColor(int color) {
            editorHintColor = color;
            return  this;
        }

        public Builder setEditorTextSize(int size) {
            editorTextSize = size;
            return  this;
        }

        public Builder setEditorTextColor(int color) {
            editorTextColor = color;
            return  this;
        }

        public Builder setEditorBackgroundColor(int color) {
            editorBackgroundColor = color;
            return  this;
        }

        public Builder setEditorBackgroundDrawable(int drawable) {
            editorBackgroundDrawable = drawable;
            return  this;
        }


        // General Picker
        public Builder setLinesColor(int color) {
            separatorLinesColor = color;
            return this;
        }

        public Builder setPickerBackgroundColor(int color) {
            pickerBackgroundColor = color;
            return this;
        }

        public Builder setPickerBackgroundDrawable(int drawable) {
            pickerBackgroundDrawable= drawable;
            return this;
        }
    }

}