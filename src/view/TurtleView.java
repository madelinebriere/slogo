package view;

import java.io.File;

import javafx.geometry.Point2D;
import main.Defaults;
import user_structures.NamedImageWrapper;

public class TurtleView extends ActorView
{
	private static final String TURTLE_IMAGES_LOCATION = "images";
	private PenView pen;
	
	public TurtleView(Defaults defaults, int id)
	{
		super(defaults, id);
		pen = new PenView(defaults.pen());
	}

	@Override
	public void step()
	{
		super.step();
		this.getPen().step();
	}

	public PenView getPen()
	{
		return pen;
	}

	@Override
	public void setHeading(double newHeading)
	{
		super.setHeading(newHeading);
		if (pen != null) {
			pen.waitTransition(200);
		}
	}

	@Override
	public void move(Point2D newLocation)
	{
		pen.move(this.getActor().getLocation(), newLocation);
		super.move(newLocation);
	}

}