package com.dumbo.myhome.fun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import com.dumbo.myhome.R;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView; 
/**
 * 
 * @author klee
 *
 */
public class PayActivity extends Activity {
	public static String title;
	payziyeActivity pay=new payziyeActivity();
	private String[] values = new String[] { "电费", "水费", "燃气费", "其他缴费", };

	private int[] images = new int[] { R.drawable.dianfei, R.drawable.shuifei, R.drawable.ranqifei,
			R.drawable.qitajiaofei };

	private int[] point = new int[] { R.drawable.triangle, R.drawable.triangle, R.drawable.triangle,
			R.drawable.triangle, R.drawable.triangle, R.drawable.triangle };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		ListView list = (ListView) findViewById(R.id.listview_pay);
		ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < values.length; i++) {
			HashMap<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("values", values[i]);
			listItem.put("images", images[i]);
			listItem.put("point", point[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.payment_list_item,
				new String[] { "values", "images", "point" },
				new int[] { R.id.list_title, R.id.list_img, R.id.list_point });
		list.setAdapter(adapter);
		
        //添加点击  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
             	if(arg2==0){
            		title="电费";
            		pay.titleName(title);
            		Intent intent = new Intent();
        			intent.setClass(PayActivity.this, com.dumbo.myhome.fun.payziyeActivity.class);
        			startActivity(intent);
            	}
            	if(arg2==1){
            		title="水费";
            		pay.titleName(title);
            		Intent intent = new Intent();
        			intent.setClass(PayActivity.this, com.dumbo.myhome.fun.payziyeActivity.class);
        			startActivity(intent);
            	}
             	if(arg2==2){
            		title="燃气费";
            		pay.titleName(title);
            		Intent intent = new Intent();
        			intent.setClass(PayActivity.this, com.dumbo.myhome.fun.payziyeActivity.class);
        			startActivity(intent);
            	}
             	if(arg2==3){
            		title="其他缴费";
            		pay.titleName(title);
            		Intent intent = new Intent();
        			intent.setClass(PayActivity.this, com.dumbo.myhome.fun.payziyeActivity.class);
        			startActivity(intent);
            	}
            	
            	
            	
            	
            }  
        }); 
		
		
		
	
	


	}

}
