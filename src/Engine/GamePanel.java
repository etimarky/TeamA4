package Engine;

import GameObject.Rectangle;



import SpriteFont.SpriteFont;
import Utils.Colors;
import Utils.Stopwatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import sun.audio.AudioData;
import javax.sound.sampled.FloatControl;
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
	protected int pointerLocationX, pointerLocationY;	
	private boolean isInstructions = false;
	protected Stopwatch keyTimer = new Stopwatch();
	private SpriteFont instructionLabel;
	private SpriteFont instruction2Label;
	private SpriteFont instruction3Label;
	private SpriteFont instruction4Label;
	private SpriteFont returnInstructionLabel;
	
	private ScreenCoordinator coordinator;
	

	/*
	 * The JPanel and various important class instances are setup here
	 */
	public GamePanel(ScreenCoordinator c1) {
		super();
		this.setDoubleBuffered(true);

		this.setSize(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();
		coordinator = c1;

	
		createInstructionsScreen();

		

		timer = new Timer(1000 / Config.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		timer.setRepeats(true);
	}

	private void createInstructionsScreen() {
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

	}


	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the
	// JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;

	}

	public static void music(String filepath, double gain) {

		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			
			
			
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);

		} catch (Exception ex) {
			System.out.println("No audio found!");
			ex.printStackTrace();

		}

	}
	public static void setVolumeLow() {
		music("src/Blossoming Inspiration Loop (online-audio-converter.com).wav",.25);

	}

	public static void setVolumeMed() {
		music("src/Blossoming Inspiration Loop (online-audio-converter.com).wav",.55);
	}

	public static void setVolumeHigh() {
		music("src/Blossoming Inspiration Loop (online-audio-converter.com).wav",2);
	}

	// this starts the timer (the game loop is started here
	public void startGame() {
		timer.start();

		music("src/Blossoming Inspiration Loop (online-audio-converter.com).wav",1);
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update() {
		
		//if (!isInstructions && !isGamePaused) {
			screenManager.update();
		//}

	}

	public void draw() {
		screenManager.draw(graphicsHandler);

	

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
