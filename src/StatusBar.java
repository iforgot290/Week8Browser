import javafx.concurrent.Worker;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class StatusBar extends HBox {
	
	private WebTabView tab;
	
	private Text textBox;
	private Worker.State state = Worker.State.READY;
	private String statusText = "Ready";
	private int percent = 0;
	
	private boolean hover = false;
	
	public StatusBar(WebTabView tab) {
		this.tab = tab;
		
		this.textBox = new Text("Ready");
		this.getChildren().add(textBox);
		
		this.tab.getEngine().getLoadWorker().stateProperty().addListener((observe, oldValue, newValue) -> updateStatus(newValue));
		this.tab.getEngine().getLoadWorker().progressProperty().addListener((observe, oldValue, newValue) -> updateStatus(newValue));
	}
	
	public void setHoverText(String text) {
		hover = true;	
		textBox.setText(text);
	}
	
	public void removeHover() {
		hover = false;
		updateStatus(state, percent);
	}
	
	private void updateStatus(Number number) {
		this.percent = (int)(number.doubleValue() * 100);
		updateStatus(state, percent);
	}
	
	private void updateStatus(Worker.State state) {
		this.state = state;
		updateStatus(state, percent);
	}
	
	private void updateStatus(Worker.State state, int percent) {
		
		switch (state) {
		case SUCCEEDED:
			statusText = "Loading complete";
			break;
		case CANCELLED:
			statusText = "Loading cancelled";
			break;
		case FAILED:
			statusText = "Error loading page";
			break;
		case RUNNING:
			statusText = percent + "% Loading...";
			break;
		case SCHEDULED:
			statusText = "Waiting...";
			break;
		default:
			statusText = "Ready";
			break;
		}
		
		if (!hover) textBox.setText(statusText);
	}

}
