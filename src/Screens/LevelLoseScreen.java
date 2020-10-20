package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;

import java.awt.*;

// This is the class for the level lose screen
public class LevelLoseScreen extends Screen {
    protected SpriteFont loseMessage;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;
    protected PlayLevel2Screen playLevel2Screen;
    protected ScreenCoordinator screenCoordinator;
    
    public LevelLoseScreen(PlayLevelScreen playLevelScreen, ScreenCoordinator sc1) {
    	screenCoordinator = sc1;
        this.playLevelScreen = playLevelScreen;
    }

    public LevelLoseScreen(PlayLevel2Screen playLevel2Screen, ScreenCoordinator sc1) {
    	screenCoordinator = sc1;
		this.playLevel2Screen = playLevel2Screen;
	}

	@Override
    public void initialize() {
        loseMessage = new SpriteFont("You lose!", 350, 270, "Comic Sans", 30, Color.white);
        instructions = new SpriteFont("Press Space to try again or Escape to go back to the main menu", 120, 300,"Comic Sans", 20, Color.white);
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, reset level. if escape is pressed, go back to main menu
        if (Keyboard.isKeyDown(Key.SPACE)) {
        	 if (screenCoordinator.getGameState() == GameState.LEVEL) {
             	playLevelScreen.resetLevel();
             }else {
             	playLevel2Screen.resetLevel();
             }
        	
        	
            
        } else if (Keyboard.isKeyDown(Key.ESC)) {
        	
        	 if (screenCoordinator.getGameState() == GameState.LEVEL) {
        		 playLevelScreen.goBackToMenu();
             }else {
            	 playLevel2Screen.goBackToMenu();
             }
            
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        loseMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
    }
}
