package com.cmd.exchange.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailMessage;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SimpleMailUtil {
    private static Log log = LogFactory.getLog(SimpleMailUtil.class);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class EmailProperty {
        public String mailServerHost;
        public String mailServerPort;
        public String mailFromAddress;
        public String mailUserName;
        public String mailPassword;
        public boolean starttls = false;  // 是否采用starttls加密算法
        public boolean validate = false;
    }

    public static class MyAuthenticator extends Authenticator {
        private String user;
        private String password;

        public MyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }
    }

    //以文本格式发送邮件
    public static boolean sendTextMail(EmailProperty mail, String toAddress, String subject, String content) {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = new Properties();
        pro.put("mail.smtp.host", mail.mailServerHost);
        pro.put("mail.smtp.port", mail.mailServerPort);
        pro.put("mail.smtp.auth", mail.validate ? "true" : "false");

        if (mail.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mail.getMailUserName(), mail.getMailPassword());
        }

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mail.getMailFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(toAddress);
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(subject);
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            mailMessage.setText(content);

            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //以HTML格式发送邮件
    public static boolean sendHtmlMail(EmailProperty mail, String toAddress, String subject, String htmlContent) {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = new Properties();
        pro.put("mail.smtp.host", mail.mailServerHost);
        pro.put("mail.smtp.port", mail.mailServerPort);
        pro.put("mail.smtp.auth", mail.validate ? "true" : "false");

        //如果需要身份认证，则创建一个密码验证器
        if (mail.isValidate()) {
            authenticator = new MyAuthenticator(mail.getMailUserName(), mail.getMailPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mail.getMailFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(toAddress);
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(subject);
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(htmlContent, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public static void sendTextMailSSL(EmailProperty mail, String toAddress, String subject, String content) {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        Properties properties = System.getProperties();// 获取系统属性
        properties.setProperty("mail.smtp.host", mail.getMailServerHost());// 设置邮件服务器
        properties.setProperty("mail.smtp.port", mail.getMailServerPort());
        properties.setProperty("mail.smtp.socketFactory.port", mail.getMailServerPort());
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            if (!mail.isStarttls()) {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.ssl.socketFactory", sf);
                log.info("user SSL");
            } else {
                properties.setProperty("mail.smtp.starttls.enable", "true");// 使用starttls加密
                log.info("user starttls");
            }

            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail.getMailUserName(), mail.getMailPassword()); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(mail.getMailFromAddress()));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            // 2.3设置邮件主题
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            // 2.4设置邮件内容
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
        } catch (Exception e) {
            log.error("send mail fialed", e);
            //e.printStackTrace();
        }
    }


    public static void main(String[] argv) {
        SimpleMailUtil.EmailProperty info = new SimpleMailUtil.EmailProperty()
                .setMailFromAddress("noreply@coin.cn").setMailUserName("noreply@coin.cn").setMailPassword("yycdnbcsxfuqcagh")
                .setMailServerHost("smtp.qq.com").setMailServerPort("465").setValidate(true);
        //SimpleMailUtil.sendTextMail(info, "122176768@qq.com", "验证码", "验证码：1248");
        SimpleMailUtil.sendTextMailSSL(info, "122176768@qq.com", "验证码", "验证码：1248");
    }
} 