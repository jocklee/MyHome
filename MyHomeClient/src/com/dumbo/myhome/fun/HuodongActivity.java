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
	private String[] values = new String[] { "���˳齱", "1Ԫ����", "�ؼ���Ʒ", "�ɷѷ���", };

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
		
        //��ӵ��  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
             	if(arg2==0){
             		Toast.makeText(getApplicationContext(), "����Գ���������ҹ�Ӱ�Ŷ...", Toast.LENGTH_SHORT).show();
            	}
            	if(arg2==1){
            		Toast.makeText(getApplicationContext(), "�����½��������ҹ�Ӱ�Ŷ...", Toast.LENGTH_SHORT).show();
            	}
             	if(arg2==2){
             		Toast.makeText(getApplicationContext(), "����ʨ����ά����...", Toast.LENGTH_SHORT).show();
            	}
             	if(arg2==3){
             		Toast.makeText(getApplicationContext(), "��Ǹ���ù������ڿ�����...", Toast.LENGTH_SHORT).show();
            	}
            	
            	
            	
            	
            }  
        }); 
		
		
		
	
	


	}

}
