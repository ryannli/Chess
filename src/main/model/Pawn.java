package model;

import java.util.HashSet;

public class Pawn extends BasePiece {


    public Pawn(Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Pawn(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<Position> moves = new HashSet<Position>();
        Position position = getPosition();

        // Gets the rank direction for legal move.
        // White player should go up; black player should go down.
        int dir = getWhitePlayer() ? 1 : -1;

        // Gets all possible relative move directions.
        Position[] possiblePosDir = {new Position(dir, 0), new Position(2 * dir, 0)
                , new Position(dir, -1), new Position(dir, 1)};

        for (Position posDis : possiblePosDir) {
            // Generates a new possible position.
            Position newPosition = new Position(position.getRank() + posDis.getRank()
                    , position.getFile() + posDis.getFile());
            // Check if the new position is valid.
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
        // Declares the direction for moving.
        // White player's pawn should move up, while blacks player's should move down.
        int dir = getWhitePlayer() ? 1 : -1;

        if (isValidMoveBasicCheck(chessboard, targetPosition)) {
            int rankDis = targetPosition.getRank() - getPosition().getRank();
            int fileDisAbs = Math.abs(targetPosition.getFile() - getPosition().getFile());

            // Legal move should always advance one square forward, except the first time
            if (rankDis * dir == 1) {
                if (fileDisAbs > 1) {
                    return false;
                } else if (fileDisAbs == 1) {
                    BasePiece targetPiece = chessboard[targetPosition.getRank()][targetPosition.getFile()];
                    // The target position should be occupied by opponent.
                    return targetPiece != null && !isSamePlayer(targetPiece);
                } else {
                    // Target position should be unoccupied.
                    return chessboard[targetPosition.getRank()][targetPosition.getFile()] == null;
                }
            } else if (rankDis * dir == 2 && fileDisAbs == 0) {
                // The first move could advance two squares, and both squares should be unoccupied.
                if (firstTime) {
                    return chessboard[targetPosition.getRank()][targetPosition.getFile()] == null
                            && chessboard[targetPosition.getRank() - dir][targetPosition.getFile()] == null;
                }
            }
        }
        return false;
    }

    /**
     * See also {@link BasePiece#getNameString()}.
     */
    @Override
    public String getNameString() {
        return "P";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Pawn clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Pawn(pos, getWhitePlayer(), getFirstTime());
    }
}