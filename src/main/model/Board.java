package model;

import model.BasePiece.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import static model.BasePiece.insideBoundary;

public class Board {
    private BasePiece[][] chessboard;
    private BasePiece whiteKing;
    private BasePiece blackKing;
    private Stack<BasePiece[][]> boardHistory;

    public Board(int height, int width) {
        chessboard = new BasePiece[height][width];
        boardHistory = new Stack<BasePiece[][]>();
    }

    public BasePiece[][] getChessBoard() {
        return chessboard;
    }


    /**
     * Sets a piece on board. Returns true if set successfully. Otherwise, the position of piece
     * may be out of board boundary, or the position is already occupied by another piece.
     *
     * @param piece the piece needs to settle on board
     * @return true if set successfully.
     */
    public boolean setPiece(BasePiece piece) {
        Position position = piece.getPosition();
        if (insideBoundary(chessboard, position)
                && chessboard[position.getRank()][position.getFile()] == null) {
            chessboard[position.getRank()][position.getFile()] = piece;
            if (piece instanceof King) {
                if (piece.getWhitePlayer()) {
                    whiteKing = piece;
                } else {
                    blackKing = piece;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the piece from board given a position. Returns null if position is out of boundary.
     *
     * @param position the given position
     * @return the piece on position or null
     */
    public BasePiece getPiece(Position position) {
        if (!insideBoundary(chessboard, position)) {
            return null;
        }
        return chessboard[position.getRank()][position.getFile()];
    }

    /**
     * Removes the piece from board given a position. Returns true if remove successfully. Otherwise, the position may
     * be out of board boundary, or there is no piece on the given position.
     *
     * @param position the given position
     * @return true if remove successfully
     */
    public boolean removePiece(Position position) {
        // Fails if component position is out out boundary or there is no component on position
        if (!insideBoundary(chessboard, position) || chessboard[position.getRank()][position.getFile()] == null) {
            return false;
        }
        chessboard[position.getRank()][position.getFile()] = null;
        return true;
    }

    /**
     * Move the piece from current position to new position.
     *
     * @param piece          the piece to move
     * @param targetPosition the target position
     */
    private void moveAndSet(BasePiece piece, Position targetPosition) {
        // Removes the component from original position
        removePiece(piece.getPosition());
        // Captures the component on new position, if any
        removePiece(targetPosition);
        piece.setPosition(targetPosition);
        setPiece(piece);
    }

    /**
     * Returns true if moving the piece to target position will make self king in check. Tries to move the piece to
     * target position, checks if king will be in check, and recovers the chessboard. The target position must be valid
     * according to piece rules before passed in.
     *
     * @param piece          the piece to move
     * @param targetPosition the target position
     * @return true if the move will make king in check
     */
    protected boolean tryMoveIfInCheck(BasePiece piece, Position targetPosition) {
        // Saves the piece on new position and the current position of given piece
        BasePiece savePiece = chessboard[targetPosition.getRank()][targetPosition.getFile()];
        Position savePosition = piece.getPosition();

        // Moves to piece to target position
        moveAndSet(piece, targetPosition);

        // Check if the owner will be in check
        boolean inCheck = inCheck(piece.getWhitePlayer());

        // Recovers the saved piece and given piece
        moveAndSet(piece, savePosition);
        if (savePiece != null)
            setPiece(savePiece);
        return inCheck;
    }

    /**
     * Gets the existing pieces on board for a given player
     *
     * @param isWhitePlayer the player
     * @return list of all existing pieces
     */
    public List<BasePiece> getPlayerAllPieces(boolean isWhitePlayer) {
        List<BasePiece> pieces = new ArrayList<BasePiece>();
        for (int rank = 0; rank < chessboard.length; rank++) {
            for (int file = 0; file < chessboard[0].length; file++) {
                if (chessboard[rank][file] != null
                        && chessboard[rank][file].getWhitePlayer() == isWhitePlayer) {
                    pieces.add(chessboard[rank][file]);
                }
            }
        }
        return pieces;
    }

    /**
     * Returns true if the piece is moved to target position successfully.
     *
     * @param piece          the piece to move
     * @param targetPosition the target position
     * @return true if the move is successful
     */
    public boolean movePiece(BasePiece piece, Position targetPosition) {
        if (piece.isValidMove(chessboard, targetPosition) && !tryMoveIfInCheck(piece, targetPosition)) {
            boardHistory.push(cloneBoard(chessboard));
            moveAndSet(piece, targetPosition);
            piece.setNoFirstTime();
            // add board history
            return true;
        }
        return false;
    }

    /**
     * Returns true if the undo successfully. Sets the current board to previous board.
     *
     * @return true if boardHistory is not empty.
     */
    public boolean undoMove() {
        if (boardHistory.size() > 0) {
            chessboard = boardHistory.pop();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets legal moves of a chess piece. Legal means obey piece rules and also chess rules (i.e. not in check)
     *
     * @param piece the chess piece
     * @return a set of legal move positions of piece
     */
    public HashSet<Position> getLegalMoves(BasePiece piece) {
        HashSet<Position> legalMoves = new HashSet<Position>();
        for (Position pos : piece.getPossibleLegalMoves(chessboard)) {
            if (!tryMoveIfInCheck(piece, pos)) {
                legalMoves.add(pos);
            }
        }
        return legalMoves;
    }

    /**
     * Returns the king on chessboard for a given player.
     *
     * @param isWhitePlayer the player
     * @return the king
     */
    public BasePiece getKing(boolean isWhitePlayer) {
        if (isWhitePlayer) {
            return whiteKing;
        } else {
            return blackKing;
        }
    }

    /**
     * Returns true if the player is in check.
     *
     * @param isWhitePlayer the player
     * @return true if in check
     */
    public boolean inCheck(boolean isWhitePlayer) {
        Position kingPosition = getKing(isWhitePlayer).getPosition();
        for (int rank = 0; rank < chessboard.length; rank++) {
            for (int file = 0; file < chessboard[0].length; file++) {
                BasePiece piece = chessboard[rank][file];
                if (piece != null && piece.getWhitePlayer() != isWhitePlayer) {
                    // The piece is opponent's
                    if (piece.isValidMove(chessboard, kingPosition)) {
                        // The opponent's piece can move to king position
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the player is in checkmate.
     *
     * @param isWhitePlayer the player
     * @return true if in checkmate
     */
    public boolean inCheckmate(boolean isWhitePlayer) {
        if (!inCheck(isWhitePlayer)) {
            // The player is not in check now
            return false;
        }
        // The player is in check now and has no legal moves.
        return noLegalMoves(isWhitePlayer);
    }

    /**
     * Returns true if the player is in stalemate.
     *
     * @param isWhitePlayer the player
     * @return true if in stalemate
     */
    public boolean inStaleMate(boolean isWhitePlayer) {
        if (inCheck(isWhitePlayer)) {
            // The player is in check now
            return false;
        }
        // The player is not in check now and has no legal moves.
        return noLegalMoves(isWhitePlayer);
    }

    /**
     * Returns true if the player has no legal moves.
     *
     * @param isWhitePlayer the player
     * @return true if no legal moves
     */
    private boolean noLegalMoves(boolean isWhitePlayer) {
        // Checks if any valid moves of any pieces can make a move that will not make king in check.
        for (BasePiece piece : getPlayerAllPieces(isWhitePlayer)) {
            for (Position position : piece.getPossibleLegalMoves(chessboard)) {
                if (!tryMoveIfInCheck(piece, position))
                    return false;
            }
        }
        return true;
    }

    /**
     * Deep copy of a chessboard
     *
     * @param chessboard the given chessboard
     * @return the cloned chessboard
     */
    private BasePiece[][] cloneBoard(BasePiece[][] chessboard) {
        BasePiece[][] newBoard = new BasePiece[chessboard.length][chessboard[0].length];
        for (int rank = 0; rank < chessboard.length; rank++) {
            for (int file = 0; file < chessboard[0].length; file++) {
                if (chessboard[rank][file] != null) {
                    newBoard[rank][file] = chessboard[rank][file].clonePiece();
                } else {
                    newBoard[rank][file] = null;
                }
            }
        }
        return newBoard;
    }

}
