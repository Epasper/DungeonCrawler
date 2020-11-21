package com.dungeoncrawler.Javiarenka.board;

public class Board {
    private int boardX;
    private int boardY;
    private EncounterTile[][] boardTiles = new EncounterTile[boardX][boardY];

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

    public EncounterTile[][] getBoardTiles() {
        return boardTiles;
    }

    public void setBoardTiles(EncounterTile[][] boardTiles) {
        this.boardTiles = boardTiles;
    }
}
