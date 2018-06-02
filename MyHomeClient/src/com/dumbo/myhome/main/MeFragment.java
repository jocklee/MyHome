package com.dumbo.myhome.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import com.dumbo.myhome.R;

/**
 * 
 * @author klee
 *
 */
public class MeFragment extends ListFragment {
	private String[] values = new String[] { "��������", "�ջ���ַ", "�����޸�", };

	private int[] images = new int[] { R.drawable.gerenziliao, R.drawable.shouhuodizhi, R.drawable.mimaxiugai };

	private int[] point = new int[] { R.drawable.triangle, R.drawable.triangle, R.drawable.triangle,
			R.drawable.triangle, R.drawable.triangle, R.drawable.triangle };

	private Button exit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment3, container, false);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < values.length; i++) {
			HashMap<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("values", values[i]);
			listItem.put("images", images[i]);
			listItem.put("point", point[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems, R.layout.list_item,
				new String[] { "values", "images", "point" },
				new int[] { R.id.list_title, R.id.list_img, R.id.list_point });
		setListAdapter(adapter);
		findView();

	}

	public void findView() {

		exit = (Button) getActivity().findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity()).setTitle("ϵͳ��ʾ")// ���öԻ������

				.setMessage("ȷ���˳���")// ������ʾ������

				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť
					@Override
					public void onClick(DialogInterface dialog, int which) {// ȷ����ť����Ӧ�¼�
						System.exit(0);
					}
				}).setNegativeButton("����", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

					@Override

					public void onClick(DialogInterface dialog, int which) {// ��Ӧ�¼�

						
						dialog.cancel();// �Ի���رա�

					}

				}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���

			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (position == 0) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), com.dumbo.myhome.me.personalActivity.class);
			startActivity(intent);
		}
		 if(position==1)
		 {
		 Intent intent = new Intent();
		 intent.setClass(getActivity(),com.dumbo.myhome.fun.AddressActivity.class);
		 startActivity(intent);
		 }
		if (position == 2) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), com.dumbo.myhome.fun.PwdActivity.class);
			startActivity(intent);
		}

	}

}
