package Engine;

import GameObject.Rectangle;
import Screens.MenuScreen;
import SpriteFont.SpriteFont;
import Utils.Colors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Game.GameState;
import Game.ScreenCoordinator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel {
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section"
	// of the game.
	private ScreenManager screenManager;

	// used to create the game loop and cycle between update and draw calls
	private Timer timer;

	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;

	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private SpriteFont pauseLabel;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;
	private boolean isInstructions = false;
	private SpriteFont instructionLabel;
	private SpriteFont instruction2Label;
	private SpriteFont instruction3Label;
	private SpriteFont instruction4Label;
	private SpriteFont returnInstructionLabel;
	private final Key iKey = Key.X;
	private ScreenCoordinator coordinator;

	/*
	 * The JPanel and various important class instances are setup here
	 */
	public GamePanel(ScreenCoordinator c1) {
		super();
		this.setDoubleBuffered(true);

		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();
		coordinator = c1;
		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Comic Sans", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);

		instructionLabel = new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20,
				Color.white);
		instruction2Label = new SpriteFont("To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20,
				Color.white);
		instruction3Label = new SpriteFont("To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 220, "Times New Roman", 20,
				Color.white);
		instruction4Label = new SpriteFont("To CROUCH: DOWN arrow key, or 'S'", 130, 260, "Times New Roman", 20,
				Color.white);
		returnInstructionLabel = new SpriteFont("Press X to return", 20, 560, "Times New Roman", 20, Color.white);
		instructionLabel.setOutlineColor(Color.white);
		instructionLabel.setOutlineThickness(2.0f);
		instruction2Label.setOutlineColor(Color.white);
		instruction2Label.setOutlineThickness(2.0f);
		instruction3Label.setOutlineColor(Color.white);
		instruction3Label.setOutlineThickness(2.0f);
		instruction4Label.setOutlineColor(Color.white);
		instruction4Label.setOutlineThickness(2.0f);
		returnInstructionLabel.setOutlineColor(Color.white);
		returnInstructionLabel.setOutlineThickness(2.0f);

		// Every timer "tick" will call the update method as well as tell the JPanel to
		// repaint
		// Remember that repaint "schedules" a paint rather than carries it out
		// immediately
		// If the game is really laggy/slow, I would consider upping the FPS in the
		// Config file.
		timer = new Timer(1000 / Config.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		timer.setRepeats(true);
	}

	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the
	// JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;

	}

	  public static void music() {
			
			try {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("src/Blossoming Inspiration Loop (online-audio-converter.com).wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				
			} catch (Exception ex) {
				System.out.println("No audio found!");
				ex.printStackTrace();
				
			}
			
		}
	// this starts the timer (the game loop is started here
	public void startGame() {
		timer.start();
		music();
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update() {

		if (coordinator.getGameState() != GameState.MENU && coordinator.getGameState() != GameState.INSTRUCTIONS && coordinator.getGameState() != GameState.CREDITS) {
			if (isInstructions == false) {
				if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {

					isGamePaused = !isGamePaused;
					keyLocker.lockKey(pauseKey);
				}

				if (Keyboard.isKeyUp(pauseKey)) {
					keyLocker.unlockKey(pauseKey);
				}
			}

			if (isGamePaused == false) {
				if (Keyboard.isKeyDown(iKey) && !keyLocker.isKeyLocked(iKey)) {
					isInstructions = !isInstructions;
					keyLocker.lockKey(iKey);
				}

				if (Keyboard.isKeyUp(iKey)) {
					keyLocker.unlockKey(iKey);
				}
			}
		}
		if (!isInstructions && !isGamePaused) {
			screenManager.update();
		}

	}

	public void draw() {
		screenManager.draw(graphicsHandler);

		// if game is paused, draw pause gfx over Screen gfx
		if (isGamePaused) {
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
					new Color(0, 0, 0, 100));
		}

		if (isInstructions) {

			instructionLabel.draw(graphicsHandler);
			instruction2Label.draw(graphicsHandler);
			instruction3Label.draw(graphicsHandler);
			instruction4Label.draw(graphicsHandler);
			returnInstructionLabel.draw(graphicsHandler);

			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
					new Color(0, 0, 0, 100));
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// every repaint call will schedule this method to be called
		// when called, it will setup the graphics handler and then call this class's
		// draw method
		graphicsHandler.setGraphics((Graphics2D) g);
		if (doPaint) {
			draw();
		}

	}
}
