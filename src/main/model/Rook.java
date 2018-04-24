package model;

import java.util.HashSet;

public class Rook extends BasePiece {

    public Rook(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Rook(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();

        // Checks all positions in same file
        for (int rank = 0; rank < chessboard.length; rank++) {
            Position newPosition = new Position(rank, position.getFile());
            if (isValidMove(chessboard, newPosition)) {
                moves.add(newPosition);
            }
        }

        // Checks all positions in same rank
        for (int file = 0; file < chessboard[0].length; file++) {
            Position newPosition = new Position(position.getRank(), file);
            if (isValidMove(chessboard, newPosition)) {
                moves.add(newPosition);
            }
        }
        return moves;
    }

    /**
     * See also {@link BasePiece#isValidMove(BasePiece[][], Position)}.
     */
    @Override
    public boolean isValidMove(BasePiece[][] chessboard, Position targetPosition) {

        if (isValidMoveBasicCheck(chessboard, targetPosition)) {
            int rankDisAbs = Math.abs(getPosition().getRank() - targetPosition.getRank());
            int fileDisAbs = Math.abs(getPosition().getFile() - targetPosition.getFile());
            if ((rankDisAbs * fileDisAbs) == 0) {
                // The rank or file in target position must be the same as current position's.
                return !isBlocked(chessboard, targetPosition);
            }
        }
        return false;
    }

    /**
     * See also {@link BasePiece#getNameString()}.
     */
    @Override
    public String getNameString() {
        return "R";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Rook clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Rook(pos, getWhitePlayer(), getFirstTime());
    }
}
