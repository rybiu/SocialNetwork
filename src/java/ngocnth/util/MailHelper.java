/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author Ruby
 */
public class MailHelper {
    
    public static void send(String receiver, String subject, String msg) 
            throws MessagingException, UnsupportedEncodingException {  
        String username = Constant.MAIL_USERNAME;  
        String password = Constant.MAIL_PASSWORD;  

        //1st step) Get the session object    
        Properties props = new Properties();  
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(username, password);  
            }  
        });  
        
        //2nd step)compose message       
        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(username, Constant.MAIL_SENDERNAME));  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));  
        message.setSubject(subject);  
        message.setText(msg);  

        //3rd step)send message  
        Transport.send(message);
    }  
    
}
