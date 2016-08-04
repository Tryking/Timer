package com.tryking.EasyList._bean;

import com.tryking.EasyList._bean.loginBean.Event;

import java.util.List;

public class DayEventReturnBean extends BaseBean{
	private String memberId;
	private String date;
	private List<Event> eventList;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
}
