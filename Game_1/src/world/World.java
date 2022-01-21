package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Bullet;
import entities.Enemie;
import entities.Entity;
import entities.Life;
import entities.WallTileAgua;
import entities.Weapon;
import main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int HEIGHT,WIDTH;
	public static final int TILE_SIZE = 32;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			HEIGHT = map.getHeight();
			WIDTH = map.getWidth();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx=0; xx < map.getWidth(); xx++) {
				for(int yy=0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32, yy*32,Tile.TILE_FLOOR2);
					if (pixelAtual == 0xFF4CFF00) {
						//chão2
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32, yy*32,Tile.TILE_FLOOR2);						
					}else if (pixelAtual == 0xFF00FFFF) {
						//agua
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32, yy*32,Tile.TILE_Water);
						WallTileAgua agua = new WallTileAgua(xx*32,yy*32,32,32,Entity.TILE_Water);
						Game.entities.add(agua);
					}else if (pixelAtual == 0xFFFFD800) {
						//madeira
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32, yy*32,Tile.TILE_MAD);
					}else if (pixelAtual == 0xFF303030) {
						//parede2
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32, yy*32,Tile.TILE_WALL2);
					}else if (pixelAtual == 0xFF7F0000) {
						//parede5
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32, yy*32,Tile.TILE_WALL5);
					}else if (pixelAtual == 0xFFFF7A32) {
						//player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}else if (pixelAtual == 0xFFFF0000) {
						//life
						Life vida = new Life(xx*32,yy*32,32,32,Entity.LIFEPACK_EN);
						vida.setMask(8,8,8,8);
						Game.entities.add(vida);
					}else if (pixelAtual == 0xFF21007F) {
						//weapon
						Weapon arma = new Weapon(xx*32,yy*32,32,32,Entity.WEAPON_EN);
						Game.entities.add(arma);
					}else if (pixelAtual == 0xFF0026FF) {
						//bullet
						Bullet municao = new Bullet(xx*32,yy*32,32,32,Entity.BULLET_EN);
						Game.entities.add(municao);
					}else if (pixelAtual == 0xFF91B0FF) {
						//enemie
						Enemie en = new Enemie(xx*32,yy*32,32,32,Entity.ENEMIE_EN);
						Game.entities.add(en);
						Game.enemie.add(en);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x/32;
		int ystart = Camera.y/32;
		
		int xfinal = xstart + (Game.WIDTH/32)+2;
		int yfinal = ystart + (Game.HEIGTH/32)+2;
		
		for(int xx = xstart; xx <= xfinal; xx++){
			for(int yy=ystart; yy <= yfinal; yy++) {
				if((xx<0)||(yy<0)||(xx>=WIDTH)||(yy>=HEIGHT)){
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
