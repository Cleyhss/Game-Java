package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class WallTileAgua extends Entity{

	private int frame = 0, maxframe = 16, index = 0, maxindex = 1;
	
	private BufferedImage[] agua;

	public WallTileAgua(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		agua = new BufferedImage[2];
		
		for (int i=0; i < 2; i++) {
			agua[i] = Game.spritesheet.getSprite(192 + (i*32), 0, 32, 32);
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
		g.drawImage(agua[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
	}

}
