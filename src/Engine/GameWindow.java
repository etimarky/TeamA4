package Engine;

import javax.swing.*;

import Game.ScreenCoordinator;

/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	private JFrame gameWindow;
	private GamePanel gamePanel;
	
	

	public GameWindow(ScreenCoordinator c1) {
		gameWindow = new JFrame("Game");
		gamePanel = new GamePanel(c1,this);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(false);
		
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
		gamePanel.setupGame();
		
		
	}

	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
		
	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
		
	}

	public void paintWindow() {
		gameWindow.repaint();
		gamePanel.repaint();
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	public void setScreenSmall() {
//		Config.setWidth(500);
//		Config.setHeight(300);
		System.out.println(Config.WIDTH);
		System.out.println(Config.HEIGHT);
		
		//paintWindow();
		
	}
	public void setScreenMed() {
//		Config.setWidth(800);
//		Config.setHeight(605);
		paintWindow();
	}
	public void setScreenLarge() {
//		Config.setWidth(1000);
//		Config.setHeight(800);
		paintWindow();
	}

	
	
}
