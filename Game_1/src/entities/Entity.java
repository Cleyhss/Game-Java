package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(320,64, 32, 32);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(320, 32, 32, 32);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(320, 0, 32, 32);
	public static BufferedImage ENEMIE_EN = Game.spritesheet.getSprite(256, 32, 32, 32);
	public static BufferedImage TILE_Water = Game.spritesheet.getSprite(192, 0, 32, 32);
	public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(96, 96, 32, 32);
	public static BufferedImage BULLETSHOOT_EN = Game.spritesheet.getSprite(224, 96, 32, 32);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public boolean debug = false;
	
	private int maskx, masky, mwidth, mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	public void setMask(int maskx, int masky, int mwidth, int mheigth) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
		
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}
	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	public void tick() {
		
	}
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1mask = new Rectangle (e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);		
		Rectangle e2mask = new Rectangle (e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		return e1mask.intersects(e2mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}


}
