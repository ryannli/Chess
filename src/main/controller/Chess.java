package controller;

import model.*;
import viewer.ChessGUI;
import viewer.ChessGUI.BoardPanel;
import viewer.ChessGUI.PiecePanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static model.BasePiece.Position;

public class Chess {
    private Board chessboard;
    private ChessGUI chessGUI;
    private BoardPanel boardPanel;
    /**
     * The current selected piece by user on chessboard
     */
    private BasePiece selectPiece;
    /**
     * If the current selected piece is white piece
     */
    private boolean currentPlayerIsWhite;

    /**
     * The string for win record on game history
     */
    private static String WIN = "Win";
    /**
     * The string for lose record on game history
     */
    private static String LOSE = "Lose";
    /**
     * The string for tie record on game history
     */
    private static String TIE = "Tie";

    public Chess() {
    }


    /**
     * Initializes the board by adding all pieces for both players at start state.
     */
    public void initialize() throws IOException {
        setChessPieces();

        chessGUI = new ChessGUI(chessboard);
        chessGUI.initialize(this);

        boardPanel = chessGUI.getChessPanel();
        addPiecesMouseListener(boardPanel.getPiecePanels());
        addRestartButtonMouseListener(chessGUI.getControlPanel().getRestartButton());
        addForfeitButtonMouseListener(chessGUI.getControlPanel().getWhiteForfeitButton(), true);
        addForfeitButtonMouseListener(chessGUI.getControlPanel().getBlackForfeitButton(), false);
        addUndoButtonMouseListener(chessGUI.getControlPanel().getUndoButton());
    }

    /**
     * Generates all pieces on board. This function decides the piece alignments on board.
     */
    private void setChessPieces() {
        chessboard = new Board(8, 8);
        generatePiece(0, 4, "king", true);
        generatePiece(0, 3, "queen", true);
        generatePiece(0, 2, "bishop", true);
        generatePiece(0, 5, "bishop", true);
        generatePiece(0, 1, "knight", true);
        generatePiece(0, 6, "knight", true);
        generatePiece(0, 0, "rook", true);
        generatePiece(0, 7, "rook", true);

        generatePiece(7, 4, "king", false);
        generatePiece(7, 3, "queen", false);
        generatePiece(7, 2, "bishop", false);
        generatePiece(7, 5, "bishop", false);
        generatePiece(7, 1, "knight", false);
        generatePiece(7, 6, "knight", false);
        generatePiece(7, 0, "rook", false);
        generatePiece(7, 7, "rook", false);

        for (int i = 0; i < 8; i++) {
            generatePiece(1, i, "pawn", true);
            generatePiece(6, i, "pawn", false);
        }

        // Generates special added pieces
        generatePiece(2, 0, "soldier", true);
        generatePiece(5, 0, "soldier", false);
        generatePiece(2, 7, "elephant", true);
        generatePiece(5, 7, "elephant", false);
        selectPiece = null;
        currentPlayerIsWhite = true;
    }

    /**
     * Generates the piece with given type and position on board
     *
     * @param rank          the rank of piece
     * @param file          the file of piece
     * @param pieceType     the type of piece
     * @param isWhitePlayer the owner of board
     */
    private void generatePiece(int rank, int file, String pieceType
            , boolean isWhitePlayer) {
        Position position = new Position(rank, file);
        BasePiece piece = null;
        switch (pieceType) {
            case "king":
                piece = new King(position, isWhitePlayer);
                break;
            case "queen":
                piece = new Queen(position, isWhitePlayer);
                break;
            case "bishop":
                piece = new Bishop(position, isWhitePlayer);
                break;
            case "rook":
                piece = new Rook(position, isWhitePlayer);
                break;
            case "knight":
                piece = new Knight(position, isWhitePlayer);
                break;
            case "pawn":
                piece = new Pawn(position, isWhitePlayer);
                break;
            case "soldier":
                piece = new Soldier(position, isWhitePlayer);
                break;
            case "elephant":
                piece = new Elephant(position, isWhitePlayer);
                break;
        }
        chessboard.setPiece(piece);
    }

