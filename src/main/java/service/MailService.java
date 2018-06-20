package service;

import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/*													// Enable for Attachment 
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
*/
public class MailService {
	public static void SendForgotPasswordMail(String toMail,final String fromMail,final String fromMailPassword,String subject,String body, String filePath) {
      // Recipient's email ID needs to be mentioned. = toMail
		
      // Sender's email ID needs to be mentioned = fromMail
		
	  // Sender's email ID's Password needs to be mentioned = fromMailPassword
			
	      // Assuming you are sending email through relay.jangosmtp.net
	      // String host = "relay.jangosmtp.net";
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(fromMail, fromMailPassword);
	            }
	         });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(fromMail));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(toMail));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Create the message part
//	         BodyPart messageBodyPart = new MimeBodyPart();				// Enable for Attachment

	         // Now set the actual message
//	         messageBodyPart.setText(body);								// Enable for Attachment
	         message.setText(body);										// Disable for Attachment

	         // Create a multipar message
//	         Multipart multipart = new MimeMultipart();					// Enable for Attachment

	         // Set text message part
//	         multipart.addBodyPart(messageBodyPart);					// Enable for Attachment
	         
	         // Part two is attachment
/*																		// Enable for Attachment
 *  	         if(filePath != null) {
	         messageBodyPart = new MimeBodyPart();
	         String filename = filePath;
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
	         }
*/	         
	         // Send the complete message parts
//	         message.setContent(multipart);								// Enable for Attachment
	         System.out.println("Hi");
	         // Send message
	         Transport.send(message);
	         System.out.println("Hi2");
	         System.out.println("Sent message successfully....");
	  
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
	
	public static void SendDynamicMail(String toMail,final String fromMail,final String fromMailPassword,String subject,String body, Set<String> attachmentFilePaths,Set<String> attachmentFileNames) {
	      // Recipient's email ID needs to be mentioned. = toMail
			
	      // Sender's email ID needs to be mentioned = fromMail
			
		  // Sender's email ID's Password needs to be mentioned = fromMailPassword
						
		      // Assuming you are sending email through relay.jangosmtp.net
		      // String host = "relay.jangosmtp.net";
		      String host = "smtp.gmail.com";

		      Properties props = new Properties();
		      props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.starttls.enable", "true");
		      props.put("mail.smtp.host", host);
		      props.put("mail.smtp.port", "587");

		      // Get the Session object.
		      Session session = Session.getInstance(props,
		         new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		               return new PasswordAuthentication(fromMail, fromMailPassword);
		            }
		         });

		      try {
		         // Create a default MimeMessage object.
		         Message message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(fromMail));

		         // Set To: header field of the header.
		         message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(toMail));

		         // Set Subject: header field
		         message.setSubject(subject);

		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();
		         // Now set the actual message
		         messageBodyPart.setText(body);
		         // Create a multipart message
		         Multipart multipart = new MimeMultipart();
		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);
		         
		         // Part two is attachment
		         if(attachmentFilePaths.size() > 0) {
				 		String[] attachmentFileNamesArray = attachmentFileNames.toArray(new String[0]);
				 		for (String attachmentFilePath : attachmentFilePaths)
				 		{
				        	 int index = 0;
				  	         if(attachmentFilePath != null) {
					         messageBodyPart = new MimeBodyPart();
					         DataSource source = new FileDataSource(attachmentFilePath);
					         messageBodyPart.setDataHandler(new DataHandler(source));
					         System.out.println(attachmentFileNamesArray[index]);
					         messageBodyPart.setFileName(attachmentFileNamesArray[index]);
					         multipart.addBodyPart(messageBodyPart);
					         }
				  	         index++;
				 		}
		         }

		         // Send the complete message parts
		         message.setContent(multipart);
		         System.out.println("Hi");
		         // Send message
		         Transport.send(message);
		         System.out.println("Hi2");
		         System.out.println("Sent message successfully....");
		  
		      } catch (MessagingException e) {
		         throw new RuntimeException(e);
		      }
		   }
}
