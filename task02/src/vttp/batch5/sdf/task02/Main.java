package vttp.batch5.sdf.task02;

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

		Board board = new Board(tttConfigFile);

		// empty_pos = tttboard.get_all_empty_pos() 

		// utility = map 

		// for each pos in empty_pos
			// newTttboard = clone tttboard 
			// newTttboard.place(X, pos) 
			
			// evaluate horizontal, vertical and diagonal rows on newTttboard 
			// 	if there are 3 X 
			// 		utility[pos] = 1 
			// 	else if there are 2 O and 1 SPACE // as the opponent will want to win 
			// 		utility[pos] = -1 
			// 	else  
			//	 	utility[pos] = 0 
	}

	public static int evaluate() {
		return 0;
	}
}
