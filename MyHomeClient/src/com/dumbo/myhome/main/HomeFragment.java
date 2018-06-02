package com.dumbo.myhome.main;

import java.util.ArrayList;

import com.dumbo.myhome.R;
import com.dumbo.myhome.gridview.MyGridView;
import com.dumbo.myhome.view_img.AdvInfo;
import com.dumbo.myhome.view_img.ViewPagerAdapter;
import com.dumbo.myhome.gridview.MyGridAdapter;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * 
 * @author klee
 *
 */
@SuppressLint("HandlerLeak")
public class HomeFragment extends Fragment {
	private MyGridView gridview;
	public View view;
	private ViewPager mViewPager;
	private TextView mIntroTv;
	private LinearLayout mDotLayout;
	private ViewPagerAdapter mViewPagerAdapter;
	/**
	 * handler����ʱ����
	 */
	private Handler mMyHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			mMyHandler.sendEmptyMessageDelayed(0, 3000);
		}
	};
	private ArrayList<AdvInfo> list = new ArrayList<AdvInfo>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_fragment1, container, false);
		initView();
		setLinstener();
		initData();

		return view;
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {

		gridview = (MyGridView) view.findViewById(R.id.gridview);

		gridview.setAdapter(new MyGridAdapter(getActivity()));

		mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		mIntroTv = (TextView) view.findViewById(R.id.tv_intro);
		mDotLayout = (LinearLayout) view.findViewById(R.id.dot_layout);
	}

	/**
	 * �����¼�����
	 */
	private void setLinstener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				Log.e("Activity", "position: " + position);
				updateIntroAndDot();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (id == 0) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.NewsActivity.class);
					startActivity(intent);

				}
				if (id == 1) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.HuodongActivity.class);
					startActivity(intent);

				}
				if (id == 2) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.FuningActivity.class);
					startActivity(intent);

				}
				if (id == 3) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.RepairActivity.class);
					startActivity(intent);

				}
				if (id == 4) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.PayActivity.class);
					startActivity(intent);

				}
				if (id == 5) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.JZActivity.class);
					startActivity(intent);

				}
				if (id == 6) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.contactActivity.class);
					startActivity(intent);

				}
				if (id == 7) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.idearActivity.class);
					startActivity(intent);

				}
				if (id == 8) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), com.dumbo.myhome.fun.FuningActivity.class);
					startActivity(intent);

				}

				// Intent intent=new Intent(this,SimpleActivity.class);
				// intent.putExtra...//SimpleActivity�ǹ��õģ����ܲ�������ʾ��Ӧ��item����
				// startActivity(intent);
			}
		});

	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		list.add(new AdvInfo(R.drawable.img_fengjing, "�羰"));
		list.add(new AdvInfo(R.drawable.im_huodong, "�"));
		list.add(new AdvInfo(R.drawable.im_shenghuojiaofei, "����ɷ�"));
		list.add(new AdvInfo(R.drawable.img_shiwuzhaoling, "ʧ������"));
		list.add(new AdvInfo(R.drawable.img_wuyefuwu, "��ҵ����"));

		initDots();
		mViewPagerAdapter = new ViewPagerAdapter(list, this.getActivity());
		mViewPager.setAdapter(mViewPagerAdapter);

		// Ĭ����1�ڶ�
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % list.size()));
		// 3�붨ʱ
		mMyHandler.sendEmptyMessageDelayed(0, 3000);
		updateIntroAndDot();
	}

	/**
	 * ��ʼ��dot
	 */
	private void initDots() {
		for (int i = 0; i < list.size(); i++) {
			View view = new View(this.getActivity());
			LayoutParams params = new LayoutParams(8, 8);
			if (i != 0) {// ��һ���㲻��Ҫ��߾�
				params.leftMargin = 5;
			}
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.selector_dot);
			mDotLayout.addView(view);
		}
	}

	/**
	 * �����ı�
	 */
	private void updateIntroAndDot() {
		int currentPage = mViewPager.getCurrentItem() % list.size();
		mIntroTv.setText(list.get(currentPage).getIntro());

		for (int i = 0; i < mDotLayout.getChildCount(); i++) {
			mDotLayout.getChildAt(i).setEnabled(i == currentPage);// ����setEnabledΪtrue�Ļ�
																	// ��ѡ��������ͻ��Ӧ��ʹ�ð�ɫ��ɫ
		}
	}

}
