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
		case R.id.rl_know: // 知道
			clickTab1Layout();
			break;
		case R.id.rl_want_know: // 我想知道
			clickTab2Layout();
			break;
		case R.id.rl_me: // 我的
			clickTab3Layout();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击第一个tab
	 */
	private void clickTab1Layout() {
		if (knowFragment == null) {
			knowFragment = new HomeFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), knowFragment);

		// 设置底部tab变化
		knowImg.setImageResource(R.drawable.btn_know_pre);
		knowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
		iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
		iWantKnowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		meImg.setImageResource(R.drawable.btn_my_nor);
		meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
	}

	/**
	 * 点击第二个tab
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
	 * 点击第三个tab
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

		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(MainActivity.this).setTitle("ϵͳ��ʾ")// ���öԻ������

					.setMessage("ȷ���˳���")// ������ʾ������

					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť
						@Override
						public void onClick(DialogInterface dialog, int which) {// ȷ����ť����Ӧ�¼�
							finish();
						}
					}).setNegativeButton("����", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

						@Override

						public void onClick(DialogInterface dialog, int which) {// ��Ӧ�¼�

							
							dialog.cancel();// �Ի���رա�

						}

					}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���

		}
		return false;
	}

}
