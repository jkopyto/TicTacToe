import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TicTacToe {
    private enum Sign {
        X,O,EMPTY
    }
    private enum Gamer {
        GAMER_1, GAMER_2
    }

    private Sign[][] board = {{Sign.EMPTY, Sign.EMPTY, Sign.EMPTY}, {Sign.EMPTY, Sign.EMPTY, Sign.EMPTY}, {Sign.EMPTY, Sign.EMPTY, Sign.EMPTY}};
    private Scanner input = new Scanner((System.in));
    private Gamer gamer = Gamer.GAMER_1;

    public void startGame() {
        displayBoard();
        makeMove();
    }

    private void displayBoard(){
        System.out.println();
        for(int i = 0; i < board.length; i++) {
            System.out.printf("%d", (i+1));

            for(int z = 0; z < board[i].length; z++) {
                System.out.printf("%5s", (board[i][z] == Sign.EMPTY ? "" : board[i][z]));
            }

            System.out.println();
        }
        System.out.printf("\n%6d%5d%5d\n",1, 2, 3);
        System.out.println();
    }

    private void makeMove(){
        System.out.printf("%s makes a move!\n", gamer);
        System.out.print("Type field x coordinate (between 1 and 3): ");
        int xCoord = input.nextInt() - 1;
        System.out.print("Type field y coordinate (between 1 and 3): ");
        int yCoord = input.nextInt() - 1;

        try {
            if(board[yCoord][xCoord] == Sign.EMPTY) {
                board[yCoord][xCoord] = gamer == Gamer.GAMER_1 ? Sign.O: Sign.X;
            } else {
                System.out.println("Field is not empty. Try again");
                makeMove();
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("There is no field like this. Try again");
            makeMove();
        }

        displayBoard();

        if(isWinner()){
            System.out.printf("\n%5s WON THIS GAME!\n", gamer);
        }
        else if (isDraw()){
            System.out.println("\nDRAW\n");
        }
        else {
            gamer = gamer == Gamer.GAMER_1 ? Gamer.GAMER_2 : Gamer.GAMER_1;
            makeMove();
        }
    }

    private boolean isDraw() {
        for(int i = 0; i < board.length; i++){
            if(!Arrays.stream(board[i]).distinct().noneMatch(n -> n == Sign.EMPTY)){
                return false;
            }
        }
        return true;
    }

    private boolean isWinner() {
        if(checkRows(board) || checkRows(rotateBoard()) || checkDiagonals()) {
            return true;
        }
        return false;
    }

    private boolean checkDiagonals() {
        if(IntStream.range(0, board.length ).map(n -> (board[n][n] == Sign.O ? 1 : 0)).allMatch(n -> n == 1)) {
            return true;
        }
        else if (IntStream.range(0, board.length).map(n -> (board[n][n] == Sign.X ? 1 : 0)).allMatch(n -> n == 1)) {
            return true;
        }
        else return false;
    }

    private boolean checkRows (Sign[][] board){
        for(int i = 0; i < board.length; i++){
            if(Arrays.stream(board[i]).distinct().allMatch(n -> n == Sign.O) || Arrays.stream(board[i]).distinct().allMatch(n -> n == Sign.O)){
                return true;
            }
        }
        return false;
    }

    private Sign[][] rotateBoard() {
        final int M = board.length;
        final int N = board[0].length;
        Sign[][] ret = new Sign[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = board[r][c];
            }
        }
        return ret;
    }
}
