package com.dumbo.myhome.view_img;

import java.util.List;

import com.dumbo.myhome.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ViewPagerAdapter extends PagerAdapter {

	private List<AdvInfo> mList;
	private Context mContext;

	public ViewPagerAdapter(List<AdvInfo> list, Context context) {
		this.mContext = context;
		this.mList = list;
	}


	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(mContext, R.layout.adapter_img, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.image);

		AdvInfo ad = mList.get(position % mList.size());
		imageView.setImageResource(ad.getIconResId());

		container.addView(view);// �?定不能少，将view加入到viewPager�?

		return view;
	}

	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// super.destroyItem(container, position, object);
		container.removeView((View) object);
	}

}
