package game;

import java.awt.Color;      
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battle.ListOfMoves;
import characters.ListOfCharacters;
import characters.PlayableCharacter;
import characters.Squad;
import display.Display;
import graphics.Assets;
import graphics.GameCamera;
import graphics.ImageLoader;
import graphics.SpriteSheet;
import input.KeyManager;
import input.MouseManager;
import items.Inventory;
import items.ListOfInventoryItems;
import states.*;
import states.BattleState;
import states.BeepTestState;
import states.GameState;
import states.MathContestState;
import states.MenuState;
import states.SettingsState;
import states.State;
import states.StressEatsState;

 public class Game implements Runnable{

	private int width, height;
	private int recentlyPlayed;
	private int coins;
	private String title;
	private Display display;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	// States
	public State catchBusState;
	public State gameState;
	public State menuState;
	public State settingsState;
	public State battleState;
	public State stressEatsState;
	public State beepTestState;
	public State mathContestState;
	
	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// Camera
	private GameCamera gameCamera;
		
	// Handler
	private Handler handler;

	// Lists of stuff
	private ListOfCharacters listOfCharacters;
	private ListOfInventoryItems listOfInventoryItems;
	private ListOfMoves listOfMoves;
	private Inventory inventory;
	private Squad squad;
	private String currentOpponentName;
	private ArrayList<BufferedImage[]> currentOpponentSprites;
	
	public Game(String title, int width, int height) {
		coins = 0;
		this.width = width;
		this.height = height;
		this.title = title;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		listOfCharacters = new ListOfCharacters(handler);
		listOfInventoryItems = new ListOfInventoryItems();
		listOfMoves = new ListOfMoves();
		inventory = new Inventory();
		PlayableCharacter[] newSquad = new PlayableCharacter[6];
		newSquad[0] = (PlayableCharacter) listOfCharacters.returnCharacter("Feng");
		newSquad[1] = (PlayableCharacter) listOfCharacters.returnCharacter("Joyce");
		newSquad[2] = (PlayableCharacter) listOfCharacters.returnCharacter("Sihan");
		newSquad[3] = (PlayableCharacter) listOfCharacters.returnCharacter("Yash");
		newSquad[4] = (PlayableCharacter) listOfCharacters.returnCharacter("Johann");
		newSquad[5] = (PlayableCharacter) listOfCharacters.returnCharacter("Angela");
		//newSquad[5] = (PlayableCharacter) characterList.returnCharacter("Misha");
		//newSquad[5] = (PlayableCharacter)characterList.returnCharacter("Angela");
		squad = new Squad(newSquad);
		currentOpponentName = "";

	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		

		
		
		
		
		

		catchBusState = new CatchBusState(handler);		
		mathContestState = new MathContestState(handler);
		beepTestState = new BeepTestState(handler);
		stressEatsState = new StressEatsState(handler);
		
		settingsState = new SettingsState(handler);
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		battleState = new BattleState(handler, g);

		State.setState(menuState);
	}
	
	private void tick() {
		recentlyPlayed++;
		
		//System.out.println(coins);
		
		keyManager.tick();
		
		if (State.getState() instanceof GameState && recentlyPlayed > 180) {
			// CHANGE TO BATTLE STATE
			if(handler.getKeyManager().battle && !(State.getState() instanceof BattleState)) {
				
				battleState = new BattleState(handler, g);
				State.setState(battleState);
				if (!((BattleState) battleState).getBattleTest().isBattleStart()) {
					startBattle();
				}
				
			} else if (handler.getKeyManager().stressEat && !(State.getState() instanceof StressEatsState)) {
				State.setState(new StressEatsState(handler));
			} else if (handler.getKeyManager().beepTest && !(State.getState() instanceof BeepTestState)) {
				State.setState(new BeepTestState(handler));
			} else if (handler.getKeyManager().mathContest && !(State.getState() instanceof MathContestState)) {
				State.setState(new MathContestState(handler));
			} else if (handler.getKeyManager().catchBus && !(State.getState() instanceof CatchBusState)) {
				State.setState(new CatchBusState(handler));
			}
		}
		//System.out.println(State.getState());
		if(State.getState() != null) {
			State.getState().tick();
		}
	}

	public void startBattle() {
		((BattleState) battleState).getBattleTest().initializeBattleAssets();
		((BattleState) battleState).getBattleTest().startRandomBattle();
		((BattleState) battleState).getBattleTest().runPhase(-1);
	}

	private void render() {

		
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.setFont(Assets.font16);




		// clear
		g.clearRect(0, 0, width, height);
		
		
		//beginning of drawing

		if(State.getState() != null && State.getState() instanceof GameState) {
			g.setColor(Color.black);
			g.fillRect(-200, -200, width + 200, height + 200);
			State.getState().render(g);
		} else if (State.getState() != null) {
			State.getState().render(g);
		}
		
		if (handler.getKeyManager().universalEnter && !(State.getState() instanceof BattleState) && handler.getWorld().getPath().equals("res/worlds/guidance.txt")) {
			g.setColor(Color.green);
			g.fillRect(10, 0, 450, 30);
			g.setColor(Color.white);
			g.drawString("Your party has been healed!", 20, 20);
		}
		
		//end of drawing
		
		
		bs.show();
		g.dispose();
		
	}
	
	public void run() {
		
		init();
		
		int fps = 60;
		double timePerTick = 1_000_000_000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if (timer >= 1_000_000_000) {
				//System.out.println(ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		
		// this will call the run method
		thread.start();
	}
	
	public synchronized void stop() {
		
		if(!running) {
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public GameState getGameState() {
		return (GameState) gameState;
	}
	
	public BattleState getBattleState() {
		return (BattleState) battleState;
	}
	
	public void setGameState() {
		State.setState(gameState);
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public State getState() {
		return State.getState();
	}
	
	public void setRecentlyPlayed() {
		recentlyPlayed = 0;
	}

	public ListOfInventoryItems getListOfInventoryItems() {
		return listOfInventoryItems;
	}

	public ListOfCharacters getListOfCharacters() {
		return listOfCharacters;
	}

	public ListOfMoves getListOfMoves() {
		return listOfMoves;
	 }

	 public Inventory getInventory() {
		return inventory;
	 }

	 public Squad getSquad() {
		return squad;
	 }

	public void increaseScore(double d) {
		coins += (int) d;
	}

	public void setCurrentOpponentName(String name) {
		currentOpponentName = name;
	}
	
	public String getCurrentOpponentName() {
		return currentOpponentName;
	}
	
	public void setCurrentOpponentSprites(ArrayList<BufferedImage[]> sprites) {
		currentOpponentSprites = sprites;
	}
	
	public ArrayList<BufferedImage[]> getCurrentOpponentSprites() {
		return currentOpponentSprites;
	}
 }