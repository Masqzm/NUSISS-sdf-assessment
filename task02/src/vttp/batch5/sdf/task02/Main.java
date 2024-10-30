package vttp.batch5.sdf.task02;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

// java -cp classes vttp.batch5.sdf.task02.Main TTT/figure1.txt
public class Main {

	public static void main(String[] args) throws Exception {
		// Get board config file 
		if(args.length < 1) {
            System.err.println("Missing arguments! Please enter filepath!!");
			System.err.println("Exiting program...");
			System.exit(-1);
        }
		String tttConfigFile = args[0];

		// Create board & populate with tttConfig file
		Board board = new Board(tttConfigFile);

		// Create map of all possible moves (K: move, V: utility value)
		//Map<Integer[], Integer> utilityMap = new HashMap<>();
		
		board.displayBoard();

		// Evaluate each move on empty positions
		for (Integer[] coord : board.getEmptyPosList()) {
			int x = coord[1];	// x is col according to req
			int y = coord[0];
			int utilityValue = 0;

			// Evaluate next move for X
			Board nextBoardMove_X = board.getDeepCopy(); 
			nextBoardMove_X.place('X', y, x);

			// if X wins
			if( nextBoardMove_X.checkRow(y) ||
				nextBoardMove_X.checkCol(x) ||
				nextBoardMove_X.checkDiagTopLeft(y, x) ||
				nextBoardMove_X.checkDiagTopRight(y, x))
				utilityValue = 1;
			// if there's no moves avail left (tie)
			else if(nextBoardMove_X.getEmptyPosList().isEmpty())
				utilityValue = 0;
			else
			{
				// Evaluate next move after X (for O)				
				for (Integer[] coord2 : nextBoardMove_X.getEmptyPosList())
				{
					int x2 = coord2[1];	// x is col according to req
					int y2 = coord2[0];
					
					// Evaluate next move for O
					Board nextBoardMove_O = nextBoardMove_X.getDeepCopy(); 
					nextBoardMove_O.place('O', y2, x2);

					// if O wins
					if( nextBoardMove_O.checkRow(y2) ||
						nextBoardMove_O.checkCol(x2) ||
						nextBoardMove_O.checkDiagTopLeft(y2, x2) ||
						nextBoardMove_O.checkDiagTopRight(y2, x2))
					{
						utilityValue = -1;
						break;	// exit loop as O found a winning move
					}
					// if there's still moves left or tie
					else
						utilityValue = 0;
				}
			}

			System.out.printf("y=%d, x=%d, utility=%d\n", y, x, utilityValue);
		}
	}
}
