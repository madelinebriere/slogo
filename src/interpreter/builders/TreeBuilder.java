package interpreter.builders;

import java.util.ArrayList;
import java.util.List;

import instruction.InstructionData;
import interpreter.classification.InstructionClassifier;
import interpreter.clean.InstructionSplitter;
import interpreter.misc.InstructionNode;
import interpreter.util.ArgumentReaderUtil;

public class TreeBuilder {
	
	private String currentText;
	private InstructionClassifier classifier;
	private List <InstructionNode> nodes;
	private InstructionData data;
	
	public TreeBuilder(String text, InstructionClassifier c, InstructionData d){
		currentText = text;
		classifier = c;
		if(!(text.isEmpty())&& !(c == null))
			nodes = InstructionSplitter.getInstructions(text, c, d);
		else
			nodes = new ArrayList<InstructionNode>();
		data = d;
	}
	
	
	/**
	 * This builds a tree of InstructioNodes given a list of Instructions 
	 * (The utility of having Instruction type is to have access
	 * to the number of arguments needed for an instruction)
	 * 
	 * @param toParse String to parse into instructions for tree constructions
	 * @param clzz InstructionClassifier used to split
	 * @return A list of single InstructionNode, each head of their own tree
	 */
	public List<InstructionNode> buildTree(){
		if(getCurrentText().isEmpty()){
			return new ArrayList<InstructionNode>();
		}
		ArrayList<InstructionNode> headNodes = new ArrayList<InstructionNode>();
		while(!getCurrentText().isEmpty()){
			InstructionNode newHead = buildSubTree();
			if(newHead!=null)
				headNodes.add(newHead); //build a list of nodes from text
		}
		return headNodes;
	}
	
	/**
	 * This is the recursive function called upon when an object of this class
	 * builds a tree. This function will iterate through the current nodes and
	 * text (what is left unprocessed from the instruction) and connect the nodes
	 * and remove the processed words from the current text. It will either perform 
	 * this process generically, if there is no BuilderUtil class associated with the
	 * instruction type of the head node, or perform specialized processing (groups,
	 * lists, user-defined commands).
	 * 
	 * This method may terminate before the instruction is entirely processed -- 
	 * if the head node is "satisfied" (gets all of its arguments), this method will 
	 * complete its connecting process and return the head node (now connected
	 * to its children).
	 * 
	 * @return a new InstructionNode representing this individual subtree (there can be 
	 * multiple subtrees in a single instruction). 
	 */
	private InstructionNode buildSubTree(){
		if(getCurrentText().isEmpty()){
			return null;
		}
		
		InstructionNode head = removeHeadNode(); //take node out of list to add to tree
		String headText = getHeadNodeText(); 
		setHeadText(head, headText); 
		decrementCurrentText(); //remove a node
		
		BuilderUtil build = BuilderUtilFactory.make(nodes, head, currentText, data);
		if(build!=null){
			setCurrentText(build.construct()); //change current text according to builder
		}
		else{
			int numArgs = ArgumentReaderUtil.getNumArgs(head.getMyClassification(), headText, data);
			head.setProperNumArgs(numArgs);
			buildChildren(numArgs, head);
		}
		return head;
	}
	
	/**
	 * The recursive part of tree-building. This method iterates through all
	 * of the children of a headnode and creates sub-trees for those nodes.
	 * @param numArgs The number of arguments this particular instruction 
	 * (the head instruction) takes
	 * @param head The current head node (just removed from the list)
	 */
	private void buildChildren(int numArgs, InstructionNode head){
		for(int i=0; i<numArgs; i++){
			head.getMyChildren().add(buildSubTree());
		}
	}
	
	private void decrementCurrentText(){
		setCurrentText(InstructionSplitter.removeFirstItem(getCurrentText()));//remove node from current text
	}
	
	private void setHeadText(InstructionNode head, String value){
		head.setMyCommand(value);
	}
	
	private InstructionNode removeHeadNode(){
		return nodes.remove(0);
	}
	
	private String getHeadNodeText(){
		return InstructionSplitter.getInstructionStrings(getCurrentText()).get(0);
	}
	
	public String getCurrentText() {
		return currentText;
	}
	public void setCurrentText(String currentText) {
		this.currentText = currentText;
	}
	public InstructionClassifier getClassifier() {
		return classifier;
	}
	public void setClassifier(InstructionClassifier classifier) {
		this.classifier = classifier;
	}
	public InstructionData getData() {
		return data;
	}
	public void setData(InstructionData data) {
		this.data = data;
	}

}
