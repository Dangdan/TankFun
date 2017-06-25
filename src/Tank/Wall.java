package Tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 
 * @author Dan
 *墙
 */
public class Wall {
	int x,y,w,h;
	TankClient tc;
	
	
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
	}
	public Rectangle getRect(){
		Rectangle r=new Rectangle(x, y, w, h);
		return r;
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
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public TankClient getTc() {
		return tc;
	}
	public void setTc(TankClient tc) {
		this.tc = tc;
	}
	
	
	

}
