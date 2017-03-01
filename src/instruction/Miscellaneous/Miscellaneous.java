package instruction.Miscellaneous;

import java.util.List;

import instruction.Instruction;
import instruction.InstructionData;
import interpreter.Interpreter;

public abstract class Miscellaneous extends Instruction {
	
	public Miscellaneous(InstructionData instructionData, List<String> args) {
		super(instructionData, args);
	}
	
	protected double runListCommands(int argumentNumber) {
		Interpreter listInterpreter = new Interpreter(getInstructionData(), "English");    //Need to change when decide on way to set language
		//Temporary:
		listInterpreter.parseAndRun(getArgumentString(argumentNumber), getInstructionData());
		return 1;
		//Possibly final:
		//return listInterpreter.parseAndRun(getArgumentString(1), getInstructionData());
		//Somehow return the value of last executed command in list - need Maddie to decide how she gives this to me
	}
}
