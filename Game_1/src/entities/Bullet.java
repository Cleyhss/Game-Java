package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;


public class Bullet extends Entity {
	
	private int frame = 0, maxframe = 6, index = 0, maxindex = 11;
	
	private BufferedImage[] municao;

	public Bullet(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		municao = new BufferedImage[12];
		
		for (int i=0; i < 12; i++) {
			municao[i] = Game.spritesheet.getSprite(320 + (i*32), 0, 32, 32);
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
		g.drawImage(municao[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
	}

}
