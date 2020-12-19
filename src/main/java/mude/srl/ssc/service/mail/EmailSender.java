/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.mail;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mude.srl.ssc.entity.Email;

/**
 *
 * @author jackarian
 */
public class EmailSender {

    private static Properties props = null;
    private static SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String password = "";
    private String login = "";
    String smtpServer;
    List<String> to;
    String from;
    String subject;
    String body;
    ArrayList<FileAndMIME> files;
    boolean textonly;
    private boolean auth = false;
    private Integer port;
    private boolean ssl = false;
    private boolean ttl = true;
    ArrayList<String> bcc;

    public EmailSender() {
    }

    public EmailSender(Email email) {
        this.login = email.getEmailPK().getLogin();
        this.password = email.getEmailPK().getPassword();
        this.auth = email.getValido();
        this.ssl = email.getSslenabled();
        this.ttl = email.getTtlenabled();
        this.port = Integer.valueOf(email.getSmtpport());
        this.from = email.getEmailPK().getLogin();
        this.smtpServer = email.getEmailPK().getSmtp();
    }

    /**
     *
     * @param login
     * @param password
     * @param smtpServer
     * @param to
     * @param from
     * @param subject
     * @param body
     * @param files
     * @param textonly
     * @param auth
     * @param port
     * @param ssl
     * @param ttl
     */
    public EmailSender(String login,
            String password, String smtpServer, ArrayList<String> to, String from, String subject, String body, ArrayList<FileAndMIME> files, boolean textonly,
            boolean auth, Integer port, boolean ssl, boolean ttl) {
        this.login = login;
        this.password = password;
        this.smtpServer = smtpServer;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.files = files;
        this.textonly = textonly;
        this.auth = auth;
        this.port = port;
        this.ssl = ssl;
        this.ttl = ttl;
    }

    /**
     *
     * @throws AddressException
     * @throws MessagingException
     * @throws IOException
     */
    public void send()
            throws AddressException, MessagingException, IOException {

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props = new Properties();

            props.setProperty("mail.smtp.timeout", "20000");
            props.setProperty("mail.smtp.connectiontimeout", "20000");
            
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", smtpServer);
            
            props.put("mail.smtp.ssl.socketFactory", sf);

            if (port == null) {
                props.put("mail.smtp.port", port.toString());
            }
            if (auth) {
                props.put("mail.smtp.auth", "true");
            }
            if (ttl && port != null) {
                props.put("mail.smtp.starttls.enable", "true");

            }
            if (ssl && port != null) {
                props.setProperty("mail.smtp.ssl.enable", "true");

            }
            props.setProperty("mail.smtp.host", smtpServer);
            //props.setProperty("mail.debug", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, password);
                }
            });

            javax.mail.Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));

            for (String add : to) {
                msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(add, false));
            }

            msg.setSubject(subject);
//Building multipart
            MimeMultipart mmp = new MimeMultipart();
//Composition of message
            MimeBodyPart mbp1 = new MimeBodyPart();
//mbp1.setText(body);
            mbp1.setContent(body, "text/html");
            mmp.addBodyPart(mbp1);
            if (files != null && files.size() > 0) {

                for (FileAndMIME file1 : files) {
                    MimeBodyPart mimeBodyForAttachmnt = new MimeBodyPart();
                    FileDataSource file = new MyFileDataSource(file1.getFile(), "application/octet-stream");
                    //mimeBodyForAttachmnt.setContent(bis,"application/octet-stream");
                    mimeBodyForAttachmnt.setDataHandler(new DataHandler(file));
                    mimeBodyForAttachmnt.setHeader("Content-Type", "application/octet-stream");
                    mimeBodyForAttachmnt.setHeader("Content-Transfer-Encoding", "base64");
                    mimeBodyForAttachmnt.setHeader("Content-Disposition", "attachment");
                    mimeBodyForAttachmnt.setFileName(file1.getFile().getName());
                    //mbp2.setContent(files.get(i).getFile(),files.get(i).getMime());
                    //System.out.println(mimeBodyForAttachmnt.getContentType());
                    mmp.addBodyPart(mimeBodyForAttachmnt);
                }
            }
            if (bcc != null && !bcc.isEmpty()) {
                for (String e : bcc) {
                    msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(e, false));
                }
            }

            msg.setContent(mmp);
            msg.saveChanges();

             // -- Set some other header information --
            msg.setHeader("X-Mailer", smtpServer);
            Calendar c = Calendar.getInstance(Locale.ITALIAN);
            msg.setSentDate(c.getTime());
            
            // Connect and send
            Transport t = session.getTransport();
            t.connect(login, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            // -- Send the message --
            //            URLName url = new URLName("smtp", smtpServer, port, null, login, password);
            //            SMTPTransport t = new SMTPTransport(session, url);
            //
            //            t.connect();
            //            t.sendMessage(msg, msg.getAllRecipients());
            //        SMTPTransport.send(msg);
            t.close();

        } catch (Exception ex) {
            //Exceptions.printStackTrace(ex);
        }

    }

    public static Properties getProps() {
        return props;
    }

    public static void setProps(Properties props) {
        EmailSender.props = props;
    }

    public static SimpleDateFormat getDataFormat() {
        return dataFormat;
    }

    public static void setDataFormat(SimpleDateFormat dataFormat) {
        EmailSender.dataFormat = dataFormat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<FileAndMIME> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileAndMIME> files) {
        this.files = files;
    }

    public boolean isTextonly() {
        return textonly;
    }

    public void setTextonly(boolean textonly) {
        this.textonly = textonly;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isTtl() {
        return ttl;
    }

    public void setTtl(boolean ttl) {
        this.ttl = ttl;
    }

    public void setTo(String email) {
        if (to == null) {
            to = new ArrayList<>();
        }
        to.add(email);
    }

    public void addBcc(String email) {
        if (bcc == null) {
            bcc = new ArrayList<>();
        }
        bcc.add(email);
    }

}

class MyFileDataSource extends FileDataSource {

    private String content = null;

    public MyFileDataSource(String string) {
        super(string);
    }

    public MyFileDataSource(File file) {
        super(file);
    }

    public MyFileDataSource(File file, String string) {
        super(file);
        this.content = string;

    }

    @Override
    public String getContentType() {
        return this.content;
    }

}

class MyAuthenticator extends Authenticator {

    private final String password;
    private final String login;

    public MyAuthenticator(String password, String login) {
        super();
        this.password = password;
        this.login = login;
    }

    @Override
    protected java.net.PasswordAuthentication getPasswordAuthentication() {
        return super.getPasswordAuthentication(); //To change body of generated methods, choose Tools | Templates.
    }

}
