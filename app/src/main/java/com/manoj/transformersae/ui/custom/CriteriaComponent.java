package com.manoj.transformersae.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.manoj.transformersae.R;

/**
 * Created by Manoj Vemuru on 2018-09-20.
 */
public class CriteriaComponent extends CardView {
    private LayoutInflater mInflater;
    private TextView mCriteriaName;
    private TextView mCriteriaValue;
    private SeekBar mCriteriaSeekBar;

    public CriteriaComponent(@NonNull Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public CriteriaComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public CriteriaComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View view = mInflater.inflate(R.layout.criteria_view_component, this, true);
        mCriteriaName = view.findViewById(R.id.criteria_name);
        mCriteriaValue = view.findViewById(R.id.criteria_value);
        mCriteriaSeekBar = view.findViewById(R.id.criteria_seekBar);

        mCriteriaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCriteriaValue.setText("" +i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setValues(@NonNull String name, Integer value, boolean hideSeekBar) {
        mCriteriaName.setText(name);
        mCriteriaValue.setText(value.toString());
        mCriteriaSeekBar.setProgress(value);
        mCriteriaSeekBar.setVisibility(hideSeekBar ? View.INVISIBLE : View.VISIBLE);
    }

    public int getCriteriaValue() {
        return Integer.parseInt(mCriteriaValue.getText().toString());
    }
}
