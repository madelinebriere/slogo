package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.SLogoException;
import instruction.InstructionData;
import interpreter.Interpreter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tool.FileTool;
import tool.FileTool.NewButton;
import tool.FileTool.OpenButton;
import tool.HelpTool;
import tool.SelectionBar;
import tool.SettingsTool;
import tool.SettingsTool.LanguageButton;
import user_structures.FunctionData;
import user_structures.VariableData;
import view.SingleLineInputBox;
import view.InputBox;
import view.SavedCommandsView;
import view.SimulationView;
import view.StackedSimulationView;
import view.View;
import view.PreferencesView;
import view.WorkspaceView;

/**
 * @author jimmy
 * @author Jesse
 *
 */
public class Controller implements Observer
{
	private TabPane root;
	private ObjectProperty<Tab> currentTab;
	private Map<Tab, SelectionBar> selectionBarMap;
	private Map<Tab, SimulationView> simulationMap;

	private Map<Tab, InputBox> inputBoxMap;
	private Map<Tab, View> workspaceMap;
	private Map<Tab, View> savedCommandsMap;

	private Map<Tab, ObservableList<VariableData>> variableMap;
	private Map<Tab, ObservableList<FunctionData>> functionMap;

	private Stage stage;
	private double printValue;
	private Defaults defaults;
	private List<String> language;
	private IntegerProperty currentIndex;

	public Controller(Stage stage, Defaults defaults)
	{
		this.stage = stage;
		this.defaults = defaults;
		language = new ArrayList<>();
		currentIndex = new SimpleIntegerProperty();
		setupItems();
		newTab();
		// currentTab is always the one selected
		currentTab.bind(root.getSelectionModel().selectedItemProperty());
		currentIndex.bind(root.getSelectionModel().selectedIndexProperty());

		stage.setTitle("SLogo");
		stage.setScene(new Scene(root));
		stage.setMaximized(true);
		stage.show();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof NewButton) {
			newTab();
		}
		if (o instanceof OpenButton) {
			openFile((File) arg);
		}
		if (o instanceof LanguageButton) {
			language.set(currentIndex.get(), (String) arg);
		}
	}

	private void setupItems()
	{
		root = new TabPane();
		currentTab = new SimpleObjectProperty<>();
		selectionBarMap = new HashMap<>();
		simulationMap = new HashMap<>();
		inputBoxMap = new HashMap<>();
		workspaceMap = new HashMap<>();
		savedCommandsMap = new HashMap<>();

		variableMap = new HashMap<>();
		functionMap = new HashMap<>();

	}

	private void newTab()
	{
		Tab tab = new Tab();
		root.getSelectionModel().select(tab);
		;
		language.add(defaults.language());
		tab.setText("untitled.logo");
		BorderPane pane = new BorderPane();
		StackedSimulationView simulation = new StackedSimulationView(defaults);
		SingleLineInputBox inputBox = new SingleLineInputBox();
		inputBox.setFocus();
		WorkspaceView workspace = new WorkspaceView();
		SavedCommandsView userCommands = new SavedCommandsView();
		PreferencesView preferences = new PreferencesView(simulation.getTurtle(), simulation);

		SelectionBar selectionBar = new SelectionBar();
		FileTool file = new FileTool(stage);
		SettingsTool settings = new SettingsTool(stage);
		HelpTool help = new HelpTool(stage);
		selectionBar.addAllTools(file, settings, help);

		List<VariableData> varList = new ArrayList<>();
		ObservableList<VariableData> variables = FXCollections.observableList(varList);
		List<FunctionData> funcList = new ArrayList<>();
		ObservableList<FunctionData> functions = FXCollections.observableList(funcList);

		workspace.setItems(variables);
		// TODO setup functions

		setupBorderPane(pane, selectionBar, simulation, inputBox, workspace, preferences);
		putIntoMaps(tab, selectionBar, simulation, inputBox, workspace, userCommands, variables, functions);
		setupObservers(simulation, inputBox, file, settings, preferences);
		setupCommands(inputBox);

		tab.setContent(pane);
		root.getTabs().add(tab);
	}

	private void setupBorderPane(BorderPane pane, SelectionBar selectionBar, SimulationView simulation,
			InputBox inputBox, WorkspaceView workspace, PreferencesView preferences)
	{

		pane.setTop(selectionBar.display());
		pane.setCenter(simulation.display());
		pane.setBottom(inputBox.display());
		pane.setLeft(workspace.display());
		pane.setRight(preferences.display());
	}

	private void putIntoMaps(Tab tab, SelectionBar selectionBar, SimulationView simulation, InputBox inputBox,
			WorkspaceView workspace, SavedCommandsView userCommands, ObservableList<VariableData> variables,
			ObservableList<FunctionData> functions)
	{

		selectionBarMap.put(tab, selectionBar);
		simulationMap.put(tab, simulation);
		inputBoxMap.put(tab, inputBox);
		workspaceMap.put(tab, workspace);
		savedCommandsMap.put(tab, userCommands);

		variableMap.put(tab, variables);
		functionMap.put(tab, functions);
	}

	private void setupObservers(SimulationView simulation, InputBox inputBox, FileTool file,
			SettingsTool settings, PreferencesView preferences)
	{

		file.addObservers(simulation);
		file.addObservers(inputBox);
		file.addObservers(this);

		settings.addObservers(simulation);
		settings.addObservers(this);
	}

	private void setupCommands(SingleLineInputBox inputBox)
	{
		inputBox.assignOnEnterCommand(e -> executeCommand(e, inputBox));
	}

	private void executeCommand(KeyEvent e, SingleLineInputBox inputBox)
	{
		if (e.getCode() == KeyCode.ENTER) {
			inputBox.enterAction(e);

			if (inputBox.getCurrentCommand() != null) {
				runCommand(inputBox, inputBox.getCurrentCommand());

			}
			inputBox.appendText("\n" + Double.toString(printValue));
			inputBox.appendPreamble();
		}
		if (e.getCode() == KeyCode.UP) {
			inputBox.upAction(e);
		}
		if (e.getCode() == KeyCode.DOWN) {
			inputBox.downAction(e);
		}
		if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.BACK_SPACE) {
			inputBox.protectPreamble(e);
		}
	}



	private void runCommand(InputBox inputBox, String command){
		InstructionData data = new InstructionData(simulationMap.get(currentTab.get()), variableMap.get(currentTab.get()), functionMap.get(currentTab.get()), language.get(currentIndex.get()));

		try {
			Interpreter interpreter = new Interpreter(data);

			printValue = interpreter.parseAndRun(command);
			simulationMap.get(currentTab.get()).step();
			inputBox.updateData(command);
		} catch (SLogoException exception) {
			exception.displayAlert();
		}
	}

	private void openFile(File file)
	{
		try {
			String command = new String(Files.readAllBytes(Paths.get(file.getPath())));

			for (Tab t : root.getTabs()) {
				if (t.isSelected()) {
					runCommand(inputBoxMap.get(t), command);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file");
		} catch (IOException e) {
			Logger.getLogger(SingleLineInputBox.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}