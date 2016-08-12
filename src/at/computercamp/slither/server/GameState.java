package at.computercamp.slither.server;

import java.util.ArrayList;
import java.util.List;

public class GameState {

	List<Item> inactiveItems = new ArrayList<Item>();
	List<Snake> snakes = new ArrayList<Snake>();

	int boardHeight = 100;
	int boardLength = 100;

}
