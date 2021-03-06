package tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.FileChooserUtil;

/**
 * @author jimmy
 * @author Jesse
 *
 */
public class SettingsMenuTool extends MenuTool
{
	public static final String name = "Settings";

	private List<AbstractMenuItem> buttons;

	public SettingsMenuTool(Stage window)
	{
		super(name, window);
	}

	@Override
	public void makeItems()
	{
		buttons = new ArrayList<>();
		addButtons(buttons, new BackgroundColorButton(), new TurtleImageButton(), new PenColorButton(),
				new LanguageButton(), new DefaultButton());
	}

	@Override
	protected List<AbstractMenuItem> getButtons()
	{
		return buttons;

	}

	public class BackgroundColorButton extends AbstractColorButton
	{
		public BackgroundColorButton()
		{
			super(new MenuItem("Background Color"));
		}
	}

	public class TurtleImageButton extends AbstractMenuItem
	{
		public TurtleImageButton()
		{
			super(new MenuItem("Turtle Image"));
			this.getItem().setOnAction(e -> {
				Stage newWindow = new Stage();
				File selectedFile = FileChooserUtil.setupFileChooser("IMAGE", "New Image",
						new File(System.getProperty("user.dir") + "/images"), "*.png").showSaveDialog(newWindow);
				if (selectedFile != null) {
					Image newImage = new Image(selectedFile.toURI().toString());
					this.setChanged();
					this.notifyObservers(newImage);
				}
			});
		}
	}

	public class PenColorButton extends AbstractColorButton
	{
		public PenColorButton()
		{
			super(new MenuItem("Pen Color"));
		}
	}

	public class LanguageButton extends AbstractLanguageItem
	{
		public LanguageButton()
		{
			super(new MenuItem("Language"));

		}
	}

	public class DefaultButton extends AbstractMenuItem
	{
		public DefaultButton()
		{
			super(new MenuItem("Save Defaults"));
			this.getItem().setOnAction(e -> {
				this.setChanged();
				this.notifyObservers();
			});
		}
	}

}