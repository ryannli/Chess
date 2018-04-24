package model;

import com.sun.istack.internal.Nullable;

import java.util.HashSet;

/**
 * BasePiece is an abstract class for all pieces on chess game.
 */

public abstract class BasePiece {

    /**
     * Position is a data structure for a pair of rank and file on chess board (or called row and col).
     * The left bottom corner on chessboard refers to rank = 0 and file = 0.
     */

    public static class Position {
        private int rank, file;

        public Position(int rank, int file) {
            this.rank = rank;
            this.file = file;
        }

        public int getRank() {
            return rank;
        }

        public int getFile() {
            return file;
        }
    }

    /**
     * True if the owner of this piece is white; otherwise black.
     */
    private boolean whitePlayer;

    /**
     * The position of piece on chess board.
     */
    private Position position;

    /**
     * Whether the piece has been moved before. True if not moved before.
     */
    public boolean firstTime;

    public BasePiece(Position position, boolean whitePlayer) {
        this.position = position;
        this.whitePlayer = whitePlayer;
        firstTime = true;
    }

    public BasePiece(Position position, boolean whitePlayer, boolean firstTime) {
        this.position = position;
        this.whitePlayer = whitePlayer;
        this.firstTime = firstTime;
    }

    /**
     * Deep copy of a chess piece.
     *
     * @return the new object of the same piece with same informaiton
     */
    abstract public BasePiece clonePiece();

    /**
     * Returns a set of positions that contains the all possible moves only according to the rule of this piece and the
     * piece is not blocked. Does not check if the move will cause the king in check.
     *
     * @param chessboard the chessboard in use now
     * @return the set of possible position for this piece
     */
    abstract public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard);

    /**
     * Returns whether the move is valid only according to the rule of this piece and the piece is not blocked.
     * Does not check if the move will cause the king in check.
     *
     * @param chessboard     the chessboard in use now
     * @param targetPosition the position that the piece is heading to
     * @return whether the move is valid
     */
    abstract public boolean isValidMove(BasePiece[][] chessboard, Position targetPosition);

    /**
     * Returns the standard and representative character for this piece.
     *
     * @return string of the piece's name
     */
    abstract public String getNameString();

    /**
     * Returns whether the move is inside chessboard boundary, the target position is not the current
     * position and the target position is not occupied by a piece with same owner.
     *
     * @param chessboard     the chessboard in use now
     * @param targetPosition the position that the piece is heading to
     * @return whether the move passes the basic check
     */
    public boolean isValidMoveBasicCheck(BasePiece[][] chessboard, Position targetPosition) {
        // Invalid if target position is outside the chessboard or it is the same as original position
        if (!insideBoundary(chessboard, targetPosition) || isSamePosition(targetPosition)) {
            return false;
        }
        int targetRank = targetPosition.getRank();
        int targetFile = targetPosition.getFile();

        // Invalid if the target position is already occupied by the same player
        if (chessboard[targetRank][targetFile] != null && isSamePlayer(chessboard[targetRank][targetFile])) {
            return false;
        }
        return true;
    }

    /**
     * Returns whether there exists piece on the line from current position towards target position.
     * The line direction can only be horizontal, vertical or diagonal.
     *
     * @param chessboard     the chessboard in use now
     * @param targetPosition the position that the piece is heading to
     * @return whether the piece is blocked
     */
    public boolean isBlocked(BasePiece[][] chessboard, Position targetPosition) {
        int rankPos = getPosition().getRank();
        int filePos = getPosition().getFile();
        int rankDist = targetPosition.getRank() - rankPos;
        int fileDist = targetPosition.getFile() - filePos;

        // Gets the direction (-1, 0 or 1) for each step from current position to target position
        int randDir = Integer.compare(rankDist, 0);
        int fileDir = Integer.compare(fileDist, 0);

        // Starts with the next position towards target
        rankPos += randDir;
        filePos += fileDir;

        for (; rankPos != targetPosition.getRank() || filePos != targetPosition.getFile()
                ; rankPos += randDir, filePos += fileDir) {
            if (chessboard[rankPos][filePos] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean getWhitePlayer() {
        return whitePlayer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        position = newPosition;
    }

    /**
     * Returns whether the new position is same as current position
     *
     * @param newPosition the target position
     * @return true if the target position is the same
     */
    public boolean isSamePosition(Position newPosition) {
        return (position.getRank() == newPosition.getRank())
                && (position.getFile() == newPosition.getFile());
    }

    /**
     * Returns whether the given piece has the same author. If the piece is null, return false.
     *
     * @param piece the piece for checking
     * @return true if the given piece has the same author
     */
    public boolean isSamePlayer(@Nullable BasePiece piece) {
        if (piece == null) {
            return false;
        }
        return getWhitePlayer() == piece.getWhitePlayer();
    }

    /**
     * Returns whether the given position is inside chessboard boundary.
     *
     * @param chessboard the chessboard in use now
     * @param position   the target position
     * @return true if position is inside the chessboard boundary
     */
    public static boolean insideBoundary(BasePiece[][] chessboard, Position position) {
        int height = chessboard.length;
        int width = chessboard[0].length;
        if (position.getFile() >= height || position.getFile() < 0 ||
                position.getRank() >= width || position.getRank() < 0) {
            return false;
        }
        return true;
    }

    public void setNoFirstTime() {
        firstTime = false;
    }

    public boolean getFirstTime() {
        return firstTime;
    }

}
