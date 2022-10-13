package com.o2.comerciosolidario.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.BindingAdapter;

import com.o2.comerciosolidario.R;

public class MultiSpinner extends androidx.appcompat.widget.AppCompatSpinner {
    private CharSequence[] entries;
    private boolean[] selected;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiSpinner);
        entries = a.getTextArray(R.styleable.MultiSpinner_android_entries);
        if(entries != null){
            selected = new boolean[entries.length];
        }

        a.recycle();
    }

    private DialogInterface.OnMultiChoiceClickListener mOnMultiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            selected[i] = b;
        }
    };

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which){
            StringBuffer spinnerBuffer = new StringBuffer();
            for (int i = 0; i < entries.length; i++) {
                if(selected[i]){
                    spinnerBuffer.append(entries[i]);
                    spinnerBuffer.append(", ");
                }
            }

            if(spinnerBuffer.length() > 2) {
                spinnerBuffer.setLength(spinnerBuffer.length() - 2);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,
                    new String[] { spinnerBuffer.toString()});

            setAdapter(adapter);

            if(listener != null) {
                listener.onItemsSelected(selected);
            }

            dialog.dismiss();
        }
    };

    @Override
    public boolean performClick(){
        new AlertDialog.Builder(getContext())
        .setMultiChoiceItems(entries, selected, mOnMultiChoiceClickListener)
        .setPositiveButton(android.R.string.ok, mOnClickListener)
        .show();

        return true;
    }

    public void setMultiSpinnerListener(MultiSpinnerListener listener){
        this.listener = listener;
    }

    public interface MultiSpinnerListener{
        public void onItemsSelected(boolean[] selected);
    }
}
