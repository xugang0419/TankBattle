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
	private static final long serialVersionUID = 7108333676029034947L;
	
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
		
		game = new Game(mode);
		play.setContentPane(game);
		play.setBounds(game.getBounds());
		play.setVisible(true);
		play.setResizable(false);
		game.requestFocus();
		new Thread(new CheckLive()).start();
	}

    /** 检查游戏是否结束，如果是，则卸载当前页面 */
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
		Init.init_image();
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
