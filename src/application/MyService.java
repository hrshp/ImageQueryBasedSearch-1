package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class MyService extends Service<Void>{
	
	String filePath;
	
	public MyService(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	String[] tags;

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				try {
		        	
		            String command = "./darknet detect cfg/yolo.cfg yolo.weights " + filePath + " -thresh 0.5";
		            Process proc = null;
		            try {
		                proc = Runtime.getRuntime().exec(command,null,new File("/home/aditi/DarkNet/darknet"));
		            } catch (IOException ex) {
		                Logger.getLogger(MyService.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		            String line = "";
		            String info = "";
		            while((line = reader.readLine())!= null)
		            {
		                info += line + '\n';
		            }
		            System.out.println(info);
		            storeTags(info);
		        } catch (IOException ex) {
		            Logger.getLogger(MyService.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
				
				return null;

			}
		};
		
		
	}
	
	public void storeTags(String info) {
		String[] lines = info.split("\n");
		String[] ans = new String[lines.length - 1];
		for (int i=1 ; i<lines.length ; ++i) {
			ans[i-1] = lines[i].substring(0, lines[i].lastIndexOf(':'));
		}
		
		this.tags = ans;
	}

}
