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

		// �����̵߳Ĳ���
		StrictMode.setThreadPolicy(
				new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork() // or
																											// .detectAll()
																											// for
																											// all
																											// detectable
																											// problems
						.penaltyLog().build());

		// ����������Ĳ���
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				// .detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reg);
		initview();

	}

	private void initview() {

		// �û���������
		regUser = (EditText) findViewById(R.id.reg_user);
		regName = (EditText) findViewById(R.id.reg_name);
		regPwd = (EditText) findViewById(R.id.reg_pwd);
		regPwd2 = (EditText) findViewById(R.id.reg_pwd2);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		txt_reg = (TextView) findViewById(R.id.txt_reg);
		// �����¼�����������
		setListener();

	}

	private void setListener() {
		btn_reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ȡeditText���û���������ݣ�����ӡ
				userName = regUser.getText().toString();
				nickName = regName.getText().toString();
				password = regPwd.getText().toString();
				password2 = regPwd2.getText().toString();
				if (checkUser()) {
					loginRemoteService(userName, nickName, password, password2);
					//���ֻ��Ŵ洢��UserName���У��Ա�������Activity����
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

				// ����һ��HttpClient����
				HttpClient httpclient = new DefaultHttpClient();
				// Զ�̵�¼URL
				// ���������ԭ�е�
				// processURL=processURL+"userName="+userName+"&password="+password;
				url = url_constant + "userName=" + userName + "&nickName=" + nickName + "&password=" + password
						+ "&password2=" + password2;
				;
				Log.d("Զ��URL", url);
				// ����HttpGet����
				HttpGet request = new HttpGet(url);
				// ������Ϣ����MIMEÿ����Ӧ���͵��������ͨ�ı���html �� XML��json�����������Ӧ����Ӧ��ƥ����Դ�������ɵ�
				// MIME ����
				// ��Դ�����ɵ� MIME ����Ӧ��ƥ��һ�ֿɽ��ܵ� MIME ���͡�������ɵ� MIME ���ͺͿɽ��ܵ� MIME ���Ͳ�
				// ƥ�䣬��ô��
				// ����
				// com.sun.jersey.api.client.UniformInterfaceException�����磬���ɽ��ܵ�
				// MIME ��������Ϊ text/xml������
				// ���ɵ� MIME ��������Ϊ application/xml�������� UniformInterfaceException��
				request.addHeader("Accept", "text/json");
				// ��ȡ��Ӧ�Ľ��
				HttpResponse response = httpclient.execute(request);
				// ��ȡHttpEntity
				HttpEntity entity = response.getEntity();
				// ��ȡ��Ӧ�Ľ����Ϣ
				String json = EntityUtils.toString(entity, "UTF-8");
				// JSON�Ľ�������

				if (json != null) {
					JSONObject jsonObject = new JSONObject(json);
					result = jsonObject.get("message").toString();
					if (result.equals("false")) {
						Toast.makeText(getApplicationContext(), "ע��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}
					if (result.equals("true")) {
						Toast.makeText(getApplicationContext(), "ע��ɹ����ѵ�½��", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), "����������������", Toast.LENGTH_SHORT).show();
		}
	}

	// �������
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	// ����û��� �����Ƿ����Ҫ��
	private boolean checkUser() {
		boolean isTel = true; // ���λ��true-���ֻ����룻false-�����ֻ�����
		boolean isUser = true;
		boolean isPwd = true;
		boolean isTong = true;
		/* �ж�������û����Ƿ��ǵ绰���� */
		if (userName.length() == 11) {
			for (int i = 0; i < userName.length(); i++) {
				char c = userName.charAt(i);
				if (!Character.isDigit(c)) {
					isTel = false;
					break; // ֻҪ��һλ������Ҫ���˳�ѭ��
				}
			}
		} else {
			isTel = false;
		}
		/* �ж����������λ���Ƿ���������8λ */
		if (password.length() >= 8) {
			isPwd = true;
		} else {
			isPwd = false;
		}
		/* �ж��������������λ���Ƿ���ͬ */
		if (password2.equals(password)) {
			isTong = true;
		} else {
			isTong = false;
		}

		/* ֻ���û��������벻Ϊ�գ������û���Ϊ11λ�ֻ�����������½ */
		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(RegActivity.this, "�ֻ��Ų���Ϊ�գ�", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(RegActivity.this, "������11λ�ֻ����룡", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(nickName)) {
			Toast.makeText(RegActivity.this, "�û�������Ϊ�գ�", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(password)) {
			Toast.makeText(RegActivity.this, "���벻��Ϊ�գ�", 0).show();
			isUser = false;
		} else if (!isPwd) {
			Toast.makeText(RegActivity.this, "���벻������8λ��", 0).show();
			isUser = false;
		} else if (!isTong) {
			Toast.makeText(RegActivity.this, "������������벻һ�£�", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}