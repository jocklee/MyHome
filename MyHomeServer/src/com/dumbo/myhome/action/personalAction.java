package com.dumbo.myhome.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dumbo.myhome.op.PersonalOp;
import com.dumbo.myhome.op.UserOp;
import com.opensymphony.xwork2.ActionSupport;

public class personalAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	String str = null;
	HttpServletRequest request;
	HttpServletResponse response;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	// 查询
	public void personal() {
		try {

			this.response.setContentType("text/html;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");

			Map<String, String> json = new HashMap<String, String>();
			PersonalOp personalop = new PersonalOp();
			String personal_nickname = personalop.personal_nickname;
			String personal_sex = personalop.personal_sex;
			String personal_tel = this.request.getParameter("personal_tel");
			str = personalop.PersonalQuery(personal_tel);
			System.out.println(str);
			if (str != null) {
				json.put("message", str);
				

			} else {
				json.put("message", "false");
			}

			byte[] jsonBytes = json.toString().getBytes("utf-8");
			response.setContentLength(jsonBytes.length);
			response.getOutputStream().write(jsonBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	

	// 修改
	public void personalUpdate() {
		try {

			this.response.setContentType("text/html;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");

			Map<String, String> json = new HashMap<String, String>();
			PersonalOp personalop = new PersonalOp();
			String personal_tel = this.request.getParameter("personal_tel");
			String personal_nickname=this.request.getParameter("personal_nickname");
			String personal_sex=this.request.getParameter("personal_sex");
			boolean temp=personalop.PersonalUpdata(personal_tel, personal_nickname, personal_sex);
			
			
			if (temp) {
				json.put("message", "修改成功");
				

			} else {
				json.put("message", "修改失败");
			}
			byte[] jsonBytes = json.toString().getBytes("utf-8");
			response.setContentLength(jsonBytes.length);
			response.getOutputStream().write(jsonBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
