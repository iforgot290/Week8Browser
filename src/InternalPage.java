import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;

public abstract class InternalPage extends VBox {
	
	private SimpleStringProperty title = new SimpleStringProperty();
	
	void setTitle(String title) {
		this.title.set(title);
	}
	
	public ReadOnlyStringProperty titleProperty() {
		return title;
	}

}
