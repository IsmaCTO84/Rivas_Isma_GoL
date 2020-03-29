import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

class test {
	GameLogic gameLogic = new GameLogic();

	@Test
	void testIfPanelIsEmpty() {
		assertTrue(!gameLogic.point.contains(new Point(0, 0)));

	}

	@Test
	void testIfBlinkerPresetWorks() {

		gameLogic.presetBlinker();

		assertTrue(gameLogic.point.contains(new Point(15, 10)));
		assertTrue(gameLogic.point.contains(new Point(16, 10)));
		assertTrue(gameLogic.point.contains(new Point(17, 10)));

	}

}
