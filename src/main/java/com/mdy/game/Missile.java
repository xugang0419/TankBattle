package com.mdy.game;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.JOptionPane;

/** 子弹类 */
class Missile extends MyImage{
	
	private int direction;
	private final static int speed=10;
	private final static int damage=10;
	private int id;//坦克id，判断这个子弹是谁射出的
//	private Game game;
	
	Missile(int x,int y,int direction,int _id){
		super(x,y);
		this.height=17;
		this.width=17;
		this.direction=direction;
		this.id=_id;
//		this.game = game;
	}
	private synchronized boolean isMeet(){
		for(int i=0;i<Game.wall.size();++i){
			if(Game.wall.get(i).isIntersects(this)){
				if(Game.wall.get(i).id==0){
					Game.wall.remove(i);
				}
				return true;
			}
		}
		for(int i=0;i<Game.tank.size();++i){
			if(Game.tank.get(i).isIntersects(this)){//如果子弹遇到坦克：
				if(id!=Game.tank.get(i).id){
					Game.tank.get(i).hp-=damage;
				}
				if(Game.tank.get(i).hp<=0){
//					System.out.println("坦克"+i+"血量为"+Game.tank.get(i).hp);
					if(Game.tank.get(i).id<12){//敌人 0，4，8 | 队友 12，16
						//敌人血量低于0
						Collection<Tank> coil = Game.ETank.values();
						Iterator<Tank> it = coil.iterator();
						while(it.hasNext()){
							if(it.next().equals(Game.tank.get(i))){
//								Game.init_ETank();
								System.out.println("QQQ->"+Game.tank.get(i).id);
								if(Game.tank.get(i).id == 0) {
									Game.e_rescue_time1 = 3;
								}else if(Game.tank.get(i).id == 4) {
									Game.e_rescue_time2 = 3;
								}else if(Game.tank.get(i).id == 8) {
									Game.e_rescue_time3 = 3;
								}
								
								Game.tank.get(i).flag=false;
								Game.tank.remove(i);
								it.remove();
								break;
							}
						}
					}else{
						//自己或队友血量低于0时:
						Game.tank.get(i).flag=false;
						if(Game.mode!=3){// 3指的是AI
							if(Game.tank.get(i).equals(Game.MyTank.getFirst())){//1P 坦克死了
								Game.live=false;
								com.mdy.main.Main.live=false;
								for (Tank aTank : Game.tank) {
									aTank.flag = false;
								}
								Game.isNotMove.clear();
								Game.ETank.clear();
								Game.MyTank.clear();
								Game.tank.clear();
								Game.wall.clear();
								Game.missile.clear();
							}else{
								System.out.println("2P 坦克死了");
								
								Game.MyTank.remove(1);//去除2P
								Game.tank.remove(i);//从坦克列表中去除
								
							}
						}else{
							
							Game.tank.remove(i);
							if(Game.tank.size()==1){
								JOptionPane.showMessageDialog(null,String.valueOf(Game.tank.getFirst().id)+"win!!");
								com.mdy.main.Main.live=false;
								Game.live=false;
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	boolean Move(){
		if(direction==Game.UP){
			y-=speed;
			if(isMeet()){
				return true;
			}
		}
		if(direction==Game.DOWN){
			y+=speed;
			if(isMeet()){
				return true;
			}
		}
		if(direction==Game.LEFT){
			x-=speed;
			if(isMeet()){
				return true;
			}
		}
		if(direction==Game.RIGHT){
			x+=speed;
			return isMeet();
		}
		return false;
	}
}
