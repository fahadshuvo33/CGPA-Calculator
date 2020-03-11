package com.example.cgpacalculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cgpacalculator.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private ActivityMainBinding mBinding;
    private String beforeText;
    private int cheakNull = 0;
    private EditText[] editTexts = new EditText[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        mBinding = DataBindingUtil.setContentView ( this, R.layout.activity_main );

        allId ( AppConstraint.VALIDATION );
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeText = s.toString ();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString ();
        if (text.isEmpty ()) {
            text = "";
        } else if (text.equals ( "." )) {
            s.replace ( 0, s.length (), beforeText );
            showToast ();
        } else if (cheakValid ( Float.parseFloat ( text ) )) {
            allId ( AppConstraint.FIX_BG );
        } else {
            if (!cheakValid ( Float.parseFloat ( text ) )) {
                s.replace ( 0, s.length (), beforeText );
                showToast ();

            } else {

            }
        }
    }

    public boolean cheakValid(float f) {
        if (2 <= f && f <= 4) {
            return true;
        } else {
            return false;
        }
    }

    public void clearAll(View view) {
        allId ( AppConstraint.CLEAR );
        mBinding.resultTextView.setText ( "" );
        mBinding.resultTextView.setVisibility ( View.GONE );
    }

    public void Calculate(View view) {
        ArrayList<Double> values = getValues ();
        Log.d ( "chk", "total:" + values.size () );
        if (values.size () > 0) {
            double sum = 0;
            int sum_of_v=0;
            for (int i = 0; i < values.size (); i++) {
                int v = 0;
                if (i >= 0 && i <= 2) {
                    v = 5;
                } else if (i > 2 && i < 5) {
                    v = 15;
                } else if (i == 5) {
                    v = 20;
                } else if (i == 6) {
                    v = 25;
                } else if (i == 7) {
                    v = 10;
                }
                sum_of_v=v+sum_of_v;
                sum = sum + (values.get ( i ) * v);

            }
            double result = sum / sum_of_v;
            String test = String.format ( "%.02f", result );
            allId ( AppConstraint.CHANGEBG );
            mBinding.resultTextView.setVisibility ( View.VISIBLE );
            mBinding.resultTextView.setText ( "Your Average CGPA is :  " + test );
            InputMethodManager imm = (InputMethodManager) getSystemService ( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow ( view.getWindowToken (), 0 );
        }


    }

    private ArrayList<Double> getValues() {
        ArrayList<Double> values = new ArrayList<> ();
        if (!mBinding.semester1.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester1.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester2.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester2.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester3.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester3.getText ().toString ().trim () );
            values.add ( d );
        }

        if (!mBinding.semester4.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester4.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester5.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester5.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester6.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester6.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester7.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester7.getText ().toString ().trim () );
            values.add ( d );
        }
        if (!mBinding.semester8.getText ().toString ().trim ().isEmpty ()) {
            Double d = Double.parseDouble ( mBinding.semester8.getText ().toString ().trim () );
            values.add ( d );
        }
        return values;

    }

    public void allId(String change) {
        int i;
        for (i = 1; i <= 8; i++) {
            String idName = "semester" + i;
            int resId = getResources ().getIdentifier ( idName, "id", getPackageName () );
            editTexts[i] = findViewById ( resId );

            if (change.contains ( AppConstraint.CLEAR )) {
                (editTexts[i]).setText ( "" );
                editTexts[i].setBackgroundResource ( R.drawable.result_shape );
            } else if (change.equals ( AppConstraint.CHEAK )) {
                if ((editTexts[i]).getText ().toString ().isEmpty ()) {
                    cheakNull = 0;
                } else {
                    cheakNull = 1;
                }
            } else if (change.equals ( AppConstraint.CHANGEBG )) {
                if (editTexts[i].getText ().toString ().isEmpty ()||editTexts[i].getText ().equals ( "" )) {
                    editTexts[i].setBackgroundResource ( R.drawable.edit_bg );
                } else {
                    editTexts[i].setBackgroundResource ( R.drawable.result_shape );
                }
            } else if (change.equals ( AppConstraint.VALIDATION )) {
                editTexts[i].addTextChangedListener ( this );
            } else if (change.equals ( AppConstraint.FIX_BG )) {
                if (editTexts[i].getBackground ().getConstantState ().equals (
                        getResources ().getDrawable ( R.drawable.edit_bg ).getConstantState () ) && !editTexts[i].getText ().toString ().isEmpty ()) {
                    editTexts[i].setBackgroundResource ( R.drawable.result_shape );
                }
            }
        }
    }
    public void showToast(){
        Toast toast = new Toast ( getApplicationContext () );
//        toast.setGravity ( Gravity.CENTER,0,0 );

        TextView textView= new TextView ( getApplicationContext () );
        textView.setBackgroundResource ( R.drawable.toast_bg);
        textView.setTextColor ( Color.BLACK );
        textView.setText ( "Please enter valid CGPA !" );
        textView.setPadding ( 16,8,16,8 );
        toast.setView ( textView );
        toast.show ();
    }

}
