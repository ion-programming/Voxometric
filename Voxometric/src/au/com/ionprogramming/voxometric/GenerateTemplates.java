package au.com.ionprogramming.voxometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class GenerateTemplates{


	static int BLOCK_HEIGHT = 20;
	static int BLOCK_WIDTH = 40;
	
	static double sideLength;
	static double phi;
	static Polygon topPoly;
	static Polygon leftPoly;
	static Polygon rightPoly;
	static Color outline = new Color(0, 0, 0, 1f);
	
	public static void main(String[] args){
		
		System.out.println("Test");
		
		
		setBlockSize(BLOCK_WIDTH, BLOCK_HEIGHT);
				
				BufferedImage img = null;
				Graphics g = null;
		
				{   //TOP IMAGE
					img = new BufferedImage(BLOCK_WIDTH+1, BLOCK_HEIGHT+1, BufferedImage.TYPE_INT_ARGB);
					g = img.getGraphics();
					
					
					g.setColor(outline);
					topPoly.translate(BLOCK_WIDTH/2, BLOCK_HEIGHT/2);
					g.drawPolygon(topPoly);
					topPoly.translate(-BLOCK_WIDTH/2, -BLOCK_HEIGHT/2);
					
					g.dispose();
					
					
					
					try {
						ImageIO.write(img, "png", new File("C:/Users/Lucas/Desktop/top.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				{   //LEFT IMAGE
					img = new BufferedImage(BLOCK_WIDTH/2+1, (int)((3.0/2)*(BLOCK_HEIGHT)+1), BufferedImage.TYPE_INT_ARGB);
					g = img.getGraphics();
					
					
					g.setColor(outline);
					leftPoly.translate(BLOCK_WIDTH/2, 0);
					g.drawPolygon(leftPoly);
					leftPoly.translate(-BLOCK_WIDTH/2, 0);
					
					g.dispose();
					
					
					try {
						ImageIO.write(img, "png", new File("C:/Users/Lucas/Desktop/left.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				{   //RIGHT IMAGE
					img = new BufferedImage(BLOCK_WIDTH/2+1, (int)((3.0/2)*(BLOCK_HEIGHT)+1), BufferedImage.TYPE_INT_ARGB);
					g = img.getGraphics();
					
					
					g.setColor(outline);
					g.drawPolygon(rightPoly);
					
					g.dispose();
					
					
					try {
						ImageIO.write(img, "png", new File("C:/Users/Lucas/Desktop/right.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
		
	}
	
	public static void setBlockSize(int width, int height){
		Block.width = width;
		Block.height = height;
		sideLength = Math.sqrt(width*width + height*height)/2;
		Block.phi = Math.atan(((double)height)/((double)width));
		int[] xVals = new int[]{-width/2, 0, width/2, 0};
		int[] yVals = new int[]{0, -height/2, 0, height/2};
		topPoly = new Polygon(xVals, yVals, 4);
		xVals = new int[]{-width/2, 0, 0, -width/2};
		yVals = new int[]{0, height/2, 3*height/2, height};
		leftPoly = new Polygon(xVals, yVals, 4);
		xVals = new int[]{0, width/2, width/2, 0};
		yVals = new int[]{height/2, 0, height, 3*height/2};
		rightPoly = new Polygon(xVals, yVals, 4);
	}

	
	
}
