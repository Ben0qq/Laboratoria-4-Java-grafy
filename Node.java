import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/* 
 *  Program: Edytor grafu kolorowego
 *     Plik: Node.java
 *            
 *            
 *    Autor: Damian Bednarz 241283
 *     Data:  listopad 2018 r.
 */

public class Node {
	
	protected int x;
	protected int y;
	private static final Font font = new Font("SansSerif", 1, 16);
	protected int r;
	
	private Color color;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Node(int x,int y) {
		this.x=x;
		this.y=y;
		this.r=20;
		this.color=Color.WHITE;
		this.name="?";
	}

	public Node(int x,int y, Color color, String name) {
		this.x=x;
		this.y=y;
		this.r=20;
		this.color=color;
		this.name=name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isMouseOver(int mx, int my){
		return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x-20, y-20, 40, 40);
		g.setColor(Color.BLACK);
		g.drawOval(x-20, y-20, 40, 40);
		g.setFont(font);
		FontRenderContext fontReader = ((Graphics2D)g).getFontRenderContext();
        Rectangle2D frame = font.getStringBounds(this.name, fontReader);
		g.drawString(name, x - (int)frame.getWidth()/2, y + (int)frame.getHeight()/4);
		
	}
	
	@Override
	public String toString(){
		return String.format("%s(%d,%d,%8X)",name,x,y,color.getRGB());
	}
	
}
