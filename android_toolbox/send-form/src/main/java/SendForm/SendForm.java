package SendForm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.customviews.clelia.sendform.R;

import SendForm.Custom.CaviarEditText;
import SendForm.Custom.CaviarTextView;
import SendForm.Spinner.CaviarButton;
import SendForm.Spinner.Spinner;

/**
 * Created by Clelia López on 3/1/16
 */
public class SendForm
    extends View {

    /**
     * Attributes
     */
    LinearLayout container;
    private CaviarTextView nameTextView;
    private CaviarEditText nameEditText;
    private View expandButton;
    private CaviarTextView addressTextView;
    private CaviarEditText addressEditText;
    private CaviarTextView currencyTextView;
    private Spinner currencySpinner;
    private CaviarTextView amountTextView;
    private CaviarEditText amountEditText;
    private CaviarTextView unitTextView;
    private Spinner unitSpinner;
    private CaviarTextView noteTextView;
    private CaviarEditText noteEditText;
    private CaviarButton cancelButton;
    private CaviarButton sendButton;

    private String cancelToastMessage;
    private String sendToastMessage;
    private String addressErrorMessage;
    private String emptyErrorMessage;

    private int backgroundColor = -1;
    private Drawable backgroundDrawable = null;

    private Context context;
    protected static OnSendButtonClickListenerListener listener = null;

    private String TAG = SendForm.class.getSimpleName();


    public SendForm(Context context) {
        super(context);

        this.context = context;

        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) inflater.inflate(R.layout.send_form, null);

        nameTextView = (CaviarTextView) container.getChildAt(0);
        nameEditText = (CaviarEditText) container.getChildAt(1);
        LinearLayout addressLabelContainer = (LinearLayout) container.getChildAt(2);
        expandButton = addressLabelContainer.getChildAt(0);
        addressTextView = (CaviarTextView) addressLabelContainer.getChildAt(1);
        addressEditText = (CaviarEditText) container.getChildAt(3);
        currencyTextView = (CaviarTextView) container.getChildAt(4);
        LinearLayout currencyContainer = (LinearLayout) container.getChildAt(5);
        currencySpinner = (Spinner) currencyContainer.getChildAt(0);
        LinearLayout amountUnitContainer = (LinearLayout) container.getChildAt(6);
        LinearLayout amountContainer = (LinearLayout) amountUnitContainer.getChildAt(0);
        amountTextView = (CaviarTextView) amountContainer.getChildAt(0);
        amountEditText = (CaviarEditText) amountContainer.getChildAt(1);
        LinearLayout unitContainer = (LinearLayout) amountUnitContainer.getChildAt(1);
        unitTextView = (CaviarTextView) unitContainer.getChildAt(0);
        unitSpinner = (Spinner) unitContainer.getChildAt(1);
        noteTextView = (CaviarTextView) container.getChildAt(7);
        noteEditText = (CaviarEditText) container.getChildAt(8);
        LinearLayout sendCancelContainer = (LinearLayout) container.getChildAt(9);
        cancelButton = (CaviarButton) sendCancelContainer.getChildAt(0);
        sendButton = (CaviarButton) sendCancelContainer.getChildAt(1);

        // Setting up spinners
        currencySpinner.getSpinner().setItems(R.array.currency_array);
        unitSpinner.getSpinner().setItems(R.array.unit_array);

        // Setting up messages
        cancelToastMessage = getContext().getString(R.string.cancel_toast_label);
        sendToastMessage = getContext().getString(R.string.send_toast_label);
        addressErrorMessage = getContext().getString(R.string.address_error_message_label);
        emptyErrorMessage = getContext().getString(R.string.empty_message_label);

        // Setting up onClockListeners
        expandButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = addressEditText.getText().toString().length();
                if (addressEditText.hasFocus()) {
                    if (length > 27 && length < 34)
                        addressEditText.setError(null);
                    else if(length > 26)
                        addressEditText.setError(addressErrorMessage);
                } else if (length < 27 || length > 34)
                    addressEditText.setError(addressErrorMessage);
            }
        });

        addressEditText.setOnFocusChangeListener(
            new OnFocusChangeListener() {
                 @Override
                 public void onFocusChange(View view, boolean hasFocus) {
                     int length = addressEditText.getText().toString().length();
                     if(!hasFocus)
                         if (length < 27 || length > 34)
                             addressEditText.setError(addressErrorMessage);
                 }
            }
        );
    }

    /**
     * View Constructor
     */
    private View buildView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout container = (LinearLayout) inflater.inflate(R.layout.send_form, null);

        if(backgroundColor != -1)
            container.getRootView().setBackgroundColor(backgroundColor);
        if(backgroundDrawable != null)
            container.getRootView().setBackground(backgroundDrawable);

        replaceView(container.getChildAt(0), nameTextView);
        replaceView(container.getChildAt(1), nameEditText);
        LinearLayout addressLabelContainer = (LinearLayout) container.getChildAt(2);
        replaceView(addressLabelContainer.getChildAt(0), expandButton);
        replaceView(addressLabelContainer.getChildAt(1), addressTextView);
        replaceView(container.getChildAt(3), addressEditText);
        replaceView(container.getChildAt(4), currencyTextView);
        LinearLayout currencyContainer = (LinearLayout) container.getChildAt(5);
        replaceView(currencyContainer.getChildAt(0), currencySpinner);
        LinearLayout amountUnitContainer = (LinearLayout) container.getChildAt(6);
        LinearLayout amountContainer = (LinearLayout) amountUnitContainer.getChildAt(0);
        replaceView(amountContainer.getChildAt(0), amountTextView);
        replaceView(amountContainer.getChildAt(1), amountEditText);
        LinearLayout unitContainer = (LinearLayout) amountUnitContainer.getChildAt(1);
        replaceView(unitContainer.getChildAt(0), unitTextView);
        replaceView(unitContainer.getChildAt(1), unitSpinner);
        replaceView(container.getChildAt(7), noteTextView);
        replaceView(container.getChildAt(8), noteEditText);
        LinearLayout sendCancelContainer = (LinearLayout) container.getChildAt(9);
        replaceView(sendCancelContainer.getChildAt(0), cancelButton);
        replaceView(sendCancelContainer.getChildAt(1), sendButton);

        return container;
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

    /**
     * Called when the user clicks a button to perform an action
     *
     * @param view  indicates the view component pressed by the user
     */
    public void handleClick(View view) {
        if(view.getId() == R.id.expand_button) {
            if(addressEditText.getVisibility() == VISIBLE) {
                expandButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_chevron_closed));
                addressEditText.setVisibility(GONE);
            } else {
                expandButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_more));
                addressEditText.setVisibility(VISIBLE);
            }
        } else if(view.getId() == R.id.cancel_button) {
            Toast.makeText(context, cancelToastMessage, Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.send_button) {
            Bundle data = new Bundle();
            if(validateFields()) {
                data.putString("name", nameEditText.getText().toString().trim());
                data.putString("address", addressEditText.getText().toString().trim());
                data.putString("currency", currencySpinner.getSpinner().getText().toString().trim());
                data.putFloat("amount", Float.parseFloat(amountEditText.getText().toString().trim()));
                data.putString("unit", unitSpinner.getSpinner().getText().toString().trim());
                data.putString("note", noteEditText.getText().toString().trim());

                // Listener set up
                if(listener != null)
                    listener.onClick(data);

                Toast.makeText(context, sendToastMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, emptyErrorMessage, Toast.LENGTH_SHORT).show();
                listener.onClick(null);
            }
        }
    }

    public boolean validateFields() {
        boolean name = !nameEditText.getText().toString().equals("");
        boolean address = !addressEditText.getText().toString().equals("");
        boolean amount = !amountEditText.getText().toString().equals("");
        boolean note = !noteEditText.getText().toString().equals("");
        return (name && address && amount && note);
    }

    public interface OnSendButtonClickListenerListener {
        void onClick(Bundle data);
    }

    public static class SendFormListener {

        public void setListener(OnSendButtonClickListenerListener externalListener) {
            listener = externalListener;
        }
    }

    // Name
    public void setNameLabelTextSize(int size) {
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNameLabelTextColor(int color) {
        nameTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setNameEditorTextSize(int size) {
        nameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNameEditorTextColor(int color) {
        nameEditText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setNameEditorHintTextColor(int color) {
        nameEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }


    // Address
    public void setAddressLabelTextSize(int size) {
        addressTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setAddressLabelTextColor(int color) {
        addressTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setAddressEditorTextSize(int size) {
        addressEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setAddressEditorTextColor(int color) {
        addressEditText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setAddressEditorHintTextColor(int color) {
        addressEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }


    // Currency
    public void setCurrencyLabelTextSize(int size) {
        currencyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setCurrencyLabelTextColor(int color) {
        currencyTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setCurrencySpinnerTextSize(int size) {
        currencySpinner.getSpinner().setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        currencySpinner.getSpinner().setItemTextSize(size);
    }

    public void setCurrencySpinnerTextColor(int color) {
        currencySpinner.getSpinner().setTextColor(ContextCompat.getColor(context, color));
        currencySpinner.getSpinner().setItemTextColor(color);
    }

    public void setCurrencySpinnerItems(int array) {
        currencySpinner.getSpinner().setItems(array);
    }


    // Amount
    public void setAmountLabelTextSize(int size) {
        amountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setAmountLabelTextColor(int color) {
        amountTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setAmountEditorTextSize(int size) {
        amountEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setAmountEditorTextColor(int color) {
        amountEditText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setAmountEditorHintTextColor(int color) {
        amountEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }


    // Unit
    public void setUnitLabelTextSize(int size) {
        unitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setUnitLabelTextColor(int color) {
        unitTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setUnitSpinnerTextSize(int size) {
        unitSpinner.getSpinner().setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        unitSpinner.getSpinner().setItemTextSize(size);
    }

    public void setUnitSpinnerTextColor(int color) {
        unitSpinner.getSpinner().setTextColor(ContextCompat.getColor(context, color));
        unitSpinner.getSpinner().setItemTextColor(color);
    }

    public void setUnitSpinnerItems(int array) {
        unitSpinner.getSpinner().setItems(array);
    }


    // Note
    public void setNoteLabelTextSize(int size) {
        noteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNoteLabelTextColor(int color) {
        noteTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setNoteEditorTextSize(int size) {
        noteEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNoteEditorTextColor(int color) {
        noteEditText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setNoteEditorHintTextColor(int color) {
        noteEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }

    // Button
    public void setCancelButtonTextColor(int color) {
        cancelButton.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setCancelButtonBackgroundDrawable(int drawable) {
        cancelButton.setBackground(ContextCompat.getDrawable(context, drawable));
    }

    public void setSendButtonTextColor(int color) {
        sendButton.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setSendButtonBackgroundDrawable(int drawable) {
        sendButton.setBackground(ContextCompat.getDrawable(context, drawable));
    }


    // Messages
    public void setMessages(int AddressErrorMessage, int CancelToastMessage, int SendToastMessage) {
        if(AddressErrorMessage != -1)
            addressErrorMessage = context.getString(AddressErrorMessage);
        if(CancelToastMessage != -1)
            cancelToastMessage = context.getString(CancelToastMessage);
        if(SendToastMessage != -1)
            sendToastMessage = context.getString(SendToastMessage);
    }


    // Background
    public void setColor(int color) {
        backgroundColor = ContextCompat.getColor(context, color);
    }

    public void setDrawable(int drawable) {
        backgroundDrawable = ContextCompat.getDrawable(context, drawable);
    }


    /**
     * Builder class
     */
    public static class Builder {

        private SendForm sendForm;

        // Name
        private int nameLabelSize = -1;
        private int nameLabelColor = -1;
        private int nameEditorSize = -1;
        private int nameEditorColor = -1;
        private int nameEditorHintColor = -1;

        // Address
        private int addressLabelSize = -1;
        private int addressLabelColor = -1;
        private int addressEditorSize = -1;
        private int addressEditorColor = -1;
        private int addressEditorHintColor = -1;

        // Currency
        private int currencyLabelSize = -1;
        private int currencyLabelColor = -1;
        private int currencySpinnerTextColor = -1;
        private int currencySpinnerTextSize = -1;
        private int currencySpinnerItemsArray = -1;

        // Amount
        private int amountLabelSize = -1;
        private int amountLabelColor = -1;
        private int amountEditorSize = -1;
        private int amountEditorColor = -1;
        private int amountEditorHintColor = -1;

        // Currency
        private int unitLabelSize = -1;
        private int unitLabelColor = -1;
        private int unitSpinnerTextColor = -1;
        private int unitSpinnerTextSize = -1;
        private int unitSpinnerItemsArray = -1;

        // Note
        private int noteLabelSize = -1;
        private int noteLabelColor = -1;
        private int noteEditorSize = -1;
        private int noteEditorColor = -1;
        private int noteEditorHintColor = -1;

        // Button
        private int cancelButtonTextColor = -1;
        private int cancelButtonDrawable = -1;
        private int sendButtonTextColor = -1;
        private int sendButtonDrawable = -1;

        // Messages
        private int cancelMessage = -1;
        private int sendMessage = -1;
        private int addressMessage = -1;

        // Background
        private int backgroundColor = -1;
        private int backgroundDrawable = -1;


        public Builder(Context context) {
            sendForm = new SendForm(context);
        }

        public View build() {

            // Name
            if(nameLabelSize != -1)
                sendForm.setNameLabelTextSize(nameLabelSize);
            if(nameLabelColor != -1)
                sendForm.setNameLabelTextColor(nameLabelColor);
            if(nameEditorSize != -1)
                sendForm.setNameEditorTextSize(nameEditorSize);
            if(nameEditorColor != -1)
                sendForm.setNameEditorTextColor(nameEditorColor);
            if(nameEditorHintColor != -1)
                sendForm.setNameEditorHintTextColor(nameEditorHintColor);

            // Address
            if(addressLabelSize != -1)
                sendForm.setAddressLabelTextSize(addressLabelSize);
            if(addressLabelColor != -1)
                sendForm.setAddressLabelTextColor(addressLabelColor);
            if(addressEditorSize != -1)
                sendForm.setAddressEditorTextSize(addressEditorSize);
            if(addressEditorColor != -1)
                sendForm.setAddressEditorTextColor(addressEditorColor);
            if(addressEditorHintColor != -1)
                sendForm.setAddressEditorHintTextColor(addressEditorHintColor);

            // Currency
            if(currencyLabelSize != -1)
                sendForm.setCurrencyLabelTextSize(currencyLabelSize);
            if(currencyLabelColor != -1)
                sendForm.setCurrencyLabelTextColor(currencyLabelColor);
            if(currencySpinnerTextSize != -1)
                sendForm.setCurrencySpinnerTextSize(currencySpinnerTextSize);
            if(currencySpinnerTextColor!= -1)
                sendForm.setCurrencySpinnerTextColor(currencySpinnerTextColor);
            if(currencySpinnerItemsArray != -1)
                sendForm.setCurrencySpinnerItems(currencySpinnerItemsArray);

            // Amount
            if(amountLabelSize != -1)
                sendForm.setAmountLabelTextSize(amountLabelSize);
            if(amountLabelColor != -1)
                sendForm.setAmountLabelTextColor(amountLabelColor);
            if(amountEditorSize != -1)
                sendForm.setAmountEditorTextSize(amountEditorSize);
            if(amountEditorColor != -1)
                sendForm.setAmountEditorTextColor(amountEditorColor);
            if(amountEditorHintColor != -1)
                sendForm.setAmountEditorHintTextColor(amountEditorHintColor);

            // Unit
            if(unitLabelSize != -1)
                sendForm.setUnitLabelTextSize(unitLabelSize);
            if(unitLabelColor != -1)
                sendForm.setUnitLabelTextColor(unitLabelColor);
            if(unitSpinnerTextSize != -1)
                sendForm.setUnitSpinnerTextSize(unitSpinnerTextSize);
            if(unitSpinnerTextColor!= -1)
                sendForm.setUnitSpinnerTextColor(unitSpinnerTextColor);
            if(unitSpinnerItemsArray != -1)
                sendForm.setUnitSpinnerItems(unitSpinnerItemsArray);

            // Note
            if(noteLabelSize != -1)
                sendForm.setNoteLabelTextSize(noteLabelSize);
            if(noteLabelColor != -1)
                sendForm.setNoteLabelTextColor(noteLabelColor);
            if(noteEditorSize != -1)
                sendForm.setNoteEditorTextSize(noteEditorSize);
            if(noteEditorColor != -1)
                sendForm.setNoteEditorTextColor(noteEditorColor);
            if(noteEditorHintColor != -1)
                sendForm.setNoteEditorHintTextColor(noteEditorHintColor);

            // Button
            if(cancelButtonTextColor != -1)
                sendForm.setCancelButtonTextColor(cancelButtonTextColor);
            if(cancelButtonDrawable != -1)
                sendForm.setCancelButtonBackgroundDrawable(cancelButtonDrawable);
            if(sendButtonTextColor != -1)
                sendForm.setSendButtonTextColor(sendButtonTextColor);
            if(sendButtonDrawable != -1)
                sendForm.setSendButtonBackgroundDrawable(sendButtonDrawable);

            // Messages
            if(addressMessage != -1 || cancelMessage != -1 || sendMessage != -1)
                sendForm.setMessages(addressMessage, cancelMessage, sendMessage);

            // Background
            if(backgroundColor != -1)
                sendForm.setColor(backgroundColor);

            if(backgroundDrawable != -1)
                sendForm.setDrawable(backgroundDrawable);

            return sendForm.buildView();
        }


        // Name
        public Builder setNameLabelSize(int size) {
            nameLabelSize = size;
            return this;
        }

        public Builder setNameLabelColor(int color) {
            nameLabelColor = color;
            return this;
        }

        public Builder setNameEditorTextSize(int size) {
            nameEditorSize = size;
            return this;
        }

        public Builder setNameEditorTextColor(int color) {
            nameEditorColor = color;
            return this;
        }

        public Builder setNameEditorHintTextColor(int color) {
            nameEditorHintColor = color;
            return this;
        }


        // Address
        public Builder setAddressLabelSize(int size) {
            addressLabelSize = size;
            return this;
        }

        public Builder setAddressLabelColor(int color) {
            addressLabelColor = color;
            return this;
        }

        public Builder setAddressEditorTextSize(int size) {
            addressEditorSize = size;
            return this;
        }

        public Builder setAddressEditorTextColor(int color) {
            addressEditorColor = color;
            return this;
        }

        public Builder setAddressEditorHintTextColor(int color) {
            addressEditorHintColor = color;
            return this;
        }


        // Currency
        public Builder setCurrencyLabelSize(int size) {
            currencyLabelSize = size;
            return this;
        }

        public Builder setCurrencyLabelColor(int color) {
            currencyLabelColor = color;
            return this;
        }

        public Builder setCurrencySpinnerTextSize(int size) {
            currencySpinnerTextSize = size;
            return this;
        }

        public Builder setCurrencySpinnerTextColor(int color) {
            currencySpinnerTextColor = color;
            return this;
        }

        public Builder setCurrencySpinnerItems(int array) {
            currencySpinnerItemsArray = array;
            return this;
        }


        // Amount
        public Builder setAmountLabelSize(int size) {
            amountLabelSize = size;
            return this;
        }

        public Builder setAmountLabelColor(int color) {
            amountLabelColor = color;
            return this;
        }

        public Builder setAmountEditorTextSize(int size) {
            amountEditorSize = size;
            return this;
        }

        public Builder setAmountEditorTextColor(int color) {
            amountEditorColor = color;
            return this;
        }

        public Builder setAmountEditorHintTextColor(int color) {
            amountEditorHintColor = color;
            return this;
        }


        // Unit
        public Builder setUnitLabelSize(int size) {
            unitLabelSize = size;
            return this;
        }

        public Builder setUnitLabelColor(int color) {
            unitLabelColor = color;
            return this;
        }

        public Builder setUnitSpinnerTextSize(int size) {
            unitSpinnerTextSize = size;
            return this;
        }

        public Builder setUnitSpinnerTextColor(int color) {
            unitSpinnerTextColor = color;
            return this;
        }

        public Builder setUnitSpinnerItems(int array) {
            unitSpinnerItemsArray = array;
            return this;
        }


        // Note
        public Builder setNoteLabelSize(int size) {
            noteLabelSize = size;
            return this;
        }

        public Builder setNoteLabelColor(int color) {
            noteLabelColor = color;
            return this;
        }

        public Builder setNoteEditorTextSize(int size) {
            noteEditorSize = size;
            return this;
        }

        public Builder setNoteEditorTextColor(int color) {
            noteEditorColor = color;
            return this;
        }

        public Builder setNoteEditorHintTextColor(int color) {
            noteEditorHintColor = color;
            return this;
        }


        // Button
        public Builder setCancelButtonTextColor(int color) {
            cancelButtonTextColor = color;
            return this;
        }

        public Builder setCancelButtonBackgroundDrawable(int color) {
            cancelButtonDrawable = color;
            return this;
        }

        public Builder setSendButtonTextColor(int color) {
            sendButtonTextColor = color;
            return this;
        }

        public Builder setSendButtonBackgroundDrawable(int color) {
            sendButtonDrawable = color;
            return this;
        }


        // Message
        public Builder setCancelMessage(int message) {
            cancelMessage = message;
            return this;
        }

        public Builder setSendMessage(int message) {
            sendMessage = message;
            return this;
        }

        public Builder setAddressEditeTextMessage(int message) {
            addressMessage = message;
            return this;
        }

        // Background
        public Builder setBackgroundColor(int color) {
            backgroundColor = color;
            return this;
        }

        public Builder setBackgroundDrawable(int drawable) {
            backgroundDrawable = drawable;
            return this;
        }
    }
}