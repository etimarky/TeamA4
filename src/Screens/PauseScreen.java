package Screens;

import Engine.Config;
import Engine.GamePanel;
import Engine.GameWindow;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;

// This class is for the level cleared screen
public class PauseScreen extends Screen {
	private SpriteFont volumeControl;
	private SpriteFont volumeLow;
	private SpriteFont volumeMed;
	private SpriteFont volumeHigh;
	private SpriteFont aspectControl;
	private SpriteFont aspectHigh;
	private SpriteFont aspectMed;
	private SpriteFont aspectLow;
	private SpriteFont returnInstructionLabel;
	private GameWindow gameWindow;
	private KeyLocker keyLocker;
	protected int pointerLocationX, pointerLocationY;
	protected Stopwatch keyTimer = new Stopwatch();
	protected int currentItemHovered, itemSelected;
	
	public PauseScreen(ScreenCoordinator screenCoordinator) {
		
	}

	@Override
	public void initialize() {
		volumeControl = new SpriteFont("Volume Control", 100, 150, "Comic Sans", 30, Color.white);

		volumeControl.setOutlineColor(Color.black);
		volumeControl.setOutlineThickness(2.0f);
		volumeLow = new SpriteFont("Low", 400, 150, "Comic Sans", 24, Color.white);
		volumeLow.setOutlineColor(Color.black);
		volumeLow.setOutlineThickness(2.0f);
		volumeMed = new SpriteFont("Medium", 500, 150, "Comic Sans", 24, Color.white);
		volumeMed.setOutlineColor(Color.black);
		volumeMed.setOutlineThickness(2.0f);
		volumeHigh = new SpriteFont("High", 630, 150, "Comic Sans", 24, Color.white);
		volumeHigh.setOutlineColor(Color.black);
		volumeHigh.setOutlineThickness(2.0f);

		aspectControl = new SpriteFont("Aspect Ratio", 100, 300, "Comic Sans", 30, Color.white);
		aspectControl.setOutlineColor(Color.black);
		aspectControl.setOutlineThickness(2.0f);
		aspectHigh = new SpriteFont("Large", 630, 300, "Comic Sans", 24, Color.white);
		aspectHigh.setOutlineColor(Color.black);
		aspectHigh.setOutlineThickness(2.0f);
		aspectMed = new SpriteFont("Medium", 500, 300, "Comic Sans", 24, Color.white);
		aspectMed.setOutlineColor(Color.black);
		aspectMed.setOutlineThickness(2.0f);
		aspectLow = new SpriteFont("Small", 400, 300, "Comic Sans", 24, Color.white);
		aspectLow.setOutlineColor(Color.black);
		aspectLow.setOutlineThickness(2.0f);
		returnInstructionLabel = new SpriteFont("Press X to return", 20, 560, "Times New Roman", 20, Color.white);
		returnInstructionLabel.setOutlineColor(Color.white);
		returnInstructionLabel.setOutlineThickness(2.0f);
		

//		
        keyTimer.setWaitTime(200);
        itemSelected = -1;
       
	}

	@Override
	public void update() {

		keyLocker.lockKey(Key.SPACE);
		currentItemHovered = 0;
		//keyLocker.lockKey(Key.SPACE);
		
		if (Keyboard.isKeyDown(Key.RIGHT) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentItemHovered++;
        } else if (Keyboard.isKeyDown(Key.LEFT) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentItemHovered--;
        }
		// if down is pressed on last menu item or up is pressed on first menu item,
		// "loop" the selection back around to the beginning/end
		 if (currentItemHovered > 7) {
	            currentItemHovered = 0;
	        } else if (currentItemHovered < 0) {
	            currentItemHovered = 7;
	        }


		if (currentItemHovered == 0) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(255, 215, 0));
			pointerLocationX = 50;
			pointerLocationY = 130;
		} else if (currentItemHovered == 1) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(255, 215, 0));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 130;
		} else if (currentItemHovered == 2) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(255, 215, 0));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 130;

		} else if (currentItemHovered == 3) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(255, 215, 0));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 130;
		} else if (currentItemHovered == 4) {
			aspectControl.setColor(new Color(255, 215, 0));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 280;
		} else if (currentItemHovered == 5) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(255, 215, 0));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 280;
		} else if (currentItemHovered == 6) {

			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(49, 207, 240));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(255, 215, 0));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 280;
		} else if (currentItemHovered == 7) {
			aspectControl.setColor(new Color(49, 207, 240));
			aspectHigh.setColor(new Color(255, 215, 0));
			aspectLow.setColor(new Color(49, 207, 240));
			aspectMed.setColor(new Color(49, 207, 240));
			volumeHigh.setColor(new Color(49, 207, 240));
			volumeLow.setColor(new Color(49, 207, 240));
			volumeMed.setColor(new Color(49, 207, 240));
			volumeControl.setColor(new Color(49, 207, 240));
			pointerLocationX = 50;
			pointerLocationY = 280;
		}

		// if space is pressed on menu item, change to appropriate screen based on which
		// menu item was chosen
		if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        
		if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
			itemSelected = currentItemHovered;
			if (itemSelected == 0) {
				
			} else if (itemSelected == 1) {
				setVolumeLow();

			} else if (itemSelected == 2) {
				setVolumeMed();
			} else if (itemSelected == 3) {
				setVolumeHigh();
			} else if (itemSelected == 4) {

			} else if (itemSelected == 5) {
				setScreenSmall();

			} else if (itemSelected == 6) {
				setScreenMed();
			} else if (itemSelected == 7) {
				setScreenLarge();
			}
		}
	}

	public void setScreenSmall() {
		Config.GAME_WINDOW_WIDTH = 500;
		Config.GAME_WINDOW_HEIGHT = 300;
		gameWindow.paintWindow();

	}

	public void setScreenMed() {
		Config.GAME_WINDOW_WIDTH = 800;
		Config.GAME_WINDOW_HEIGHT = 605;
		gameWindow.paintWindow();
	}

	public void setScreenLarge() {
		Config.GAME_WINDOW_WIDTH = 1000;
		Config.GAME_WINDOW_HEIGHT = 800;
		gameWindow.paintWindow();
	}

	public void setVolumeLow() {
		GamePanel.setVolumeLow();
		
	}

	public void setVolumeMed() {
		GamePanel.setVolumeMed();
	}

	public void setVolumeHigh() {
		GamePanel.setVolumeHigh();
	}

	public int getItemSelected() {
		return itemSelected;
	}

	public void draw(GraphicsHandler graphicsHandler) {
		// pauseLabel.draw(graphicsHandler);
		aspectControl.draw(graphicsHandler);
		aspectHigh.draw(graphicsHandler);
		aspectLow.draw(graphicsHandler);
		aspectMed.draw(graphicsHandler);
		volumeHigh.draw(graphicsHandler);
		volumeLow.draw(graphicsHandler);
		volumeMed.draw(graphicsHandler);
		volumeControl.draw(graphicsHandler);
		graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20,
				new Color(49, 207, 240), Color.black, 2);
//		graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
//				new Color(0, 0, 0, 100));
	}
}
