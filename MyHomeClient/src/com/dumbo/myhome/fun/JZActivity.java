package com.dumbo.myhome.fun;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JZActivity extends Activity {

	private static String url = "http://221.214.12.81:7777/MyHomeServer/jiazheng.action?";
	private final String url_constant = "http://221.214.12.81:7777/MyHomeServer/jiazheng.action?";
	private EditText jz_name;
	private EditText jz_tel;
	private EditText jz_address;
	private EditText jz_kind;
	private EditText jz_work;
	private EditText jz_money;
	private Button btn_refer1;
	private String editTel;
	private String editName;
	private String editWork;
	private String editKind;
	private String editMoney;
	private String editAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());

		// 设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()

				.penaltyLog().penaltyDeath().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiazheng);
		initview();

	}

	private void initview() {

		// 用户名和密码
		jz_name = (EditText) findViewById(R.id.jz_name);
		jz_tel = (EditText) findViewById(R.id.jz_tel);
		jz_kind = (EditText) findViewById(R.id.jz_kind);
		jz_address = (EditText) findViewById(R.id.jz_address);
		jz_work = (EditText) findViewById(R.id.jz_work);
		jz_money = (EditText) findViewById(R.id.jz_money);
		btn_refer1 = (Button) findViewById(R.id.btn_refer1);

		// 设置事件监听器方法
		setListener();

	}

	private void setListener() {
		btn_refer1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// 获取editText中用户输入的数据
				editTel = jz_tel.getText().toString();
				editName = jz_name.getText().toString();
				editAddress = jz_address.getText().toString();
				editKind = jz_kind.getText().toString();
				editWork = jz_work.getText().toString();
				editMoney = jz_money.getText().toString();
				if (checkUser())
					jiazhengRemoteService(editTel, editName, editAddress, editKind, editWork, editMoney);
			}
		});
	}

	public void jiazhengRemoteService(String editTel, String editName, String editAddress, String editKind,
			String editWork, String editMoney) {
		String result = null;

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL

				url = url_constant + "editTel=" + editTel + "&editName=" + editName + "&editAddress=" + editAddress
						+ "&editKind=" + editKind + "&editWork=" + editWork + "&editMoney=" + editMoney;
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
					if (result.equals("false")) {

						Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
					}
					if (result.equals("true")) {
						Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(JZActivity.this, com.dumbo.myhome.main.MainActivity.class);
						JZActivity.this.startActivity(intent);
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

	// 检测网络
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	// 监测 用户输入的表单是否正确

	private boolean checkUser() {
		boolean isTel = true; // 标记位：true-是手机号码；false-不是手机号码
		boolean isUser = true;

		/* 判断输入的用户名是否是电话号码 */
		if (editTel.length() == 11) {
			for (int i = 0; i < editTel.length(); i++) {
				char c = editTel.charAt(i);
				if (!Character.isDigit(c)) {
					isTel = false;
					break; // 只要有一位不符合要求退出循环
				}
			}
		} else {
			isTel = false;
		}

		/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */
		if (TextUtils.isEmpty(editName)) {
			Toast.makeText(JZActivity.this, "姓名不能为空！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editTel)) {
			Toast.makeText(JZActivity.this, "手机号不能为空！", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(JZActivity.this, "请输入11位手机号码！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editAddress)) {
			Toast.makeText(JZActivity.this, "地址不能为空！", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}