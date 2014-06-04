import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

public class Main {
	public static void main(String args[]) throws IOException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", "", "");
			System.out.println("STORE " + store);
			
			Folder inbox = store.getFolder("dear wifey");
			inbox.open(Folder.READ_ONLY);
			List<Msg> msgs = new ArrayList<Msg>();

			int count = 0;
			int total = inbox.getMessages().length;
			Message messages[] = inbox.getMessages();
			for (Message message : messages) {
				if(message.getFrom()[0].toString().equals("jon kartago lamida <jonkartagolamida@gmail.com>")){
					System.out.println("processing " + count + " of " + total);
					System.out.println("processing subject " + message.getSubject());
					System.out.println("from: " + message.getFrom()[0].toString());
					Msg m = new Msg();
					m.setSubject(message.getSubject());
					m.setSentDate(message.getSentDate());
					Object content = message.getContent();
					
					if (content instanceof MimeMultipart) {
						MimeMultipart multipart = (MimeMultipart)content;
						BodyPart b = multipart.getBodyPart(0);
						if(b.getContent() instanceof MimeMultipart){
							MimeMultipart multipart2 = (MimeMultipart)b.getContent();
							m.setContent(multipart2.getBodyPart(0).getContent().toString());
						}else{
							m.setContent(b.getContent().toString());
						}
						
					}
					if (content instanceof String)  
					{  
						System.out.println("string content!!");
					    m.setContent((String)content); 
					    //System.out.println();
					}
					msgs.add(m);
//					if(count == 10)break;
				}
				count++;
			}
			FileUtils.writeStringToFile(new File("msgs.json"), new GsonBuilder().setPrettyPrinting().create().toJson(msgs));
			XStream xStream = new XStream();
			FileUtils.writeStringToFile(new File("msgs.xml"), StringEscapeUtils.unescapeXml(xStream.toXML(msgs)));
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public static void main4(String[] args) throws JsonSyntaxException, IOException {
		List<Msg> msgs = new GsonBuilder().create().fromJson(FileUtils.readFileToString(new File("msgs-utf8.json")),  new TypeToken<List<Msg>>(){}.getType());
		XStream xStream = new XStream();
		FileUtils.writeStringToFile(new File("msgs.xml"), StringEscapeUtils.unescapeXml(xStream.toXML(msgs)));
	}

	public static void maindd(String[] args) throws JsonSyntaxException, IOException {
		List<Msg> msgs = new GsonBuilder().create().fromJson(FileUtils.readFileToString(new File("msgs.json")),  new TypeToken<List<Msg>>(){}.getType());
		List<Msg> msgs2 = new ArrayList<Msg>();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmmss");
		for(Msg m : msgs){
			m.setDdmmyyyyhhmmss(format.format(m.getSentDate()));
			System.out.println(m.getDdmmyyyyhhmmss());
			msgs2.add(m);
		}
		FileUtils.writeStringToFile(new File("msgs-new.json"), new GsonBuilder().setPrettyPrinting().create().toJson(msgs2));
	}
}
