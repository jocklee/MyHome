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
		setContentView(R.layout.activity_repair);
		initview();

	}

	private void initview() {

		// �û���������
		repair_tel = (EditText) findViewById(R.id.repair_tel);
		repair_name = (EditText) findViewById(R.id.repair_name);
		repair_address = (EditText) findViewById(R.id.repair_address);
		repair_why = (EditText) findViewById(R.id.repair_why);
		btn_refer = (Button) findViewById(R.id.btn_refer);
		// �����¼�����������anzhuo
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

				// ����һ��HttpClient����
				HttpClient httpclient = new DefaultHttpClient();
				// Զ�̵�¼URL

				url = url_constant + "editTel=" + editTel + "&editName=" + editName + "&editAddress=" + editAddress
						+ "&editWhy=" + editWhy;
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

						Toast.makeText(getApplicationContext(), "�ύʧ��", Toast.LENGTH_SHORT).show();
					}
					if (result.equals("true")) {
						Toast.makeText(getApplicationContext(), "�ύ�ɹ�", Toast.LENGTH_SHORT).show();
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

	// ����û�������ֻ��Ż������Ƿ����Ҫ��
	private boolean checkUser() {
		boolean isTel = true; // ���λ��true-���ֻ����룻false-�����ֻ�����
		boolean isUser = true;

		/* �ж�������û����Ƿ��ǵ绰���� */
		if (editTel.length() == 11) {
			for (int i = 0; i < editTel.length(); i++) {
				char c = editTel.charAt(i);
				if (!Character.isDigit(c)) {
					isTel = false;
					break; // ֻҪ��һλ������Ҫ���˳�ѭ��
				}
			}
		} else {
			isTel = false;
		}

		/* ֻ���ֻ��Ų�Ϊ�գ������ֻ���Ϊ11λ�������ύ */
		if (TextUtils.isEmpty(editTel)) {
			Toast.makeText(RepairActivity.this, "�ֻ��Ų���Ϊ�գ�", 0).show();
			isUser = false;
		} else if (!isTel) {
			Toast.makeText(RepairActivity.this, "������11λ�ֻ����룡", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editName)) {
			Toast.makeText(RepairActivity.this, "��������Ϊ�գ�", 0).show();
			isUser = false;
		} else if (TextUtils.isEmpty(editAddress)) {
			Toast.makeText(RepairActivity.this, "��ַ����Ϊ�գ�", 0).show();
			isUser = false;
		} else {
			isUser = true;
		}
		return isUser;

	}

}