package com.dumbo.myhome.main;

import com.dumbo.myhome.R;
import com.dumbo.myhome.me.personalActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * 
 * @author klee
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout knowLayout, iWantKnowLayout, meLayout;

	private Fragment knowFragment, iWantKnowFragment, meFragment, currentFragment;

	private ImageView knowImg, iWantKnowImg, meImg;

	private TextView knowTv, iWantKnowTv, meTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initTab();
	}

	private void initUI() {
		knowLayout = (RelativeLayout) findViewById(R.id.rl_know);
		iWantKnowLayout = (RelativeLayout) findViewById(R.id.rl_want_know);
		meLayout = (RelativeLayout) findViewById(R.id.rl_me);
		knowLayout.setOnClickListener(this);
		iWantKnowLayout.setOnClickListener(this);
		meLayout.setOnClickListener(this);

		knowImg = (ImageView) findViewById(R.id.iv_know);
		iWantKnowImg = (ImageView) findViewById(R.id.iv_i_want_know);
		meImg = (ImageView) findViewById(R.id.iv_me);
		knowTv = (TextView) findViewById(R.id.tv_know);
		iWantKnowTv = (TextView) findViewById(R.id.tv_i_want_know);
		meTv = (TextView) findViewById(R.id.tv_me);

	}

	private void initTab() {
		if (knowFragment == null) {
			knowFragment = new HomeFragment();
		}

		if (!knowFragment.isAdded()) {

			getSupportFragmentManager().beginTransaction().add(R.id.content_layout, knowFragment).commit();

			currentFragment = knowFragment;

			knowImg.setImageResource(R.drawable.btn_know_pre);
			knowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
			iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
			iWantKnowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
			meImg.setImageResource(R.drawable.btn_my_nor);
			meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_know: // 鐭ラ亾
			clickTab1Layout();
			break;
		case R.id.rl_want_know: // 鎴戞兂鐭ラ亾
			clickTab2Layout();
			break;
		case R.id.rl_me: // 鎴戠殑
			clickTab3Layout();
			break;
		default:
			break;
		}
	}

	/**
	 * 鐐瑰嚮绗竴涓猼ab
	 */
	private void clickTab1Layout() {
		if (knowFragment == null) {
			knowFragment = new HomeFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), knowFragment);

		// 璁剧疆搴曢儴tab鍙樺寲
		knowImg.setImageResource(R.drawable.btn_know_pre);
		knowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
		iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
		iWantKnowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		meImg.setImageResource(R.drawable.btn_my_nor);
		meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
	}

	/**
	 * 鐐瑰嚮绗簩涓猼ab
	 */
	private void clickTab2Layout() {
		if (iWantKnowFragment == null) {
			iWantKnowFragment = new IWantKnowFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), iWantKnowFragment);

		knowImg.setImageResource(R.drawable.btn_know_nor);
		knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iWantKnowImg.setImageResource(R.drawable.btn_wantknow_pre);
		iWantKnowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
		meImg.setImageResource(R.drawable.btn_my_nor);
		meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

	}

	/**
	 * 鐐瑰嚮绗笁涓猼ab
	 */
	private void clickTab3Layout() {
		if (meFragment == null) {
			meFragment = new MeFragment();
		}

		addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
		knowImg.setImageResource(R.drawable.btn_know_nor);
		knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
		iWantKnowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		meImg.setImageResource(R.drawable.btn_my_pre);
		meTv.setTextColor(getResources().getColor(R.color.bottomtab_press));

	}

	/**
	 * 
	 * 
	 * @param transaction
	 * @param fragment
	 */
	private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
		if (currentFragment == fragment)
			return;

		if (!fragment.isAdded()) { // 濡傛灉褰撳墠fragment鏈娣诲姞锛屽垯娣诲姞鍒癋ragment绠＄悊鍣ㄤ腑
			transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")// 设置对话框标题

					.setMessage("确定退出吗")// 设置显示的内容

					.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
							finish();
						}
					}).setNegativeButton("返回", new DialogInterface.OnClickListener() {// 添加返回按钮

						@Override

						public void onClick(DialogInterface dialog, int which) {// 响应事件

							
							dialog.cancel();// 对话框关闭。

						}

					}).show();// 在按键响应事件中显示此对话框

		}
		return false;
	}

}
