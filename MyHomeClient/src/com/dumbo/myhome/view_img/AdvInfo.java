package com.dumbo.myhome.view_img;


public class AdvInfo {
	private int iconResId;
	private String intro;
	
	
	public AdvInfo(int iconResId, String intro) {
		super();
		this.iconResId = iconResId;
		this.intro = intro;
	}
	public int getIconResId() {
		return iconResId;
	}
	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	
}
