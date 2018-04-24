package model;

import java.util.HashSet;

/**
 * This piece class follows the rule of Chinese Pawn - Soldier. Before traversing half of the chessboard, it
 * can only move forward one step; after that, it can either move forward or left or right for one step. For
 * every step, it can move as long as the target position is empty, or it can eat one piece if the target
 * position is occupied by opponent.
 */

public class Soldier extends BasePiece {
    public Soldier(BasePiece.Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Soldier(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<BasePiece.Position> moves = new HashSet<BasePiece.Position>();
        int dir = getWhitePlayer() ? 1 : -1;
        BasePiece.Position position = getPosition();
        int rank = position.getRank();
        int file = position.getFile();

        Position newPosition = new Position(rank + dir, file);
        if (isValidMove(chessboard, new Position(rank + dir, file))) {
            moves.add(newPosition);
        }

        if (isPassedHalfway(position, getWhitePlayer())) {
            for (int fileGradient = -1; fileGradient <= 1; fileGradient += 2) {
                newPosition = new Position(rank, file + fileGradient);
                if (isValidMove(chessboard, newPosition)) {
                    moves.add(newPosition);
                }
            }
        }

        return moves;
    }

    /**
     * See also {@link BasePiece#isValidMove(BasePiece[][], BasePiece.Position)}.
     */
    @Override
    public boolean isValidMove(BasePiece[][] chessboard, BasePiece.Position targetPosition) {

        int dir = getWhitePlayer() ? 1 : -1;
        int rankDis = targetPosition.getRank() - getPosition().getRank();
        int fileDisAbs = Math.abs(targetPosition.getFile() - getPosition().getFile());
        if (isValidMoveBasicCheck(chessboard, targetPosition)) {
            if (rankDis * dir == 1) {
                // Cannot move horizontally.
                return fileDisAbs == 0;
            } else {
                // Can move horizontally for at most one step.
                if (isPassedHalfway(getPosition(), getWhitePlayer())) {
                    return rankDis == 0 && fileDisAbs <= 1;
                }
            }
        }
        return false;
    }

    private boolean isPassedHalfway(BasePiece.Position position, boolean isWhitePlayer) {
        return ((isWhitePlayer && position.getRank() >= 4) || (!isWhitePlayer && position.getRank() < 4));
    }

    /**
     * See also {@link BasePiece#getNameString()}.
     */
    @Override
    public String getNameString() {
        return "S";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Soldier clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Soldier(pos, getWhitePlayer(), getFirstTime());
    }
}
