package Tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 
 * @author Dan
 *血块，吃了可以加生命值
 */
public class Blood {
	private int x,y;
	private TankClient tc;
	private boolean live=true;
	//移动轨迹，用一串点来模拟
	private int[][] pos={{300,400},{300,420},{300,450},{320,450},{330,450},{380,450},{400,470},{400,480},{420,480},{430,480}};
	int step=0;
	public void draw(Graphics g){
		if(!live) return;
		Color c=g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, 15, 15);
		g.setColor(c);
		move();
	}
	private void move() {
		step++;
		if(step==pos[step].length){
			step=0;
		}
		this.x=pos[step][0];
		this.y=pos[step][1];
	}
	public Rectangle getRect(){
		Rectangle r=new Rectangle(x, y, 15, 15);
		return r;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
}
