package model;

import java.util.HashSet;

public class Bishop extends BasePiece {

    public Bishop(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }
    public Bishop(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /** See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}. */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();

        // Add moves in four diagonal directions.
        for (int quadrant = 1; quadrant <= 4; quadrant ++) {
            int rank = position.getRank();
            int file = position.getFile();
            int rankGradient = quadrant <= 2 ? 1 : -1;
            int fileGradient = (quadrant == 1 || quadrant == 4) ? 1 : -1;
            for (; rank < chessboard.length && file < chessboard[0].length && rank >=0 && file >=0 ;
                    rank += rankGradient, file += fileGradient) {
                Position newPosition = new Position(rank, file);
                if (isValidMove(chessboard, newPosition)) {
                    moves.add(newPosition);
                }
            }
        }

        return moves;
    }

    /** See also {@link BasePiece#isValidMove(BasePiece[][], Position)}. */
    @Override
    public boolean isValidMove(BasePiece[][] chessboard, Position targetPosition) {

        if (isValidMoveBasicCheck(chessboard, targetPosition)) {
            int rankDisAbs = Math.abs(getPosition().getRank() - targetPosition.getRank());
            int fileDisAbs = Math.abs(getPosition().getFile() - targetPosition.getFile());
            if (rankDisAbs == fileDisAbs) {
                // The move is diagonal.
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
        return "B";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Bishop clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Bishop(pos, getWhitePlayer(), getFirstTime());
    }
}
