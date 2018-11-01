import java.util.ArrayList;

import javafx.concurrent.Worker;

public class HistoryManager {
	
	private WebTabView view;
	
	private ArrayList<String> history = new ArrayList<String>();
	private int position = -1;
	
	private boolean button = false;
	
	public HistoryManager(WebTabView view) {
		this.view = view;
		
		view.getEngine().getLoadWorker().stateProperty().addListener((observe, oldValue, newValue) -> onLoaded(newValue));
	}
	
	public void back() {
		button = true;
		view.loadPage(history.get(position-=1));
	}
	
	public boolean canBack() {
		return position > 0;
	}
	
	public void forward() {
		button = true;
		view.loadPage(history.get(position+=1));
	}
	
	public boolean canForward() {
		return position < history.size() - 1;
	}
	
	private void onLoaded(Worker.State state) {
		if (state == Worker.State.SUCCEEDED) {
			if (!button) {
				
				if (position < history.size() - 1) {
					ArrayList<String> newHistory = new ArrayList<String>();
					
					for (int i = 0; i <= position; i++) {
						newHistory.add(history.get(i));
					}
					
					history = newHistory;
				}
				
				history.add(view.getEngine().getLocation());
				position = history.size() - 1;
			}
			
			button = false;
			view.getAddressBar().updateButtons();
		}
	}

}
