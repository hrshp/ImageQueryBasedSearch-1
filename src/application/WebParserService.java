package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class WebParserService extends Service<Void>{

	Document doc;
	String tag;
	
	static class Pair{
		String text;
		String uri;
		public Pair(String text, String uri) {
			this.text = text;
			this.uri = uri;
		}
		
		public boolean isValid() {
			String[] check = {"",
							  "\n\n",
							  "Consumer Ads Help Center",
							  "\n",
							  "Cached",
							  "Similar",
							  "Website",
							  "Directions",
							  "Ads Settings",
							  "Why This Ad page",
							  "Feedback"};
			
			for (int i=0 ; i<check.length ; ++i) {
				if (this.text.equals(check[i])) {
					return false;
				}
			}
			return true;
		}
		
	}
	
	ArrayList<Pair> list;
	
	
	public WebParserService(String tag) {
		this.tag = tag;
		this.list = new ArrayList<Pair>();
	}


	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				
				try {
		            doc = Jsoup.connect("https://www.google.com/search?q=" + tag).get();
		        } catch (IOException ex) {
		            Logger.getLogger(WebParserService.class.getName()).log(Level.SEVERE, null, ex);
		        }	
				
				Elements links = doc.getElementsByTag("a");
				for(Element link : links)
		        {
		            String l=link.attr("href");//actual link where the url will go to. HREF is the attribute of the href tag
		            if(l.length()>0)
		            {
		                if(l.length()<4)
		                    l = doc.baseUri()+l.substring(1);
		                else if(!l.substring(0,4).equals("http"))
		                    l = doc.baseUri()+l.substring(1);
		            }
		            Pair pair = new Pair(link.text(), l);
		           list.add(pair);
		        }
				return null;
			}};
	}

}
