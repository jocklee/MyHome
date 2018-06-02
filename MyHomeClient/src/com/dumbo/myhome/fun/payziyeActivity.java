package com.dumbo.myhome.fun;

import com.dumbo.myhome.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class payziyeActivity extends Activity {
	public TextView textView1;
	public static String title;
	private Button btn_refer3;
	private EditText pay_tel;
	private EditText pay_money;
	private String editTel;
	private String editMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		textView1 = (TextView) findViewById(R.id.textView1);
		
		pay_tel = (EditText) findViewById(R.id.pay_tel);
		pay_money = (EditText) findViewById(R.id.pay_money);

		editTel = pay_tel.getText().toString();
		editMoney = pay_money.getText().toString();
		textView1.setText(this.title);
		

	
	}

	public void titleName(String title) {
		this.title = title;
		
	}


}