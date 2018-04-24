package model;

import java.util.HashSet;

public class King extends BasePiece {

    public King(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public King(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();

        // Add moves in one step directions.
        for (int rank = -1; rank <= 1; rank++) {
            for (int file = -1; file <= 1; file++) {
                Position newPosition = new Position(position.getRank() + rank, position.getFile() + file);
                if (isValidMove(chessboard, newPosition)) {
                    moves.add(newPosition);
                }
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
            int rankDistAbs = Math.abs(getPosition().getRank() - targetPosition.getRank());
            int fileDistAbs = Math.abs(getPosition().getFile() - targetPosition.getFile());

            // returns true if the target position only needs one step.
            return rankDistAbs <= 1 && fileDistAbs <= 1;
        }
        return false;
    }

    /**
     * See also {@link BasePiece#getNameString()}.
     */
    @Override
    public String getNameString() {
        return "K";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public King clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new King(pos, getWhitePlayer(), getFirstTime());
    }
}
