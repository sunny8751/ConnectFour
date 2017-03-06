package com.sunwoo.game;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private int[][] board;
    // 0- neither
    // 1- Player (red)
    // 2- Computer (blue)

    private boolean playerTurn = true;
    private String playerName = "Player";
    private String winner = "";
    String difficulty = "";
    private Random rand;

    public GameLogic(int rows, int cols) {
        board = new int[rows][cols];
        rand = new Random();
    }
    
    public void turn(int col) {
        if (isGameOver()) {
            return;
        }
        if (playerTurn) {
            playerTurn(col);
        } else {
            computerTurn();
        }
    }

    public void playerTurn(int col) {
        int row = getRowLength() - 1;
        while (row >= 0 && board[row][col] != 0) {
            row --;
        }
        if (row != -1) {
            board[row][col] = 1;
            checkStatus(board, 1);
            playerTurn = false;
        }
    }
    
    private void computerTurn() {
        if (difficulty.equals("Easy")) {
            easyComputerTurn();
        } else if (difficulty.equals("Medium")) {
            mediumComputerTurn();
        } else if (difficulty.equals("Hard")) {
            hardComputerTurn();
        }
    }

    private void easyComputerTurn() {
        //RANDOM LEVEL: randomly chooses where to place the coin
        int col = rand.nextInt(getColLength());
        int row = getRowLength() - 1;
        while (board[row][col] != 0) {
            row --;
            if (row < 0) {
                col = rand.nextInt(getColLength());
                row = getRowLength() - 1;
                continue;
            }
        }
        board[row][col] = 2;
        checkStatus(board, 2);
        playerTurn = true;
    }
    
    private void mediumComputerTurn() {
        //STANDARD LEVEL: attempts to block the player when needed, but doesn't operate with any advanced strategy
        Node root = new Node(board);
        minimax(root, true, 2);
        board[root.bestRow][root.bestCol] = 2;
        checkStatus(board, 2);
        playerTurn = true;
    }
    
    private void hardComputerTurn() {
        //BOSS LEVEL: minimax theorem to look-ahead and use an intelligent strategy
        Node root = new Node(board);
        minimax(root, true, 6);
        board[root.bestRow][root.bestCol] = 2;
        checkStatus(board, 2);
        playerTurn = true;
    }

    private int minimax(Node node, boolean maximum, int depth) {
        if (depth == 0 || node.isLeafNode() || checkIfWon(node.board, maximum ? 2 : 1)) {
            return node.getValue();
        }
        if (maximum) {
            //this is the computer's level in the tree
            //the goal is to choose the best move
            int bestValue = -1000000;
            for(Node child : node.getChildren(2)) {
                if (checkIfWon(child.board, 2)) {
                    //computer already won, so return 1
                    bestValue = 1;
                    node.bestCol = child.chosenCol;
                    node.bestRow = child.chosenRow;
                } else {
                    int childValue = minimax(child, false, depth - 1);
                    bestValue = Math.max(bestValue, childValue);
                    if (bestValue == childValue) {
                        node.bestCol = child.chosenCol;
                        node.bestRow = child.chosenRow;
                    }
                }
            }
            return bestValue;
        } else {
            //this is the player's level in the tree
            //the goal is to choose the worst move
            int bestValue = 1000000;
            for(Node child : node.getChildren(1)) {
                if (checkIfWon(child.board, 1)) {
                    //player already won, so return -1
                    bestValue = -1;
                    node.bestCol = child.chosenCol;
                    node.bestRow = child.chosenRow;
                } else {
                    int childValue = minimax(child, true, depth - 1);
                    bestValue = Math.min(bestValue, childValue);
                }
            }
            return bestValue;
        }
    }
    
    //represents each state of the game
    private class Node {
        int[][] board;
        //the move that changed for the state of the game
        int chosenRow = -1, chosenCol = -1;
        int bestRow = -1, bestCol = -1;

        public Node(int[][] board) {
            this.board = board;
        }
        
        public Node[] getChildren(int id) {
            ArrayList<Node> childrenArrayList = new ArrayList<Node>(5);
            for(int col = 0; col < getColLength(); col++) {
                for(int row = getRowLength() - 1; row >= 0; row--) {
                    if (board[row][col] == 0) {
                        int [][] newBoard = deepCopy(board);
                        newBoard[row][col] = id;
                        Node n = new Node(newBoard);
                        n.chosenRow = row;
                        n.chosenCol = col;
                        childrenArrayList.add(n);
                        break;
                    }
                }
            }
            return childrenArrayList.toArray(new Node[childrenArrayList.size()]);
        }
        
        private int[][] deepCopy(int[][] originalBoard) {
            int [][] newBoard = new int[originalBoard.length][originalBoard[0].length];
            for (int i = 0; i < originalBoard.length; i++) {
                for (int j = 0; j < originalBoard[0].length; j++) {
                    newBoard[i][j] = originalBoard[i][j];
                }
            }
            return newBoard;
        }
        
        public int getValue() {
            //value of the node based on how good it is to move there
            if (checkIfWon(board, 2)) {
                return 1;
            } else if (checkIfWon(board, 1)) {
                return -1;
            } else {
                return 0;
            }
        }
        
        public boolean isLeafNode() {
            return isBoardFull(board);
        }
    }

    private void checkStatus(int[][] board, int id) {
        if (checkIfWon(board, id)) {
            if (id == 1) {
                winner = String.format("%s is the winner!", playerName);
            } else if (id == 2){
                winner = "Computer is the winner!";
            }
        }
        //check if there is a draw
        //board must be full
        if (isBoardFull(board)) {
            winner = "It's a draw!";
        }
    }
    
    private boolean checkIfWon(int[][] board, int id) {
        //check if someone won
        boolean h = checkHorizontal(board, id);
        boolean v = checkVertical(board, id);
        boolean rd = checkRightDiagonal(board, id); //bottom left corner to top right corner
        boolean ld = checkLeftDiagonal(board, id); //bottom right corner to top left corner
        if (h || v || rd || ld) {
            return true;
        }
        return false;
    }

    private boolean checkHorizontal(int[][] board, int id) {
        int count = 0;
        for (int i = 0; i < getRowLength(); i++) {
            count = 0;
            for (int j = 0; j < getColLength(); j++) {
                if (board[i][j] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }
    
    private boolean checkVertical(int[][] board, int id) {
        int count = 0;
        for (int j = 0; j < getColLength(); j++) {
            count = 0;
            for (int i = 0; i < getRowLength(); i++) {
                if (board[i][j] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkRightDiagonal(int[][] board, int id) {
        int count;
        for (int i = 0; i < getRowLength(); i++) {
            count = 0;
            int row = i;
            int col = 0;
            while (row >= 0 && col < getColLength()) {
                if (board[row][col] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                row--;
                col++;
            }
        }
        for (int j = 0; j < getColLength(); j++) {
            count = 0;
            int row = getRowLength() - 1;
            int col = j;
            while (row >= 0 && col < getColLength()) {
                if (board[row][col] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                row--;
                col++;
            }
        }
        return false;
    }
    
    private boolean checkLeftDiagonal(int[][] board, int id) {
        int count;
        for (int i = 0; i < getRowLength(); i++) {
            count = 0;
            int row = i;
            int col = getColLength() - 1;
            while (row >= 0 && col >= 0) {
                if (board[row][col] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                row--;
                col--;
            }
        }
        for (int j = 0; j < getColLength(); j++) {
            count = 0;
            int row = getRowLength() - 1;
            int col = j;
            while (row >= 0 && col >= 0) {
                if (board[row][col] == id) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                row--;
                col--;
            }
        }
        return false;
    }
    
    public void clearBoard() {
        board = new int[getRowLength()][getColLength()];
    }
    
    public void startOver() {
        clearBoard();
        winner = "";
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public void setPlayerMove(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
    
    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getImage(int row, int col) {
        String src = "";
        if (board[row][col] == 0) {
            src = "white_circle.svg";
        } else if (board[row][col] == 1) {
            src = "red_circle.svg";
        } else if (board[row][col] == 2) {
            src = "blue_circle.svg";
        }
        return src;
    }
    
    private boolean isBoardFull(int[][] board) {
        boolean fullBoard = true;
        for (int i = 0; i < getColLength(); i++) {
            if (board[0][i] == 0) {
                fullBoard = false;
            }
        }
        return fullBoard;
    }
    
    public boolean isGameOver() {
        return !winner.equals("");
    }
    
    public boolean isPlayerTurn() {
        return playerTurn;
    }
    
    public String getWinner() {
        return winner;
    }

    public int getRowLength() {
        return board.length;
    }

    public int getColLength() {
        return board[0].length;
    }
}
