package Tank;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author Dan
 *爆炸
 */
public class Explode {
	int x,y;
	private boolean live=true;
	
	int[] radius={1,2,4,8,16,32,64,40,30,8,2};//爆照用不同圆的直径来演示
	int step=0;
	
	TankClient tc=null;

	public Explode(int x, int y) {
		this.x = x;
		this.y = y;

	}
	
	public Explode(int x, int y,TankClient tc) {
		this(x, y);
		this.tc=tc;
	}
	
	public void draw(Graphics g){
		if(!live){
			tc.explodes.remove(this);
			return;
		}
		if(step==radius.length){
			live=false;
			step=0;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, radius[step], radius[step]);
		g.setColor(c);
		step++;
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
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	
	
	

}
