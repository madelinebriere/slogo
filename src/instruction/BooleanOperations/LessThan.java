package instruction.BooleanOperations;

import java.util.List;

import instruction.InstructionData;
import util.MathUtil;

public class LessThan extends BooleanOperation{
	
	public LessThan(InstructionData data,  List<String> args, String myText){
		super(data, args, myText);
	}
	
	@Override
	public double execute() {
		return MathUtil.doubleLessThan(getArgumentDouble(0), getArgumentDouble(1)) ? 1 : 0;
	}

}
