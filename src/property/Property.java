package property;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Because of the intertwinings between xml and graphically updatable settings,
 * these methods are intertwined into one class--Property
 * 
 * @author jimmy
 *
 * @param <T>
 */
public abstract class Property<T>
{
	private T value;
	private Label valueLabel;
	private String name;

	public Property(String name)
	{
		this.name = name;
		valueLabel = new Label(name + " value: " + this.getValue());
	}

	public T getValue()
	{
		return value;
	}

	public String getName()
	{
		return name;
	}

	public void setValue(T value)
	{
		this.value = value;
		updateDisplay();
	}

	public String convertToXML()
	{
		return "<" + name + ">" + value + "</" + name + ">";
	}

	public Node display()
	{
		return valueLabel;
	}

	protected void updateDisplay()
	{
		valueLabel.setText(name + " value : " + this.getValue().toString());
	}

	public abstract void setValue(String stringValue);

	public abstract Node makeDynamicUpdater();

}