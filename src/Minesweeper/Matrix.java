package Minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Matrix extends JFrame {
	private static final long serialVersionUID = -9115614610357804995L;
	
	//Desktop.getDesktop().browse(new URI(https://github.com/kerimdragolj/Minesweepers));
	//JLabel label = new JLabel(new ImageIcon(Matrix.class.getResource("/resource/boom.png")));
	
	//declaration of ...
	private int[][] matrix;
	private int a = 0;
	private int size = 50;
	private int[][] matrix1;
	
	private ImageIcon cool;
	private ImageIcon sad;
	private ImageIcon happy;
	private ImageIcon flag;
	private ImageIcon zero;
	private ImageIcon one;
	private ImageIcon two;
	private ImageIcon three;
	private ImageIcon four;
	private ImageIcon five;
	private ImageIcon six;
	private ImageIcon seven;
	private ImageIcon eight;
	private ImageIcon nine;
	private ImageIcon boom;
	private ImageIcon danger;
	private ImageIcon skull;
	
	private String soundExp = "/resources/clearExplosion.wav";
	
	private JPanel panel = new JPanel();
	private JButton[][] buttons;
	private Font f = new Font("serif", Font.BOLD, 24);
	private Color c = new Color(161,252,109);
	private Color c1 = new Color(80,173,26);
	

	public Matrix() {
		
		flag = new ImageIcon(Matrix.class.getResource("/resources/Flag.png"));
		zero = new ImageIcon(Matrix.class.getResource("/resources/Number_zero_24.png"));
		one = new ImageIcon(Matrix.class.getResource("/resources/Number_one_24.png"));
		two = new ImageIcon(Matrix.class.getResource("/resources/Number_two_24.png"));
		three = new ImageIcon(Matrix.class.getResource("/resources/Number_three_Button_24.png"));
		four = new ImageIcon(Matrix.class.getResource("/resources/Number_four_24.png"));
		five = new ImageIcon(Matrix.class.getResource("/resources/Number_5_24.png"));
		six = new ImageIcon(Matrix.class.getResource("/resources/six.png"));
		boom = new ImageIcon(Matrix.class.getResource("/resources/boom1.png"));
		skull = new ImageIcon(Matrix.class.getResource("/resources/blackSkull.png"));
		sad = new ImageIcon(Matrix.class.getResource("/resources/sad.png"));
		happy = new ImageIcon(Matrix.class.getResource("/resources/happy.png"));
		cool = new ImageIcon(Matrix.class.getResource("/resources/cool.png"));
		
		
		try { //parse inserted string to number and setting it as size of matrix and number of buttons
				do {	
					String s = (String)(JOptionPane
							.showInputDialog(null,"Insert number of fields in range 3 - 25 (n x n)", "Minesweeper", JOptionPane.INFORMATION_MESSAGE, cool, null, ""));
					a = Integer.parseInt(s);
				} while(a < 3 || a > 25);
				
				matrix1 = new int[a][a];
				buttons = new JButton[a][a];
				
		} catch (NumberFormatException e) { //if there is nothing inserted inside of field throw this exception
			System.exit(0);
		}
		
		int mines = a;
		for (int i = 0; i < mines; i++) {
			matrix1[((int) (Math.random() * a))][((int) (Math.random() * a))] = -1;

		}
		
		//add set matrix with mines and numbers around mines
		matrix = matrixWithMines(matrix1);

		//printing solution on console
		 for (int i = 0; i < a; i++) { 
		   	System.out.println(); 
		   	for (int j = 0; j < a; j++) {
		    		System.out.printf("%2d", matrix[i][j]);
		    	 } 
		   }
		 
		//setting layout for buttons
		//setLayout(new GridLayout(a + 1, a, 1, 1));
		setSize(size * a, size * a);

		//creating buttons, setting background and adding mouse listeners on each one of them
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < a; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setOpaque(true);
				buttons[i][j].setBackground(Color.DARK_GRAY);
				buttons[i][j].addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getButton() == 1) { //if right click is pressed
							for (int i = 0; i < buttons.length; i++) {
								for (int j = 0; j < buttons[i].length; j++) {
									if (e.getSource() == buttons[i][j]) {
										if (matrix[i][j] != -1) { //if on that position is not mine
											openField(i, j); //open field method
										} else { //if on that position is mine
											buttons[i][j].setIcon(boom); //set icon
											buttons[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
											buttons[i][j].setBackground(Color.RED); //set background color
											for (int k = 0; k < buttons.length; k++) {
												for (int z = 0; z < buttons[i].length; z++) { 
													//and search for all other mines and on their positions set icon nad background
													if (matrix[k][z] == -1 && buttons[k][z].getIcon() == null || buttons[k][z].getIcon() == flag) {
														buttons[k][z].setIcon(skull);
														buttons[k][z].setBorder(BorderFactory.createLoweredBevelBorder());
														buttons[k][z].setBackground(Color.RED);
													}
												}
											} //if mine is discovered open this JOptionPane
											playSound(soundExp);
											
											
											int x = JOptionPane.showOptionDialog(null, "KABOOOOOMMMM!! GAME OVER!! \n            PLAY AGAIN?", "Game Over!", 
														JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, sad, new String[]{"Yes","No","Github.com"}, null);
											 
											// new window when game over
											if (x == JOptionPane.YES_OPTION) {
												// closing old minesweeper window
												dispose();
												// opening new one
												new Matrix();
											} else if(x == JOptionPane.CANCEL_OPTION){
												
												try {
													Desktop.getDesktop().browse(new URI("https://github.com/kerimdragolj/Minesweepers"));
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												} catch (URISyntaxException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												}
												dispose();
											} else {
												System.exit(0);
											}
										}
										if(won()) { //method that checks if you have won, if its true open new JOptionPane
											if (JOptionPane
													.showConfirmDialog(
															null,
															"WELL DONE!! YOUR REVEALD ALL SAFE FIELDS! \n            PLAY AGAIN?",
															"WARNING",
															JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, happy) == JOptionPane.YES_OPTION) {
												dispose(); //if answer is yes, dispose last window and open new one
												new Matrix();
											} else { //if answer is no, just exit
												System.exit(0);
											}
										} 
									}
								}
							}
						} else if(e.getButton() == 3) { //if left click is pressed
							for (int i = 0; i < buttons.length; i++) {
								for (int j = 0; j < buttons[i].length; j++) {
									if (e.getSource() == buttons[i][j]) {
										if(buttons[i][j].getIcon() == null && buttons[i][j].getText().equals("")) {
											buttons[i][j].setIcon(flag); //set flag if that button is not revealed
										} else if(buttons[i][j].getIcon() == flag){ 
											buttons[i][j].setIcon(null); //if flag is already on that button, delete it 
										}	
									}
								}
							}
						}
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						super.mouseEntered(e);
						for (int i = 0; i < buttons.length; i++) {
							for (int j = 0; j < buttons[i].length; j++) {
								if (e.getSource() == buttons[i][j]) { //if cursor entered on that button
									if(buttons[i][j].getBackground() == Color.DARK_GRAY) {
										buttons[i][j].setOpaque(true);
										buttons[i][j].setBackground(Color.LIGHT_GRAY); //set background color to white
									}
								}
							}
						}
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						super.mouseExited(e);
						for (int i = 0; i < buttons.length; 
								i++) {
							for (int j = 0; j < buttons[i].length; j++) {
								if (e.getSource() == buttons[i][j]) { //if cursor exited from button
									if(buttons[i][j].getBackground() == Color.LIGHT_GRAY) {
										buttons[i][j].setOpaque(true); 
										buttons[i][j].setBackground(Color.DARK_GRAY); //set background back back to gray
									}
								}
							}
						}
					}
				});
				panel.setLayout(new GridLayout(a, a));
				panel.add(buttons[i][j]);
			}
		}
		//setting up characteristics  of frame
			
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	/**
	 * This method adds 1 to every number around '-1', only if its not -1
	 * 
	 * @param m
	 *            - matrix
	 * @return matrix with added numbers around '-1'
	 */
	public static int[][] matrixWithMines(int m[][]) {
		//firstly search for "-1"
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (m[i][j] == -1) { //if its found
					for (int k = i - 1; k <= i + 1; k++) {
						for (int y = j - 1; y <= j + 1; y++) {
							if (k >= 0 && y >= 0 && k < m.length
									&& y < m[i].length) {
								//add 1 to every number around it if its not -1
								if (m[k][y] != -1) 
									m[k][y] += 1;
							}
						}
					}
				}
			}
		}
		return m;
	}
	
	/**
	 * This recursive method reveals fields, if field is zero, opens it and fields around it,
	 * if that field is number bigger than zero, just reveals it
	 * @param x
	 * @param y
	 */
	public void openField(int x, int y) {
		
		//check if given numbers going out of window bounds
		if ((x < 0 || x >= a) || (y < 0 || y >= a)) {
			return;
		}	//checks if that field is zero and if its not already revealed
		if (matrix[x][y] == 0 && buttons[x][y].getBackground() != c) {
			buttons[x][y].setBorder(BorderFactory.createLoweredBevelBorder());
			buttons[x][y].setOpaque(true);
			buttons[x][y].setBackground(c);
			buttons[x][y].setIcon(zero);
			
			openField(x + 1, y);
			openField(x - 1, y);
			openField(x, y + 1);
			openField(x, y - 1);
			openField(x + 1, y - 1);
			openField(x - 1, y + 1);
			openField(x - 1, y - 1);
			openField(x + 1, y + 1);
		//if number on that field is bigger than zero
		//sets loweredBevelBorder, background, and different icons for each number
		} else if(matrix[x][y] > 0){
			buttons[x][y].setBorder(BorderFactory.createLoweredBevelBorder());
			buttons[x][y].setOpaque(true);
			buttons[x][y].setBackground(c1);
			if(matrix[x][y] == 1) { 
				buttons[x][y].setIcon(one);
			} else if(matrix[x][y] == 2) {
				buttons[x][y].setIcon(two);
			} else if(matrix[x][y] == 3) {
				buttons[x][y].setIcon(three);
			} else if(matrix[x][y] == 4) {
				buttons[x][y].setIcon(four);
			} else if(matrix[x][y] == 5) {
				buttons[x][y].setIcon(five);
			} else if(matrix[x][y] == 6) {
				buttons[x][y].setIcon(six);
			} else if(matrix[x][y] > 6) {
				buttons[x][y].setIcon(danger);
			}
		}
		repaint();
		return;
	}

	public static void main(String[] args) {
		
		new Matrix();
		try {
			

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException

				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		
	}
	
	
	/**
	 * This method check if u have already won
	 * @return
	 */
	public boolean won() {

		int counter = 0;
		int counter1 = 0;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) { 
				//counts how many unrevealed fields there is
				if (buttons[i][j].getBackground() != c && buttons[i][j].getBackground() != c1) {
					counter++; //if field is not revealed add 1 to counter
				}
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] == -1) { //counts all fields that has mine
					counter1++;
				}
			}		
		}
		//compares if number of mines is equal to number of fields that aint revealed
		if (counter == counter1) {
			return true; //if result is true then u have won
		} else {
			return false; //if result is false then u havent won yet
		}
	}	
	
	static void playSound(String sound) {
		try {
			URL url = Matrix.class.getResource(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/50000);
		} catch(Exception e) {
			
		}
	}
}