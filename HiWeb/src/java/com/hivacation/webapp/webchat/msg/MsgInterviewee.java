/*
 * 被访问者
 * */
package com.hivacation.webapp.webchat.msg;

public class MsgInterviewee {

	public Object accept(Msg msg){
		//处理公共部分
		return msg.invoke(this);
	}
}
