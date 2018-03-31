package com.hivacation.webapp.webchat.msg;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.hivacation.webapp.webchat.msg.commsg.TextMsg;
import com.hivacation.webapp.webchat.msg.eventmsg.ScanEvent;
import com.hivacation.webapp.webchat.msg.eventmsg.SubscribeEvent;
import com.hivacation.webapp.webchat.msg.eventmsg.UnSubscribeEvent;


public class MsgFactory {

	private static MsgFactory f;
	
	private MsgFactory(){
		
	}
	
	public static MsgFactory getInstance(){
		if(f==null){
			f=new MsgFactory();
		}
		return f;
	}

	public Msg parse(Document doc) {
		/*
		 * 文本消息
		 * 
		 * <xml>
		 	<ToUserName><![CDATA[toUser]]></ToUserName>
		 	<FromUserName><![CDATA[fromUser]]></FromUserName>
		 	<CreateTime>1348831860</CreateTime>
		 	<MsgType><![CDATA[text]]></MsgType>
		 	<Content><![CDATA[this is a test]]></Content>
		 	<MsgId>1234567890123456</MsgId>
		 </xml>
		 * 
		 * */
		
		Element xml=doc.getRootElement();
		//xml.elements();//List
		Element ToUserNameNode=xml.element("ToUserName");
		String toUserName=ToUserNameNode.getText();
		Element FromUserNameNode=xml.element("FromUserName");
		String fromUserName=FromUserNameNode.getText();
		Element MsgTypeNode=xml.element("MsgType");
		String msgType=MsgTypeNode.getText();
		
		if("text".equals(msgType)){
			//文本消息
			Element MsgIdNode=xml.element("MsgId");
			String msgId=MsgIdNode.getText();
			Element ContentNode=xml.element("Content");
			String content=ContentNode.getText();
			
			TextMsg msg=new TextMsg();
			msg.setToUserName(toUserName);
			msg.setFromUserName(fromUserName);
			msg.setMsgId(msgId);
			msg.setContent(content);
			msg.setMsgType(msgType);
			
			return msg;
		}else if("image".equals(msgType)){
			/*
			 * <xml>
			 <ToUserName><![CDATA[toUser]]></ToUserName>
			 <FromUserName><![CDATA[fromUser]]></FromUserName>
			 <CreateTime>1348831860</CreateTime>
			 <MsgType><![CDATA[image]]></MsgType>
			 <PicUrl><![CDATA[this is a url]]></PicUrl>
			 <MediaId><![CDATA[media_id]]></MediaId>
			 <MsgId>1234567890123456</MsgId>
			 </xml>
			 * */
		}else if("event".equals(msgType)){
			/*
			 * 事件推送
			 * <xml>
				<ToUserName><![CDATA[toUser]]></ToUserName>
				<FromUserName><![CDATA[FromUser]]></FromUserName>
				<CreateTime>123456789</CreateTime>
				<MsgType><![CDATA[event]]></MsgType>
				<Event><![CDATA[subscribe]]></Event>
				</xml>
			 * */
			Element EventNode=xml.element("Event");
			String event=EventNode.getText();
			if("subscribe".equals(event)){
				//关注事件
				/*
				 * <xml><ToUserName><![CDATA[toUser]]></ToUserName>
					<FromUserName><![CDATA[FromUser]]></FromUserName>
					<CreateTime>123456789</CreateTime>
					<MsgType><![CDATA[event]]></MsgType>
					<Event><![CDATA[subscribe]]></Event>
					<EventKey><![CDATA[qrscene_123123]]></EventKey>
					<Ticket><![CDATA[TICKET]]></Ticket>
					</xml>
				 * */
				//如果有eventKey，则此二维码有参数，否则则是单纯的关注事件
				SubscribeEvent subscribeEvent=new SubscribeEvent();
				subscribeEvent.setEvent("subscribe");
				subscribeEvent.setFromUserName(fromUserName);
				subscribeEvent.setToUserName(toUserName);
				subscribeEvent.setMsgType(msgType);
				
				Element eventKeyNode=xml.element("EventKey");
				if(eventKeyNode!=null){
					String eventKey=eventKeyNode.getText();
					if(StringUtils.isNotEmpty(eventKey)){
						if(eventKey.contains("qrscene_")){
							String ekey=eventKey.replace("qrscene_", "");
							subscribeEvent.setEventKey(ekey);
						}else{
							subscribeEvent.setEventKey(eventKey);
						}
					}
				}
				Element ticketNode=xml.element("Ticket");
				if(ticketNode!=null){
					String ticket=ticketNode.getText();
					if(StringUtils.isNotEmpty(ticket)){
						subscribeEvent.setTicket(ticket);
					}
				}
				return subscribeEvent;
			}else if("unsubscribe".equals(event)){
				/*
				 * 取消关注
				 * <xml>
					<ToUserName><![CDATA[toUser]]></ToUserName>
					<FromUserName><![CDATA[FromUser]]></FromUserName>
					<CreateTime>123456789</CreateTime>
					<MsgType><![CDATA[event]]></MsgType>
					<Event><![CDATA[unsubscribe]]></Event>
					</xml>
				 * */
				UnSubscribeEvent unSubscribeEvent=new UnSubscribeEvent();
				unSubscribeEvent.setFromUserName(fromUserName);
				unSubscribeEvent.setToUserName(toUserName);
				unSubscribeEvent.setMsgType(msgType);
				unSubscribeEvent.setEvent("unsubscribe");
				
				return unSubscribeEvent;
			}else if("SCAN".equalsIgnoreCase(event)){
				/*
				 * 单纯的扫码事件
				 * <xml>
					<ToUserName><![CDATA[toUser]]></ToUserName>
					<FromUserName><![CDATA[FromUser]]></FromUserName>
					<CreateTime>123456789</CreateTime>
					<MsgType><![CDATA[event]]></MsgType>
					<Event><![CDATA[SCAN]]></Event>
					<EventKey><![CDATA[SCENE_VALUE]]></EventKey>
					<Ticket><![CDATA[TICKET]]></Ticket>
					</xml>
				 * */
				ScanEvent scanEvent=new ScanEvent();
				scanEvent.setFromUserName(fromUserName);
				scanEvent.setToUserName(toUserName);
				scanEvent.setMsgType(msgType);
				scanEvent.setEvent("SCAN");
				
				Element eventKeyNode=xml.element("EventKey");
				if(eventKeyNode!=null){
					String eventKey=eventKeyNode.getText();
					scanEvent.setEventKey(eventKey);
				}
				Element ticketNode=xml.element("Ticket");
				if(ticketNode!=null){
					String ticket=ticketNode.getText();
					scanEvent.setTicket(ticket);
				}
				return scanEvent;
			}
		}
		
		
		return null;
	}
}
