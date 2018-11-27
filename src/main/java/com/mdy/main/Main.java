package com.mdy.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.mdy.game.Game;

/**
 * 选择模式界面，主界面
 */
public class Main extends JFrame {

    private JFrame play;
    private Game game = null;
    public static boolean live;
	private static int PlayTime=0;


	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setResizable(false);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

    private void play(int mode){
		play = new JFrame("Live"+":"+String.valueOf(PlayTime++)+"s");
		live=true;
		play.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(false);
		if(mode<4){
			game = new Game(mode);
		}
//		else if(mode==4){
//			game = new Game(mode, client.socket);
//		}
		play.setContentPane(game);
		play.setBounds(game.getBounds());
		play.setVisible(true);
		play.setResizable(false);
		game.requestFocus();
		new Thread(new CheckLive()).start();
	}

    private void init_image(){
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

	class CheckLive implements Runnable{
		public void run(){
			while(live){
				play.setTitle("TankBattle"+" Live"+String.valueOf(PlayTime++)+"s");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			play.dispose();
			play.remove(game);
			play=null;
			game=null;
			setVisible(true);
		}
	}

    private Main() {
		init_image();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-600)/3,(screenSize.height-600)/3 ,600 , 600);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		getContentPane().add(panel);
		panel.setLayout(null);


		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/img/title.gif")));
		lblNewLabel.setBounds(10, 10, 577, 213);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(224, 243, 144, 34);
		btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/img/SinglePlayer.gif")));
		btnNewButton.addActionListener(e ->play(1));
		btnNewButton.setBorderPainted(false);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(224, 298, 144, 34);
		btnNewButton_1.setIcon(new ImageIcon(Main.class.getResource("/img/DoublePlayer.gif")));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.addActionListener(e -> play(2));
		panel.add(btnNewButton_1);
		
	}
}
