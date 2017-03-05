package com.sunwoo.game;

import java.util.Random;

public class GameLogic {

    private int[][] board;
    // 0- neither
    // 1- Player (red)
    // 2- Computer (blue)

    private String playerName = "Sunwoo";
    private String winner = "";
    private Random rand;

    public GameLogic(int rows, int cols) {
        board = new int[rows][cols];
        rand = new Random();
    }

    public void playerTurn(int col) {
        int row = getRowLength() - 1;
        while (row >= 0 && board[row][col] != 0) {
            row --;
        }
        if (row != -1) {
            board[row][col] = 1;
            checkStatus(row, col, 1);
            computerTurn();
        }
    }

    private void computerTurn() {
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
        checkStatus(row, col, 2);
    }
    
    private void checkStatus(int row, int col, int id) {
        //check if someone won
        checkHorizontal(row, col, id);
        checkVertical(row, col, id);
        checkRightDiagonal(row, col, id); //bottom left corner to top right corner
        checkLeftDiagonal(row, col, id); //bottom right corner to top left corner
        
        //check if there is a draw
        //board must be full
        boolean fullBoard = true;
        for (int i = 0; i < getRowLength(); i++) {
            if (board[i][0] == 0) {
                fullBoard = false;
            }
        }
        if (fullBoard) {
            setWinner(-1);
        }
    }
    
    private void setWinner(int id) {
        if (id == -1) {
            winner = "It's a draw!";
        } else if (id == 1) {
            winner = String.format("%s is the winner!", playerName);
        } else if (id == 2){
            winner = "Computer is the winner!";
        }
    }
    
    private void checkHorizontal(int row, int col, int id) {
        int count = 0;
        for (int i = 3; i >= -3; i--) {
            if (col + i < 0 || col + i >= getColLength()) {
                continue;
            }
            if (board[row][col + i] == id) {
                count++;
                if (count == 4) {
                    setWinner(id);
                }
            } else {
                count = 0;
            }
        }
    }
    
    private void checkVertical(int row, int col, int id) {
        int count = 0;
        for (int i = 3; i >= -3; i--) {
            if (row + i < 0 || row + i >= getRowLength()) {
                continue;
            }
            if (board[row + i][col] == id) {
                count++;
                if (count == 4) {
                    setWinner(id);
                }
            } else {
                count = 0;
            }
        }
    }
    
    private void checkRightDiagonal(int row, int col, int id) {
        int count = 0;
        for (int i = 3; i >= -3; i--) {
            if (row + i < 0 || row + i >= getRowLength() || col - i < 0 || col - i >= getColLength()) {
                continue;
            }
            if (board[row + i][col - i] == id) {
                count++;
                if (count == 4) {
                    setWinner(id);
                }
            } else {
                count = 0;
            }
        }
    }
    
    private void checkLeftDiagonal(int row, int col, int id) {
        int count = 0;
        for (int i = 3; i >= -3; i--) {
            if (row + i < 0 || row + i >= getRowLength() || col + i < 0 || col + i >= getColLength()) {
                continue;
            }
            if (board[row + i][col + i] == id) {
                count++;
                if (count == 4) {
                    setWinner(id);
                }
            } else {
                count = 0;
            }
        }
    }
    
    public void clearBoard() {
        board = new int[getRowLength()][getColLength()];
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
    
    public boolean isGameOver() {
        return !winner.equals("");
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
