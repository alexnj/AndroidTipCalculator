package com.alexnj.tipquick;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class TipActivity extends Activity {
	private EditText etBillAmount;
	private RatingBar rbTipRating;
	private TextView tvFinalTip;

	private void recalculateTip() {
		RatingBar rbTipRating = (RatingBar) findViewById(R.id.rbServiceRating);
		TextView tvTipRating = (TextView) findViewById(R.id.tvTipRating);

		Resources res = getResources();
		String strBillAmount = etBillAmount.getText().toString();

		if(strBillAmount.isEmpty()) {
			tvFinalTip.setText(res.getString(R.string.tip_messaging));
			return;
		}
		
		String[] tip_rating = res.getStringArray(R.array.tip_rating);
		tvTipRating.setText( tip_rating[(int) (rbTipRating.getRating()-1)] ); 

		Float fBillAmount = Float.valueOf(strBillAmount);
		Float fTipRating = 10+(rbTipRating.getRating() - 1 )*5;
		
		String strTipMessage = res.getString( R.string.tip_strfmt,
				fTipRating, fBillAmount*fTipRating/100, fBillAmount + (fBillAmount*fTipRating/100) );
		
		tvFinalTip.setText( strTipMessage );
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        etBillAmount = (EditText)findViewById(R.id.etBillAmount);
        rbTipRating  = (RatingBar)findViewById(R.id.rbServiceRating);
        tvFinalTip   = (TextView)findViewById(R.id.tvFinalTip);
        
        TextWatcher twInputChanged = new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                recalculateTip();
			}
			
			@Override
			public void afterTextChanged(Editable s) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        };
        
        rbTipRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            	recalculateTip();
            }
        });
        
        etBillAmount.addTextChangedListener( twInputChanged ); 
    }
       
}
