package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.BulletShoot;
import entities.Enemie;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.Tile;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 400;
	public static final int HEIGTH = 320;
	private final int SCALE = 2;
	private BufferedImage image;
			
	public static List<Entity> entities;
	public static List<Enemie> enemie;
	public static List<BulletShoot> power;
	public static List<Tile> tiles;
	
	public static Spritesheet spritesheet;
	
	public static Player player;
	public static World world;
	public static Random rand;
	
	public UI ui;
	
	public Game(){
		rand = new Random();
		setFocusable(true);
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGTH*SCALE));
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemie = new ArrayList<Enemie>();
		power = new ArrayList<BulletShoot>();
		tiles = new ArrayList<Tile>();
		spritesheet = new Spritesheet("/spritesmapa.png");
		player = new Player(0,0,32,32,spritesheet.getSprite(0, 0, 32, 32));
		entities.add(player);
		world = new World("/mapa.png");		
	}
	
	public void initFrame(){
		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String args[]){
		Game game = new Game();
		game.start();
	}	
	
	public void tick(){
		for(int i=0; i < entities.size();i++){
			Entity e = entities.get(i);
			e.tick();
		}
		
		for (int i=0; i < power.size(); i++) {
			power.get(i).tick();
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color (19,19,19));
		g.fillRect(0, 0, WIDTH, HEIGTH);
		
		world.render(g);
		
		
		/*for(int i=0; i < tiles.size();i++){
			Tile t = tiles.get(i);
			t.render(g);
		}*/
		
		for(int i=0; i < entities.size();i++){
			Entity e = entities.get(i);
			e.render(g);
		}
		for (int i=0; i < power.size(); i++) {
			power.get(i).render(g);
		}
		ui.render(g);
		//Graphics2D g2= (Graphics2D) g;		
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGTH*SCALE, null);
		bs.show();
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
				
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT||
				key == KeyEvent.VK_D) {
			player.right = true;
		}else if(key == KeyEvent.VK_LEFT||
				key == KeyEvent.VK_A) {
			player.left = true;
		}
		if (key == KeyEvent.VK_UP||
				key == KeyEvent.VK_W){
			player.up = true;
		}else if (key == KeyEvent.VK_DOWN||
				key == KeyEvent.VK_S){
			player.down = true;
		}
		if(key == KeyEvent.VK_E) {
			player.shoot=true;
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT||
				key == KeyEvent.VK_D) {
			player.right = false;
		}else if(key == KeyEvent.VK_LEFT||
				key == KeyEvent.VK_A) {
			player.left = false;
		}
		if (key == KeyEvent.VK_UP||
				key == KeyEvent.VK_W){
			player.up = false;
		}else if (key == KeyEvent.VK_DOWN||
				key == KeyEvent.VK_S){
			player.down = false;
		}
	
				
	}
}
	
