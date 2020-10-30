package ru.itmo.wp.web.page;


import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("gameState");
        if (state == null) {
            newGame(request, view);
        } else {
            view.put("state", state);
        }
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(3);
        request.getSession().setAttribute("gameState", state);
        view.put("state", state);

        throw new RedirectException(request.getRequestURI());
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("gameState");
        if (state == null) {
            throw new RedirectException("/ticTacToe");
        }
        for (Map.Entry<String, String[]> paramEntry : request.getParameterMap().entrySet()) {
            String param = paramEntry.getKey();
            if (param.startsWith("cell_") && param.length() >= "cell_".length() + 2) {
                int row = param.charAt(param.length() - 2) - '0';
                int col = param.charAt(param.length() - 1) - '0';
                if (state.isValid(row, col)) {
                    state.makeMove(row, col);
                    break;
                }
            }
        }
        view.put("state", state);

        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final int size;
        private final Cell[][] cells;
        private Phase phase;
        private boolean crossesMove;

        public State(int size) {
            this.size = size;
            this.cells = new Cell[size][size];
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
        }

        public Phase getPhase() {
            return phase;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public int getSize() {
            return size;
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        private Cell getTempMove() {
            return crossesMove ? Cell.X : Cell.O;
        }

        public boolean isValid(int x, int y) {
            return 0 <= x && x < size && 0 <= y && y < size;
        }

        private boolean checkLine(int x, int y, int dX, int dY) {
            Cell temp = getTempMove();
            int count = 0;
            while (isValid(x, y)) {
                if (cells[x][y] == temp) {
                    count++;
                }
                x += dX;
                y += dY;
            }
            return count >= size;
        }

        private boolean win() {
            Cell temp = getTempMove();

            if (checkLine(0, 0, 1, 1)
                    || checkLine(size - 1, 0, -1, 1)) {
                return true;
            }
            for (int i = 0; i < size; ++i) {
                if (checkLine(i, 0, 0, 1) || checkLine(0, i, 1, 0)) {
                    return true;
                }
            }

            return false;
        }

        public void makeMove(int row, int col) {
            if (phase != Phase.RUNNING || cells[row][col] != null) {
                return;
            }
            if (crossesMove) {
                cells[row][col] = Cell.X;
                if (win()) {
                    phase = Phase.WON_X;
                }
            } else {
                cells[row][col] = Cell.O;
                if (win()) {
                    phase = Phase.WON_O;
                }
            }
            if (draw()) {
                phase = Phase.DRAW;
            }
            crossesMove = !crossesMove;
        }

        private boolean draw() {
            for (Cell[] row : cells) {
                for (Cell cell : row) {
                    if (cell == null) {
                        return false;
                    }
                }
            }
            return true;
        }

        public enum Phase {
            WON_X("WON_X"), WON_O("WON_O"), DRAW("DRAW"), RUNNING("RUNNING");

            private final String name;

            public String toString() {
                return name;
            }

            Phase(String name) {
                this.name = name;
            }
        }

        private enum Cell {
            X("X"), O("O");

            private final String name;

            public String getName() {
                return name;
            }

            Cell(String name) {
                this.name = name;
            }
        }
    }
}
