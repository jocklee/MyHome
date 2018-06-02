package com.dumbo.myhome.fun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
public class HuodongActivity extends Activity {
	public static String title;
	payziyeActivity pay=new payziyeActivity();
	private String[] values = new String[] { "幸运抽奖", "1元抢购", "特价商品", "缴费返利", };

	private int[] images = new int[] { R.drawable.xingyunchoujiang, R.drawable.yiyuanqianggou, R.drawable.tejiashangpin,
			R.drawable.jiaofeifanli };

	private int[] point = new int[] { R.drawable.triangle, R.drawable.triangle, R.drawable.triangle,
			R.drawable.triangle, R.drawable.triangle, R.drawable.triangle };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huodong);

		ListView list = (ListView) findViewById(R.id.listview_pay);
		ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < values.length; i++) {
			HashMap<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("values", values[i]);
			listItem.put("images", images[i]);
			listItem.put("point", point[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.huodong_list_item,
				new String[] { "values", "images", "point" },
				new int[] { R.id.list_title, R.id.list_img, R.id.list_point });
		list.setAdapter(adapter);
		
        //添加点击  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
             	if(arg2==0){
             		Toast.makeText(getApplicationContext(), "程序猿哥哥正在连夜加班哦...", Toast.LENGTH_SHORT).show();
            	}
            	if(arg2==1){
            		Toast.makeText(getApplicationContext(), "程序媛姐姐正在连夜加班哦...", Toast.LENGTH_SHORT).show();
            	}
             	if(arg2==2){
             		Toast.makeText(getApplicationContext(), "攻城狮正在维护中...", Toast.LENGTH_SHORT).show();
            	}
             	if(arg2==3){
             		Toast.makeText(getApplicationContext(), "抱歉！该功能正在开发中...", Toast.LENGTH_SHORT).show();
            	}
            	
            	
            	
            	
            }  
        }); 
		
		
		
	
	


	}

}
