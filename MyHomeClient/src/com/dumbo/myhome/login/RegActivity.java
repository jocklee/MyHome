package com.dumbo.myhome.login;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends Activity {

	private static String url = "http://221.214.12.81:7777/MyHomeServer/reg.action?";
	private final String url_constant = "http://221.214.12.81:7777/MyHomeServer/reg.action?";
	private EditText regUser;
	private EditText regName;
	private EditText regPwd;
	private EditText regPwd2;
	private Button btn_reg;
	private TextView txt_reg;
	private String userName;
	private String nickName;
	private String password;
	private String password2;

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reg);
		initview();

	}

	private void initview() {

		// 用户名和密码
		regUser = (EditText) findViewById(R.id.reg_user);
		regName = (EditText) findViewById(R.id.reg_name);
		regPwd = (EditText) findViewById(R.id.reg_pwd);
		regPwd2 = (EditText) findViewById(R.id.reg_pwd2);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		txt_reg = (TextView) findViewById(R.id.txt_reg);
		// 设置事件监听器方法
		setListener();

	}

	private void setListener() {
		btn_reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取editText中用户输入的数据，并打印
				userName = regUser.getText().toString();
				nickName = regName.getText().toString();
				password = regPwd.getText().toString();
				password2 = regPwd2.getText().toString();
				if (checkUser()) {
					loginRemoteService(userName, nickName, password, password2);
					//将手机号存储到UserName类中，以便于其他Activity调用
					UserName user=new UserName();
					user.getuserName(userName);
				}
			}
		});

		txt_reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegActivity.this, com.dumbo.myhome.login.LoginActivity.class);
				RegActivity.this.startActivity(intent);
			}
		});

	}

	public void loginRemoteService(String userName, String nickName, String password, String password2) {
		String result = null;

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL
				// 下面这句是原有的
				// processURL=processURL+"userName="+userName+"&password="+password;
				url = url_constant + "userName=" + userName + "&nickName=" + nickName + "&password=" + password
						+ "&password2=" + password2;
				;
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
						Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
					}
					if (result.equals("true")) {
						Toast.makeText(getApplicationContext(), "注册成功，已登陆！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(RegActivity.this, com.dumbo.myhome.main.MainActivity.class);
						RegActivity.this.startActivity(intent);
						Toast.makeText(getApplicationContext(),nickName, Toast.LENGTH_SHORT).show();
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

	// 检测用户名 密码是否符合要求
	private boolean checkUser() {
		boolean isTel = true; // 标记位：true-是手机号码；false-不是手机号码
		boolean isUser = true;
		boolean isPwd = true;
		boolean isTong = true;
		/* 判断输入的用户名是否是电话号码 */
		if (userName.length() == 11) {
			for (int i = 0; i < userName.length(); i++) {
				char c = userName.charAt(i);
				if (!Character.isDigit(c)) {
					isTel = false;
					break; // 只要有一位不符合要求退出循环
				}
			}
		} else {
			isTel = false;
		}
		/* 判断输入的密码位数是否满足最少8位 */
		if (password.length() >= 8) {
			isPwd = true;
		} else {
			isPwd = false;
		}
		/* 判断两次输入的密码位数是否相同 */
		if (password2.equals(password)) {
			isTong = true;
		} else {
			isTong = false;
		}

		/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */
		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(RegActivity.this, "手机号不能为空！", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(RegActivity.this, "请输入11位手机号码！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(nickName)) {
			Toast.makeText(RegActivity.this, "用户名不能为空！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(password)) {
			Toast.makeText(RegActivity.this, "密码不能为空！", 0).show();
			isUser = false;
		} else if (!isPwd) {
			Toast.makeText(RegActivity.this, "密码不能少于8位！", 0).show();
			isUser = false;
		} else if (!isTong) {
			Toast.makeText(RegActivity.this, "两次输入的密码不一致！", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}