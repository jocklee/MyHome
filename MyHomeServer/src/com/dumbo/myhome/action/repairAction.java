package com.dumbo.myhome.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dumbo.myhome.op.RepairOp;
import com.dumbo.myhome.op.UserOp;
import com.opensymphony.xwork2.ActionSupport;

public class repairAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
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

	public void repair() {
		try {

			this.response.setContentType("text/html;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");

			Map<String, String> json = new HashMap<String, String>();

			String editTel = this.request.getParameter("editTel");
			String editName = this.request.getParameter("editName");
			String editAddress = this.request.getParameter("editAddress");
			String editWhy = this.request.getParameter("editWhy");
			RepairOp repairop  = new RepairOp();
	
			temp=repairop.repairUpdata(editTel, editName, editAddress, editWhy);
			
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
