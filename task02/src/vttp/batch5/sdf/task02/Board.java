package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class Board {
    public final int BOARD_WIDTH = 3;
    // public final char BOARD_X = 'X';    // player (to evaluate)
    // public final char BOARD_O = 'O';
    // public final char BOARD_BLANK = '.';

    private char[][] board;

    public Board(String configFile) {
        board = new char[BOARD_WIDTH][BOARD_WIDTH];

        readPopulateBoard(configFile);

        displayBoard();
    }

    // Returns list of empty position on board
    public List<Integer[]> getAllEmptyPos() {
        
    }

    // Fn. to read file and populate board
    public void readPopulateBoard(String file) {
        try(Reader reader = new FileReader(file);
        	BufferedReader br = new BufferedReader(reader)) {
            
            String line = "";
            int row = 0;
            while((line = br.readLine()) != null)       // read line if it exists
            {
                if(row < BOARD_WIDTH)
                {
                    // Loop thru each char
                    int col = 0;
                    for(char ch : line.trim().toCharArray()) 
                    {
                        if(col < BOARD_WIDTH)
                        {
                            board[row][col] = ch;
                            ++col;
                        }
                        else
                            break;
                    }
                }
                ++row;
            }
        } catch(FileNotFoundException ex) {
			System.err.println("ERROR: File not found!");
			ex.printStackTrace();
            System.err.println("Exiting program...");
            System.exit(-1);
		} catch(IOException ex) {
			System.err.println("ERROR: IO error!");
			ex.printStackTrace();
            System.err.println("Exiting program...");
            System.exit(-1);
		}
    }

    public void displayBoard() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) 
                System.out.print(board[row][col]);
            
            System.out.println();
        }
    }
}
