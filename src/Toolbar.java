import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Toolbar extends JPanel {

	@Override
	public void setBackground(Color bg) {
		Color myColor = Color.decode("#88D498");

		super.setBackground(myColor);
	}

	JButton play;
	JButton clear;
	JButton nextStep;
	JLabel fpsLabel;
	JSlider framesPerSecond;
	JComboBox presetsComboBox;
	JLabel presetsLabel;
	String[] presets;
	JButton infoBtn;

	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15; // initial frames per second

	public Toolbar() {

		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Botones
		play = new JButton("PLAY");
		clear = new JButton("CLEAR");
		nextStep = new JButton("NEXT");

		play.setBackground(Color.decode("#1A936F"));
		play.setForeground(Color.decode("#114B5F"));
		clear.setBackground(Color.decode("#1A936F"));
		clear.setForeground(Color.decode("#114B5F"));
		nextStep.setBackground(Color.decode("#1A936F"));
		nextStep.setForeground(Color.decode("#114B5F"));

		nextStep.setPreferredSize(new Dimension(100, 40));
		nextStep.setFont(new Font("", Font.BOLD, 25));

		play.setPreferredSize(new Dimension(100, 40));
		play.setFont(new Font("", Font.BOLD, 25));

		clear.setPreferredSize(new Dimension(105, 40));
		clear.setFont(new Font("", Font.BOLD, 25));

		play.setFocusPainted(false); // para que no se vea marcado despues de pulsar boton
		nextStep.setFocusPainted(false); // para que no se vea marcado despues de pulsar boton
		clear.setFocusPainted(false); // para que no se vea marcado despues de pulsar boton

		// ==========

		// Slider + nombre
		fpsLabel = new JLabel("SPEED:");

		fpsLabel.setForeground(Color.decode("#114B5F"));
		fpsLabel.setFont(new Font("", Font.BOLD, 17));
		// ==
		framesPerSecond = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

		framesPerSecond.setMinorTickSpacing(1);
		framesPerSecond.setMajorTickSpacing(10);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		framesPerSecond.setBackground(Color.decode("#88D498"));
		framesPerSecond.setForeground(Color.decode("#114B5F"));
		framesPerSecond.setFocusable(false);// para que no deje un borde de color al tocarlo

		// =========

		// ComboBox
		presets = new String[] { "None", "Glider", "Lightweight spaceship", "Pulsar", "Blinker", "Quadpole" };
		presetsComboBox = new JComboBox(presets);

		presetsComboBox.setPreferredSize(new Dimension(100, 40));
		presetsComboBox.setBackground(Color.decode("#88D498"));
		presetsComboBox.setForeground(Color.decode("#114B5F"));
		presetsComboBox.setFont(new Font("", Font.BOLD, 15));
		presetsComboBox.setFocusable(false);

		presetsLabel = new JLabel("Presets:");
		presetsLabel.setFont(new Font("", Font.BOLD, 17));
		presetsLabel.setForeground(Color.decode("#114B5F"));
		// =========

		// Info button
		infoBtn = new JButton("?");

		infoBtn.setBackground(Color.decode("#A49EA0"));
		infoBtn.setForeground(Color.decode("#114B5F"));
		infoBtn.setPreferredSize(new Dimension(32, 32));
		infoBtn.setFont(new Font("", Font.BOLD, 15));
		infoBtn.setFocusPainted(false);

		//
		add(clear);
		add(play);
		add(nextStep);
		add(fpsLabel);
		add(framesPerSecond);
		add(presetsLabel);
		add(presetsComboBox);
		add(infoBtn);

	}

}
