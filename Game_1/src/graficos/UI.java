package graficos;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(15,8,70,10);
		g.setColor(Color.green);
		g.fillRect(15,8,(int)((Game.player.vida/Game.player.maxVida)*70),10);
	}
		
}
