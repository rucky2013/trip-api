package com.ulplanet.trip.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * 
 * 发送邮件
 * @author zhangxd
 * @date 2015年7月14日
 *
 */
public class MailSender {
	
	final static Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	/** 账户 */
	private static String username;
	
	/** 密码 */
	private static String password;
	
	/** 昵称 */
	private static String nickname;
	
	/** 端口 */
	private static int port;
	
	/** 地址 */
	private static String host;
	
	/**
	 * 构造函数
	 */
	private MailSender(){}
	
	static{
		reload();
	}
	
	/**
	 * 重新加载配置文件
	 *
	 */
	public static void reload(){
		PropertiesLoader loader = new PropertiesLoader("email.properties");
		
		username = Objects.toString(loader.getProperty("mail.username"), "");
		password = Objects.toString(loader.getProperty("mail.password"), "");
		nickname = Objects.toString(loader.getProperty("mail.nickname"), "");
		port = NumberHelper.toInt(loader.getProperty("mail.port"), 25);
		host = Objects.toString(loader.getProperty("mail.host"), "smtp.163.com");
	}
	
	/**
	 * 发送普通邮件
	 * @param targetEmail - 发送目标邮箱，多个请使用,号隔开
	 * @param title - 标题
	 * @param content - 内容
	 * @return True or False
	 */
	public static boolean send(String targetEmail, String title, String content){
		return send(targetEmail, null, title, content);
	}
	
	/**
	 * 异步发送邮件
	 * @param targetEmail
	 * @param title
	 * @param content
	 */
	public static void asyncSend(final String targetEmail, final String title, final String content) {
		
		new Thread(() -> {
            try {
                MailSender.send(targetEmail, title, content);
            } catch (Exception ex) {
                logger.error("mail sender error To: " + targetEmail + " Mail Title: " + title + " Content: " + content, ex);
            }
        }).start();
	} 
	
	/**
	 * 发送普通邮件
	 * @param targetEmail - 发送目标邮箱，多个请使用,号隔开
	 * @param ccTargetEmail - 抄送目标邮箱，多个请使用,号隔开
	 * @param title - 标题
	 * @param content - 内容
	 * @return True or False
	 */
	public static boolean send(String targetEmail, String ccTargetEmail, String title, String content){
		Transport transport = null;
		try{
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true"); //这样才能通过验证
			props.put("mail.transpost.protocol", "smtp");
			props.put("mail.smpt.port", String.valueOf(port));
			
			Session session = Session.getInstance(props, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}});
			
			Message msg = new MimeMessage(session);
			//加载发件人地址
			msg.setFrom(new InternetAddress(username, nickname));
			//加载收件人地址
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));
			if(StringHelper.isNotBlank(ccTargetEmail)){
				//加载抄送人地址
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccTargetEmail));
			}
			msg.setSentDate(new Date());
			//加载标题
			msg.setSubject(MimeUtility.encodeWord(title));
			//加载内容
			msg.setContent(content, "text/html;charset=utf-8");
			
			transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			Transport.send(msg);
			transport.close();
			return true;
		}catch (Exception ee){
			ee.printStackTrace();
			try {if(transport != null) transport.close();} catch (MessagingException e) {e.printStackTrace();}
			return false;
		}
	}
	
	/**
	 * 异步发送邮件
	 * @param targetEmail
	 * @param ccTargetEmail
	 * @param title
	 * @param content
	 */
	public static void asyncSend(final String targetEmail,
			final String ccTargetEmail, final String title, final String content) {
		
		new Thread(() -> {
            try {
                MailSender.send(targetEmail, ccTargetEmail, title, content);
            } catch (Exception ex) {
                logger.error("mail sender error To: " + targetEmail
                        + "CC: " + ccTargetEmail + " Mail Title: " + title + " Content: " + content, ex);
            }
        }).start();
	} 
	
	/**
	 * 发送带附件的邮件
	 * @param targetEmail - 发送目标邮箱，多个请使用,号隔开
	 * @param title - 标题
	 * @param content - 内容
	 * @param filePath - 附件路径
	 * @return True or False
	 */
	public static boolean send(String targetEmail, String title, String content, String[] filePath){
		return send(targetEmail, null, title, content, filePath);
	}
	
	/**
	 * 异步发送带附件的邮件
	 * @param targetEmail
	 * @param title
	 * @param content
	 * @param filePath
	 */
	public static void asyncSend(final String targetEmail,
			final String title, final String content, final String[] filePath) {
		
		new Thread(() -> {
            try {
                MailSender.send(targetEmail, title, content, filePath);
            } catch (Exception ex) {
                logger.error("mail sender error To: " + targetEmail
                        + " Mail Title: " + title + " Content: " + content + " File: " + Arrays.toString(filePath), ex);
            }
        }).start();
	}
	
	/**
	 * 发送带附件的邮件
	 * @param targetEmail - 发送目标邮箱，多个请使用,号隔开
	 * @param ccTargetEmail - 抄送目标邮箱，多个请使用,号隔开
	 * @param title - 标题
	 * @param content - 内容
	 * @param filePath - 附件路径
	 * @return True or False
	 */
	public static boolean send(String targetEmail, String ccTargetEmail, String title, String content, String[] filePath){
		Transport transport = null;
		try{
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true"); //这样才能通过验证
			props.put("mail.transpost.protocol", "smtp");
			props.put("mail.smpt.port", String.valueOf(port));
			
			Session session = Session.getInstance(props, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}});
			
			Message msg = new MimeMessage(session);
			//加载发件人地址
			msg.setFrom(new InternetAddress(username, nickname));
			//加载收件人地址
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));
			if(StringHelper.isNotBlank(ccTargetEmail)){
				//加载抄送人地址
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccTargetEmail));
			}
			msg.setSentDate(new Date());
			//加载标题
			msg.setSubject(MimeUtility.encodeWord(title));
			
			//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			
			//设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(contentPart);
			
			//添加附件
			if(filePath != null && filePath.length > 0){
				for(String f : filePath){
					File file = new File(f);
					if(file.exists() && file.isFile()){
						BodyPart messageBodyPart= new MimeBodyPart();
						DataSource source = new FileDataSource(f); //附件地址
						//添加附件的内容
						messageBodyPart.setDataHandler(new DataHandler(source));
						//添加附件的标题
						messageBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
						multipart.addBodyPart(messageBodyPart);
					}
				}
			}
			
			msg.setContent(multipart);
			
			transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			Transport.send(msg);
			transport.close();
			return true;
		} catch (MessagingException | UnsupportedEncodingException ee) {
            ee.printStackTrace();
			try {
                if(transport != null) transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
			return false;
		}
	}
	
	/**
	 * 异步发送带附件的邮件
	 * @param targetEmail
	 * @param ccTargetEmail
	 * @param title
	 * @param content
	 * @param filePath
	 */
	public static void asyncSend(final String targetEmail, final String ccTargetEmail,
			final String title, final String content, final String[] filePath) {
		
		new Thread(() -> {
            try {
                MailSender.send(targetEmail, ccTargetEmail, title, content, filePath);
            } catch (Exception ex) {
                logger.error("mail sender error To: " + targetEmail + " CC: " + ccTargetEmail
                        + " Mail Title: " + title + " Content: " + content + " File: " + Arrays.toString(filePath), ex);
            }
        }).start();
	}
	
	public static void main(String[] args){
		MailSender.asyncSend("zhangxudong@ulplanet.com", "阿斯顿爱的", "阿斯利康等加拉加斯的拉动");
		MailSender.asyncSend("zhangxudong@ulplanet.com", "adsf", "fasdfasfsafsfsfsfas");
	}
	
}
