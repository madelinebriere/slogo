package instruction.TurtleCommands;

import java.util.List;

import instruction.InstructionData;

/**
 * 
 * @author jimmy
 *
 */
public class SetTowards extends TurtleCommand
{

	public SetTowards(InstructionData instructionData, List<String> args, String myText)
	{
		super(instructionData, args, myText);
	}

	@Override
	public double execute()
	{
		double x = getArgumentDouble(0);
		double y = getArgumentDouble(1);
		double currX = this.getActiveTurtle().getLocation().getX();
		double currY = this.getActiveTurtle().getLocation().getY();
		double currHeading = this.getActiveTurtle().getHeading();
		double newHeading = Math.toDegrees(Math.atan((y - currY) / (x - currX)));
		if (y - currY < 0) {
			newHeading += 180;
		}
		this.setHeading(newHeading);
		return newHeading - currHeading;
	}
}
