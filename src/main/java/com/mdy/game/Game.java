package com.mdy.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;

import com.alibaba.fastjson.JSON;


/**
 * 坦克游戏界面
 */
public class Game extends JPanel {
    private static final long serialVersionUID = 3514701851303922198L;

    private static PrintWriter writer;

    static int mode;
    static boolean live;
    private Image OffScreenImage;
    public static Image array[] = new Image[23];
    //一般图像的大小
    private static final int width=60;
    private static final int height=60;
    //坦克的血量和弹药数
    static final int HP=60;
    static final int MP=60;
    //坦克的移动区域
    private static int screenWidth =1200;
    private static int screenHeight = 900;
    //坦克的移动
    static final int UP=3;
    static final int DOWN=0;
    static final int LEFT=1;
    static final int RIGHT=2;
    //图像的标志
//    private final static int walls=0;
//    private final static int steels=1;
    private final static int enemy1=0;
    private final static int enemy2=4;
    private final static int enemy3=8;
    private final static int play1=12;
    private final static int play2=16;
//    private final static int tankmissile=22;

    //存放地图上无法移动过的方块
    volatile static LinkedList<Rectangle> isNotMove = new LinkedList<>();

    volatile static LinkedList<Missile> missile = new LinkedList<>();

    volatile static LinkedList<Wall> wall = new LinkedList<>();

    public static LinkedList<Tank> MyTank = new LinkedList<>();
    public static LinkedList<Tank> tank = new LinkedList<>();//所有坦克(我方+敌方)
    static Map<Integer,Tank> ETank = new HashMap<>();


