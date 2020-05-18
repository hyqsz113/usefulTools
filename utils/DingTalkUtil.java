package com.idianyou.redsdk;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @ClassName: DingTalkUtil.java
 * @version: v1.0.0
 * @Description: 钉钉通知类
 * @see https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.11cc4a975aDFgw&treeId=257&articleId=105735&docType=1
 * @author: yangyu
 * @date: 2019年2月16日 下午6:18:13
 */
public class DingTalkUtil {

	private static final Logger logger = LoggerFactory.getLogger(DingTalkUtil.class);
	
	/**
	 * 每个人根据自己的token替换 //TODO
	 */
	private static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=d6bca0b1b47310f5fc401fadc152502223b8f82b297898d1fcb397513051dc28";
	
	private static final String CODING = "utf-8";
	
	private static DingTalkUtil dingTalkUtil = null;
	private static EventBus eBus = null;
	private static DingSendListener dingSendListener = null;
	
	private static Lock lock = new ReentrantLock(true);
	
	public static DingTalkUtil getDingTalk() {
		if(dingTalkUtil == null) {
			try {
				lock.lock();
				if(dingTalkUtil == null) {
					httpclient = HttpClients.createDefault();
					httppost = new HttpPost(WEBHOOK_TOKEN);
					httppost.addHeader("Content-Type", "application/json; charset="+CODING);
					eBus = new AsyncEventBus("ding", Executors.newFixedThreadPool(2));
					dingSendListener = new DingTalkUtil().new DingSendListener();
					eBus.register(dingSendListener);
					dingTalkUtil = new DingTalkUtil();
				}
			} finally {
				lock.unlock();
			}
		}
		return dingTalkUtil;
	}
	
	/**
	 * @Description:注销掉当前事件进程 一般不需要用它
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: yangyu
	 * @date: 2019年2月16日 下午10:36:32
	 */
	public void unregisterEventBus() {
		if(eBus != null && dingSendListener != null) {
			eBus.unregister(dingSendListener);
		}
	}
	
	/**
	 * @Description: 发送钉钉消息(使用异步)
	 * @param sendJsonMsg
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: yangyu
	 * @date: 2019年2月16日 下午10:37:02
	 */
	public void sendDingMsg(JSONObject sendJsonMsg) {
		DingSendEvent event = new DingSendEvent(sendJsonMsg);
		eBus.post(event);
	}
	
	private static CloseableHttpClient httpclient = null;
	private static HttpPost httppost = null;
	
	private class DingSendEvent{
		private JSONObject sendJSON;
		public JSONObject getSendJSON() {
			return sendJSON;
		}
		public DingSendEvent(JSONObject sendJSON) {
			this.sendJSON = sendJSON;
		}
	}
	
	private class DingSendListener{
		@Subscribe
	    public void send(DingSendEvent sendEvent){
			StringEntity se = new StringEntity(sendEvent.getSendJSON().toJSONString(), CODING);
			httppost.setEntity(se);
			try {
				HttpResponse response = httpclient.execute(httppost);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response.getEntity(), CODING);
					if(StringUtils.isNotEmpty(result) && JSON.parseObject(result).getInteger("errcode").intValue() == 0) {
						//发送成功
					}else{
						logger.error("钉钉告警通知失败--" + result);
					}
				} else {
					logger.error("钉钉告警通知错误--"+response.getStatusLine().getStatusCode());
				}
			} catch (Exception e) {
				logger.error("钉钉告警通知异常--"+sendEvent.getSendJSON().toJSONString(),e);
			}
	    }
	}
	
	public static JSONObject getTextMsg(String content,String... atMobiles) {
		return getTextMsg(content, false, atMobiles);
	}
	
	/**
	 * @Description: 获取文本类型消息对象
	 * @param content 内容
	 * @param isAtAll 是否艾特所有人
	 * @param atMobiles 如果不是艾特所有人,可以选择艾特的手机号,是可变数组
	 * @return
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: yangyu
	 * @date: 2019年2月16日 下午10:37:33
	 */
	public static JSONObject getTextMsg(String content,boolean isAtAll,String... atMobiles) {
		JSONObject json = new JSONObject();
		json.put("msgtype", "text");
		JSONObject textJSON = new JSONObject();
		textJSON.put("content", content);
		json.put("text", textJSON);
		JSONObject atJSON = getAtJSON(isAtAll, atMobiles);
		if(atJSON.size() > 0) {
			json.put("at", atJSON);
		}
		return json;
	}
	
	public static JSONObject getMarkdownMsg(String title,String content,String... atMobiles) {
		return getMarkdownMsg(title, content, false, atMobiles);
	}
	
	/**
	 * @Description: 获取markdown类型消息
	 * @param title 具体看钉钉的文档
	 * @param content 具体看钉钉的文档
	 * @param isAtAll 是否艾特所有人
	 * @param atMobiles 如果不是艾特所有人,可以选择艾特的手机号,是可变数组
	 * @return
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: yangyu
	 * @date: 2019年2月16日 下午10:39:11
	 */
	public static JSONObject getMarkdownMsg(String title,String content,boolean isAtAll,String... atMobiles) {
		JSONObject json = new JSONObject();
		json.put("msgtype", "markdown");
		JSONObject markdownJson = new JSONObject();
		markdownJson.put("title", title);
		markdownJson.put("text", content);
		json.put("markdown", markdownJson);
		JSONObject atJSON = getAtJSON(isAtAll, atMobiles);
		if(atJSON.size() > 0) {
			json.put("at", atJSON);
		}
		return json;
	}
	
	private static JSONObject getAtJSON(boolean isAtAll,String... atMobiles) {
		JSONObject atJSON = new JSONObject();
		if(atMobiles.length > 0) {
			atJSON.put("atMobiles", atMobiles);// 当@的是所有人时候 这个将失效
		}
		if(isAtAll) {
			atJSON.put("isAtAll", isAtAll);
		}
		return atJSON;
	}
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("#### ").append("这是主题").append("\n").append("这是内容");
		//TODO 根据自己需要发送什么类型的消息 以及内容的拼接,还有要艾特的信息
		JSONObject textMsg = DingTalkUtil.getMarkdownMsg("都说了这是主题", sb.toString(), "13537673785");
		DingTalkUtil.getDingTalk().sendDingMsg(textMsg);
	}
}
