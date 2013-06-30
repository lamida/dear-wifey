package net.lamida.dearwifey;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lamida.dearwifey.entity.Message;

import org.apache.commons.io.FileUtils;

import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
//import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class DataServlet extends HttpServlet {
	static {
        ObjectifyService.register(Message.class);
        ObjectifyService.register(MessageParent.class);
    }
	
	private static final Logger log = Logger.getLogger(DataServlet.class.getName());
	
	private final String secretKey = "b451b4n93t123";
	private final String action = "action";
	private final String actionInitData = "initData";
	private final String actionListAll = "listall";
	private final String actionListAllId = "listallid";
	private final String actionFind = "find";
	private final String actionFindLatest = "findlatest";
	private final String actionFindRandom = "findrandom";
	private final String actionFindBySubject = "findbysubject";
	private final String actionFindBySubjectLike = "findbysubjectlike";
	private final String actionFindByDate = "findbydate";
	private final String actionDelete = "delete";
	private final String messageSubject = "subject";
	private final String messageId = "messageid";
	private final String messageDate = "messagedate";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("doGet");
		
		resp.setContentType("text/json");
		
		processRequest(req, resp);

	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		System.out.println("processRequest");
		if (req.getParameterMap().size() == 0) {
			System.out.println("empty params");
			resp.getWriter().println("error: empty params");
		} else {
			String actionParameter = req.getParameter(action);
			String keyParameter = req.getParameter("key");
			if(actionParameter != null && keyParameter != null && keyParameter.equals(secretKey)){
				if (actionParameter.equals(actionInitData)) {
					initData();
					resp.getWriter().println(actionInitData);
					
				}else if (actionParameter.equals(actionListAll)) {
					List<Message> list = listAll();
					resp.getWriter().println(new Gson().toJson(list));
					
				}else if (actionParameter.equals(actionListAllId)) {
					List<Long> list = listAllId();
					resp.getWriter().println(new Gson().toJson(list));
					
				}else if (actionParameter.equals(actionFind)) {
					Long id = 1L;
					if(req.getParameter(messageId) != null){
						id = Long.parseLong(req.getParameter(messageId));
					}
					Message m = find(id);
					resp.getWriter().println(new Gson().toJson(m));
					
				}else if (actionParameter.equals(actionFindLatest)) {
					Message m = findLatest();
					resp.getWriter().println(new Gson().toJson(m));
					
				}else if (actionParameter.equals(actionFindRandom)) {
					Message m = findRandom();
					resp.getWriter().println(new Gson().toJson(m));
					
				}else if (actionParameter.equals(actionFindBySubject)) {
					String subject = null;
					if(req.getParameter(subject) != null){
						subject = req.getParameter(subject);
					}
					List<Message> list = findBySubject(subject);
					resp.getWriter().println(new Gson().toJson(list));
					
				}else if (actionParameter.equals(actionFindBySubjectLike)) {
					String subject = null;
					if(req.getParameter(messageSubject) != null){
						subject = req.getParameter(messageSubject);
					}
					List<Message> list = findBySubjectLike(subject);
					resp.getWriter().println(new Gson().toJson(list));
					
				}else if (actionParameter.equals(actionFindByDate)) {
					String ddmmyyyy = null;
					if(req.getParameter(messageDate) != null){
						ddmmyyyy = req.getParameter(messageDate);
					}
					List<Message> list = findByDate(ddmmyyyy);
					resp.getWriter().println(new Gson().toJson(list));
				}else if (actionParameter.equals(actionDelete)) {
					delete();
				}
			}
		}
	}

	private void save(final List<Message> msgs) {
		System.out.println("save bulk: " + msgs.size());
		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				int i = 0;
				for(Message m : msgs){
					System.out.println("saving: " + m.getSubject());
					m.setOwner(Key.create(new MessageParent(i+1L)));
					m.setContentText(new Text(m.getContent()));
					ofy().save().entities(m);
					System.out.println("ID " + m.getId());
					i++;
				}
			}
		}); 
	}

	private List<Message> listAll() {
		System.out.println("listAll");
		return ofy().load().type(Message.class).list();
	}

	/**
	 * TODO implement
	 * @return
	 */
	private List<Long> listAllId() {
//		System.out.println("listAllId");
//		List<com.googlecode.objectify.Key<Message>> list = ofy().load().type(Message.class).keys().list();
		return new ArrayList<Long>();
	}

	/**
	 * TODO implement
	 * @return
	 */
	private Message find(Long id) {
		System.out.println("find");
		return ofy().load().type(Message.class).id(id).get();
	}

	private Message findRandom() {
		System.out.println("findRandom");
		List<Message> list = listAll();
		Random r = new Random();
		int x = r.nextInt(list.size());
		return list.get(x);
	}

	private Message findLatest() {
		System.out.println("findLatest");
		List<Message> list = listAll();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	private List<Message> findByDate(String ddmmyyyy) {
		System.out.println("findByDate");
		return ofy().load().type(Message.class).filter("ddmmyyyyhhmmss >=", ddmmyyyy).filter("ddmmyyyyhhmmss <", ddmmyyyy + "\uFFFD").list(); 
	}

	private List<Message> findBySubject(String subject) {
		System.out.println("findBySubject");
		return ofy().load().type(Message.class).filter("subject", subject).list();
	}

	private List<Message> findBySubjectLike(String subject) {
		System.out.println("findBySubjectLike");
		return ofy().load().type(Message.class).filter("subject >=", subject).filter("subject <", subject + "\uFFFD").list(); 
	}
	
	private void initData(){
		System.out.println("initData");
		try {
			ServletContext context = getServletContext();
			URL url = context.getResource("/WEB-INF/msgs-new.json");
			String string = FileUtils.readFileToString(new File(url.getPath()));
			List<Message> list = new Gson().fromJson(string, new TypeToken<List<Message>>(){}.getType());
			save(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete(){
		System.out.println("delete");
		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				ofy().delete().entities(ofy().load().type(Message.class).list());
			}
		}); 
		
	}
}
