package com.dumbo.myhome.fun;

import com.dumbo.myhome.R;
import com.dumbo.myhome.login.UserName;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class contactActivity extends Activity {
	private Button btn_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		btn_contact = (Button) findViewById(R.id.btn_contact);
		setListener();
	}
	private void setListener() {
		btn_contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "抱歉！该功能正在开发中...", Toast.LENGTH_SHORT).show();
			}
		});
	}
}