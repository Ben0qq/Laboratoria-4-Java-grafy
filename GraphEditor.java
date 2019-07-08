import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/* 
 *  Program: Edytor grafu kolorowego
 *     Plik: GraphEditor.java
 *            
 *            
 *    Autor: Damian Bednarz 241283
 *     Data:  listopad 2018 r.
 */


public class GraphEditor extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String APP_AUTHOR = "Autor: Damian Bednarz Data: listopad 2018";
	private static final String APP_TITLE = "Graf kolorowy";
	
	private static final String APP_INSTRUCTION =
			"                                            INSTRUKCJA \n\n" + 
	        "Aktywna klawisze:\n" +
			"   strza³ki ==> przesuwanie wszystkich kó³\n" +
			"   SHIFT + strza³ki ==> szybkie przesuwanie wszystkich kó³\n\n" +
			"ponadto gdy kursor wskazuje ko³o:\n" +
			"   DEL   ==> kasowanie ko³a lub krawêdzi\n" +
			"   r,y,b ==> zmiana koloru ko³a\n\n" +
			"Operacje myszka:\n" +
			"   przeci¹ganie ==> przesuwanie wszystkich kó³\n" +
			"   PPM ==> tworzenie nowego ko³a w niejscu kursora\n" +
	        "ponadto gdy kursor wskazuje ko³o:\n" +
	        "   przeci¹ganie ==> przesuwanie ko³a\n" +
			"   PPM ==> zmiana koloru ko³a, usuwanie ko³a, dodawanie krawêdzi, zmiana nazwy\n" +
			" ponadto gdy kursor wskazuje krawêdz:\n" +
	        "   przeci¹ganie ==> przesuwanie krawêdzi\n" +
			"   PPM ==> zmiana koloru krawêdzi\n" +
	        "                   lub usuwanie krawêdzi\n";
	
	public static void main(String[] args) {
		new GraphEditor();
	}
	
	private JMenuBar menuBar = new JMenuBar ();
	private JMenu menuGraph = new JMenu ("Graph");
	private JMenu menuHelp = new JMenu("Help");
	private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
	private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_E);
	private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_X);
	private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", KeyEvent.VK_L);
	private JMenuItem menuListOfEdges = new JMenuItem("List of Edges",  KeyEvent.VK_I);
	private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
	private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_S);
	
	private GraphPanel panel = new GraphPanel();
	
	public GraphEditor () {
		super(APP_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setContentPane(panel);
		createMenu();
		showBuildingExample();
		setVisible(true);
	}
	
	private void showListOfNodes (Graph graph) {
		Node array[] = graph.getNodes();
		int i = 0;
		StringBuilder message = new StringBuilder ("Liczba wêz³ów: " + array.length+"\n");
		for(Node node : array) {
			message.append(node + "    ");
			if(++i % 5 == 0) {
				message.append("\n");
			}
		}
		JOptionPane.showMessageDialog(this, message, APP_TITLE + " - lista wêz³ów", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void showListOfEdges (Graph graph) {
		Edge array[] = graph.getEdges();
		int i = 0;
		StringBuilder message = new StringBuilder ("Liczba krawêdzi: " + array.length+"\n");
		for(Edge edge : array) {
			message.append(edge + "    ");
			if(++i % 5 == 0) {
				message.append("\n");
			}
		}
		JOptionPane.showMessageDialog(this, message, APP_TITLE + " - lista krawêdzi", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void createMenu() {
			menuNew.addActionListener(this);
			menuShowExample.addActionListener(this);
			menuExit.addActionListener(this);
			menuListOfNodes.addActionListener(this);
			menuListOfEdges.addActionListener(this);
			menuAuthor.addActionListener(this);
			menuInstruction.addActionListener(this);
			
			menuGraph.add(menuNew);
			menuGraph.add(menuShowExample);
			menuGraph.addSeparator();
			menuGraph.add(menuListOfNodes);
			menuGraph.add(menuListOfEdges);
			menuGraph.addSeparator();
			menuGraph.add(menuExit);
			
			menuHelp.add(menuInstruction);
			menuHelp.add(menuAuthor);
			
			menuBar.add(menuGraph);
			menuBar.add(menuHelp);
			setJMenuBar(menuBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source==menuNew) {
			panel.setGraph(new Graph());
		}
		if(source==menuShowExample) {
			showBuildingExample();
		}
		if(source==menuListOfNodes) {
			showListOfNodes(panel.getGraph());
		}
		if(source==menuListOfEdges) {
			showListOfEdges(panel.getGraph());
		}
		if(source==menuInstruction) {
			JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
		}
		if(source==menuAuthor) {
			JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		if(source==menuExit) {
			System.exit(0);
		}
	}

	private void showBuildingExample () {
		Graph graph = new Graph ();
		Node n1 = new Node (100,100,Color.DARK_GRAY,"A");
		Node n2 = new Node (100,200,Color.RED,"B");
		Node n3 = new Node(200, 100,Color.GREEN,"C");
		Node n4 = new Node(200, 250,Color.PINK,"D");
		
		Edge e1 = new Edge (n1,n2,Color.BLACK);
		Edge e2 = new Edge (n3,n4,Color.MAGENTA);
		Edge e3 = new Edge (n2,n4,Color.BLACK);

		graph.addEdge(e1);
		graph.addEdge(e2);
		graph.addEdge(e3);
		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		panel.setGraph(graph);
	}
	
}
