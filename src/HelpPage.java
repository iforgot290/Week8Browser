import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelpPage extends InternalPage {
	
	public HelpPage(BrowserView view) {
		Text title = new Text("Welcome to my browser!");
		title.setFont(Font.font(20));
		
		String welcome = "To get started, simply enter a web address and hit enter go. If what you entered didn't qualify as a valid URL, a google search results page will pop up with what you typed in.";
		Text welcomeText = new Text(welcome);
		welcomeText.wrappingWidthProperty().bind(view.widthProperty().subtract(40));
		
		this.setSpacing(20);
		this.setPadding(new Insets(20, 20, 20, 20));
		
		getChildren().addAll(title, welcomeText);
		
		this.setTitle("Help");
	}

}
