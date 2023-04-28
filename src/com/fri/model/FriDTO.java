package com.fri.model;

import com.member.model.MemberDTO;

public class FriDTO {
	private int friend_num;
	private int friend_request;  // 건사람
	private int friend_response;	// 받는 사람
	private String request_wait;   //수락대기
	private String request_accept; //신청 수락 
	private MemberDTO memberDTO;
	
	public int getFriend_num() {
		return friend_num;
	}
	public void setFriend_num(int friend_num) {
		this.friend_num = friend_num;
	}
	public int getFriend_request() {
		return friend_request;
	}
	public void setFriend_request(int friend_request) {
		this.friend_request = friend_request;
	}
	public int getFriend_response() {
		return friend_response;
	}
	public void setFriend_response(int friend_response) {
		this.friend_response = friend_response;
	}
	public String getRequest_wait() {
		return request_wait;
	}
	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	public void setRequest_wait(String request_wait) {
		this.request_wait = request_wait;
	}
	public String getRequest_accept() {
		return request_accept;
	}
	public void setRequest_accept(String request_accept) {
		this.request_accept = request_accept;
	}
}
