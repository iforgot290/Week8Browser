import java.util.ArrayList;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BrowserView extends BorderPane {

	private ArrayList<WebTabView> tabs = new ArrayList<WebTabView>();
	private HBox bar = new HBox();

	private Week8Program program;
	
	private SimpleStringProperty title = new SimpleStringProperty();
	
	public BrowserView(Week8Program program) {
		this(null, program);
	}
	
	public BrowserView(String url, Week8Program program) {
		this.program = program;

		Button newTab = new Button("+Tab");
		newTab.setOnMouseClicked(event -> openTab());

		bar.getChildren().add(newTab);
		
		if (url == null)
			openTab();
		else 
			openTab(url);
		
		setTop(bar);
	}
	
	public void openTab() {
		openTab(new WebTabView(new HelpPage(this), this));
	}
	
	public void openTab(String url) {
		openTab(new WebTabView(url, this));
	}

	public void openTab(WebTabView tab) {
		tabs.add(tab);

		Button button = new Button("Help");
		button.textProperty().bind(tab.titleProperty());
		title.bind(tab.titleProperty());
		button.setOnMouseClicked(event -> {
			
			if (event.getButton() == MouseButton.PRIMARY) {
				setCenter(tab);
				title.bind(tab.titleProperty());
			} else if (event.getButton() == MouseButton.SECONDARY) {
				closeTab(tab, (Button) event.getSource());
			}
			
		});

		bar.getChildren().add(bar.getChildren().size() - 1, button);
		setCenter(tab);
	}
	
	public void closeTab(WebTabView tab, Button button) {
		tabs.remove(tab);
		bar.getChildren().remove(button);
		
		if (bar.getChildren().size() <= 1) openTab();
		else setCenter(tabs.get(tabs.size() - 1));
	}
	
	public ReadOnlyStringProperty titleProperty() {
		return title;
	}

}
