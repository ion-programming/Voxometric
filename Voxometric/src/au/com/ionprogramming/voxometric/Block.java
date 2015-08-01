package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

public class Block {

	int x, y, z;
	Color top, xmin, xmax, ymin, ymax, t, s;
	Image topTex, lTex, rTex;
	static int width;
	static int height;
	static double sideLength;
	static double phi;
	static Polygon topPoly;
	static Polygon leftPoly;
	static Polygon rightPoly;
	static Color outline = new Color(0, 0, 0, 0.25f);
	
	int coveredFaces = 0;	//bits: ymax, ymin, xmax, xmin, top
	private boolean isTransparent = false;
	private boolean renderTag = false;
	private boolean textured = false;

	public Block(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setColor(Color t, Color s){
		this.t = t;
		this.s = s;
		top = t;
		xmin = s;
		xmax = s;
		ymin = s;
		ymax = s;
	}
	
	public void setTexture(Image t, Image l, Image r){
		topTex = t;
		lTex = l;
		rTex = r;
		textured = true;
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		if(coveredFaces == 31 || !renderTag){
			return;
		}
		int dx = x - (int)cx;
		int dy = y - (int)cy;
		int dz = z - (int)cz;
		int deltaX = 0;
		int deltaY = 0;
		switch(angle){
			case 0:
				deltaX = (dx - dy)*Block.width/2
						- (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Block.width/2);
				deltaY = (dx + dy)*Block.height/2
						- (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Block.height/2 - (cz - (int)cz)*Block.height);
				break;
			case 1:
				deltaX = (-dy - dx)*Block.width/2 
						- (int)(((-cy + (int)cy + 0.5) - (cx - (int)cx - 0.5))*Block.width/2);
				deltaY = (-dy + dx)*Block.height/2 
						- (int)(((-cy + (int)cy + 0.5) + (cx - (int)cx - 0.5))*Block.height/2 - (cz - (int)cz)*Block.height);
				break;
			case 2:
				deltaX = -(dx - dy)*Block.width/2 
						+ (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Block.width/2);
				deltaY = - (dx + dy)*Block.height/2 
						+ (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Block.height/2 + (cz - (int)cz)*Block.height);
				break;
			case 3:
				deltaX = -((-dy - dx)*Block.width/2 
						- (int)(((-cy + (int)cy + 0.5) - (cx - (int)cx - 0.5))*Block.width/2));
				deltaY = - (-dy + dx)*Block.height/2 
						+ (int)(((-cy + (int)cy + 0.5) + (cx - (int)cx - 0.5))*Block.height/2 + (cz - (int)cz)*Block.height);
		}
		deltaX += width/2;
		deltaY += height/2 - dz*Block.height;
		if(deltaX < -Block.width/2 || deltaX > width + Block.width/2 || deltaY < -3*Block.height/2 || deltaY > height + Block.height/2){
			return;	//not on screen
		}
		boolean drawLeft = true;
		boolean drawRight = true;
		boolean drawTop = (coveredFaces & 1) == 0;
		Color cl = new Color(0, 0, 0);
		Color cr = new Color(0, 0, 0);
		switch(angle){
			case 0:
				cl = ymax;
				cr = xmax;
				drawLeft = (coveredFaces/16 & 1) == 0;
				drawRight = (coveredFaces/4 & 1) == 0;
				break;
			case 1:
				cl = xmax;
				cr = ymin;
				drawLeft = (coveredFaces/4 & 1) == 0;
				drawRight = (coveredFaces/8 & 1) == 0;
				break;
			case 2:
				cl = ymin;
				cr = xmin;
				drawLeft = (coveredFaces/8 & 1) == 0;
				drawRight = (coveredFaces/2 & 1) == 0;
				break;
			case 3:
				cl = xmin;
				cr = ymax;
				drawLeft = (coveredFaces/2 & 1) == 0;
				drawRight = (coveredFaces/16 & 1) == 0;
		}
		Transform t = null;
		if((drawTop || drawLeft || drawRight) && ! textured){
			t = Transform.createTranslateTransform(deltaX, deltaY);
		}
		if(textured){
			if(drawTop){
				g.drawImage(topTex, deltaX - Block.width/2, deltaY - Block.height/2);
			}
			if(drawLeft){
				g.drawImage(lTex, deltaX - Block.width/2, deltaY);
			}
			if(drawRight){
				g.drawImage(rTex, deltaX, deltaY);
			}
		}
		else {
			if(drawTop){
				g.setColor(top);
				g.fill(topPoly.transform(t));
				g.setColor(outline);
				g.draw(topPoly.transform(t));
			}
			if(drawLeft){
				g.setColor(cl);
				g.fill(leftPoly.transform(t));
				g.setColor(outline);
				g.draw(leftPoly.transform(t));
			}
			if(drawRight){
				g.setColor(cr);
				g.fill(rightPoly.transform(t));
				g.setColor(outline);
				g.draw(rightPoly.transform(t));
			}
		}
		
	}
	
	public static void setBlockSize(int width, int height){
		Block.width = width;
		Block.height = height;
		sideLength = Math.sqrt(width*width + height*height)/2;
		Block.phi = Math.atan(((double)height)/((double)width));
		float[] points = new float[]{-width/2, 0, 0, -height/2, width/2, 0, 0, height/2}; 
		topPoly = new Polygon(points);
		points = new float[]{-width/2, 0, 0, height/2, 0, 3*height/2, -width/2, height}; 
		leftPoly = new Polygon(points);
		points = new float[]{0, height/2, width/2, 0, width/2, height, 0, 3*height/2};
		rightPoly = new Polygon(points);
	}

	public boolean isTextured(){
		return textured;
	}
	
	public boolean isTransparent() {
		return isTransparent;
	}

	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}

	public boolean isRenderTagged() {
		return renderTag;
	}

	public void setRenderTag(boolean renderTag) {
		this.renderTag = renderTag;
	}
}
