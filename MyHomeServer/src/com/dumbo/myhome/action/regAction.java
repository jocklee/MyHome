package com.dumbo.myhome.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dumbo.myhome.op.UserOp;
import com.opensymphony.xwork2.ActionSupport;

public class regAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	boolean temp=false;
	HttpServletRequest request;
	HttpServletResponse response;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void reg() {
		try {

			this.response.setContentType("text/html;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");

			Map<String, String> json = new HashMap<String, String>();

			String username = this.request.getParameter("userName");
			String password = this.request.getParameter("password");
			String nickname=this.request.getParameter("nickName");
			String password2 = this.request.getParameter("password2");
			UserOp userDaoImp = new UserOp();
			
			temp=userDaoImp.reg(username, nickname,password, password2);
			
			if (temp) {
				json.put("message", "true");;
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
}