    /** 重画(刷新)线程-repaint */
    class Draw implements Runnable{
        public void run() {
            while(live){
                repaint();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 子弹移动-线程 */
    class MissileMove implements Runnable{
        public void run() {
            while(live){
                synchronized ("") {
                    for(int i=0;i<missile.size();++i){
                        if(!missile.isEmpty()){
                            if(missile.get(i).Move()&&live){
                                missile.remove(i);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    class Rescue_EMMY implements Runnable{
        public void run() {
        	while(live){
        		synchronized ("AQQ") {
        			if(e_rescue_time1 > 0) {
        				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        				if(--e_rescue_time1 <= 0) {
        					init_ETank(1);
        					e_rescue_time1 = -1;
        				};
        			}
        			if(e_rescue_time2 > 0) {
        				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        				if(--e_rescue_time2 <= 0) {
        					init_ETank(2);
        					e_rescue_time2 = -1;
        				};
        			}
        			if(e_rescue_time3 > 0) {
        				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        				if(--e_rescue_time3 <= 0) {
        					init_ETank(3);
        					e_rescue_time3 = -1;
        				};
        			}
        		}
        		try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    			
    		}
        }
    }
    
    

    /**
     * 监听按键
     */
    private class KeyBoardListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key<65){
                if(key!=KeyEvent.VK_SHIFT&&!MyTank.isEmpty()){
                    MyTank.getFirst().key=key;
                    MyTank.getFirst().move=true;
                }
            }else{
                if(key!=KeyEvent.VK_G&&!MyTank.isEmpty()){
                    switch (key){
                        case KeyEvent.VK_W:key = KeyEvent.VK_UP;break;
                        case KeyEvent.VK_A:key = KeyEvent.VK_LEFT;break;
                        case KeyEvent.VK_S:key = KeyEvent.VK_DOWN;break;
                        case KeyEvent.VK_D:key = KeyEvent.VK_RIGHT;break;
                    }
                    MyTank.getLast().key=key;
                    MyTank.getLast().move=true;
                }
            }
        }
        public void keyReleased(KeyEvent e){
            super.keyReleased(e);
            int key = e.getKeyCode();
            if(key<65){
                if(!MyTank.isEmpty()){
                    if(key!=KeyEvent.VK_SHIFT&&key==MyTank.getFirst().key){
                        MyTank.getFirst().move=false;
                    }else{
                        MyTank.getFirst().GetKey(key);
                        if(mode==4){
                            writer.println(Integer.toString(key));
                        }
                        if(mode==3){
                        	System.out.println("坦克mode = 3");
                        }
                    }
                }
            }else{
                switch (key){
                    case KeyEvent.VK_W:key = KeyEvent.VK_UP;break;
                    case KeyEvent.VK_A:key = KeyEvent.VK_LEFT;break;
                    case KeyEvent.VK_S:key = KeyEvent.VK_DOWN;break;
                    case KeyEvent.VK_D:key = KeyEvent.VK_RIGHT;break;
                    case KeyEvent.VK_G:key = KeyEvent.VK_SHIFT;break;
                }
                if(!MyTank.isEmpty()){
                    if(key!=KeyEvent.VK_SHIFT&&key==MyTank.getLast().key){
                        MyTank.getLast().move=false;
                    }else{
                        MyTank.getLast().GetKey(key);
                        if(mode==4){
                            writer.println(Integer.toString(key));
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化敌方坦克
     */
    static void init_ETank(){
    	init_ETank(1);
    	init_ETank(2);
    	init_ETank(3);
    	init_ETank(4);
    }
    
    
    static void init_ETank(int t_code){
    	
        Coordination EB1 = new Coordination(height, width);
        Coordination EB2 = new Coordination(60,60);
        if(t_code == 1 && !ETank.containsKey(1)){
            Tank t = new Tank(EB2.x,EB2.y,DOWN,enemy1,20);
            ETank.put(1 , t);
            tank.add(t);
        }
        if(t_code == 2 && !ETank.containsKey(2)){
            Tank t = new Tank(screenWidth/2,EB2.y,DOWN,enemy2,20);
            ETank.put(2 , t);
            tank.add(t);
        }
        if(t_code == 3 && !ETank.containsKey(3)){
            Tank t = new Tank(screenWidth-120,EB2.y,DOWN,enemy3,20);
            ETank.put(3 , t);
            tank.add(t);
        }
//        if(!ETank.containsKey(4)){
//            Tank t = new Tank(EB1.x,EB1.y,DOWN,enemy3,20);
//            ETank.put(4 , t);
//            tank.add(t);
//        }
    }


    static int e_rescue_time1 = -1;//敌方坦克死后，3秒再复活
    static int e_rescue_time2 = -1;//敌方坦克死后，3秒再复活
    static int e_rescue_time3 = -1;//敌方坦克死后，3秒再复活

    
    
    

    private static void init_Tank(int mode){
    	System.out.println(screenWidth);//1200
    	System.out.println(screenHeight);//900
        Tank p1 = new Tank(600,600,UP,play1,0);
        p1.speed=20;
        p1.hp = 3000;
        p1.mp = 3000;
        tank.add(p1);
        MyTank.add(p1);
        if(mode==2){
            Tank p2 = new Tank(300,100,DOWN,play2,0);
            p2.speed=20;
            tank.add(p2);
            MyTank.add(p2);
        }
    }

    /**
     * 初始化地图
     */
    private void init_map(){
        for(int i = 0; i< screenWidth /width; ++i){
            for(int j = 0; j< screenHeight /height-3; ++j){
                if(i==0||i== screenWidth /width-1||j==0||j== screenHeight /height-4){
                    wall.add(new Wall(i*width,j*height,1));
                }
            }
        }

        for(int i=1;i<16;++i){
            Wall t = new Wall(60*i,540,i&1);
            wall.add(t);
            if((i&1)==1){
                isNotMove.add(t.getRect());
            }
        }
    }


    

    /**
     * 重绘函数
     */
    synchronized public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Color c = g2.getColor();
        //绘画墙体
        for (Wall aWall : wall) {
            g2.drawImage(array[aWall.id], aWall.x, aWall.y, null);
        }

        //画坦克的身体
        for (Tank aTank : tank) {
            g2.drawImage(array[2 + aTank._direction + aTank.id], aTank.x, aTank.y, null);
            g2.setColor(Color.RED);
            g2.draw3DRect(aTank.x, aTank.y + 5, HP, 5, true);
            g2.fill3DRect(aTank.x, aTank.y + 5, aTank.hp, 5, true);
            g2.setColor(Color.BLUE);
            g2.draw3DRect(aTank.x, aTank.y + 10, MP, 5, true);
            g2.fill3DRect(aTank.x, aTank.y + 10, aTank.mp, 5, true);
            g2.setColor(c);
        }
        //子弹绘画
        for(int i=0;i<missile.size();++i){
            if(!missile.isEmpty())
                g2.drawImage(array[22],missile.get(i).x,missile.get(i).y,null);
        }
    }
    
    @Override
    synchronized public void update(Graphics g) {
    	System.out.println("画");
        super.update(g);
        if(OffScreenImage == null)
            OffScreenImage = this.createImage(screenWidth, screenHeight);
        Graphics goffscrenn = OffScreenImage.getGraphics();    //设置一个内存画笔颜色为前景图片颜色
        Color c = goffscrenn.getColor();    //还是先保存前景颜色
        goffscrenn.setColor(Color.BLACK);    //设置内存画笔颜色为绿色
        goffscrenn.fillRect(0, 0, screenWidth, screenHeight);    //画成图片，大小为游戏大小
        goffscrenn.setColor(c);    //还原颜色
        g.drawImage(OffScreenImage, 0, 0, null);    //在界面画出保存的图片
        paint(goffscrenn);    //把内存画笔调用给paint
    }

    public Game(int mode) {
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBounds(300, 100, 1200, 768);
        setLayout(null);
        Game.mode=mode;
        init_map();//初始化地形
        init_Tank(mode); //初始化自己的坦克
        addKeyListener(new KeyBoardListener());
        live=true;
        new Thread(new MissileMove()).start();
        new Thread(new Draw()).start();
        if(mode==1)init_ETank();//初始化敌人的坦克
        
        new Thread(new Rescue_EMMY()).start();//敌人坦克死后复活延迟
        
        
        
    }
    
}
