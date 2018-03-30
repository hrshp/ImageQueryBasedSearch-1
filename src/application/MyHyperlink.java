package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class MyHyperlink extends Hyperlink{
	private String text;
	private String uri;
	
	public MyHyperlink(String text, String uri) {
		this.text = text;
		this.uri = uri;
		this.setText(this.text);
		this.setOnClickAction();
	}
	
	public String text() {
		return this.text;
	}
	
	public String uri() {
		return this.uri;
	}
	
	public void setOnClickAction() {
		this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					new ProcessBuilder("x-www-browser", uri).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
