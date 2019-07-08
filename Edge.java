import java.awt.Color;
import java.awt.Graphics;

/* 
 *  Program: Edytor grafu kolorowego
 *     Plik: Edge.java
 *            
 *            
 *    Autor: Damian Bednarz 241283
 *     Data:  listopad 2018 r.
 */


public class Edge {
	
	Node start;
	Node end;
	
	Color color;
	
	public Edge (Node s,Node e) {
		start=s;
		end=e;
		this.color=Color.BLACK;
	}

	public Edge(Node start, Node end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isMouseOver (int mx, int my) {
		double x1=start.getX();
		double y1=start.getY();
		double x2=end.getX();
		double y2=end.getY();
		double a,b;
		double w=x1-x2; // wyliczanie a i b prostej macierzami
		double wx=y1-y2;
		double wy=x1*y2-x2*y1;
		if(w!=0) {
			a=wx/w;
			b=wy/w;
		}else {
			if((mx<=(x1+2))&&(mx>=(x1-2)))
				return true;
			else {
				return false;
			}
		}
		if((mx>x1&&mx>x2)||(mx<x1&&mx<x2)) {
			if(Math.abs(x1-x2)>=2)
			return false;
		}
		if((my>y1&&my>y2)||(my<y1&&my<y2)) {
			if(Math.abs(y1-y2)>=2)
			return false;
		}
		if(((Math.abs(a*mx-my+b))/(Math.sqrt(a*a+1))<=5.0)) {
			return true;
		}
		return false;
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		g.setColor(Color.BLACK);
	}
	
	public String toString () {
		return (" " + start.toString() + " ==>" + end.toString() + ", " + String.format("%8X", color.getRGB()));
	}
}
