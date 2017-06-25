package Tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import Tank.Tank.Direction;
/**
 * 
 * @author Dan
 *子弹类
 */
public class Missile {
	private static final int XSPEED = 30;//横向移动速度
	private static final int YSPEED = 30;//纵向移动速度
	
	public static final int WIDTH = 10;//子弹宽度
	public static final int HEIGHT = 10;//子弹高度
	
	int x,y;//位置
	Direction dir;//方向
	private boolean live=true;//是否存活
	private TankClient tc;
	private boolean good;//是我方子弹还是敌方子弹
	
	public boolean isGood() {
		return good;
	}
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x, int y, Direction dir,boolean good,TankClient tc) {
		this(x, y, dir);
		this.good=good;
		this.tc=tc;
	}
	
	public boolean isLive() {
		return live;
	}

	public void draw(Graphics g){
		if(!live){//若未存活，则不画
			tc.missiles.remove(this);
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
		
		
	}
	//移动
	private void move() {
		switch(dir){
		case L:
			x-=XSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case LU:
			x-=XSPEED;y-=YSPEED;
			break;
		case LD:
			x-=XSPEED;y+=YSPEED;
			break;
		case RU:
			x+=XSPEED;y-=YSPEED;
			break;
		case RD:
			x+=XSPEED;y+=YSPEED;
			break;
		
		}
		//越界则失效
		if(this.x<0||this.y<0||this.x>TankClient.GAME_WIDTH||this.y>TankClient.GAME_HEIGHT){
			live=false;
		}
		
	}
	//攻击坦克，产生爆炸
	public boolean hitTank(Tank t){
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
			if(t.isGood()){
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0){
					t.setLive(false);
				}
				
			}else{
				t.setLive(false);
			}
			this.live=false;
			Explode e=new Explode(x, y, this.tc);
			this.tc.explodes.add(e);
			return true;
		}
		return false;
	}
	//攻击多辆坦克
	public boolean hitTank(List<Tank> tanks){
		
		for (int i = 0; i < tanks.size(); i++) {
			Tank t=tanks.get(i);
			if(this.hitTank(t)){
				return true;
			}
			
		}
		return false;
		
	}
	//攻击到墙，子弹消失
	public boolean hitWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.live=false;
			return true;
		}
		return false;
	}
	public Rectangle getRect(){
		Rectangle r=new Rectangle(x, y, WIDTH, HEIGHT);
		return r;
	}
	
}
