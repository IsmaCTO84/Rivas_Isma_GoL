import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameLogic extends JPanel
		implements ComponentListener, MouseListener, MouseMotionListener, Runnable, PointsInterface {

	private Dimension d_gameBoardSize = new Dimension(0, 0); // Lo dejo a cero para que no sea nulo
	ArrayList<Point> point = new ArrayList<Point>(0); // Almacena las coordenadas que he clickado

	int grid_block_size = 15;
	int fps = 15;

	/*
	 * Uso componentListener y defino la dimension a 0 porque si lo defino de
	 * primeras, no hace nada, busqué información y una solucion era usar esta
	 * interfaz
	 */

	public GameLogic() {

		addComponentListener(this);// Listener de los componentes
		addMouseListener(this); // Listener de un solo click
		addMouseMotionListener(this); // Listener para mantener pulsado

	}

	@Override
	public void setBackground(Color bg) {

		super.setBackground(Color.decode("#F9F5EA"));
	}

	// -------------------------------------
	// METODOS DA INTERFAZ "PointsInterface"
	// -------------------------------------

	public void addPointIntoArray(int x, int y) {

		/*
		 * Si el punto en el que clickamos NO contiene ningun punto ya clickado, añade
		 * un punto
		 */

		boolean pointInArray = point.contains(new Point(x, y));

		if (!pointInArray) {
			point.add(new Point(x, y));

		}

		repaint();
	}

	public void removePointIntoArray(int x, int y) {

		/*
		 * Si el punto en el que clickamos NO contiene ningun punto ya clickado, añade
		 * un punto
		 */

		boolean pointInArray = point.contains(new Point(x, y));

		if (pointInArray) {
			point.remove(new Point(x, y));
		}

		repaint();
	}

	public void removePoint(MouseEvent me) {

		/* Escucha el mouse y añade los puntos */

		int x = me.getPoint().x / grid_block_size - 1;
		int y = me.getPoint().y / grid_block_size - 1;
		if ((x >= 0) && (x < d_gameBoardSize.width) && (y >= 0) && (y < d_gameBoardSize.height)) {
			removePointIntoArray(x, y);
		}
	}

	public void addPoint(MouseEvent me) {

		/* Escucha el mouse y añade los puntos */

		int x = me.getPoint().x / grid_block_size - 1;
		int y = me.getPoint().y / grid_block_size - 1;
		if ((x >= 0) && (x < d_gameBoardSize.width) && (y >= 0) && (y < d_gameBoardSize.height)) {
			addPointIntoArray(x, y);

		}
	}

	// -------------------------------------
	// -------------------------------------

	public void clearGrid() {
		point.clear();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			for (Point newPoint : point) {
				// Draw new point
				g.setColor(Color.decode("#1A936F"));
				g.fillRect(grid_block_size + (grid_block_size * newPoint.x),
						grid_block_size + (grid_block_size * newPoint.y), grid_block_size, grid_block_size);
			}
		} catch (ConcurrentModificationException cme) {
		}

		// Cuadricula
		g.setColor(Color.decode("#232426"));

		// Verticales
		for (int i = 0; i <= d_gameBoardSize.width; i++) {
			g.drawLine(((i * grid_block_size) + grid_block_size), grid_block_size,
					(i * grid_block_size) + grid_block_size,
					grid_block_size + (grid_block_size * d_gameBoardSize.height));
		}
		// Horizontales
		for (int i = 0; i <= d_gameBoardSize.height; i++) {
			g.drawLine(grid_block_size, ((i * grid_block_size) + grid_block_size),
					grid_block_size * (d_gameBoardSize.width + 1), ((i * grid_block_size) + grid_block_size));
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// Aqui doy las dimensiones que arriba no podia dar
		d_gameBoardSize = new Dimension(getWidth() / grid_block_size - 2, getHeight() / grid_block_size - 2);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			addPoint(e);

		}

		if (SwingUtilities.isRightMouseButton(e)) {

			removePoint(e);

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			addPoint(e);

		}

		if (SwingUtilities.isRightMouseButton(e)) {

			removePoint(e);

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void run() {
		boolean[][] gameBoard = new boolean[d_gameBoardSize.width + 2][d_gameBoardSize.height + 2];
		for (Point current : point) {
			gameBoard[current.x + 1][current.y + 1] = true;
		}
		ArrayList<Point> survivingCells = new ArrayList<Point>(0);
		// Iterate through the array, follow game of life rules
		for (int x = 1; x < gameBoard.length - 1; x++) {
			for (int y = 1; y < gameBoard[0].length - 1; y++) {
				int surrounding = 0;
				if (gameBoard[x - 1][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x - 1][y]) {
					surrounding++;
				}
				if (gameBoard[x - 1][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x][y]) {
					// Cell is alive, Can the cell live? (2-3)
					if ((surrounding == 2) || (surrounding == 3)) {
						survivingCells.add(new Point(x - 1, y - 1));
					}
				} else {
					// Cell is dead, will the cell be given birth? (3)
					if (surrounding == 3) {
						survivingCells.add(new Point(x - 1, y - 1));
					}
				}
			}
		}
		clearGrid();
		point.addAll(survivingCells);
		repaint();
		try {

			// En el slider pone 0 pero no puede dividir entre 0 asique le sumo uno
			if (fps == 0) {
				fps = 1;
			}
			Thread.sleep(1000 / fps);
			run();
		} catch (InterruptedException ex) {
		}
	}

	// =====================
	// PRESETS OF COMBO BOX
	// =====================
	public void presetGlider() {
		clearGrid();

		point.add(new Point(5, 5));
		point.add(new Point(6, 5));
		point.add(new Point(7, 5));
		point.add(new Point(7, 4));
		point.add(new Point(6, 3));

		repaint();
	}

	public void presetGLightweight_spaceship() {
		clearGrid();
		point.add(new Point(5, 5));
		point.add(new Point(6, 5));
		point.add(new Point(7, 5));
		point.add(new Point(8, 5));
		point.add(new Point(8, 4));
		point.add(new Point(8, 3));
		point.add(new Point(7, 2));
		point.add(new Point(4, 4));
		point.add(new Point(4, 2));

		repaint();
	}

	public void presetPulsar() {
		clearGrid();

		// Arriba izquierda
		point.add(new Point(10, 5));
		point.add(new Point(11, 5));
		point.add(new Point(12, 5));

		point.add(new Point(13, 7));
		point.add(new Point(13, 8));
		point.add(new Point(13, 9));

		point.add(new Point(12, 10));
		point.add(new Point(11, 10));
		point.add(new Point(10, 10));

		point.add(new Point(8, 9));
		point.add(new Point(8, 8));
		point.add(new Point(8, 7));
		// Arriba derecha
		point.add(new Point(16, 5));
		point.add(new Point(17, 5));
		point.add(new Point(18, 5));

		point.add(new Point(15, 7));
		point.add(new Point(15, 8));
		point.add(new Point(15, 9));

		point.add(new Point(18, 10));
		point.add(new Point(17, 10));
		point.add(new Point(16, 10));

		point.add(new Point(20, 9));
		point.add(new Point(20, 8));
		point.add(new Point(20, 7));
		// Abajo derecha

		point.add(new Point(16, 12));
		point.add(new Point(17, 12));
		point.add(new Point(18, 12));

		point.add(new Point(15, 13));
		point.add(new Point(15, 14));
		point.add(new Point(15, 15));

		point.add(new Point(18, 17));
		point.add(new Point(17, 17));
		point.add(new Point(16, 17));

		point.add(new Point(20, 13));
		point.add(new Point(20, 14));
		point.add(new Point(20, 15));

		// Abajo izquierda

		point.add(new Point(10, 12));
		point.add(new Point(11, 12));
		point.add(new Point(12, 12));

		point.add(new Point(13, 13));
		point.add(new Point(13, 14));
		point.add(new Point(13, 15));

		point.add(new Point(12, 17));
		point.add(new Point(11, 17));
		point.add(new Point(10, 17));

		point.add(new Point(8, 13));
		point.add(new Point(8, 14));
		point.add(new Point(8, 15));

		repaint();
	}

	public void presetBlinker() {
		clearGrid();

		// Arriba izquierda
		point.add(new Point(15, 10));
		point.add(new Point(16, 10));
		point.add(new Point(17, 10));

		repaint();
	}

	public void presetQuadpole() {
		clearGrid();

		point.add(new Point(15, 10));
		point.add(new Point(15, 11));
		point.add(new Point(16, 10));
		point.add(new Point(16, 12));
		point.add(new Point(18, 12));
		point.add(new Point(18, 14));
		point.add(new Point(20, 14));
		point.add(new Point(21, 15));
		point.add(new Point(21, 16));
		point.add(new Point(20, 16));

		repaint();
	}

	public void step() {
		boolean[][] gameBoard = new boolean[d_gameBoardSize.width + 2][d_gameBoardSize.height + 2];
		for (Point current : point) {
			gameBoard[current.x + 1][current.y + 1] = true;
		}
		ArrayList<Point> survivingCells = new ArrayList<Point>(0);
		// Iterate through the array, follow game of life rules
		for (int x = 1; x < gameBoard.length - 1; x++) {
			for (int y = 1; y < gameBoard[0].length - 1; y++) {
				int surrounding = 0;
				if (gameBoard[x - 1][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x - 1][y]) {
					surrounding++;
				}
				if (gameBoard[x - 1][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y - 1]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y]) {
					surrounding++;
				}
				if (gameBoard[x + 1][y + 1]) {
					surrounding++;
				}
				if (gameBoard[x][y]) {
					// Cell is alive, Can the cell live? (2-3)
					if ((surrounding == 2) || (surrounding == 3)) {
						survivingCells.add(new Point(x - 1, y - 1));
					}
				} else {
					// Cell is dead, will the cell be given birth? (3)
					if (surrounding == 3) {
						survivingCells.add(new Point(x - 1, y - 1));
					}
				}
			}
		}
		clearGrid();
		point.addAll(survivingCells);
		repaint();
	}

	// =====================
	// =====================

}