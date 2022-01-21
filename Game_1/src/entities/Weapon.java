package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Weapon extends Entity{
	
	private int frame = 0, maxframe = 12, index = 0, maxindex = 1;
	private BufferedImage[] arma;

	public Weapon(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		arma = new BufferedImage[2];
		
		for (int i=0; i < 2; i++) {
			arma[i] = Game.spritesheet.getSprite(320 + (i*32), 32, 32, 32);
		}
	}
	
	public void tick() {
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
		g.drawImage(arma[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
	}

}
