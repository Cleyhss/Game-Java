package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Life extends Entity {
	
	private int frame = 0, maxframe = 8, index = 0, maxindex = 7;
	
	private BufferedImage[] vida;

	public Life(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		vida = new BufferedImage[8];
		
		for (int i=0; i < 8; i++) {
			vida[i] = Game.spritesheet.getSprite(320 + (i*32), 64, 32, 32);
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
		g.drawImage(vida[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
	}

}
