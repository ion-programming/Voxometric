package au.com.ionprogramming.voxometric;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	static final long serialVersionUID = 1L;
 
	private JPanel contentPane;
	private int width, height;
	private GameSpace game;

	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		game = new GameSpace(this.width, this.height);
		game.setBackground(Color.black);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, this.width + 6, this.height + 28);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(game);
		game.setVisible(true);
		game.init();
		setVisible(true);
	}
	public int getW(){
		return width;
	}
	public void setW(int width){
		this.width = width;
		setBounds(0, 0, this.width + 6, this.height + 28);
		game.setW(width);
	}
	public int getH(){
		return height;
	}
	public void setH(int height){
		this.height = height;
		setBounds(0, 0, this.width + 6, this.height + 28);
		game.setH(height);
	}
	public double getFPS(){
		return game.getFPS();
	}
	public void setFPS(double FPS){
		game.setFPS(FPS);
	}

}
