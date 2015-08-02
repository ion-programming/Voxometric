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
		for(int lx = 0; lx < input.chunkSize; lx++){
			for(int ly = 0; ly < input.chunkSize; ly++){
				for(int lz = 0; lz < input.chunkSize; lz++){
					if(input.chunkData[lx][ly][lz] != null && !input.chunkData[lx][ly][lz].isTextured()){
						
						int r = (int)(input.chunkData[lx][ly][lz].t.getRed()*c.getRed()/255*(topAng + 90)/180);
						int g = (int)(input.chunkData[lx][ly][lz].t.getGreen()*c.getGreen()/255*(topAng + 90)/180);
						int b = (int)(input.chunkData[lx][ly][lz].t.getBlue()*c.getBlue()/255*(topAng + 90)/180);
						r = check(r + input.chunkData[lx][ly][lz].top.getRed());
						g = check(g + input.chunkData[lx][ly][lz].top.getGreen());
						b = check(b + input.chunkData[lx][ly][lz].top.getBlue());
						if(input.chunkData[lx][ly][lz].isTransparent()){
							input.chunkData[lx][ly][lz].top = new Color(r, g, b, input.chunkData[lx][ly][lz].t.getAlpha());
						}
						else{
							input.chunkData[lx][ly][lz].top = new Color(r, g, b);
						}
						
						r = (int)(input.chunkData[lx][ly][lz].s.getRed()*c.getRed()/255*(xmaxAng + 90)/180);
						g = (int)(input.chunkData[lx][ly][lz].s.getGreen()*c.getGreen()/255*(xmaxAng + 90)/180);
						b = (int)(input.chunkData[lx][ly][lz].s.getBlue()*c.getBlue()/255*(xmaxAng + 90)/180);
						r = check(r + input.chunkData[lx][ly][lz].xmax.getRed());
						g = check(g + input.chunkData[lx][ly][lz].xmax.getGreen());
						b = check(b + input.chunkData[lx][ly][lz].xmax.getBlue());
						if(input.chunkData[lx][ly][lz].isTransparent()){
							input.chunkData[lx][ly][lz].xmax = new Color(r, g, b, input.chunkData[lx][ly][lz].s.getAlpha());
						}
						else{
							input.chunkData[lx][ly][lz].xmax = new Color(r, g, b);
						}

						r = (int)(input.chunkData[lx][ly][lz].s.getRed()*c.getRed()/255*(xminAng + 90)/180);
						g = (int)(input.chunkData[lx][ly][lz].s.getGreen()*c.getGreen()/255*(xminAng + 90)/180);
						b = (int)(input.chunkData[lx][ly][lz].s.getBlue()*c.getBlue()/255*(xminAng + 90)/180);
						r = check(r + input.chunkData[lx][ly][lz].xmin.getRed());
						g = check(g + input.chunkData[lx][ly][lz].xmin.getGreen());
						b = check(b + input.chunkData[lx][ly][lz].xmin.getBlue());
						if(input.chunkData[lx][ly][lz].isTransparent()){
							input.chunkData[lx][ly][lz].xmin = new Color(r, g, b, input.chunkData[lx][ly][lz].s.getAlpha());
						}
						else{
							input.chunkData[lx][ly][lz].xmin = new Color(r, g, b);
						}

						r = (int)(input.chunkData[lx][ly][lz].s.getRed()*c.getRed()/255*(ymaxAng + 90)/180);
						g = (int)(input.chunkData[lx][ly][lz].s.getGreen()*c.getGreen()/255*(ymaxAng + 90)/180);
						b = (int)(input.chunkData[lx][ly][lz].s.getBlue()*c.getBlue()/255*(ymaxAng + 90)/180);
						r = check(r + input.chunkData[lx][ly][lz].ymax.getRed());
						g = check(g + input.chunkData[lx][ly][lz].ymax.getGreen());
						b = check(b + input.chunkData[lx][ly][lz].ymax.getBlue());
						if(input.chunkData[lx][ly][lz].isTransparent()){
							input.chunkData[lx][ly][lz].ymax = new Color(r, g, b, input.chunkData[lx][ly][lz].s.getAlpha());
						}
						else{
							input.chunkData[lx][ly][lz].ymax = new Color(r, g, b);
						}

						r = (int)(input.chunkData[lx][ly][lz].s.getRed()*c.getRed()/255*(yminAng + 90)/180);
						g = (int)(input.chunkData[lx][ly][lz].s.getGreen()*c.getGreen()/255*(yminAng + 90)/180);
						b = (int)(input.chunkData[lx][ly][lz].s.getBlue()*c.getBlue()/255*(yminAng + 90)/180);
						r = check(r + input.chunkData[lx][ly][lz].ymin.getRed());
						g = check(g + input.chunkData[lx][ly][lz].ymin.getGreen());
						b = check(b + input.chunkData[lx][ly][lz].ymin.getBlue());
						if(input.chunkData[lx][ly][lz].isTransparent()){
							input.chunkData[lx][ly][lz].ymin = new Color(r, g, b, input.chunkData[lx][ly][lz].s.getAlpha());
						}
						else{
							input.chunkData[lx][ly][lz].ymin = new Color(r, g, b);
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
