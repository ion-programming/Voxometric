package au.com.ionprogramming.voxometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Block {

	int x, y, z;
	Color top, xmin, xmax, ymin, ymax, t, s;
	static int width;
	static int height;
	static double sideLength;
	static double phi;
	static Polygon topPoly;
	static Polygon leftPoly;
	static Polygon rightPoly;
	
	public Block(int x, int y, int z, Color t, Color s){
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
		this.s = s;
		
		top = t;
		xmin = s;
		xmax = s;
		ymin = s;
		ymax = s;
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		int dx = x - (int)cx;
		int dy = y - (int)cy;
		int dz = z - (int)cz;
		int deltaX = 0;
		int deltaY = 0;
		Color cl = new Color(0, 0, 0);
		Color cr = new Color(0, 0, 0);
		switch(angle){
			case 0:
				cl = ymax;
				cr = xmax;
				deltaX = (dx - dy)*Block.width/2
						- (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Math.cos(phi)*sideLength);
				deltaY = -dz*Block.height + (dx + dy)*Block.height/2
						- (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Math.sin(phi)*sideLength - (cz - (int)cz)*Block.height);
				break;
			case 1:
				cl = xmax;
				cr = ymin;
				deltaX = (-dy - dx)*Block.width/2 
						- (int)(((-cy + (int)cy - 0.5) - (cx - (int)cx - 0.5))*Math.cos(phi)*sideLength);
				deltaY = -dz*Block.height - (-dy + dx)*Block.height/2 
						+ (int)(((-cy + (int)cy - 0.5) + (cx - (int)cx - 0.5))*Math.sin(phi)*sideLength + (cz - (int)cz)*Block.height);
				break;
			case 2:
				cl = ymin;
				cr = xmin;
				deltaX = -((dx - dy)*Block.width/2 
						- (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Math.cos(phi)*sideLength));
				deltaY = -dz*Block.height + (dx + dy)*Block.height/2 
						- (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Math.sin(phi)*sideLength - (cz - (int)cz)*Block.height);
				break;
			case 3:
				cl = xmin;
				cr = ymax;
				deltaX = -((-dy - dx)*Block.width/2 
						- (int)(((-cy + (int)cy - 0.5) - (cx - (int)cx - 0.5))*Math.cos(phi)*sideLength));
				deltaY = -dz*Block.height + (-dy + dx)*Block.height/2 
						- (int)(((-cy + (int)cy - 0.5) + (cx - (int)cx - 0.5))*Math.sin(phi)*sideLength - (cz - (int)cz)*Block.height);
		}
		deltaX += width/2;
		deltaY += height/2;
		topPoly.translate(deltaX, deltaY);
		leftPoly.translate(deltaX, deltaY);
		rightPoly.translate(deltaX, deltaY);
		g.setColor(top);
		g.fillPolygon(topPoly);
		g.setColor(cl);
		g.fillPolygon(leftPoly);
		g.setColor(cr);
		g.fillPolygon(rightPoly);
		topPoly.translate(-deltaX, -deltaY);
		leftPoly.translate(-deltaX, -deltaY);
		rightPoly.translate(-deltaX, -deltaY);
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
