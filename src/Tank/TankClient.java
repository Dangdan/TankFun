package Tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import Tank.Tank.Direction;
/**
 * 
 * @author Dan
 *游戏主窗口
 */
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH=800;
	public static final int GAME_HEIGHT=600;
	
	
	Tank my=new Tank(600,500,true,Direction.STOP,this);
	List<Tank> enermies=new ArrayList<Tank>();
	List<Explode> explodes=new ArrayList<Explode>();
	List<Missile> missiles=new ArrayList<Missile>();
	Wall w=new Wall(200, 200, 400, 30, this);
	Blood bb=new Blood();
	
	Image offImage=null;
	//在主页面画出各个物体
	@Override
	public void paint(Graphics g) {
		//敌方坦克若消灭完，新增一批
		if(enermies.size()<=0){
			for (int i = 0; i < 7; i++) {
				Tank t=new Tank(50+40*(i-1), 90, false,Direction.D, this);
				this.enermies.add(t);
			}
		}
		
		g.setColor(Color.WHITE);
		//显示屏幕中子弹，爆炸，地方的数量以及我方生命值
		g.drawString("missiles:"+missiles.size(), 40, 40);
		g.drawString("explodes:"+explodes.size(), 40, 60);
		g.drawString("enermies:"+enermies.size(), 40, 80);
		g.drawString("mylife:"+my.getLife(), 40, 100);
		//子弹攻击
		for (int i = 0; i < missiles.size(); i++) {
			Missile m=missiles.get(i);
			m.draw(g);
			m.hitTank(enermies);
			m.hitTank(my);
			m.hitWall(w);
		}
		//爆炸
		for (int i = 0; i < explodes.size(); i++) {
			Explode e=explodes.get(i);
			e.draw(g);
		}
		
		//我方坦克
		my.draw(g);
		//血块
		bb.draw(g);
		my.eatBlood(bb);
		my.CollidesWithWall(w);
		my.CollidesWithTank(enermies);
		
		
		//敌方坦克
		for (int i = 0; i < enermies.size(); i++) {
			Tank ee=enermies.get(i);
			ee.draw(g);
			ee.CollidesWithWall(w);
			ee.CollidesWithTank(enermies);
		}
		
		//墙
		w.draw(g);
		
		
		
	}
	
	//刷新页面
	@Override
	public void update(Graphics g) {
		if(offImage==null){
			offImage=this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics offScreen=offImage.getGraphics();
		Color co=offScreen.getColor();
		offScreen.setColor(Color.DARK_GRAY);
		offScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		offScreen.setColor(co);
		paint(offScreen);
		g.drawImage(offImage, 0, 0, null);
		
		
	}
	//初始化主窗口
	public void lunch(){
		for (int i = 0; i < 10; i++) {
			Tank t=new Tank(50+40*(i-1), 90, false,Direction.D, this);
			this.enermies.add(t);
		}
		this.setLocation(50, 50);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.darkGray);
		new Thread(new paintThread()).start();
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
	}
	//主函数
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.lunch();
		
	}
	//移动的坦克
	private class paintThread implements Runnable{
		public void run() {
			//每隔一段时间重画刷新一次
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	//键盘监听类
	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			my.keyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			my.keyReleased(e);
		}
	}
}
