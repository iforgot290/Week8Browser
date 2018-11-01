import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Worker;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebTabView extends BorderPane {

	private WebView view;
	private WebEngine engine;
	
	private AddressBar addressBar;
	private StatusBar statusBar;
	
	private BrowserView browser;
	private HistoryManager history;
	
	private SimpleStringProperty title = new SimpleStringProperty();

	public WebTabView(String url, BrowserView browser) {
		this(browser);

		loadPage(url);
		title.bind(engine.titleProperty());
		setCenter(view);
	}

	public WebTabView(InternalPage page, BrowserView browser) {
		this(browser);

		title.bind(page.titleProperty());
		setCenter(page);
	}

	private WebTabView(BrowserView browser) {
		this.browser = browser;

		view = new WebView();
		engine = view.getEngine();
		
		this.history = new HistoryManager(this);
		
		this.addressBar = new AddressBar(this);
		this.statusBar = new StatusBar(this);
		setTop(addressBar);
		setBottom(statusBar);
		
		engine.getLoadWorker().stateProperty().addListener((observe, oldValue, newValue) -> onLoaded(newValue));
	}

	public void loadPage(String url) {
		if (!(getCenter() instanceof WebView)) {
			title.bind(engine.titleProperty());
			setCenter(view);
		}

		engine.load(url.toString());
	}

	private void onLoaded(Worker.State state) {

		if (state == Worker.State.SUCCEEDED) {
			getAddressBar().getAddressField().setText(getEngine().getLocation());
		}

	}
	
	public ReadOnlyStringProperty titleProperty() {
		return title;
	}
	
	public WebEngine getEngine() {
		return engine;
	}
	
	public AddressBar getAddressBar() {
		return addressBar;
	}
	
	public HistoryManager getHistoryManager() {
		return history;
	}
	
	public BrowserView getBrowser() {
		return browser;
	}

}
