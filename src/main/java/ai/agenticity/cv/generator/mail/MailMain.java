//package com.fbullini.mail;
//
////import jolie.runtime.*;
////import jolie.runtime.embedding.RequestResponse;
//
//
//import javax.activation.DataHandler;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.activation.DataSource;
//import java.io.*;
//
//import java.util.ArrayList;
//
//import java.util.List;
//import java.util.Properties;
//
///*
// * type SendRequest:void{
//    .subject:string
//    .message:string
//    .to:string
//    .attachments:void{
//        .filename:string
//        .contentType:string
//        .content:any
//    }
//}
//* typical error
//* https://support.google.com/mail/answer/7126229?p=BadCredentials&visit_id=637765740577324206-2887117339&rd=2#cantsignin&zippy=%2Ci-cant-sign-in-to-my-email-client
// */
//public class MailMain {
//
//    public static void main(String[] args){
//        List<Attachment> la=new ArrayList<>();
//        //File as Buffer;
//        //ByteArray ba=new ByteArray( );
//        la.add(new Attachment("","",null));//TODO
//        String message="Tasks:\n" +
//                "";
//        send("Factures Octobre","fbullo@gmail.com",message,la);
//    }
//
//    public static void send(String subject,  String recipient, String message, List<Attachment> attachments) {
//        final String username = "fbullo@gmail.com";
//        final String password = "FBGeneva20150829android";
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        prop.put("mail.mime.charset", "UTF-8");
//        prop.put("mail.mime.decodetext.strict","false");
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                        return new javax.mail.PasswordAuthentication(username, password);
//                    }
//                });
//        try {
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress("fbullo@gmail.com"));
//            ArrayList<InternetAddress[]> addresses = new ArrayList<>();
//            try {
//                    mimeMessage.addRecipient( Message.RecipientType.TO,
//                            new InternetAddress(recipient));
//                } catch (MessagingException messagingException) {
//                    messagingException.printStackTrace();
//                }
//            //molti recipients
////            request.getChildren("to").forEach(value -> {
////                try {
////                    message.addRecipient( Message.RecipientType.TO,
////                            new InternetAddress(value.strValue()));
////                } catch (MessagingException messagingException) {
////                    messagingException.printStackTrace();
////                }
////            });
//            // ========= TODO ATTACMENTS ==================================
//            if (attachments.size()>0){
////                Multipart multipart = new MimeMultipart();
////                MimeBodyPart textBodyPart = new MimeBodyPart();
////
////                textBodyPart.setContent(request.getFirstChild("message").strValue(), "text/html ; charset= UTF-8");
////
////                multipart.addBodyPart( textBodyPart );
////
////                for( int counter = 0; counter < request.getChildren( "attachments" ).size(); counter++ ) {
////                    final String contentTypeMulti = request.getChildren("attachments").get(counter).getFirstChild("contentType").strValue();
////                    final byte[] content = request.getChildren("attachments").get(counter).getFirstChild("content").byteArrayValue().getBytes();
////                    DataHandler dh = new DataHandler( new DataSource() {
////
////                        public InputStream getInputStream()
////                                throws IOException {
////                            return new ByteArrayInputStream(content);
////                        }
////                        public OutputStream getOutputStream()
////                                throws IOException {
////                            throw new IOException("Operation not supported");
////                        }
////                        public String getContentType() {
////                            return contentTypeMulti;
////                        }
////                        public String getName() {
////                            return "mail attachment";
////                        }
////                    });
////                    BodyPart attachmentPart = new MimeBodyPart();
////                    attachmentPart.setDataHandler(dh);
////                    attachmentPart.setFileName(request.getChildren("attachments").get(counter).getFirstChild("filename").strValue());
////                    multipart.addBodyPart(attachmentPart);
////                    mimeMessage.setContent(multipart);
////                    mimeMessage.setSubject(request.getFirstChild("subject").strValue());
////                }
//            }else {
//                mimeMessage.setSubject(subject);
//                mimeMessage.setContent(message, "text/html ; charset = UTF-8");
//            }
//            Transport.send(mimeMessage);
//        } catch (AddressException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
