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
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	private ImageView xiala;
	private Animation my_Translate;// 位移动画
	private Animation my_Rotate;// 旋转动画
	private RelativeLayout rl;

	// 登陆注册按钮
	private Button btnLogin;
	private TextView txt_reg;

	// 用户名和密码
	private EditText txUserName;
	private EditText txPassWord;
	public static String userName;
	private String password;

	// 局域网的ip，也就是手机和电脑同时的ip，或者服务器所在的公网ip
	private static String url = "http://221.214.12.81:7777/MyHomeServer/login.action?";
	private final String url_constant = "http://221.214.12.81:7777/MyHomeServer/login.action?";

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
		setContentView(R.layout.activity_login);
		// 获取控件
		initview();
		anim();
		// 设置事件监听器方法
		setListener();
		rl.startAnimation(my_Translate);// 载人时的动画
	}

	private void initview() {
		rl = (RelativeLayout) findViewById(R.id.rl);
		xiala = (ImageView) findViewById(R.id.xiala);
		// 用户名和密码
		txUserName = (EditText) findViewById(R.id.edit_user);
		txPassWord = (EditText) findViewById(R.id.edit_pwd);

		// 登陆注册按钮
		btnLogin = (Button) findViewById(R.id.btn_login);
		txt_reg = (TextView) findViewById(R.id.txt_reg);

		// 监听器

		txt_reg.setOnClickListener(this);

		// 监听器
		xiala.setOnClickListener(this);

	}

	private void anim() {
		my_Translate = AnimationUtils.loadAnimation(this, R.anim.my_translate);
		my_Rotate = AnimationUtils.loadAnimation(this, R.anim.my_rotate);
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userName = txUserName.getText().toString();
				password = txPassWord.getText().toString();
				if (checkUser()) {
					loginRemoteService(userName, password);
					// 将手机号存储到UserName类中，以便于其他Activity调用
					UserName user = new UserName();
					user.getuserName(userName);
				}

			}
		});
	}

	/**
	 * 获取Struts2 Http 登录的请求信息
	 * 
	 * @param userName
	 * @param password
	 */
	public void loginRemoteService(String userName, String password) {
		String result = null;
		if (userName.equals("12345678901") && password.equals("12345678")) {
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, com.dumbo.myhome.main.MainActivity.class);
			LoginActivity.this.startActivity(intent);
			txPassWord.setText("");
			Toast.makeText(getApplicationContext(), "您正在使用管理员账号登陆，无法连接服务器！", Toast.LENGTH_SHORT).show();
		}

		if (checkNetwork()) {
			try {

				// 创建一个HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 远程登录URL

				url = url_constant + "userName=" + userName + "&password=" + password;
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

						Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
					}
					if (result.equals("true")) {
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, com.dumbo.myhome.main.MainActivity.class);
						LoginActivity.this.startActivity(intent);
						txPassWord.setText("");
					
						

					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
				
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
			}
		}

		else {
			Toast.makeText(getApplicationContext(), "请检查您的网络连接", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xiala:

			xiala.startAnimation(my_Rotate);
			break;

		case R.id.txt_reg:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, com.dumbo.myhome.login.RegActivity.class);
			LoginActivity.this.startActivity(intent);
			break;
		default:
			break;
		}
	}

	// 监测网络
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	// 监测 用户输入的 手机号 密码是否符合要求
	private boolean checkUser() {
		boolean isTel = true; // 标记位：true-是手机号码；false-不是手机号码
		boolean isUser = true;
		boolean isPwd = true;
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

		/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */
		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(LoginActivity.this, "手机号不能为空！", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(LoginActivity.this, "请输入11位手机号码！", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(password)) {
			Toast.makeText(LoginActivity.this, "密码不能为空！", 0).show();
			isUser = false;
		} else if (!isPwd) {
			Toast.makeText(LoginActivity.this, "密码不能少于8位！", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}
