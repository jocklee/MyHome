package com.dumbo.myhome.gridview;

import com.dumbo.myhome.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description:
 * @author: 
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] img_text = { "����", "�����", "Ѱ������", "����", "����ɷ�", "��������",
			"��ϵ��ҵ", "�������", "ȫ��Ӧ��", };
	public int[] imgs = { R.drawable.gonggao, R.drawable.img_huodong,
			R.drawable.xunwuqishi, R.drawable.baoxiu,
			R.drawable.img_shenghuojiaofei, R.drawable.jiazheng,
			R.drawable.lianxiwuye, R.drawable.yijianfankun, R.drawable.quanbuyingyong };
	

// this.setOnItemClickListener(new OnItemClickListener(){
// 
// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {//��дѡ������¼��Ĵ�����
//                            
//        // arg1�Ǳ������view��arg2�Ǳ����view��gridview�е�����        
//            }  
	

	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(imgs[position]);

		tv.setText(img_text[position]);
		return convertView;
	}

}
