package instruction.TurtleCommands;

import instruction.InstructionData;
import interpreter.InstructionNode;

public class Backward extends TurtleCommand{
	private static final int NUM_ARGS = 1;
	
	public Backward(){
		super(new InstructionData(), new InstructionNode());
		//TODO: check and complete
	}
	
	public Backward(InstructionData instructionData, InstructionNode root) {
		super(instructionData, root);
	}

	@Override
	public double execute() {
		double distance = getArguments().get(0);
		move(-distance);
		return distance;
	}

	@Override
	public int getNumArgs() {
		return NUM_ARGS;
	}
}