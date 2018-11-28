package com.mdy.main;

import javax.swing.ImageIcon;

import com.mdy.game.Game;

public class Init {

	
	
	public static void init_image(){
		Game.array[0] = new ImageIcon(Main.class.getResource("/img/walls.gif")).getImage();
		Game.array[1] = new ImageIcon(Main.class.getResource("/img/steels.gif")).getImage();
		Game.array[2] = new ImageIcon(Main.class.getResource("/img/enemy1D.gif")).getImage();
		Game.array[3] = new ImageIcon(Main.class.getResource("/img/enemy1L.gif")).getImage();
		Game.array[4] = new ImageIcon(Main.class.getResource("/img/enemy1R.gif")).getImage();
		Game.array[5] = new ImageIcon(Main.class.getResource("/img/enemy1U.gif")).getImage();
		Game.array[6] = new ImageIcon(Main.class.getResource("/img/enemy2D.gif")).getImage();
		Game.array[7] = new ImageIcon(Main.class.getResource("/img/enemy2L.gif")).getImage();
		Game.array[8] = new ImageIcon(Main.class.getResource("/img/enemy2R.gif")).getImage();
		Game.array[9] = new ImageIcon(Main.class.getResource("/img/enemy2U.gif")).getImage();
		Game.array[10] = new ImageIcon(Main.class.getResource("/img/enemy3D.gif")).getImage();
		Game.array[11] = new ImageIcon(Main.class.getResource("/img/enemy3L.gif")).getImage();
		Game.array[12] = new ImageIcon(Main.class.getResource("/img/enemy3R.gif")).getImage();
		Game.array[13] = new ImageIcon(Main.class.getResource("/img/enemy3U.gif")).getImage();
		Game.array[14] = new ImageIcon(Main.class.getResource("/img/p1tankD.gif")).getImage();
		Game.array[15] = new ImageIcon(Main.class.getResource("/img/p1tankL.gif")).getImage();
		Game.array[16] = new ImageIcon(Main.class.getResource("/img/p1tankR.gif")).getImage();
		Game.array[17] = new ImageIcon(Main.class.getResource("/img/p1tankU.gif")).getImage();
		Game.array[18] = new ImageIcon(Main.class.getResource("/img/p2tankD.gif")).getImage();
		Game.array[19] = new ImageIcon(Main.class.getResource("/img/p2tankL.gif")).getImage();
		Game.array[20] = new ImageIcon(Main.class.getResource("/img/p2tankR.gif")).getImage();
		Game.array[21] = new ImageIcon(Main.class.getResource("/img/p2tankU.gif")).getImage();
		Game.array[22] = new ImageIcon(Main.class.getResource("/img/tankmissile.gif")).getImage();
	}
	
	
}
