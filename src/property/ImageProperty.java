package property;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import util.Pair;
import view.ActorView;
/**
 * 
 * @author jimmy
 *
 */

/*
 * Note from Matthew: Whenever you instantiate a new ImageProperty instance,
 * you MUST call addDefaultImageToInitialImageFilename() after setting the default
 * image, else several Instructions will not work. Ideally, you could do all this in the
 * ImageProperty constructor, but I did not want to modify this class more than necessary.
 * 
 */

public class ImageProperty extends Property<ImageView>
{
	//private static final Map<String, ImageView> INDEXED_IMAGES = new HashMap<>();
	private static final List<Pair<String, ImageView>> INDEXED_IMAGES = new ArrayList<>();
	private static final String TURTLE_IMAGES_LOCATION = "images";
	
	private ImageView displayImage;
	
	static{
		File currentFolder = new File(".");
		File srcFolder = currentFolder.getParentFile();
		File imagesFolder = new File(srcFolder, TURTLE_IMAGES_LOCATION);
		for(File imageFile : imagesFolder.listFiles()){
			INDEXED_IMAGES.add(new Pair<>(imageFile.getName(), new ImageView(new Image(imageFile.getName()))));
		}
	}
	
	public ImageProperty(String name)
	{
		super(name);
		displayImage = new ImageView();
		super.setValue(new ImageView());
		displayImage.setFitHeight(ActorView.ACTOR_HEIGHT);
		displayImage.setFitWidth(ActorView.ACTOR_WIDTH);
		displayImage.setPreserveRatio(true);
	}
	/*
	public Map<String, ImageView> getIndexedImages(){
		return INDEXED_IMAGES;
	}
	*/
	public List<Pair<String, ImageView>> getIndexedImages(){
		return INDEXED_IMAGES;
	}
	/**
	 * Fixes the .equals() problem. Consult me for details.
	 * @author Matthew
	 * @param initialImageFilename
	 */
	
	public void addDefaultImageToInitialImageFilename(String initialImageFilename){
		//INDEXED_IMAGES.put(initialImageFilename, displayImage);
		//For List, iterate over and find filename match, and replace that element
		 for(Pair<String, ImageView> pair : INDEXED_IMAGES){
			 if(pair.getMyA().equals(initialImageFilename)){  //TODO Change to Comparator, TODO error throwing
				 pair.setMyB(displayImage);
			 }
		 }
	}
	
	@Override
	public void setValue(ImageView image)
	{
		this.setValue(image.getImage());
	}
	public void setValue(Image image)
	{
		this.getValue().setImage(image);
		updateDisplay();
	}
	@Override
	public void setValue(String stringValue)
	{
		this.setValue(new Image(stringValue));
	}
	@Override
	public void updateDisplay()
	{
		displayImage.setImage(this.getValue().getImage());
		displayImage.setEffect(this.getValue().getEffect());
	}
	@Override
	public ImageView display()
	{
		return displayImage;
	}
	@Override
	public Node makeDynamicUpdater()
	{
		Label label = new Label(String.format("Update %s", this.getName()));
		Button input = initializeButton(String.format("Update %s", this.getName()));
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		vbox.getChildren().add(input);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
	}
	private Button initializeButton(String defaultText)
	{
		Button input = new Button();
		input.setText(defaultText);
		Stage newWindow = new Stage();
		input.setOnAction(e -> {
			File selectedFile = setupFileChooser().showOpenDialog(newWindow);
			if (selectedFile != null) {
				this.setValue(new Image(selectedFile.toURI().toString()));
			}
		});
		return input;
	}
	private FileChooser setupFileChooser()
	{
		final String EXTENSION = "*.png";
		FileChooser chooser = new FileChooser();
		chooser.setTitle("New Image");
		File defaultDirectory = new File(System.getProperty("user.dir") + "/images");
		chooser.setInitialDirectory(defaultDirectory);
		chooser.getExtensionFilters().setAll(new ExtensionFilter("IMAGE", EXTENSION));
		return chooser;
	}
}