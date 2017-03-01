package models;

import javafx.beans.property.*;

/**
 * Variable that holds String and double value
 * @author Jesse
 *
 */
public class Variable {
	private StringProperty name;
	private DoubleProperty value;
	
	public StringProperty nameProperty(){
		if(name == null){
			name = new SimpleStringProperty(this, "variable");
		}
		return name;
	}
	public void setName(String value){
		nameProperty().set(value);
	}
	public String getName(){
		return nameProperty().get();
	}
	
	public DoubleProperty valueProperty(){
		if(value == null){
			value = new SimpleDoubleProperty(0);
		}
		return value;
	}
	public void setValue(double value){
		valueProperty().set(value);
	}
	public double getValue(){
		return valueProperty().get();
	}

}
