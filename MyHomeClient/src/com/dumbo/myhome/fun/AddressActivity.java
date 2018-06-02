package com.dumbo.myhome.fun;

import com.dumbo.myhome.R;
import com.dumbo.myhome.login.RegActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddressActivity extends Activity {

	private Button btn_save;
	private EditText address_sheng;
	private EditText address_shi;
	private EditText address_xian;
	private EditText address_more;
	private static String editSheng=null, editShi=null, editxian=null,editmore=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);

		btn_save = (Button) findViewById(R.id.btn_refer5);

		address_sheng = (EditText) findViewById(R.id.address_sheng);
		address_shi = (EditText) findViewById(R.id.address_shi);
		address_xian = (EditText) findViewById(R.id.address_xian);
		address_more = (EditText) findViewById(R.id.address_more);
		editSheng = address_sheng.getText().toString();
		editShi = address_sheng.getText().toString();
		editxian = address_sheng.getText().toString();
		editmore = address_sheng.getText().toString();
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!editSheng.equals(null) && !editShi.equals(null) && !editxian .equals(null)) {
				Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
				address_sheng.setEnabled(false);
				address_shi.setEnabled(false);
				address_xian.setEnabled(false);
				address_more.setEnabled(false);
				} else {
					Toast.makeText(getApplicationContext(), "请正确输入地址", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}