import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	GameLogic gameLogic;
	private Toolbar toolbar;
	private Thread game;
	boolean nextClick = true; // if the next click should stop the simulation
	boolean firstStart = true; // first time start is pressed

	public MainFrame() {

		super("Juego de la vida");
		setLayout(new BorderLayout());

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		toolbar = new Toolbar();
		gameLogic = new GameLogic();

		add(gameLogic, BorderLayout.CENTER);
		add(toolbar, BorderLayout.SOUTH);
		ImageIcon img = new ImageIcon("icon.png");

		setIconImage(img.getImage());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(900, 600);
		setLocationRelativeTo(null);
		setVisible(true);

		// Boton Play y stop
		toolbar.play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (firstStart) {
					setGameBeingPlayed(true);
					toolbar.play.setText("STOP");
					toolbar.play.setBackground(Color.decode("#F1607E"));
					toolbar.play.setForeground(Color.decode("#F1607E"));

					firstStart = false;
				} else {
					if (nextClick) {
						setGameBeingPlayed(false);
					} else {
						setGameBeingPlayed(true);
					}
					if (nextClick) {
						toolbar.play.setText("PLAY");
						// Cambio el color de nuevo para que no se quede en rojo
						toolbar.play.setBackground(Color.decode("#1A936F"));
						toolbar.play.setForeground(Color.decode("#114B5F"));
						nextClick = false; // Parado, proximo click inicia

					} else {
						toolbar.play.setText("STOP");
						toolbar.play.setBackground(Color.decode("#E73C3E"));
						toolbar.play.setForeground(Color.decode("#F1607E"));

						nextClick = true; // Iniciado, al proximo click, se para

					}
				}

			}
		});

		// Boton clear
		toolbar.clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				gameLogic.clearGrid();
				gameLogic.repaint();
			}
		});

		// Slider
		toolbar.framesPerSecond.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				gameLogic.fps = toolbar.framesPerSecond.getValue();

			}
		});

		// ComboBox
		toolbar.presetsComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String x = String.valueOf(toolbar.presetsComboBox.getSelectedItem());

				if (x.equals("Glider")) {
					gameLogic.presetGlider();

				} else if (x.equals("Lightweight spaceship")) {
					gameLogic.presetGLightweight_spaceship();

				} else if (x.equals("Pulsar")) {
					gameLogic.presetPulsar();

				} else if (x.equals("Blinker")) {

					gameLogic.presetBlinker();
				} else if (x.equals("Quadpole")) {
					gameLogic.presetQuadpole();
				} else if (x.equals("None")) {
					gameLogic.clearGrid();
					gameLogic.repaint();

				}

			}
		});

		// Info button
		toolbar.infoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Juego de la vida\nSe trata de un juego de cero jugadores,cada celula tiene 8 celulas \"vecinas\", que son las que estan proximas a ella, incluidas las diagonales.\nLas celulas tienen dos estados: estan \"vivas\" o \"muertas\".\nTodas las celulas se actualizan simulneamente en cada turno, siguiendo estas reglas:\n"
								+ "\n"
								+ "- Una celula muerta con exactamente 3 celulas vecinas vivas \"nace\" (es decir, al turno siguiente estara viva).\r\n"
								+ "- Una celula viva con 2 o 3 celulas vecinas vivas sigue viva, en otro caso muere (por \"soledad\" o \"superpoblacion\").\n\nAutor: Ismael Rivas Saborido");

			}
		});

		// Step Button
		toolbar.nextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameLogic.step();

			}
		});

	}

	public void setGameBeingPlayed(boolean isBeingPlayed) { // COMPROBAR SI ESTA SIENDO EJECUTADO
		if (isBeingPlayed) {

			game = new Thread(gameLogic);
			game.start();

		} else {

			game.interrupt();
		}
	}

}
