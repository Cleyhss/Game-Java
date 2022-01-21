package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Tile {
	
	//public static BufferedImage TILE_WALL1 = Game.spritesheet.getSprite(96, 0, 32, 32);
	public static BufferedImage TILE_WALL2 = Game.spritesheet.getSprite(128, 0, 32, 32);
	//public static BufferedImage TILE_WALL3 = Game.spritesheet.getSprite(160, 0, 32, 32);
	//public static BufferedImage TILE_WALL4 = Game.spritesheet.getSprite(96,32, 32, 32);
	public static BufferedImage TILE_WALL5 = Game.spritesheet.getSprite(128,32, 32, 32);
	//public static BufferedImage TILE_WALL6 = Game.spritesheet.getSprite(160,32, 32, 32);
	public static BufferedImage TILE_MAD = Game.spritesheet.getSprite(192,96, 32, 32);
	public static BufferedImage TILE_FLOOR1 = Game.spritesheet.getSprite(96, 64, 32, 32);
	
	public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(96, 96, 32, 32);
	
	public static BufferedImage TILE_Water = Game.spritesheet.getSprite(192, 0, 32, 32);
	/*public static BufferedImage[] TILE_FLOOR1;
	public static BufferedImage[] TILE_FLOOR2;
	public static BufferedImage[] TILE_Water;*/
	
	private BufferedImage sprite;
	protected int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;		
		
		/*TILE_FLOOR1 = new BufferedImage[3];
		TILE_FLOOR2 = new BufferedImage[3];
		TILE_Water = new BufferedImage[2];
		
		for (int i=0; i < 3; i++) {
			TILE_FLOOR1[i] = Game.spritesheet.getSprite(96 + (i*32), 64, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			TILE_FLOOR2[i] = Game.spritesheet.getSprite(96 + (i*32), 96, 32, 32);
		}
		for (int i=0; i < 2; i++) {
			TILE_Water[i] = Game.spritesheet.getSprite(192 + (i*32), 0, 32, 32);
		}*/
	}
	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}


}
