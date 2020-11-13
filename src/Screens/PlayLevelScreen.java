package Screens;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Maps.TestMap2;
import Maps.TestMap3;
import Maps.TestMap4;
import Maps.TestMap5;
import Players.Cat;
import Utils.Stopwatch;


// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
	private KeyLocker keyLocker = new KeyLocker();
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected Stopwatch screenTimer = new Stopwatch();
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    protected PauseScreen pauseScreen;
    protected LevelSelectScreen levelSelectScreen;
    protected int levelNum = 0;
    
    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
    }
    public PlayLevelScreen(ScreenCoordinator screenCoordinator, PlayLevelScreenState state) {
        this.screenCoordinator = screenCoordinator;
        this.playLevelScreenState = state;
      
    }

    public void initialize() {
       
        this.map = getCurrentMap();
        map.reset();
        System.out.println(levelNum);
        
        levelSelectScreen = new LevelSelectScreen(this);
        levelSelectScreen.initialize();
        pauseScreen = new PauseScreen(screenCoordinator);
        pauseScreen.initialize();
        this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.player.setMap(map);
        this.player.addListener(this);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
        
            case RUNNING:
            	if (Keyboard.isKeyDown(Key.P)&& !keyLocker.isKeyLocked(Key.P)) {
            		playLevelScreenState = PlayLevelScreenState.PAUSE;
            		keyLocker.lockKey(Key.P);
            	}else {
            		player.update();
                    map.update(player);
            	}
            	
            	if (Keyboard.isKeyUp(Key.P)) {
					keyLocker.unlockKey(Key.P);
            	}
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                levelClearedScreen = new LevelClearedScreen();
                levelClearedScreen.initialize();
                screenTimer.setWaitTime(2500);
                playLevelScreenState = PlayLevelScreenState.LEVEL_WIN_MESSAGE;
                break;
            // if level cleared screen is up and the timer is up for how long it should stay out, go back to main menu
            case LEVEL_WIN_MESSAGE:
                if (screenTimer.isTimeUp()) {
                	nextLevel();
                	playLevelScreenState = PlayLevelScreenState.RUNNING;
                }
                break;
            // if player died in level, bring up level lost screen
            case PLAYER_DEAD:
                levelLoseScreen = new LevelLoseScreen(this,screenCoordinator);
                levelLoseScreen.initialize();
                playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE_MESSAGE;
                break;
            // wait on level lose screen to make a decision (either resets level or sends player back to main menu)
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.update();
                break;
            case LEVEL_SELECT:
            	levelSelectScreen.update();
            	break;
            case PAUSE:
            	if (Keyboard.isKeyDown(Key.P)&& !keyLocker.isKeyLocked(Key.P)) {
            		playLevelScreenState = PlayLevelScreenState.RUNNING;
            		keyLocker.lockKey(Key.P);
            		
            	}
            	if (Keyboard.isKeyUp(Key.P)) {
					keyLocker.unlockKey(Key.P);
            	}
            	
            	break;
     
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
            case LEVEL_COMPLETED:
            	
            case PLAYER_DEAD:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_WIN_MESSAGE:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.draw(graphicsHandler);
                break;
            case LEVEL_SELECT:
            	levelSelectScreen.draw(graphicsHandler);
            	break;
            case PAUSE:
            	 map.draw(graphicsHandler);
                 player.draw(graphicsHandler);
                 pauseScreen.draw(graphicsHandler);
                 break;
            
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {
        playLevelScreenState = PlayLevelScreenState.PLAYER_DEAD;
    }
    
    
    public Map getCurrentMap() {
    	if (levelNum == 0) {
    		return new TestMap();
    	}else if (levelNum == 1){
    		System.out.println("Level 2");
    		return new TestMap2();
    	}
    	else if (levelNum == 2) { 
    		return new TestMap3();
    	}
    	else if (levelNum == 3) {
    		return new TestMap4();
    		
    	}
    	else {
    		return new TestMap5();
    	}
    }
    
    public void resetLevel() {
    	playLevelScreenState = PlayLevelScreenState.RUNNING;
        initialize();
    }

    public void goBackToMenu() {
    	screenCoordinator.setGameState(GameState.MENU);
    }

    public void nextLevel() {
    	levelNum++;
    	initialize();
    }
    public int getLevelNum() {
    	
    	return levelNum;
    }
    public void setLevelNum(int num) {
    	levelNum = num;
    	
    }
    
    public void setPlayLevelScreenState(PlayLevelScreenState state) {
    	playLevelScreenState = state;
    }
    // This enum represents the different states this screen can be in
    public enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE, LEVEL_SELECT, PAUSE
    }
    
    
}
