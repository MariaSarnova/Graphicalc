import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class CalculatorView  implements ActionListener{

	protected JFrame frame;
	protected JPanel display;
	protected JPanel button;
	protected JPanel graph;
	protected JPanel graphDisplay;
	protected JTabbedPane tabs;
	protected JTextArea inputEquation;
	protected JTextArea equationDisplay;
	protected JTextArea graphEquation;
	protected CalculatorController calcControl;
	protected Graphics2D g;
	protected Font displayFont;

	protected void createTabs() {
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.addTab("Equations", display);
		tabs.addTab("Graph", graph);
		tabs.setVisible(true);
		frame.add(tabs);

		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.addTab("Keyboard", button);
		tabs.setVisible(true);
		frame.add(tabs);
	}

	protected void createFrame()
	{
		frame = new JFrame("Graphicalc");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 2));
		frame.setSize(1250, 720);
	}

	protected void createGraphPanel()
	{
		graph = new JPanel();
		graph.setLayout(null);
		graph.setVisible(true);
		frame.add(graph, BorderLayout.WEST);
		addToGraphPanel();

	}
	protected void createDisplayPanel()
	{
		display = new JPanel();
		display.setLayout(null);
		frame.add(display, BorderLayout.WEST);
		addToDisplayPanel();
	}

	protected void addToDisplayPanel()
	{
		inputEquation = new JTextArea("Enter equation here: ", 3, 5);
		inputEquation.setLineWrap(true);
		inputEquation.setEditable(false);
		inputEquation.setFont(displayFont);
		inputEquation.setBounds(0, 0, 600, 50);
		display.add(inputEquation);
		equationDisplay = new JTextArea("Previous Equations: \n", 3, 5);
		equationDisplay.setLineWrap(true);
		equationDisplay.setEditable(false);
		equationDisplay.setFont(displayFont);
		equationDisplay.setBounds(0, 60, 600, 600);
		display.add(equationDisplay);

	}


	public CalculatorView()
	{
		displayFont = new Font("Dialogue", Font.PLAIN, 18);

		createFrame();
		createGraphPanel();
		createDisplayPanel();
		createButtonPanel();
		createTabs();


		calcControl = new CalculatorController();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		g = (Graphics2D) graphDisplay.getGraphics();
	}

	protected void createButtonPanel() {
		button = new JPanel();
		button.setLayout(new GridLayout(6, 6));
		frame.add(button, BorderLayout.EAST);
		addButtonsToButtonPanel();
	}



	protected void addButtonsToButtonPanel() {
		ArrayList<JButton> buttonList= new ArrayList<JButton>();

		JButton plus = new JButton("+");
		buttonList.add(plus);
		JButton minus = new JButton("-");
		buttonList.add(minus);
		JButton multiply = new JButton("*");
		buttonList.add(multiply);
		JButton divide = new JButton("/");
		buttonList.add(divide);
		JButton openParen = new JButton("(");
		buttonList.add(openParen);
		JButton closeParen = new JButton(")");
		buttonList.add(closeParen);

		JButton seven = new JButton(Integer.toString(7));
		buttonList.add(seven);
		JButton eight = new JButton(Integer.toString(8));
		buttonList.add(eight);
		JButton nine = new JButton(Integer.toString(9));
		buttonList.add(nine);
		JButton power = new JButton("^");
		buttonList.add(power);
		JButton squared = new JButton("x^2");
		buttonList.add(squared);
		JButton sqrt = new JButton("sqrt");
		buttonList.add(sqrt);

		JButton four = new JButton(Integer.toString(4));
		buttonList.add(four);
		JButton five = new JButton(Integer.toString(5));
		buttonList.add(five);
		JButton six = new JButton(Integer.toString(6));
		buttonList.add(six);
		JButton sine = new JButton("sin()");
		buttonList.add(sine);
		JButton cosine = new JButton("cos()");
		buttonList.add(cosine);
		JButton tan = new JButton("tan()");
		buttonList.add(tan);

		JButton one = new JButton(Integer.toString(1));
		buttonList.add(one);
		JButton two = new JButton(Integer.toString(2));
		buttonList.add(two);
		JButton three = new JButton(Integer.toString(3));
		buttonList.add(three);
		JButton pi = new JButton("pi");
		buttonList.add(pi);
		JButton ln = new JButton("ln()");
		buttonList.add(ln);
		JButton e = new JButton("e");
		buttonList.add(e);

		JButton period = new JButton(".");
		buttonList.add(period);
		JButton zero = new JButton(Integer.toString(0));
		buttonList.add(zero);
		JButton negative = new JButton("(-)");
		buttonList.add(negative);
		JButton x = new JButton("x");
		buttonList.add(x);
		JButton enter = new JButton("Enter");
		buttonList.add(enter);
		JButton graph = new JButton("Graph");
		buttonList.add(graph);

		JButton delete = new JButton("Delete");
		buttonList.add(delete);
		JButton clear = new JButton("Clear");
		buttonList.add(clear);
		JButton clearAll = new JButton("<html>"+"Clear"+"<br>"+"All"+"<html>");
		buttonList.add(clearAll);
		JButton clearGraph = new JButton("<html>"+"Clear"+"<br>"+"Graph"+"<html>");
		buttonList.add(clearGraph);

		Font f = new Font("Dialogue", Font.PLAIN, 22);

		for(int j = 0; j < buttonList.size(); j++){
			JButton temp = buttonList.get(j);
			temp.setFont(f);
			temp.setActionCommand(temp.getText());
			temp.addActionListener(this);
			button.add(temp);
		}
	}

	protected void addToGraphPanel() {
		graphEquation = new JTextArea("Graph: Y = ", 600, 50);
		graphEquation.setEditable(false);
		graphEquation.setFont(displayFont);
		graphEquation.setBounds(0, 0, 600, 50);
		graph.add(graphEquation);

		graphDisplay = new JPanel();
		graphDisplay.setVisible(true);
		graphDisplay.setLayout(null);
		graphDisplay.setBounds(0, 50, 650, 650);
		graph.add(graphDisplay);
	}

	public void actionPerformed(ActionEvent arg0) {
		String result = arg0.getActionCommand();
		String[] fullEquation;
		String[] newText;

		if (result.equals("Enter")) {

			fullEquation = calcControl.update("Enter");
			String eq = fullEquation[0];
			String sol = fullEquation[1];

			equationDisplay.insert("\n", 22);
			equationDisplay.insert(sol, 22);
			equationDisplay.insert(" = ", 22);
			equationDisplay.insert(eq, 22);
			equationDisplay.insert("\n", 22);
			equationDisplay.insert("\n", 22);
			inputEquation.setText("");

			// If the list of equations gets longer than the given screen size clear the
			// screen of previous equations and start over
			if (equationDisplay.getLineCount() > 24) {
				equationDisplay.setText(eq + " = " + sol);
				equationDisplay.append("\n");
			}
		}
		// If the user pushes the "Graph" button graph the equation if they are on the graphPanel
		else if (result.equals("Graph")) {
			if (graphDisplay.isShowing()) {
				String[] coordinates = calcControl.update("Graph");
				drawPoints(coordinates);
			}
		}
		// If the user pushes "Clear All" reset all text areas to their original state
		else if (result.equals("<html>"+"Clear"+"<br>"+"All"+"<html>")) {
			newText = calcControl.update(result);
			inputEquation.setText(newText[0]);
			graphEquation.setText(newText[0]);
			equationDisplay.setText("Previous equations: ");
		}
		// If the user pushes "Clear Graph" reset the graph if it is showing
		else if (result.equals("<html>"+"Clear"+"<br>"+"Graph"+"<html>")) {
			if (graphDisplay.isShowing()) {
				clearGraph();
				drawGrid();
			}
		}
		// Otherwise add the input to the equation stored in the model
		else {
			newText = calcControl.update(result);
			inputEquation.setText(newText[0]);
			graphEquation.setText(newText[0]);
		}
	}




	public void drawGrid() {
		g.setColor(Color.gray);
		int boxSize = 30;
		

		for (int i=0; i<=20; i++) {
			if (i%10==0) g.setStroke(new BasicStroke(3));
			g.drawLine(boxSize*i, 0, boxSize*i, 600);
			g.drawLine(0, boxSize*i, 600, boxSize*i);
			g.setStroke(new BasicStroke(1));
		}
	}
	

	public void drawPoints(String[] coordinates) {
        drawGrid();
        for (int j=0; j<coordinates.length-1; j++){
        	g.setColor(Color.red);
        	g.drawLine(j,300-Double.valueOf(coordinates[j]).intValue(),j+1,300-Double.valueOf(coordinates[j+1]).intValue());
        }
	}
	

	public void clearGraph() {
		g.clearRect(0,0,600,600);
	}
}
