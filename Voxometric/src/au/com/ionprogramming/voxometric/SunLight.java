package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Color;

public class SunLight {
	
	private double xDir, yDir, zDir, topAng, xmaxAng, xminAng, ymaxAng, yminAng;
	private Color c;
	
	public SunLight(int xDir, int yDir, int zDir, Color c){
		this.xDir = xDir;
		this.yDir = yDir;
		this.zDir = zDir;
		topAng = Math.toDegrees(Math.atan(zDir/Math.sqrt(xDir*xDir + yDir*yDir)));
		xmaxAng = Math.toDegrees(Math.atan(xDir/Math.sqrt(zDir*zDir + yDir*yDir)));
		xminAng = -xmaxAng;
		ymaxAng = Math.toDegrees(Math.atan(yDir/Math.sqrt(xDir*xDir + zDir*zDir)));
		if(xDir < 0 && xmaxAng < 0){
			xmaxAng *= -1;
			xminAng = -xmaxAng;
		}
		if(yDir < 0 && ymaxAng < 0){
			ymaxAng *= -1;
			yminAng = -ymaxAng;
		}
		if(zDir < 0 && topAng < 0){
			topAng *= -1;
		}
		this.c = c;
	}
	
	public Chunk illuminate(Chunk input){
		if(input == null){
			return null;
		}
		for(int lx = 0; lx < input.chunkSize; lx++){
			for(int ly = 0; ly < input.chunkSize; ly++){
				for(int lz = 0; lz < input.chunkSize; lz++){
					if(input.chunkData[lx][ly][lz] != null && !input.chunkData[lx][ly][lz].isTextured()){
						Block block = input.chunkData[lx][ly][lz];
						
						int r = (int)(block.t.getRed()*c.getRed()/255*(topAng + 90)/180);
						int g = (int)(block.t.getGreen()*c.getGreen()/255*(topAng + 90)/180);
						int b = (int)(block.t.getBlue()*c.getBlue()/255*(topAng + 90)/180);
						r = check(r + block.top.getRed());
						g = check(g + block.top.getGreen());
						b = check(b + block.top.getBlue());
						if(block.isTransparent()){
							block.top = new Color(r, g, b, block.t.getAlpha());
						}
						else{
							block.top = new Color(r, g, b);
						}
						
						r = (int)(block.s.getRed()*c.getRed()/255*(xmaxAng + 90)/180);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(xmaxAng + 90)/180);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(xmaxAng + 90)/180);
						r = check(r + block.xmax.getRed());
						g = check(g + block.xmax.getGreen());
						b = check(b + block.xmax.getBlue());
						if(block.isTransparent()){
							block.xmax = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.xmax = new Color(r, g, b);
						}

						r = (int)(block.s.getRed()*c.getRed()/255*(xminAng + 90)/180);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(xminAng + 90)/180);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(xminAng + 90)/180);
						r = check(r + block.xmin.getRed());
						g = check(g + block.xmin.getGreen());
						b = check(b + block.xmin.getBlue());
						if(block.isTransparent()){
							block.xmin = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.xmin = new Color(r, g, b);
						}

						r = (int)(block.s.getRed()*c.getRed()/255*(ymaxAng + 90)/180);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(ymaxAng + 90)/180);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(ymaxAng + 90)/180);
						r = check(r + block.ymax.getRed());
						g = check(g + block.ymax.getGreen());
						b = check(b + block.ymax.getBlue());
						if(block.isTransparent()){
							block.ymax = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.ymax = new Color(r, g, b);
						}

						r = (int)(block.s.getRed()*c.getRed()/255*(yminAng + 90)/180);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(yminAng + 90)/180);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(yminAng + 90)/180);
						r = check(r + block.ymin.getRed());
						g = check(g + block.ymin.getGreen());
						b = check(b + block.ymin.getBlue());
						if(block.isTransparent()){
							block.ymin = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.ymin = new Color(r, g, b);
						}
					}
				}
			}
		}
		return input;
	}
	private int check(int i){
		if(i < 0){
			i = 0;
		}
		else if(i > 255){
			i = 255;
		}
		return i;
	}
}
