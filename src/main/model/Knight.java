package model;

import java.util.HashSet;

public class Knight extends BasePiece {

    public Knight(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Knight(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();
        int[] possiblePos = {1, -1, 2, -2};
        for (int rank : possiblePos) {
            for (int file : possiblePos) {
                if (Math.abs(rank * file) == 2) {
                    // Only eight combinations can meet this requirement.
                    Position newPosition = new Position(position.getRank() + rank, position.getFile() + file);
                    if (isValidMove(chessboard, newPosition)) {
                        moves.add(newPosition);
                    }
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
            int rankDisAbs = Math.abs(getPosition().getRank() - targetPosition.getRank());
            int fileDisAbs = Math.abs(getPosition().getFile() - targetPosition.getFile());
            return (rankDisAbs * fileDisAbs) == 2;
        }
        return false;
    }

    /**
     * See also {@link BasePiece#getNameString()}.
     */
    @Override
    public String getNameString() {
        return "N";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Knight clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Knight(pos, getWhitePlayer(), getFirstTime());
    }
}
