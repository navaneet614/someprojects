import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class MenuScreen extends JPanel implements MouseListener, KeyListener {

	private Game game; // the game panel
	public JFrame window;  // the window
	private JLabel start, options, exit, resume, restart, newGame, mainMenu, controls, changeCar, changeGamemode, sound,
			optionsBack, car1, car2, car3, car4, car5, car6, destruction, race, changeMusic;  //all the buttons you can click in the menus
	private Font menuFont, selectedFont; //the fonts used for the buttons
	private Image background, tutorial, changeCarImage; //the images used
	private JPanel control, optionPanel; //the jpanels shown when you click controls or options
	private ArrayList<JLabel> labelsShowing; //the array list of the labels on the screen
	public int selectedIndex; //the index (in labelsShowing) of the label that is selected
	private boolean changeCarShowing, changeGamemodeShowing, showRace; //the booleans that determine what images are shown in the menu
	public Image selectedCarImage; //the image of the car selected
	private String raceDesc1, raceDesc2, raceDesc3, destructDesc1, destructDesc2; //text about the gamemodes
	
	private boolean justChanged = false;
	
	private Thread bg;

	public enum gameModes { //all the different gamemodes
		RACE, DESTRUCTION
	};

	public gameModes modeSelected; //the gamemode that is selected
	public Sound backGroundMusic;

	public MenuScreen() {
		raceDesc1 = "In this mode, you have to dodge";
		raceDesc2 = "all the cars on the highway";
		raceDesc3 = "while trying to get the highest score you can.";
		destructDesc1 = "In this mode, you have to hit";
		destructDesc2 = "as many cars as you can in 30 seconds.";
		modeSelected = gameModes.RACE; //the default gamemode is race
		showRace = true;
		labelsShowing = new ArrayList<JLabel>();
		selectedIndex = 0;
		background = new ImageIcon(Menu.class.getResource("/resources/background.png")).getImage();
		tutorial = new ImageIcon(Menu.class.getResource("/resources/controls.png")).getImage();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//gets the screen resolution of the system

		//making the window
		window = new JFrame("Highway Racing");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(100, 100, (int) (screenSize.getWidth() / (1920.0 / 600)),
				(int) (screenSize.getHeight() / (1080.0 / 900)));
		window.setResizable(false);
		window.addKeyListener(this); //used for the menu
//		 window.setUndecorated(true);
		window.setVisible(true);
		
		game = new Game(this);

		control = new JPanel() { //initializing the jpanel shown when controls is clicked
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background, 0, 0, 600, 900, null);
				g.drawImage(tutorial, 0, 0, 600, 900, null);
			}
		};
		//removing control when a key is pressed or the mouse is clicked
		control.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				window.remove(control);
				setVisible(true);
				game.setVisible(true);
				window.requestFocus();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		control.addKeyListener((new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				window.remove(control);
				setVisible(true);
				game.setVisible(true);
				window.requestFocus();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		}));

		//initializing all the labels that are shown in the menu
		menuFont = new Font("Comic Sans MS", Font.PLAIN, 30);
		selectedFont = new Font("Comic Sans MS", Font.PLAIN, 40);
		this.setBorder(new EmptyBorder(350, 0, 150, 0));
		this.setLayout(new GridLayout(0, 1));

		start = new JLabel("Start", SwingConstants.CENTER);
		start.setFont(menuFont);
		start.addMouseListener(this);
		start.setForeground(Color.WHITE);

		options = new JLabel("Options", SwingConstants.CENTER);
		options.setFont(menuFont);
		options.addMouseListener(this);
		options.setForeground(Color.WHITE);

		exit = new JLabel("Exit", SwingConstants.CENTER);
		exit.setFont(menuFont);
		exit.addMouseListener(this);
		exit.setForeground(Color.WHITE);

		resume = new JLabel("Resume", SwingConstants.CENTER);
		resume.setFont(menuFont);
		resume.addMouseListener(this);
		resume.setForeground(Color.WHITE);

		restart = new JLabel("Restart", SwingConstants.CENTER);
		restart.setFont(menuFont);
		restart.addMouseListener(this);
		restart.setForeground(Color.WHITE);

		newGame = new JLabel("New Game", SwingConstants.CENTER);
		newGame.setFont(menuFont);
		newGame.addMouseListener(this);
		newGame.setForeground(Color.WHITE);

		mainMenu = new JLabel("Main Menu", SwingConstants.CENTER);
		mainMenu.setFont(menuFont);
		mainMenu.addMouseListener(this);
		mainMenu.setForeground(Color.WHITE);

		controls = new JLabel("Controls", SwingConstants.CENTER);
		controls.setFont(menuFont);
		controls.addMouseListener(this);
		controls.setForeground(Color.WHITE);

		changeCar = new JLabel("Change Car", SwingConstants.CENTER);
		changeCar.setFont(menuFont);
		changeCar.addMouseListener(this);
		changeCar.setForeground(Color.WHITE);

		changeGamemode = new JLabel("Change Gamemode", SwingConstants.CENTER);
		changeGamemode.setFont(menuFont);
		changeGamemode.addMouseListener(this);
		changeGamemode.setForeground(Color.WHITE);

		sound = new JLabel("Sound: On", SwingConstants.CENTER);
		sound.setFont(menuFont);
		sound.addMouseListener(this);
		sound.setForeground(Color.WHITE);

		optionsBack = new JLabel("Back", SwingConstants.CENTER);
		optionsBack.setFont(menuFont);
		optionsBack.addMouseListener(this);
		optionsBack.setForeground(Color.WHITE);

		//initializing the panel shown when options is clicked
		optionPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background, 0, 0, 600, 900, null);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
				g.setColor(Color.WHITE);
				g.drawString("Options", 300 - g.getFontMetrics().stringWidth("Options") / 2, 150);
				//drawing the pictures needed when the user is selecting a car
				if (changeCarShowing) {
					g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
					g.drawString("Car " + (Arrays.asList(game.carImages).indexOf(changeCarImage) + 1), 400, 600);
					g.drawImage(changeCarImage, 400, 450, game.carWidth, game.carHeight, this);
				///drawing the pictures needed when the user is selecting a gamemode
				} else if (changeGamemodeShowing) {
					g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
					//if the user wants the description of racing
					if (showRace) {
						g.drawImage(new ImageIcon(Menu.class.getResource("/resources/racePic.jpg")).getImage(), 100,
								200, 400, 200, this);
						g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
						g.drawString(raceDesc1, 300 - g.getFontMetrics().stringWidth(raceDesc1) / 2, 440);
						g.drawString(raceDesc2, 300 - g.getFontMetrics().stringWidth(raceDesc2) / 2, 460);
						g.drawString(raceDesc3, 300 - g.getFontMetrics().stringWidth(raceDesc3) / 2, 480);
					//if the user wants the description of destruction
					} else if (!showRace) {
						g.drawImage(new ImageIcon(Menu.class.getResource("/resources/destructPic.jpg")).getImage(), 100,
								200, 400, 200, this);
						g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
						g.drawString(destructDesc1, 300 - g.getFontMetrics().stringWidth(destructDesc1) / 2, 440);
						g.drawString(destructDesc2, 300 - g.getFontMetrics().stringWidth(destructDesc2) / 2, 460);
					}
					//information about changing the gamemode
					g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
					// g.setColor(Color.RED);
					String infoTxt1 = "If you change the gamemode,";
					String infoTxt2 = "the game will restart.";
					g.drawString(infoTxt1, 300 - g.getFontMetrics().stringWidth(infoTxt1) / 2, 520);
					g.drawString(infoTxt2, 300 - g.getFontMetrics().stringWidth(infoTxt2) / 2, 550);
				}
			}
		};

		//initializing more menu options
		car1 = new JLabel("Car 1", SwingConstants.LEFT);
		car1.setFont(menuFont);
		car1.addMouseListener(this);
		car1.setForeground(Color.WHITE);
		car2 = new JLabel("Car 2", SwingConstants.LEFT);
		car2.setFont(menuFont);
		car2.addMouseListener(this);
		car2.setForeground(Color.WHITE);
		car3 = new JLabel("Car 3", SwingConstants.LEFT);
		car3.setFont(menuFont);
		car3.addMouseListener(this);
		car3.setForeground(Color.WHITE);
		car4 = new JLabel("Car 4", SwingConstants.LEFT);
		car4.setFont(menuFont);
		car4.addMouseListener(this);
		car4.setForeground(Color.WHITE);
		car5 = new JLabel("Car 5", SwingConstants.LEFT);
		car5.setFont(menuFont);
		car5.addMouseListener(this);
		car5.setForeground(Color.WHITE);
		car6 = new JLabel("Car 6", SwingConstants.LEFT);
		car6.setFont(menuFont);
		car6.addMouseListener(this);
		car6.setForeground(Color.WHITE);

		race = new JLabel("Highway Racing (Selected)");
		race.setFont(menuFont);
		race.addMouseListener(this);
		race.setForeground(Color.WHITE);
		destruction = new JLabel("Destruction Derby");
		destruction.setFont(menuFont);
		destruction.addMouseListener(this);
		destruction.setForeground(Color.WHITE);
		
		changeMusic = new JLabel("Change Music", SwingConstants.CENTER);
		changeMusic.setFont(menuFont);
		changeMusic.addMouseListener(this);
		changeMusic.setForeground(Color.WHITE);
				
		backGroundMusic = new Sound("bgMusic.wav");
		bg = new Thread(backGroundMusic);
		bg.start();
		
		//starting with the main menu
		mainMenu();

