package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Board {
    public final int BOARD_WIDTH = 3;
    // public final char BOARD_X = 'X';    // player (to evaluate)
    // public final char BOARD_O = 'O';
    public final char BOARD_BLANK = '.';

    private char[][] board;

    // To store all empty pos in board
    private List<Integer[]> emptyPosList;

    public Board() {
        board = new char[BOARD_WIDTH][BOARD_WIDTH];
        emptyPosList = new ArrayList<>();
    }
    public Board(String configFile) {
        this();

        readPopulateBoard(configFile);
    }

    // Fn. to read file and populate board
    public void readPopulateBoard(String file) {
        System.out.println("Processing: " + file);
        System.out.println();

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
                            if(ch == BOARD_BLANK)
                            {
                                Integer[] blankCoord = {row, col};
                                emptyPosList.add(blankCoord);
                            }

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
        System.out.println("Board:");

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) 
                System.out.print(board[row][col]);
            
            System.out.println();
        }

        System.out.println();
        System.out.println("-----------------------------");
    }

    public void place(char marker, int row, int col)
    {
        board[row][col] = marker;
        
        // Fill up prev empty pos 
        //Integer[] prevEmptyPos = {row, col};
        //emptyPosList.remove(prevEmptyPos);     // doesnt work

        Integer[] prevEmptyPos = {row, col};

        for (Integer[] coord : emptyPosList) {
            if(coord[0] == row && coord[1] == col)
            {
                prevEmptyPos = coord;
                break;
            }
        }

        emptyPosList.remove(prevEmptyPos);
    }

    // Returns true if there is 3 in a row based on last row's coord
    public boolean checkRow(int lastRowCoord) {
        // Go thru each col of that row
        for(int col = 0; col < BOARD_WIDTH-1; col++)
        {
            // Detected a mismatch, return no win
            if(board[lastRowCoord][col] != board[lastRowCoord][col+1]) 
                return false;
        }
        // All markers match in this row, return win
        return true;
    }
    
    // Returns true if there is 3 in a row based on last col's coord
    public boolean checkCol(int lastColCoord) {
        // Go thru each row of that col
        for(int row = 0; row < BOARD_WIDTH-1; row++)
        {
            // Detected a mismatch, return false (no win)
            if(board[row][lastColCoord] != board[row+1][lastColCoord]) 
                return false;
        }
        // All markers match in this col, return win
        return true;
    }

    // Check diagonals based on last move
    public boolean checkDiagTopLeft(int moveX, int moveY) {
        // if not on diagonal, return don't use this check
        if(moveX != moveY)
            return false;

        for(int i = 0; i < BOARD_WIDTH-1; i++)
        {
            // Detected a mismatch, return false (no win)
            if(board[i][i] != board[i+1][i+1])
                return false; 
        }

        return true;
    }
    public boolean checkDiagTopRight(int moveX, int moveY) {
        // if not on diagonal, return don't do this check
        if(moveX != (BOARD_WIDTH-1 - moveY))
            return false;   

        // hardcoding cos Im lazy & theres no time 
        if( board[BOARD_WIDTH-1][0] == board[1][1] &&
            board[0][BOARD_WIDTH-1] == board[1][1])
            return true;
            
        return false;
    }

    // Getters & Setters
    public void setEmptyPosList(List<Integer[]> emptyPosList) {
        this.emptyPosList = emptyPosList;
    }
    public List<Integer[]> getEmptyPosList() {
        return emptyPosList;
    }
    public Board getDeepCopy() {
        Board boardClone = new Board();

        // Copy each row over
        for(int row = 0; row < BOARD_WIDTH; row++)
            boardClone.board[row] = this.board[row].clone();

        List<Integer[]> emptyPosListClone = new ArrayList<>();
        for (Integer[] coord : emptyPosList) 
            emptyPosListClone.add(coord);
        
        boardClone.setEmptyPosList(emptyPosListClone);

        return boardClone;
    }
}
