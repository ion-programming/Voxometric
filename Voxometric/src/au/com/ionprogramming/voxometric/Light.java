package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Color;

public class Light {
	
	private int x, y, z;
	private double topAng, xmaxAng, xminAng, ymaxAng, yminAng;
	private int throwRadius;
	private Color c;
	
	public Light(int x, int y, int z, int throwRadius, Color c){
		this.x = x;
		this.y = y;
		this.z = z;
		this.throwRadius = throwRadius;
		this.c = c;
	}
	
	public Chunk illuminate(Chunk input){
		x -= input.chunkSize*input.x;
		y -= input.chunkSize*input.y;
		z -= input.chunkSize*input.z;
		for(int lx = (x - throwRadius > 0? x - throwRadius : 0); lx < (x + throwRadius < input.chunkSize? x + throwRadius : input.chunkSize); lx++){
			for(int ly = (y - throwRadius > 0? y - throwRadius : 0); ly < (y + throwRadius < input.chunkSize? y + throwRadius : input.chunkSize); ly++){
				for(int lz = (z - throwRadius > 0? z - throwRadius : 0); lz < (z + throwRadius < input.chunkSize? z + throwRadius : input.chunkSize); lz++){
					double distance = 0;
					if(input.chunkData[lx][ly][lz] != null && !input.chunkData[lx][ly][lz].isTextured() && (distance = Math.sqrt((x - lx)*(x - lx) + (y - ly)*(y - ly) + (z - lz)*(z - lz))) <= throwRadius){
						Block block = input.chunkData[lx][ly][lz];
						topAng = Math.toDegrees(Math.atan((z - lz)/Math.sqrt((x - lx)*(x - lx) + (y - ly)*(y - ly))));
						xmaxAng = Math.toDegrees(Math.atan((x - lx)/Math.sqrt((z - lz)*(z - lz) + (y - ly)*(y - ly))));
						xminAng = -xmaxAng;
						ymaxAng = Math.toDegrees(Math.atan((y - ly)/Math.sqrt((x - lx)*(x - lx) + (z - lz)*(z - lz))));
						if((x - lx) < 0 && xmaxAng < 0){
							xmaxAng *= -1;
							xminAng = -xmaxAng;
						}
						if((y - ly) < 0 && ymaxAng < 0){
							ymaxAng *= -1;
							yminAng = -ymaxAng;
						}
						if((z - lz) < 0 && topAng < 0){
							topAng *= -1;
						}
						
						double distChange = (throwRadius - distance)/throwRadius;
						
						int r = (int)(block.t.getRed()*c.getRed()/255*(topAng + 90)/180*distChange);
						int g = (int)(block.t.getGreen()*c.getGreen()/255*(topAng + 90)/180*distChange);
						int b = (int)(block.t.getBlue()*c.getBlue()/255*(topAng + 90)/180*distChange);
						r = check(r + block.top.getRed());
						g = check(g + block.top.getGreen());
						b = check(b + block.top.getBlue());
						if(block.isTransparent()){
							block.top = new Color(r, g, b, block.t.getAlpha());
						}
						else{
							block.top = new Color(r, g, b);
						}
						
						r = (int)(block.s.getRed()*c.getRed()/255*(xmaxAng + 90)/180*distChange);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(xmaxAng + 90)/180*distChange);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(xmaxAng + 90)/180*distChange);
						r = check(r + block.xmax.getRed());
						g = check(g + block.xmax.getGreen());
						b = check(b + block.xmax.getBlue());
						if(block.isTransparent()){
							block.xmax = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.xmax = new Color(r, g, b);
						}

						r = (int)(block.s.getRed()*c.getRed()/255*(xminAng + 90)/180*distChange);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(xminAng + 90)/180*distChange);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(xminAng + 90)/180*distChange);
						r = check(r + block.xmin.getRed());
						g = check(g + block.xmin.getGreen());
						b = check(b + block.xmin.getBlue());
						if(block.isTransparent()){
							block.xmin = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.xmin = new Color(r, g, b);
						}
						r = (int)(block.s.getRed()*c.getRed()/255*(ymaxAng + 90)/180*distChange);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(ymaxAng + 90)/180*distChange);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(ymaxAng + 90)/180*distChange);
						r = check(r + block.ymax.getRed());
						g = check(g + block.ymax.getGreen());
						b = check(b + block.ymax.getBlue());
						if(block.isTransparent()){
							block.ymax = new Color(r, g, b, block.s.getAlpha());
						}
						else{
							block.ymax = new Color(r, g, b);
						}

						r = (int)(block.s.getRed()*c.getRed()/255*(yminAng + 90)/180*distChange);
						g = (int)(block.s.getGreen()*c.getGreen()/255*(yminAng + 90)/180*distChange);
						b = (int)(block.s.getBlue()*c.getBlue()/255*(yminAng + 90)/180*distChange);
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
		x += input.chunkSize*input.x;
		y += input.chunkSize*input.y;
		z += input.chunkSize*input.z;
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