//		window.setVisible(true);

	}
	
	//draws the background of the menu
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 600, 900, null);
		g.setFont(new Font("Comic Sans MS", Font.ITALIC | Font.BOLD, 40));
		g.setColor(Color.BLACK);
		g.drawString("Highway Racing", 300 - (g.getFontMetrics().stringWidth("Highway Racing")) / 2, 100);
	}

	//displays the main menu
	private void mainMenu() {
		selectedIndex = 0;
		labelsShowing.clear();
		window.remove(game);
		game = new Game(this);
		window.add(this);
		this.setVisible(true);
		labelsShowing.add(start);
		labelsShowing.add(options);
		labelsShowing.add(controls);
		labelsShowing.add(exit);
		for (int i = 0; i < labelsShowing.toArray().length; i++) {
			this.add(labelsShowing.get(i));
		}
		this.repaint();
		labelsShowing.get(selectedIndex).setFont(selectedFont);
		window.repaint();
		window.revalidate();
	}

	//called when start is clicked
	private void start() {
		this.remove(game);
		game = new Game(this);
		window.addKeyListener(game.player);
		window.addKeyListener(game);
		window.remove(this);
		window.add(game);
		game.timer.start();
		window.repaint();
		window.revalidate();
	}

	//called when the game is paused
	public void pause(Graphics g) {
		labelsShowing.clear();
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("Game Paused", 300 - g.getFontMetrics().stringWidth("Game Paused") / 2, 200);
		game.setBorder(new EmptyBorder(350, 0, 150, 0));
		game.setLayout(new GridLayout(0, 1, 1, 1));
		labelsShowing.add(resume);
		labelsShowing.add(restart);
		labelsShowing.add(options);
		labelsShowing.add(controls);
		labelsShowing.add(mainMenu);
		labelsShowing.add(exit);
		for (int i = 0; i < labelsShowing.toArray().length; i++) {
			game.add(labelsShowing.get(i));
		}
		labelsShowing.get(selectedIndex).setFont(selectedFont);
		game.revalidate();
	}
	
	//called when resume is clicked
	public void resume() {
		game.remove(resume);
		game.remove(options);
		game.remove(exit);
		game.remove(restart);
		game.remove(newGame);
		game.remove(mainMenu);
		game.remove(controls);
		resetLabels();
		if (game.pause)
			game.pause = false;
		if(justChanged){
			justChanged = false;
			game.timeUsed = 0;
		}
		game.lastTime = System.currentTimeMillis();
		selectedIndex = 0;
	}

	//called when the game has ended
	public void end(Graphics g) {
		labelsShowing.clear();
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		g.drawString("Game Over", 300 - g.getFontMetrics().stringWidth("Game Over") / 2, 100);
		game.setBorder(new EmptyBorder(350, 0, 150, 0));
		game.setLayout(new GridLayout(0, 1, 1, 1));
		labelsShowing.add(newGame);
		labelsShowing.add(options);
		labelsShowing.add(controls);
		labelsShowing.add(mainMenu);
		labelsShowing.add(exit);
		for (int i = 0; i < labelsShowing.toArray().length; i++) {
			game.add(labelsShowing.get(i));
		}
		labelsShowing.get(selectedIndex).setFont(selectedFont);
		game.revalidate();
	}

	//called when controls is clicked
	private void controls() {
		this.setVisible(false);
		game.setVisible(false);
		window.add(control);
		control.requestFocus();
		window.revalidate();
		window.repaint();
	}

	//called when options is clicked
	private void options(boolean remove) {
		game.setVisible(remove);
		this.setVisible(remove);
		if (!remove) {
			selectedIndex = 0;
			labelsShowing.clear();
			window.add(optionPanel);
			optionPanel.removeAll();
			optionPanel.setBorder(new EmptyBorder(350, 0, 150, 0));
			optionPanel.setLayout(new GridLayout(0, 1));
			labelsShowing.add(changeCar);
			labelsShowing.add(changeGamemode);
			labelsShowing.add(sound);
			labelsShowing.add(changeMusic);
			labelsShowing.add(optionsBack);
			for (int i = 0; i < labelsShowing.toArray().length; i++) {
				optionPanel.add(labelsShowing.get(i));
			}
		} else {
			selectedIndex = 0;
			labelsShowing.clear();
			game.removeAll();
			window.remove(optionPanel);
			optionPanel.removeAll();
			game.setBorder(new EmptyBorder(350, 0, 150, 0));
			game.setLayout(new GridLayout(0, 1));
			if (changeCarShowing) {
				changeCarShowing = false;
				options(false);
			} else if (changeGamemodeShowing) {
				changeGamemodeShowing = false;
				options(false);
			} else if (game.pause) {
				labelsShowing.add(resume);
				labelsShowing.add(restart);
				labelsShowing.add(options);
				labelsShowing.add(controls);
				labelsShowing.add(mainMenu);
				labelsShowing.add(exit);
				for (int i = 0; i < labelsShowing.toArray().length; i++) {
					game.add(labelsShowing.get(i));
				}
			} else if (game.gameEnd) {
				labelsShowing.add(newGame);
				labelsShowing.add(options);
				labelsShowing.add(controls);
				labelsShowing.add(mainMenu);
				labelsShowing.add(exit);
				for (int i = 0; i < labelsShowing.toArray().length; i++) {
					game.add(labelsShowing.get(i));
				}
			} else {
				this.setVisible(true);
				this.removeAll();
				labelsShowing.add(start);
				labelsShowing.add(options);
				labelsShowing.add(controls);
				labelsShowing.add(exit);
				for (int i = 0; i < labelsShowing.toArray().length; i++) {
					this.add(labelsShowing.get(i));
				}
			}
		}
		labelsShowing.get(selectedIndex).setFont(selectedFont);
		window.revalidate();
		window.repaint();
	}

	// called when the user wants to change their car
	private void changeCar() {
		if (!changeCarShowing) {
			selectedIndex = 0;
		}
		changeCarShowing = true;
		optionPanel.removeAll();
		labelsShowing.clear();
		optionPanel.repaint();
		optionPanel.setLayout(new GridLayout(0, 1));
		labelsShowing.add(car1);
		labelsShowing.add(car2);
		labelsShowing.add(car3);
		labelsShowing.add(car4);
		labelsShowing.add(car5);
		labelsShowing.add(car6);
		labelsShowing.add(optionsBack);
		for (int i = 0; i < labelsShowing.toArray().length; i++) {
			optionPanel.add(labelsShowing.get(i));
		}
		if (game.player.getImage().equals(game.carImages[0]))
			labelSelected(car1);
		else if (game.player.getImage().equals(game.carImages[1]))
			labelSelected(car2);
		else if (game.player.getImage().equals(game.carImages[2]))
			labelSelected(car3);
		else if (game.player.getImage().equals(game.carImages[3]))
			labelSelected(car4);
		else if (game.player.getImage().equals(game.carImages[4]))
			labelSelected(car5);
		else if (game.player.getImage().equals(game.carImages[5]))
			labelSelected(car6);
		else {
			labelSelected(car4);
		}
		optionPanel.revalidate();
	}

	// called when the user wants to change the gamemode
	private void changeGamemode() {
		if (!changeGamemodeShowing) {
			selectedIndex = 0;
		}
		changeGamemodeShowing = true;
		optionPanel.removeAll();
		labelsShowing.clear();
		optionPanel.repaint();
		optionPanel.setLayout(new GridLayout(0, 1));
		optionPanel.setBorder(new EmptyBorder(550, 0, 100, 0));
		if (modeSelected.equals(gameModes.RACE))
			race.setText("Highway Racing (Selected)");
		else if (modeSelected.equals(gameModes.DESTRUCTION))
			destruction.setText("Destruction Derby (Selected)");
		labelsShowing.add(race);
		labelsShowing.add(destruction);
		labelsShowing.add(optionsBack);
		for (int i = 0; i < labelsShowing.toArray().length; i++) {
			optionPanel.add(labelsShowing.get(i));
		}
		optionPanel.revalidate();
	}
	
	private void changeMusic(){
		backGroundMusic.stop();
		JFrame j = new JFrame("Music Change");
		j.setResizable(false);
		j.setAlwaysOnTop(true);
		j.setBounds(window.getX()+window.getWidth()/2-window.getWidth()/6, window.getY()+window.getHeight()/2-window.getHeight()/6, window.getWidth()/3, window.getHeight()/3);
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JTextPane input = new JTextPane();
		input.setText("Type the file you want to play here");
		j.add(input);
		input.setAutoscrolls(true);
		input.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent arg0) {
				backGroundMusic = new Sound(input.getText());
				bg = new Thread(backGroundMusic);
				if(game.playSound){
					bg.start();
				}
			}});
		j.setVisible(true);
	}

	// resets all the labels to the normal font
	private void resetLabels() {
		start.setFont(menuFont);
		options.setFont(menuFont);
		exit.setFont(menuFont);
		resume.setFont(menuFont);
		restart.setFont(menuFont);
		newGame.setFont(menuFont);
		mainMenu.setFont(menuFont);
		controls.setFont(menuFont);
		changeCar.setFont(menuFont);
		changeGamemode.setFont(menuFont);
		sound.setFont(menuFont);
		optionsBack.setFont(menuFont);
		car1.setFont(menuFont);
		car2.setFont(menuFont);
		car3.setFont(menuFont);
		car4.setFont(menuFont);
		car5.setFont(menuFont);
		car6.setFont(menuFont);
		race.setFont(menuFont);
		destruction.setFont(menuFont);
		changeMusic.setFont(menuFont);
	}

	// resets the car and gamemode labels
	private void resetChangeLabels() {
		car1.setText("Car 1");
		car2.setText("Car 2");
		car3.setText("Car 3");
		car4.setText("Car 4");
		car5.setText("Car 5");
		car6.setText("Car 6");
		race.setText("Highway Racing");
		destruction.setText("Destruction Derby");
	}

	// updates the labels when a label is clicked (takes in the label clicked)
	private void labelSelected(Object j) {
		if (j.equals(start)) {
			start();
		} else if (j.equals(options)) {
			options(false);
		} else if (j.equals(exit)) {
			System.exit(0);
		} else if (j.equals(resume)) {
			resume();
		} else if (j.equals(restart) || j.equals(newGame)) {
			game.restart();
			this.setVisible(false);
			game.setVisible(true);
			resume();
		} else if (j.equals(mainMenu)) {
			mainMenu();
		} else if (j.equals(controls)) {
			controls();
		} else if (j.equals(optionsBack)) {
			options(true);
		} else if (j.equals(changeCar)) {
			changeCar();
		} else if (j.equals(changeGamemode)) {
			changeGamemode();
		} else if (j.equals(sound)) {
			game.playSound = !game.playSound;
			if (game.playSound){
				sound.setText("Sound: On");
				backGroundMusic.start();
			}
			else{
				sound.setText("Sound: Off");
				backGroundMusic.stop();
			}
			sound.setFont(selectedFont);
		} else if (j.equals(car1)) {
			selectedCarImage = game.carImages[0];
			game.player.setImage(game.carImages[0]);
			resetChangeLabels();
			car1.setText("Car 1 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(car2)) {
			selectedCarImage = game.carImages[1];
			game.player.setImage(game.carImages[1]);
			resetChangeLabels();
			car2.setText("Car 2 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(car3)) {
			selectedCarImage = game.carImages[2];
			game.player.setImage(game.carImages[2]);
			resetChangeLabels();
			car3.setText("Car 3 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(car4)) {
			selectedCarImage = game.carImages[3];
			game.player.setImage(game.carImages[3]);
			resetChangeLabels();
			car4.setText("Car 4 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(car5)) {
			selectedCarImage = game.carImages[4];
			game.player.setImage(game.carImages[4]);
			resetChangeLabels();
			car5.setText("Car 5 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(car6)) {
			selectedCarImage = game.carImages[5];
			game.player.setImage(game.carImages[5]);
			resetChangeLabels();
			car6.setText("Car 6 (Selected)");
			optionPanel.repaint();
		} else if (j.equals(race)) {
			resetChangeLabels();
			race.setText("Highway Racing (Selected)");
			modeSelected = gameModes.RACE;
			optionPanel.repaint();
			game.restart();
		} else if (j.equals(destruction)) {
			resetChangeLabels();
			destruction.setText("Destruction Derby (Selected)");
			modeSelected = gameModes.DESTRUCTION;
			optionPanel.repaint();
			game.restart();
			justChanged = true;
		} else if (j.equals(changeMusic)) {
			changeMusic();
		}

	}

	// updates the font of the labels on mouseover
	@Override
	public void mouseEntered(MouseEvent m) {
		resetLabels();
		if (m.getSource().equals(start)) {
			start.setFont(selectedFont);
		} else if (m.getSource().equals(newGame)) {
			newGame.setFont(selectedFont);
		} else if (m.getSource().equals(options)) {
			options.setFont(selectedFont);
		} else if (m.getSource().equals(exit)) {
			exit.setFont(selectedFont);
		} else if (m.getSource().equals(resume)) {
			resume.setFont(selectedFont);
		} else if (m.getSource().equals(restart)) {
			restart.setFont(selectedFont);
		} else if (m.getSource().equals(mainMenu)) {
			mainMenu.setFont(selectedFont);
		} else if (m.getSource().equals(controls)) {
			controls.setFont(selectedFont);
		} else if (m.getSource().equals(changeCar)) {
			changeCar.setFont(selectedFont);
		} else if (m.getSource().equals(optionsBack)) {
			optionsBack.setFont(selectedFont);
		} else if (m.getSource().equals(changeCar)) {
			changeCar.setFont(selectedFont);
		} else if (m.getSource().equals(changeGamemode)) {
			changeGamemode.setFont(selectedFont);
		} else if (m.getSource().equals(sound)) {
			sound.setFont(selectedFont);
		} else if (m.getSource().equals(car1)) {
			car1.setFont(selectedFont);
			changeCarImage = game.carImages[0];
			optionPanel.repaint();
		} else if (m.getSource().equals(car2)) {
			car2.setFont(selectedFont);
			changeCarImage = game.carImages[1];
			optionPanel.repaint();
		} else if (m.getSource().equals(car3)) {
			car3.setFont(selectedFont);
			changeCarImage = game.carImages[2];
			optionPanel.repaint();
		} else if (m.getSource().equals(car4)) {
			car4.setFont(selectedFont);
			changeCarImage = game.carImages[3];
			optionPanel.repaint();
		} else if (m.getSource().equals(car5)) {
			car5.setFont(selectedFont);
			changeCarImage = game.carImages[4];
			optionPanel.repaint();
		} else if (m.getSource().equals(car6)) {
			car6.setFont(selectedFont);
			changeCarImage = game.carImages[5];
			optionPanel.repaint();
		} else if (m.getSource().equals(race)) {
			race.setFont(selectedFont);
			showRace = true;
			optionPanel.repaint();
		} else if (m.getSource().equals(destruction)) {
			destruction.setFont(selectedFont);
			showRace = false;
			optionPanel.repaint();
		} else if(m.getSource().equals(changeMusic)){
			changeMusic.setFont(selectedFont);
		}

		selectedIndex = labelsShowing.indexOf(m.getSource());
	}

	// updates the labels when mouse has left the label
	@Override
	public void mouseExited(MouseEvent m) {
		if (m.getSource().equals(start)) {
			start.setFont(menuFont);
		} else if (m.getSource().equals(newGame)) {
			newGame.setFont(menuFont);
		} else if (m.getSource().equals(options)) {
			options.setFont(menuFont);
		} else if (m.getSource().equals(exit)) {
			exit.setFont(menuFont);
		} else if (m.getSource().equals(resume)) {
			resume.setFont(menuFont);
		} else if (m.getSource().equals(restart)) {
			restart.setFont(menuFont);
		} else if (m.getSource().equals(mainMenu)) {
			mainMenu.setFont(menuFont);
		} else if (m.getSource().equals(controls)) {
			controls.setFont(menuFont);
		} else if (m.getSource().equals(changeCar)) {
			changeCar.setFont(menuFont);
		} else if (m.getSource().equals(optionsBack)) {
			optionsBack.setFont(menuFont);
		} else if (m.getSource().equals(changeCar)) {
			changeCar.setFont(menuFont);
		} else if (m.getSource().equals(changeGamemode)) {
			changeGamemode.setFont(menuFont);
		} else if (m.getSource().equals(sound)) {
			sound.setFont(menuFont);
		} else if (m.getSource().equals(car1)) {
			car1.setFont(menuFont);
		} else if (m.getSource().equals(car2)) {
			car2.setFont(menuFont);
		} else if (m.getSource().equals(car3)) {
			car3.setFont(menuFont);
		} else if (m.getSource().equals(car4)) {
			car4.setFont(menuFont);
		} else if (m.getSource().equals(car5)) {
			car5.setFont(menuFont);
		} else if (m.getSource().equals(car6)) {
			car6.setFont(menuFont);
		}
		if (!labelsShowing.get(selectedIndex).equals(car1) || !labelsShowing.get(selectedIndex).equals(car2)
				|| !labelsShowing.get(selectedIndex).equals(car3) || !labelsShowing.get(selectedIndex).equals(car4)
				|| !labelsShowing.get(selectedIndex).equals(car5) || !labelsShowing.get(selectedIndex).equals(car6)) {
			changeCarImage = selectedCarImage;
			optionPanel.repaint();
		} else if (m.getSource().equals(race)) {
			race.setFont(menuFont);
			if (modeSelected.equals(gameModes.RACE))
				showRace = true;
			else
				showRace = false;
			optionPanel.repaint();
		} else if (m.getSource().equals(destruction)) {
			destruction.setFont(menuFont);
			if (modeSelected.equals(gameModes.DESTRUCTION))
				showRace = false;
			else
				showRace = true;
			optionPanel.repaint();
		} else if(m.getSource().equals(changeMusic)){
			changeMusic.setFont(menuFont);
		}
	}

	@Override
	public void mousePressed(MouseEvent m) {
		labelSelected(m.getSource());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// updates the label that is highlighted when the user uses the arrow keys
	// and selects a label when enter is pressed
	@Override
	public void keyPressed(KeyEvent k) {
		resetLabels();
		switch (k.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if (selectedIndex < labelsShowing.size() - 1)
				selectedIndex++;
			else 
				selectedIndex = 0;
			labelsShowing.get(selectedIndex).setFont(selectedFont);
			break;
		case KeyEvent.VK_UP:
			if (selectedIndex != 0)
				selectedIndex--;
			else 
				selectedIndex = labelsShowing.size()-1;
			labelsShowing.get(selectedIndex).setFont(selectedFont);
			break;
		case KeyEvent.VK_ENTER:
			if (selectedIndex < labelsShowing.size())
				labelSelected(labelsShowing.get(selectedIndex));
			break;
		}
		if (labelsShowing.get(selectedIndex).equals(car1)) {
			changeCarImage = game.carImages[0];
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(car2)) {
			changeCarImage = game.carImages[1];
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(car3)) {
			changeCarImage = game.carImages[2];
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(car4)) {
			changeCarImage = game.carImages[3];
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(car5)) {
			changeCarImage = game.carImages[4];
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(car6)) {
			changeCarImage = game.carImages[5];
			optionPanel.repaint();
		} else if (!labelsShowing.get(selectedIndex).equals(car1) || !labelsShowing.get(selectedIndex).equals(car2)
				|| !labelsShowing.get(selectedIndex).equals(car3) || !labelsShowing.get(selectedIndex).equals(car4)
				|| !labelsShowing.get(selectedIndex).equals(car5) || !labelsShowing.get(selectedIndex).equals(car6)) {
			changeCarImage = selectedCarImage;
			optionPanel.repaint();
		}
		if (labelsShowing.get(selectedIndex).equals(race)) {
			showRace = true;
			optionPanel.repaint();
		} else if (labelsShowing.get(selectedIndex).equals(destruction)) {
			showRace = false;
			optionPanel.repaint();
		} else if (!labelsShowing.get(selectedIndex).equals(race)
				|| !labelsShowing.get(selectedIndex).equals(destruction)) {
			if (modeSelected.equals(gameModes.RACE)) {
				showRace = true;
				optionPanel.repaint();
			} else if (modeSelected.equals(gameModes.DESTRUCTION)) {
				showRace = false;
				optionPanel.repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	// the main method
	public static void main(String[] args) {
		new MenuScreen();
	}
}
