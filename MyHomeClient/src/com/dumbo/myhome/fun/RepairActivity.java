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

public class RepairActivity extends Activity {

	private static String url = "http://221.214.12.81:7777/MyHomeServer/repair.action?";
	private final String url_constant = "http://221.214.12.81:7777/MyHomeServer/repair.action?";
	private EditText repair_tel;
	private EditText repair_name;
	private EditText repair_address;
	private EditText repair_why;
	private Button btn_refer;
	private String editTel;
	private String editName;
	private String editAddress;
	private String editWhy;

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
		setContentView(R.layout.activity_repair);
		initview();

	}

	private void initview() {

		// 用户名和密码
		repair_tel = (EditText) findViewById(R.id.repair_tel);
		repair_name = (EditText) findViewById(R.id.repair_name);
		repair_address = (EditText) findViewById(R.id.repair_address);
		repair_why = (EditText) findViewById(R.id.repair_why);
		btn_refer = (Button) findViewById(R.id.btn_refer);
		// 设置事件监听器方法anzhuo
		setListener();

	}

	private void setListener() {
		btn_refer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTel = repair_tel.getText().toString();
				editName = repair_name.getText().toString();
				editAddress = repair_address.getText().toString();
				editWhy = repair_why.getText().toString();

				if (checkUser()) {
					repairRemoteService(editTel, editName, editAddress, editWhy);
				}
			}
		});
	}

	public void repairRemoteService(String editTel, String editName, String editAddress, String editWhy) {
		String result = null;

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL

				url = url_constant + "editTel=" + editTel + "&editName=" + editName + "&editAddress=" + editAddress
						+ "&editWhy=" + editWhy;
				Log.d("远程URL", url);
				// 创建HttpGet对象
				HttpGet request = new HttpGet(url);
				// 请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的
				// MIME 类型
				// 资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不
				// 匹配，那么将
				// 生成
				// com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的
				// MIME 类型设置为 text/xml，而将
				// 生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
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
						intent.setClass(RepairActivity.this, com.dumbo.myhome.main.MainActivity.class);
						RepairActivity.this.startActivity(intent);
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

	// 检测用户输入的手机号或密码是否符合要求
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

		/* 只有手机号不为空，并且手机号为11位才允许提交 */
		if (TextUtils.isEmpty(editTel)) {
			Toast.makeText(RepairActivity.this, "手机号不能为空！", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(RepairActivity.this, "请输入11位手机号码！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editName)) {
			Toast.makeText(RepairActivity.this, "姓名不能为空！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editAddress)) {
			Toast.makeText(RepairActivity.this, "地址不能为空！", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}