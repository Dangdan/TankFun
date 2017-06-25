package Tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Dan
 *坦克
 */
public class Tank {
	public static final int XSPEED=5; //横向移动速度
	public static final int YSPEED=5; //纵向移动速度
	public static final int WIDTH = 30; //坦克宽度
	public static final int HEIGHT = 30; //坦克高度
	/*
	 * 坦克的位置
	 */
	private int x;
	private int y;
	/*
	 * 坦克上一位置
	 */
	private int oldX;
	private int oldY;
	/*
	 * 坦克是我军还是敌军
	 */
	private boolean good;
	//坦克方向
	enum Direction{L,R,U,D,LU,LD,RU,RD,STOP};
	//坦克是否存活
	private boolean live=true;
	//生命值
	private int life=100;
	
	private BloodBar bb=new BloodBar(); 
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	private static Random rd=new Random();
	private  int step=rd.nextInt(12)+3;
	
	private Direction dir=Direction.STOP;
	private Direction ptdir=dir;//炮筒方向
	private boolean KeyL=false,KeyR=false,KeyU=false,KeyD=false;
	
	TankClient tc;
	public Tank(int x, int y,boolean good ,Direction dir,TankClient tc) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.tc=tc;
		this.dir=dir;
		this.good=good;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}
	
	public void draw(Graphics g){
		if(!live)	{
			if(!good){
				this.tc.enermies.remove(this);
				return;
			}
			return;
		}
		Color c=g.getColor();
		if(this.good) g.setColor(Color.PINK);
		else g.setColor(Color.blue);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		switch(ptdir){
		
		case L:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x, y+this.HEIGHT/2);
			break;
		case R:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x+this.WIDTH, y+this.HEIGHT/2);
			break;
		case U:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x+this.WIDTH/2, y);
			break;
		case D:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x+this.WIDTH/2, y+this.HEIGHT);
			break;
		case LU:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x, y);
			break;
		case LD:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x, y+this.HEIGHT);
			break;
		case RU:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x+this.WIDTH, y);
			break;
		case RD:
			g.drawLine(x+this.WIDTH/2, y+this.HEIGHT/2, x+this.WIDTH, y+this.HEIGHT);
			break;
		}
		if(this.dir!=Direction.STOP){
			this.ptdir=this.dir;
		}
		g.setColor(c);
		
		if(good) bb.draw(g);
		move();
	}
	//到上一步
	private void stay(){
		x=oldX;
		y=oldY;
	}
	//移动
	private void move() {
		this.oldX=x;
		this.oldY=y;
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
		case STOP:
			break;
		}
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+this.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-this.WIDTH;
		if(y+this.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-this.HEIGHT;
		
		if(!good){
			Direction[] dirs=Direction.values();
			if(step==0){
				int rn=rd.nextInt(dirs.length);
				this.dir=dirs[rn];
				step=rd.nextInt(12)+3;
			}
			step--;
			if(rd.nextInt(40)>38){
				this.fire();
			}
			
		}
	
	}
	
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live=true;
				this.setLife(100);
			}
			break;
		case KeyEvent.VK_LEFT:
			KeyL=true;
			break;
		case KeyEvent.VK_UP:
			KeyU=true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyR=true;
			break;
		case KeyEvent.VK_DOWN:
			KeyD=true;
			break;
				
		}
		location();
	}
	public boolean isGood() {
		return good;
	}

	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			KeyL=false;
			break;
		case KeyEvent.VK_UP:
			KeyU=false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyR=false;
			break;
		case KeyEvent.VK_DOWN:
			KeyD=false;
			break;
		case KeyEvent.VK_A:
			superfire();
			break;
				
		}
		location();
	}
	void location(){
		if(KeyL&&!KeyR&&!KeyD&&!KeyU) dir=Direction.L;
		else if(!KeyL&&KeyR&&!KeyD&&!KeyU) dir=Direction.R;
		else if(!KeyL&&!KeyR&&KeyD&&!KeyU) dir=Direction.D;
		else if(!KeyL&&!KeyR&&!KeyD&&KeyU) dir=Direction.U;
		else if(KeyL&&!KeyR&&!KeyD&&KeyU) dir=Direction.LU;
		else if(KeyL&&!KeyR&&KeyD&&!KeyU) dir=Direction.LD;
		else if(!KeyL&&KeyR&&!KeyD&&KeyU) dir=Direction.RU;
		else if(!KeyL&&KeyR&&KeyD&&!KeyU) dir=Direction.RD;
		else if(!KeyL&&!KeyR&&!KeyD&&!KeyU) dir=Direction.STOP;
		
	}
	//发射子弹
	public Missile fire(){
		if(!this.live) return null;
		Missile m=new Missile(x, y, this.ptdir,this.good,this.tc);
		m.x=this.x+this.WIDTH/2-m.WIDTH/2;
		m.y=this.y+this.HEIGHT/2-m.HEIGHT/2;
		this.tc.missiles.add(m);
		return m;
	}
	//朝某方向发射
	public Missile fire(Direction dir){
		if(!this.live) return null;
		Missile m=new Missile(x, y, dir,this.good,this.tc);
		m.x=this.x+this.WIDTH/2-m.WIDTH/2;
		m.y=this.y+this.HEIGHT/2-m.HEIGHT/2;
		this.tc.missiles.add(m);
		return m;
	}
	//朝八方发射
	public void superfire(){
		Direction[] dirs=Direction.values();
		for (int i = 0; i < dirs.length-1; i++) {
			fire(dirs[i]);
		}
	}
	
	public Rectangle getRect(){
		Rectangle r=new Rectangle(x, y, WIDTH, HEIGHT);
		return r;
	}
	//与墙相撞，回到上一步
	public boolean CollidesWithWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	//与坦克相撞，回到上一步
	public boolean CollidesWithTank(List<Tank> tanks){
		for (int i = 0; i < tanks.size(); i++) {
			Tank t=tanks.get(i);
			if(this!=t){
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		
		return false;
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
	//内部类血条
	private class BloodBar{
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawRect(x-1, y-10,WIDTH, 10);
			int w=WIDTH*life/100;
			g.fillRect(x-1, y-10, w, 10);
		}
	}
	//吃血块
	public boolean eatBlood(Blood b){
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
			this.life=100;
			b.setLive(false);
			return true;
		}
		return false;
	}

}
