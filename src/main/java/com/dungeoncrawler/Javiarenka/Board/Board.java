package com.dungeoncrawler.Javiarenka.Board;

public class Board {
    private int boardX;
    private int boardY;
    private Tile[][] boardTiles = new Tile[boardX][boardY];

    public Board(int boardX, int boardY) {
        this.boardX = boardX;
        this.boardY = boardY;
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    public Tile[][] getBoardTiles() {
        return boardTiles;
    }

    public void setBoardTiles(Tile[][] boardTiles) {
        this.boardTiles = boardTiles;
    }
}
