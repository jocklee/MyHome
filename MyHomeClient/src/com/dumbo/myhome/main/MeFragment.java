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
	private String[] values = new String[] { "个人资料", "收货地址", "密码修改", };

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
				new AlertDialog.Builder(getActivity()).setTitle("系统提示")// 设置对话框标题

				.setMessage("确定退出吗")// 设置显示的内容

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
						System.exit(0);
					}
				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {// 添加返回按钮

					@Override

					public void onClick(DialogInterface dialog, int which) {// 响应事件

						
						dialog.cancel();// 对话框关闭。

					}

				}).show();// 在按键响应事件中显示此对话框

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
