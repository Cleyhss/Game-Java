package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Enemie extends Entity {
	
	private int frame = 0, maxframe = 8, index = 0, maxindex = 3;
	private double speed = 1;
	private int maskx=8, masky=8, maskw=7, maskh=7;
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	
	public boolean right, left;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;

	public Enemie(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, null);
		rightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage[4];
		
		for (int i=0; i < 4; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(192 + (i*32), 32, 32, 32);
		}
		for (int i=0; i < 4; i++) {
			leftEnemy[i] = Game.spritesheet.getSprite(192 + (i*32), 64, 32, 32);
		}
	}
	
	public void tick() {
		//if(Game.rand.nextInt(100)<30) {	
		if (this.isColiddingWithPlayer() == false) {
		if ((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			dir = right_dir;
			x+=speed;
		}else if((int)x > Game.player.getX() && World.isFree((int) (x-speed), this.getY())
				&& !isColidding((int) (x-speed), this.getY())) {
			dir = left_dir;
			x-=speed;
		}
		if ((int)y < Game.player.getY() && World.isFree( this.getX(), (int)(y+speed))
				&& !isColidding(this.getX(), (int)(y+speed))) {
			y+=speed;
		}else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
				&& !isColidding(this.getX(), (int)(y-speed))) {
			y-=speed;
		}	
		}else {
			if(Game.rand.nextInt(100) < 10) {	
				Game.player.vida-=Game.rand.nextInt(3);
				Game.player.isDamaged = true;
				//System.out.println(Game.player.vida);
			}
		}
		frame++;
		if(frame == maxframe) {
			frame = 0;
			index++;
		}
		if(index > maxindex) {
			index = 0;
		}
		}
	public boolean isColiddingWithPlayer() {
		Rectangle enemieCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 28,28);
		
		return enemieCurrent.intersects(player);
	}
	
	public boolean isColidding(int xnext, int ynext){
		Rectangle enemieCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		
		for(int i=0; i < Game.enemie.size();i++) {
			Enemie e = Game.enemie.get(i);
			if (e == this) 
				continue;
			Rectangle targetEnemie = new Rectangle(e.getX()+ maskx, e.getY()+ masky, maskw, maskh);
			if(enemieCurrent.intersects(targetEnemie)) {
				return true;
			}
		
		}
		
		
		return false;
	}
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightEnemy[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}else if(dir == left_dir) {
			g.drawImage(leftEnemy[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		
	}
}
