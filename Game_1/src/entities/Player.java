package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graficos.Spritesheet;
import graficos.UI;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down;
	public double speed = 2;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = down_dir;
	
	private int frame = 0, maxframe = 4, index = 0, maxindex = 2;
	private boolean moved = false;
	private int damageFrames = 0;
	
	private BufferedImage playerDL;
	private BufferedImage playerDR;
	private BufferedImage playerDU;
	private BufferedImage playerDD;
	private BufferedImage playerDDCE;
	private BufferedImage playerDDCD;
	private BufferedImage playerDDBD;
	private BufferedImage playerDDBE;
	
	public boolean isDamaged = false;
	
	private int municao = 0;
	private boolean arma = false;
	public boolean shoot = false;
	
	public double vida = 100, maxVida = 100;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;	
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] dbePlayer;
	private BufferedImage[] dbdPlayer;
	private BufferedImage[] dcePlayer;
	private BufferedImage[] dcdPlayer;
			
	public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		dbePlayer = new BufferedImage[3];
		dbdPlayer = new BufferedImage[3];
		dcePlayer = new BufferedImage[3];
		dcdPlayer = new BufferedImage[3];
		
		playerDL =  Game.spritesheet.getSprite(64 , 256, 32, 32);
		playerDR = Game.spritesheet.getSprite(0, 320, 32, 32);
		playerDU =  Game.spritesheet.getSprite(32, 288, 32, 32);
		playerDD = Game.spritesheet.getSprite(0, 256, 32, 32);
		playerDDCE = Game.spritesheet.getSprite(0, 288, 32, 32);
		playerDDCD = Game.spritesheet.getSprite(64, 288, 32, 32);
		playerDDBD = Game.spritesheet.getSprite(32, 320, 32, 32);
		playerDDBE = Game.spritesheet.getSprite(32, 256, 32, 32);
		
		for (int i=0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 192, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 64, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 128, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 0, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			dcePlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 96, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			dcdPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 160, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			dbePlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 32, 32, 32);
		}
		for (int i=0; i < 3; i++) {
			dbdPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 224, 32, 32);
		}
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed),(int)y)) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),(int)y)) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree((int)x,(int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if (down && World.isFree((int)x,(int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		if(moved) {
			frame++;
			if(frame == maxframe) {
				frame = 0;
				index++;
			}
			if(index > maxindex) {
				index = 0;
			}
		}
		checkColisionLife();
		checkColisionMunicao();
		checkColisionArma();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 5) {
				this.damageFrames=0;
				isDamaged=false;
			}
		}
		
		if(shoot && arma) {
			shoot=false;
			System.out.println("OLHA A PREEEEEDDDAAAAAA!!!!");
		}
		
		if (vida<=0) {
			Game.entities.clear();
			Game.enemie.clear();
			Game.entities = new ArrayList<Entity>();
			Game.enemie = new ArrayList<Enemie>();
			Game.spritesheet = new Spritesheet("/spritesmapa.png");
			Game.player = new Player(0,0,32,32,Game.spritesheet.getSprite(0, 0, 32, 32));
			Game.entities.add(Game.player);
			Game.world = new World("/mapa.png");	
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGTH/2),0, World.HEIGHT*32 - Game.HEIGTH);
	}
	public void checkColisionArma() {
		for(int i=0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					arma = true;
					Game.entities.remove(atual);
					return;
					
				}
			}
		}
	}	
	public void checkColisionMunicao() {
		for(int i=0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					municao++;
					Game.entities.remove(atual);
					return;
					
				}
			}
		}
	}
	
	public void checkColisionLife() {
		for(int i=0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Life) {
				if(Entity.isColidding(this, atual)) {
					vida+=10;
					if(vida > 100) 
						vida = 100;
						Game.entities.remove(atual);
						return;
					
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(right && up) {
				g.drawImage(dcdPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(left && up) {
				g.drawImage(dcePlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(left && down) {
				g.drawImage(dbePlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(right && down) {
				g.drawImage(dbdPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			if(right && up) {
				g.drawImage(playerDDCD, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(left && up) {
				g.drawImage(playerDDCE, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(left && down) {
				g.drawImage(playerDDBE, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(right && down) {
				g.drawImage(playerDDBD, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == right_dir) {
				g.drawImage(playerDR, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(playerDL, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == up_dir) {
				g.drawImage(playerDU, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(playerDD, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}
		
}
