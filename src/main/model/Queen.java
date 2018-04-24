package model;

import java.util.HashSet;

public class Queen extends BasePiece {

    public Queen(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Queen(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();

        // Queen's legal moves are the superset of rook's and bishop's with same position
        Rook rook = new Rook(position, getWhitePlayer());
        Bishop bishop = new Bishop(position, getWhitePlayer());
        moves.addAll(rook.getPossibleLegalMoves(chessboard));
        moves.addAll(bishop.getPossibleLegalMoves(chessboard));
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

            // Checks if rank relative distance is 0, file relative distance is 0 or
            // rank relative distance equals to file relative distance
            if ((rankDisAbs * fileDisAbs) == 0 || (rankDisAbs == fileDisAbs)) {
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
        return "Q";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Queen clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Queen(pos, getWhitePlayer(), getFirstTime());
    }
}
