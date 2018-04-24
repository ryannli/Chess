package model;

import java.util.HashSet;

/**
 * This piece class follows the rule of Chinese Bishop - Soldier. It can only take (2, 2) step towards four directions.
 * Moreover, if the (1, 1) step is not empty, it will be "blocked" and cannot move.
 */

public class Elephant extends BasePiece {
    public Elephant(BasePiece.Position position, boolean whitePlayer) {
        super(position, whitePlayer);
    }

    public Elephant(Position position, boolean whitePlayer, boolean firstTime) {
        super(position, whitePlayer, firstTime);
    }

    /**
     * See also {@link BasePiece#getPossibleLegalMoves(BasePiece[][])}.
     */
    @Override
    public HashSet<BasePiece.Position> getPossibleLegalMoves(BasePiece[][] chessboard) {
        HashSet<BasePiece.Position> moves = new HashSet<BasePiece.Position>();
        BasePiece.Position position = getPosition();

        // Add moves in four diagonal directions.
        for (int quadrant = 1; quadrant <= 4; quadrant++) {
            int rank = position.getRank();
            int file = position.getFile();
            int rankGradient = quadrant <= 2 ? 2 : -2;
            int fileGradient = (quadrant == 1 || quadrant == 4) ? 2 : -2;

            Position newPosition = new Position(rank + rankGradient, file + fileGradient);
            if (isValidMove(chessboard, newPosition)) {
                moves.add(newPosition);
            }

        }
        return moves;
    }

    /**
     * See also {@link BasePiece#isValidMove(BasePiece[][], BasePiece.Position)}.
     */
    @Override
    public boolean isValidMove(BasePiece[][] chessboard, BasePiece.Position targetPosition) {

        if (isValidMoveBasicCheck(chessboard, targetPosition)) {
            int rankDisAbs = Math.abs(getPosition().getRank() - targetPosition.getRank());
            int fileDisAbs = Math.abs(getPosition().getFile() - targetPosition.getFile());
            if (rankDisAbs == fileDisAbs && fileDisAbs == 2) {
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
        return "E";
    }

    /**
     * See also {@link BasePiece#clonePiece()}.
     */
    @Override
    public Elephant clonePiece() {
        Position pos = new Position(getPosition().getRank(), getPosition().getFile());
        return new Elephant(pos, getWhitePlayer(), getFirstTime());
    }
}
