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
	static Color outline = new Color(0, 0, 0, 0.1f);
	
	private static int prevAng = -1;
	private static double prevCx = Double.NaN;
	private static double prevCy = Double.NaN;
	private static double prevCz = Double.NaN;
	private static int xOffset = 0;
	private static int yOffset = 0;
	
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
		top = Color.black;
		xmin = Color.black;
		xmax = Color.black;
		ymin = Color.black;
		ymax = Color.black;
	}
	
	public void setTexture(Image t, Image l, Image r){
		topTex = t;
		lTex = l;
		rTex = r;
		textured = true;
	}
	
	private void setOffsets(int angle, double cx, double cy, double cz){
			prevAng = angle;
			prevCx = cx;
			prevCy = cy;
			prevCz = cz;
			switch(angle){
				case 0:
					xOffset = - (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Block.width/2);
					yOffset = - (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Block.height/2 - (cz - (int)cz)*Block.height);
					break;
				case 1:
					xOffset = - (int)(((-cy + (int)cy + 0.5) - (cx - (int)cx - 0.5))*Block.width/2);
					yOffset = - (int)(((-cy + (int)cy + 0.5) + (cx - (int)cx - 0.5))*Block.height/2 - (cz - (int)cz)*Block.height);
					break;
				case 2:
					xOffset = (int)(((cx - (int)cx - 0.5) - (cy - (int)cy - 0.5))*Block.width/2);
					yOffset = (int)(((cx - (int)cx - 0.5) + (cy - (int)cy - 0.5))*Block.height/2 + (cz - (int)cz)*Block.height);
					break;
				case 3:
					xOffset = (int)(((-cy + (int)cy + 0.5) - (cx - (int)cx - 0.5))*Block.width/2);
					yOffset = (int)(((-cy + (int)cy + 0.5) + (cx - (int)cx - 0.5))*Block.height/2 + (cz - (int)cz)*Block.height);
			}
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz, int chunkX, int chunkY, int chunkZ){
		if(!renderTag){
			return;
		}
		if(prevAng != angle || prevCx != cx || prevCy != cy || prevCz != cz){
			setOffsets(angle, cx, cy, cz);
		}
		int dx = x + chunkX - (int)cx;
		int dy = y + chunkY - (int)cy;
		int dz = z + chunkZ - (int)cz;
		int deltaX = 0;
		int deltaY = 0;
		switch(angle){
			case 0:
				deltaX = (dx - dy)*Block.width/2;
				deltaY = (dx + dy)*Block.height/2;
				break;
			case 1:
				deltaX = (-dy - dx)*Block.width/2;
				deltaY = (-dy + dx)*Block.height/2;
				break;
			case 2:
				deltaX = -(dx - dy)*Block.width/2;
				deltaY = - (dx + dy)*Block.height/2;
				break;
			case 3:
				deltaX = -(-dy - dx)*Block.width/2;
				deltaY = - (-dy + dx)*Block.height/2;
		}
		deltaX += width/2 + xOffset;
		deltaY += height/2 - dz*Block.height + yOffset;
		if(deltaX < -Block.width/2 || deltaX > width + Block.width/2 || deltaY < -3*Block.height/2 || deltaY > height + Block.height/2){
			return;	//not on screen
		}
		boolean drawLeft = true;
		boolean drawRight = true;
		boolean drawTop = (coveredFaces & 1) == 0;
		Color cl = null;
		Color cr = null;
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
			Transform t = Transform.createTranslateTransform(deltaX, deltaY);
			if(drawTop){
//				g.setColor(top);
//				topPoly.transform(t);
//				g.setColor(top);
//				topPoly.transform(t);
				g.setColor(top);
				g.fill(topPoly.transform(t));
				g.setColor(outline);
				g.draw(topPoly.transform(t));
			}
			if(drawLeft){
//				g.setColor(top);
//				topPoly.transform(t);
//				g.setColor(top);
//				topPoly.transform(t);
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
				
//				g.setColor(top);
//				topPoly.transform(t);
//				g.setColor(top);
//				topPoly.transform(t);
			}
		}
		
	}
	
	public boolean isCovered(int angle){
		boolean lCov = false;
		boolean rCov = false;
		boolean tCov = (coveredFaces & 1) != 0;
		switch(angle){
			case 0:
				lCov = (coveredFaces/16 & 1) != 0;
				rCov = (coveredFaces/4 & 1) != 0;
				break;
			case 1:
				lCov = (coveredFaces/4 & 1) != 0;
				rCov = (coveredFaces/8 & 1) != 0;
				break;
			case 2:
				lCov = (coveredFaces/8 & 1) != 0;
				rCov = (coveredFaces/2 & 1) != 0;
				break;
			case 3:
				lCov = (coveredFaces/2 & 1) != 0;
				rCov = (coveredFaces/16 & 1) != 0;
		}
		return lCov && rCov && tCov;
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
