import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;

public class AddressBar extends HBox {
	
	private TextField address;
	
	private Button back;
	private Button forward;
	private Button help;
	
	private WebTabView tab;
	private WebHistory history;
	
	public AddressBar(WebTabView tab) {
		this.tab = tab;
		this.history = tab.getEngine().getHistory();
		setPadding(new Insets(5, 4, 5, 4));
		setSpacing(10);
		setStyle("-fx-background-color: #336699;");
		
		back = new Button("Back");
		back.setDisable(true);
		back.setOnMouseClicked(event -> history.go(-1));
		
		forward = new Button("Forward");
		forward.setDisable(true);
		forward.setOnMouseClicked(event -> history.go(1));
		
		history.getEntries().addListener((ListChangeListener.Change<? extends Entry> entry) -> updateButtons());
		
		address = new TextField();
		HBox.setHgrow(address, Priority.ALWAYS);
		
		help = new Button("Help");
		help.setOnMouseClicked(event -> tab.getBrowser().openTab());
		
		getChildren().addAll(back, forward, address, help);
		
		address.setOnKeyPressed(event -> { if (event.getCode() == KeyCode.ENTER) onNavigate(event); });
	}
	
	private void onNavigate(KeyEvent event) {
		String url = address.getText();
		
		tab.loadPage(compileUrl(url).toString());
	}
	
	private URL compileUrl(String url) {
		
		try {
			
			String finalUrl = url;
			
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				finalUrl = ("http://" + finalUrl).replaceAll(" ", "%20");
			
			System.out.println(finalUrl);
			
			return new URL(finalUrl);
			
		} catch (MalformedURLException e) {
			return getSearchUrl(url);
		}
		
	}
	
	private URL getSearchUrl(String search) {
		try {
			return new URL("http://google.com/search?q=" + search);
		} catch (MalformedURLException e) {
			return getHomepage();
		}
	}
	
	private URL getHomepage() {
		try {
			return new URL("http://google.com/");
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	private void updateButtons() {
		back.setDisable(history.getEntries().isEmpty() || history.getCurrentIndex() == 0);
		forward.setDisable(history.getCurrentIndex() == history.getEntries().size() - 1);
	}
	
	public TextField getAddressField() {
		return address;
	}
	
	public Button getBackButton() {
		return back;
	}
	
	public Button getForwardButton() {
		return forward;
	}

}
