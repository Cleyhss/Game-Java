package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class BulletShoot extends Entity{
	
	private int frame = 0, maxframe = 8, index = 0, maxindex = 2;
	private int dx;
	private int dy; 
	private double spd = 2;
	
	private BufferedImage[] power;
	
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		
		this.dx=dx;
		this.dy=dy;
		
		power = new BufferedImage[8];
		
		for (int i=0; i < 3; i++) {
			power[i] = Game.spritesheet.getSprite(320 + (i*32), 64, 32, 32);
		}
		
	}

	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		frame++;
		if(frame == maxframe) {
			frame = 0;
			index++;
		}
		if(index > maxindex) {
			index = 0;
		}
	}
	public void render(Graphics g) {
		g.drawImage(power[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
	}
}