    /**
     * Adds undo button listener
     *
     * @param button the undo button
     */
    public void addUndoButtonMouseListener(JButton button) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    if (chessboard.undoMove()) {
                        // Switch player
                        currentPlayerIsWhite = !currentPlayerIsWhite;
                        selectPiece = null;
                        resetCurrentPlayernameOnGUI();
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(chessboard);
                        }
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * Adds restart button listener
     *
     * @param button the restart button
     */
    public void addRestartButtonMouseListener(JButton button) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    setChessPieces();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(chessboard);
                        }
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * Adds forfeit button listener
     *
     * @param button the forfeit button
     */
    public void addForfeitButtonMouseListener(JButton button, boolean isWhiteButton) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    if (isWhiteButton) {
                        chessGUI.getControlPanel().addHistory(LOSE, WIN);
                    } else {
                        chessGUI.getControlPanel().addHistory(WIN, LOSE);
                    }

                    setChessPieces();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(chessboard);
                        }
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * Adds piece panel listener for a list of piece panels
     *
     * @param piecePanels the list of piece panels
     */
    public void addPiecesMouseListener(ArrayList<PiecePanel> piecePanels) {
        // For dynamic GUI and basic piece movements
        for (PiecePanel piecePanel : piecePanels) {
            piecePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isLeftMouseButton(e)) {
                        dealWithClickOverPiece(piecePanel);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessboard);
                                checkAndInformGameEnd(chessboard.getPiece(piecePanel.getPosition()));
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

    }

    /**
     * Check if the opponent of current piece's owner is in check/checkmate/stalemate.
     * If so, pop up related caution information. End game and update game history if checkmake/stalemate.
     *
     * @param piece the rank of piece
     */
    public void checkAndInformGameEnd(BasePiece piece) {
        // Checks if the opponent is in check/checkmate/stalemate
        if (piece != null) {
            String[] playerNames = chessGUI.getControlPanel().getPlayerNames();
            String whitePlayerName = playerNames[0];
            String blackPlayerName = playerNames[1];

            if (chessboard.inCheckmate(!piece.getWhitePlayer())) {
                String first = piece.getWhitePlayer() ? LOSE : WIN;
                String second = piece.getWhitePlayer() ? WIN : LOSE;
                JOptionPane.showOptionDialog(null, "Player " +
                                (piece.getWhitePlayer() ? blackPlayerName : whitePlayerName) + " has no legal moves, " +
                                (piece.getWhitePlayer() ? whitePlayerName : blackPlayerName) + " wins!",
                        "CAUTION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{}, null);
                chessGUI.getControlPanel().addHistory(first, second);
                setChessPieces();
            } else if (chessboard.inStaleMate(!piece.getWhitePlayer())) {
                JOptionPane.showOptionDialog(null, "Stalemate! Game Ends!",
                        "CAUTION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{}, null);
                chessGUI.getControlPanel().addHistory(TIE, TIE);
                setChessPieces();
            } else if (chessboard.inCheck(!piece.getWhitePlayer())) {
                JOptionPane.showOptionDialog(null, (piece.getWhitePlayer() ? blackPlayerName : whitePlayerName)
                                + " is in Check!",
                        "CAUTION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{}, null);
            }

        }
    }

    /**
     * After click over a piece panel, updates backend information accordingly.
     * For details, update selected piece, current player, repaint GUI if needed.
     *
     * @param piecePanel the piece panel to deal with
     */
    public void dealWithClickOverPiece(PiecePanel piecePanel) {
        if (selectPiece == null) {
            BasePiece expectedPiece = chessboard.getPiece(piecePanel.getPosition());
            if (expectedPiece != null && expectedPiece.getWhitePlayer() == currentPlayerIsWhite) {
                selectPiece = expectedPiece;
            }
        } else {
            if (selectPiece.isSamePosition(piecePanel.getPosition())) {
                // Click twice to cancel selection
                selectPiece = null;
            } else if (chessboard.movePiece(selectPiece, piecePanel.getPosition())) {
                // Move successfully, then cancel selection and switch player
                selectPiece = null;
                currentPlayerIsWhite = !currentPlayerIsWhite;
                resetCurrentPlayernameOnGUI();

            } else {
                // Move unsuccessfully, then use new selection if new selection is same player,
                // otherwise keep the current selection
                BasePiece expectedPiece = chessboard.getPiece(piecePanel.getPosition());
                if (expectedPiece != null && expectedPiece.getWhitePlayer() == currentPlayerIsWhite) {
                    selectPiece = expectedPiece;
                } else {
                    piecePanel.setIllegal(true);
                }
            }
        }
    }

    /**
     * Reset the player name of current turn on GUI.
     */
    public void resetCurrentPlayernameOnGUI() {
        // Sets current player names on GUI
        String[] playerNames = chessGUI.getControlPanel().getPlayerNames();
        chessGUI.getControlPanel().getCurrentTurnLabel()
                .setText("Current Turn: " + (currentPlayerIsWhite ? playerNames[0] : playerNames[1]));
        chessGUI.getControlPanel().getCurrentTurnLabel().repaint();
    }


    protected void setBoard(Board board) {
        chessboard = board;
    }

    public Board getBoard() {
        return chessboard;
    }

    public ChessGUI getChessGUI() {
        return chessGUI;
    }

    public BasePiece getSelectPiece() {
        return selectPiece;
    }

    public static void main(String[] args) throws IOException {
        Chess chess = new Chess();
        chess.initialize();
    }
}
