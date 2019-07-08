import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/* 
 *  Program: Edytor grafu kolorowego
 *     Plik: GraphPanel.java
 *            
 *            
 *    Autor: Damian Bednarz 241283
 *     Data:  listopad 2018 r.
 */


public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	protected Graph graph;
	
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean mouseButtonLeft = false;
	@SuppressWarnings("unused")
	private boolean mouseButtonRight = false;
	protected int mouseCursor = Cursor.DEFAULT_CURSOR;
	protected Node nodeUnderCursor = null;
	protected Edge edgeUnderCursor = null;
	protected Node nodeForEdge = null;
	
	public GraphPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph (Graph graph) {
		this.graph=graph;
	}
	
	private Node findNode (int mx, int my) {
		for(Node node:graph.getNodes()) {
			if(node.isMouseOver(mx, my))
				return node;
		}
		return null;
	}
	
	private Edge findEdge (int mx, int my) {
		for(Edge edge:graph.getEdges()) {
			if(edge.isMouseOver(mx, my))
				return edge;
		}
		return null;
	}
	
	private Node findNode (MouseEvent event) {
		return findNode(event.getX(),event.getY());
	}
	
	private Edge findEdge (MouseEvent event) {
		return findEdge(event.getX(),event.getY());
	}
	
	protected void setMouseCursor(MouseEvent event) {
		nodeUnderCursor = findNode(event);
		edgeUnderCursor = findEdge(event);
		if (nodeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		}else if (edgeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		} else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		mouseX = event.getX();
		mouseY = event.getY();
	}
	
	protected void setMouseCursor() {
		nodeUnderCursor = findNode(mouseX,mouseY);
		edgeUnderCursor = findEdge(mouseX,mouseY);
		if (nodeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		}else if (edgeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		} else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
	}	

	private void moveNode (int dx, int dy, Node node) {
		node.setX(node.getX()+dx);
		node.setY(node.getY()+dy);
	}
	
	private void moveEdge(int dx, int dy, Edge edge) {
        this.moveNode(dx, dy, edge.getStart());
        this.moveNode(dx, dy, edge.getEnd());
    }
	
	private void moveAllNodes (int dx,int dy) {
		for(Node node:graph.getNodes())
			moveNode(dx,dy,node);
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		if(graph==null) return;
		graph.draw(g);
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		{  int dist;
	       if (event.isShiftDown()) dist = 10;
	                         else dist = 1;
			switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				moveAllNodes(-dist, 0);
				break;
			case KeyEvent.VK_RIGHT:
				moveAllNodes(dist, 0);
				break;
			case KeyEvent.VK_UP:
				moveAllNodes(0, -dist);
				break;
			case KeyEvent.VK_DOWN:
				moveAllNodes(0, dist);
				break;
			case KeyEvent.VK_DELETE:
				if (nodeUnderCursor != null) {
					graph.removeNode(nodeUnderCursor);
					nodeUnderCursor = null;
				}
				if (edgeUnderCursor != null) {
					graph.removeEdge(edgeUnderCursor);
					edgeUnderCursor = null;
				}
				break;
			case KeyEvent.VK_R:
				nodeUnderCursor.setColor(Color.RED);
				break;
			case KeyEvent.VK_B:
				nodeUnderCursor.setColor(Color.BLUE);
				break;
			case KeyEvent.VK_Y:
				nodeUnderCursor.setColor(Color.YELLOW);
				break;
			}
			
		}
		repaint();
		setMouseCursor();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mouseButtonLeft) {
			if(nodeUnderCursor!=null) {
				moveNode(e.getX() - mouseX,e.getY() - mouseY, nodeUnderCursor);
			}else if(edgeUnderCursor!=null){
				moveEdge(e.getX() - mouseX,e.getY() - mouseY, edgeUnderCursor);
			}else {
				moveAllNodes(e.getX() - mouseX,e.getY() - mouseY);
			}
		}
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		setMouseCursor(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		setMouseCursor(e);
		if(e.getButton()==1) {
			if(nodeForEdge != null && nodeUnderCursor != null && nodeUnderCursor != nodeForEdge) {
				this.graph.addEdge(new Edge(nodeForEdge,nodeUnderCursor));
				nodeForEdge=null;
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1) mouseButtonLeft=true;
		if(e.getButton()==3) mouseButtonRight=true;
		setMouseCursor(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1) mouseButtonLeft=false;
		if(e.getButton()==3) mouseButtonRight=false;
		setMouseCursor(e);
		if (this.nodeForEdge != null) {
            return;
        }
		if(e.getButton()==3) {
			if(nodeUnderCursor!=null) {
				createPopupMenu(e,nodeUnderCursor);
			}else if (edgeUnderCursor!=null) {
				createPopupMenu(e,edgeUnderCursor);
			}else {
				createPopupMenu(e);
			}
		}
	}
	
	protected void createPopupMenu(MouseEvent event) {
		JMenuItem menuItem;
		
		JPopupMenu popupMenu = new JPopupMenu();
		menuItem = new JMenuItem ("CreateNewNode");
		
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				graph.addNode(new Node(event.getX(), event.getY()));
				repaint();
			}
		});
		popupMenu.add(menuItem);
		popupMenu.show(event.getComponent(), event.getX(), event.getY());
	}
	
	protected void createPopupMenu (MouseEvent event, Edge edge) {
		JMenuItem menuItem,removeEdge;

		JPopupMenu popupMenu = new JPopupMenu();
		menuItem = new JMenuItem("Change edge color");
		removeEdge = new JMenuItem ("Remove Edge");
		
		menuItem.addActionListener((a) -> {
			Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    edge.getColor());
			if (newColor!=null){
				edge.setColor(newColor);
			}
			repaint();
		});
		
		removeEdge.addActionListener((a)-> {
			graph.removeEdge(edge);
			repaint();
		});
		
		popupMenu.add(menuItem);
		popupMenu.add(removeEdge);
		popupMenu.show(event.getComponent(), event.getX(), event.getY());
	}
	
	protected void createPopupMenu (MouseEvent event, Node node) {
		JMenuItem nodeColor, newEdge, removeNode, changeName;

		JPopupMenu popupMenu = new JPopupMenu();
		nodeColor = new JMenuItem("Change node color");
		newEdge = new JMenuItem("Create new edge");
		removeNode = new JMenuItem("Remove node");
		changeName = new JMenuItem("Change node name");
		
		nodeColor.addActionListener((a) -> {
			Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    node.getColor());
			if (newColor!=null){
				node.setColor(newColor);
			}
			repaint();
		});
		
		newEdge.addActionListener((a) -> {
			nodeForEdge = node;
		});
		
		removeNode.addActionListener((a)->{
			graph.removeNode(node);
			repaint();
		});
		
		changeName.addActionListener((a)->{
			String name = JOptionPane.showInputDialog(this, "Choose name");
			node.setName(name);
			repaint();
		});
		
		popupMenu.add(nodeColor);
		popupMenu.add(newEdge);
		popupMenu.add(removeNode);
		popupMenu.add(changeName);
		popupMenu.show(event.getComponent(), event.getX(), event.getY());
	}
}
