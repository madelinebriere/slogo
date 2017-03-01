package instruction.MathOperations;

import java.util.List;

import instruction.InstructionData;

public class Sum extends MathOperation{
	public Sum(InstructionData data,  List<String> args, String myText) {
		super(data, args, myText);
	}
	
	@Override
	public double execute() {
		return getArgumentDouble(0) + getArgumentDouble(1);
	}

}
