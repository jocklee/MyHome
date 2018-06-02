package com.dumbo.myhome.me;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.dumbo.myhome.R;
import com.dumbo.myhome.login.LoginActivity;
import com.dumbo.myhome.login.UserName;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class personalActivity extends Activity {
	private static String url = "http://221.214.12.81:7777/MyHomeServer/personal.action?";
	private final String url_constant = "http://221.214.12.81:7777/MyHomeServer/personal.action?";
	private final String url_constant1 = "http://221.214.12.81:7777/MyHomeServer/personalUpdate.action?";
	private TextView text_edit;
	private EditText personal_name;
	private EditText personal_tel;
	private RadioButton radio_man;
	private RadioButton radio_female;
	private RadioGroup sexs;
	private RadioButton sex;
	private static boolean flag = true;
	private boolean temp1 = true;

	UserName user = new UserName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// 设置线程的策略
		StrictMode.setThreadPolicy(
				new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork() // or
																											// .detectAll()
																											// for
																											// all
																											// detectable
																											// problems
						.penaltyLog().build());

		// 设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				// .detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);

		findView();

	}

	public void findView() {

		text_edit = (TextView) findViewById(R.id.per_edit);
		personal_name = (EditText) findViewById(R.id.personal_name);
		personal_tel = (EditText) findViewById(R.id.personal_tel);
		radio_man = (RadioButton) findViewById(R.id.radio0);
		radio_female = (RadioButton) findViewById(R.id.radio1);

		personal_tel.setText(user.userName);
		persionalCreate(user.userName);
		text_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					presonal_edit();
				}

				else
					presonal_update();
			}
		});

	}

	public void persionalCreate(String personal_tel) {
		String result = null;

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL

				url = url_constant + "personal_tel=" + personal_tel;
				Log.d("远程URL", url);
				// 创建HttpGet对象
				HttpGet request = new HttpGet(url);

				request.addHeader("Accept", "text/json");
				// 获取响应的结果
				HttpResponse response = httpclient.execute(request);
				// 获取HttpEntity
				HttpEntity entity = response.getEntity();
				// 获取响应的结果信息
				String json = EntityUtils.toString(entity, "UTF-8");
				// JSON的解析过程

				if (json != null) {
					JSONObject jsonObject = new JSONObject(json);
					result = jsonObject.get("message").toString();
					String[] results = result.split("--");
					if (result != null) {
						personal_name.setText(results[0]);
						if (results[1].equals("男")) {
							radio_man.setChecked(true);
						} else {
							radio_female.setChecked(true);
						}
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		else {
			Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
		}
	}

	// 点击保存 响应事件
	public void personalUpdate(String personal_tel, String personal_nickname, String personal_sex) {
		String result = null;

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL

				url = url_constant1 + "personal_tel=" + personal_tel + "&personal_nickname=" + personal_nickname
						+ "&personal_sex=" + personal_sex;
				Log.d("远程URL", url);
				// 创建HttpGet对象
				HttpGet request = new HttpGet(url);
				request.addHeader("Accept", "text/json");
				// 获取响应的结果
				HttpResponse response = httpclient.execute(request);
				// 获取HttpEntity
				HttpEntity entity = response.getEntity();
				// 获取响应的结果信息
				String json = EntityUtils.toString(entity, "UTF-8");
				// JSON的解析过程

				if (json != null) {
					JSONObject jsonObject = new JSONObject(json);
					result = jsonObject.get("message").toString();
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		else {
			Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
		}
	}



	public void presonal_edit() {
		personal_name.setEnabled(true);
		radio_man.setEnabled(true);
		radio_female.setEnabled(true);
		text_edit.setText("保存");
		flag = false;
	}

	public void presonal_update() {
		//boolean temp = dialog();
		dialog();
//		if (temp) {
//			personal_name.setEnabled(false);
//			radio_man.setEnabled(false);
//			radio_female.setEnabled(false);
//			text_edit.setText("编辑");
//			sexs = (RadioGroup) findViewById(R.id.radioGroup1);
//			sex = (RadioButton) findViewById(sexs.getCheckedRadioButtonId());
//			String nickName = personal_name.getText().toString();
//			String personal_sex = sex.getText().toString();
//			personalUpdate(user.userName, nickName, personal_sex);
//			persionalCreate(user.userName);
//			flag = true;
//		} else {
//			persionalCreate(user.userName);
//			personal_name.setEnabled(false);
//			radio_man.setEnabled(false);
//			radio_female.setEnabled(false);
//			text_edit.setText("编辑");
//		}
	}

	public boolean dialog() {

		new AlertDialog.Builder(personalActivity.this).setTitle("系统提示")// 设置对话框标题

				.setMessage("是否保存修改")// 设置显示的内容

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
						//temp1 = true;
						personal_name.setEnabled(false);
						radio_man.setEnabled(false);
						radio_female.setEnabled(false);
						text_edit.setText("编辑");
						sexs = (RadioGroup) findViewById(R.id.radioGroup1);
						sex = (RadioButton) findViewById(sexs.getCheckedRadioButtonId());
						String nickName = personal_name.getText().toString();
						String personal_sex = sex.getText().toString();
						personalUpdate(user.userName, nickName, personal_sex);
						persionalCreate(user.userName);
						flag = true;
					}
				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {// 添加返回按钮

					@Override

					public void onClick(DialogInterface dialog, int which) {// 响应事件

						//temp1 = false;
						persionalCreate(user.userName);
						personal_name.setEnabled(false);
						radio_man.setEnabled(false);
						radio_female.setEnabled(false);
						text_edit.setText("编辑");
						dialog.cancel();// 对话框关闭。
					}

				}).show();// 在按键响应事件中显示此对话框

		return temp1;

	}
	
	
	// 监测 网络
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
	
	
	


}
