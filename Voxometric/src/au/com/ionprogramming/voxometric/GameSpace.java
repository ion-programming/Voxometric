package au.com.ionprogramming.voxometric;

import java.applet.Applet;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameSpace extends Applet implements Runnable, KeyListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	private Image dbImage;
	private Graphics dbg;
	private int width = 1024;
	private int height = 512;
	private double time;
	private double sleepTime;
	private double FPS = 60;
	private boolean forward = false;
	private boolean back = false;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private int angle = 0;
	private double cx = 0;
	private double cy = 0;
	private double cz = 0;
	
	private Chunk chunk = Generator.generate();
	
	public GameSpace(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(){
		setSize(width, height);
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		start();
	}
	
	public void start(){
		Thread th = new Thread(this);
		th.start();
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true){
			time = System.nanoTime();
			
			repaint();
			
			sleepTime = 1000/FPS - (System.nanoTime() - time)/1000000;
			try{
				if(sleepTime > 10){
					Thread.sleep((long) sleepTime);
				}
				else{ 
					Thread.sleep(10);
				}
			} 
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
			System.out.println("FPS: " + (int)(1000/((System.nanoTime() - time)/1000000)));
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public void paint(Graphics g){
//		TODO: Revise this structure
		move();
		
		chunk.render(g, width, height, angle, cx, cy, cz);
	}
	
	public void move(){
		if(left){
			cx += 0.2;
		}
		if(right){
			cx -= 0.2;
		}
		if(forward){
			cy += 0.2;
		}
		if(back){
			cy -= 0.2;
		}
		if(up){
			cz -= 0.2;
		}
		if(down){
			cz += 0.2;
		}
	}
	public void update (Graphics g){
		if(dbImage == null){
	        dbImage = createImage (this.getSize().width, this.getSize().height);
	        dbg = dbImage.getGraphics ();
	    }
	    dbg.setColor(getBackground());
	    dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
	    dbg.setColor(getForeground());	    
	    paint (dbg);	   
	    g.drawImage(dbImage, 0, 0, this);	    
	}
	
	

	public void mouseClicked(MouseEvent e){
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q){
			if(angle > 0){
				angle--;
			}
			else{
				angle = 3;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_E){
			if(angle < 3){
				angle++;
			}
			else{
				angle = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			forward = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			back = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			up = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			forward = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			back = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			up = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public int getW(){
		return width;
	}
	public void setW(int width){
		this.width = width;
		setSize(width, height);
	}
	public int getH(){
		return height;
	}
	public void setH(int height){
		this.height = height;
		setSize(width, height);
	}
	public double getFPS(){
		return FPS;
	}
	public void setFPS(double FPS){
		this.FPS = FPS;
	}
}
