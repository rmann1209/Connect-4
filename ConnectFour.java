import java.util.Scanner;

public class ConnectFour {

    public static void main(String []args){
        Scanner scnr = new Scanner (System.in);
        int currPlayer = 1; //keeps track of current player within while loop
        char currChip = 'x'; //keeps track of current chip type
        int chosenCol; //Stores user's input for column choice
        int rowPlaced; //Stores the value returned by insertChip (the row token was placed)
        boolean gameOver = false;

        //Asks the user for height and length of board and assigns input to variables
        System.out.print("What would you like the height of the board to be? ");
        int boardHeight = scnr.nextInt();
        System.out.print("What would you like the length of the board to be? ");
        int boardLength = scnr.nextInt();
        System.out.println();

        //Creates 2d array of board, then assigns and prints it as dashes for user to see
        char [][] board = new char[boardHeight][boardLength];
        initializeBoard(board);
        printBoard(board);

        System.out.println("\nPlayer 1: x" + "\nPlayer 2: o\n"); //Tells players what their tokens are

        while(!gameOver){
            System.out.print("Player " + currPlayer +": Which column would you like to choose? ");
            chosenCol = scnr.nextInt();
            rowPlaced = insertChip(board, chosenCol, currChip);
            printBoard(board);

            gameOver = checkIfWinner(board, chosenCol, rowPlaced, currChip);

            if(!gameOver && currPlayer == 1){
                currPlayer = 2;
                currChip = 'o';
            }
            else if(!gameOver){
                currPlayer = 1;
                currChip = 'x';
            }
        }
    }

    //Prints out the current board
    public static void printBoard(char[][]array){
        for(int i= array.length-1; i>=0; i--){
            for(int j=0; j<array[0].length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Initializes board with dashes
    public static void initializeBoard(char[][] array){
        for(int i=0; i<array.length; i++){
            for(int j=0; j<array[0].length; j++){
                array[i][j] = '-';
            }
        }
    }

    //Places the token in the column that the user has chosen.
    //Will find the next available spot in that column if there are already tokens there.
    //The row that the token is placed in is returned.
    public static int insertChip(char[][]array, int col, char chipType){
        int i;
        for(i=0; i<array.length; i++){
            if (array[i][col] == '-') {
                array[i][col] = chipType;
                break;
            }
        }
        return i; //rows start with 0 at the bottom
    }


    public static boolean checkIfWinner(char[][] array, int col, int row, char chipType){
        //Checks for draw game
        boolean draw = true;
        for(int i=0; i<array.length; i++){
            for(int j=0; j<array[col].length; j++){
                if (array[i][j] == '-')
                    draw = false;
            }
        }
        if (draw) {
            System.out.println("Draw. Nobody wins.");
            return true;
        }

        int leftCol = col; //Will be used to hold the leftmost column that matches chip type
        while(leftCol != 0 && array[row][leftCol-1] == chipType){
            leftCol--;
        }

        int runCount = 0; //Keeps track of horizontal run length

        //Starting with leftmost chip or chipType in given row, checks if there are 4 in a row
        while ((leftCol != array[col].length) && (array[row][leftCol] == chipType)) {
                leftCol++;
                runCount++;
            }

        //If true, that means four in a row. Print statement of current player will print indicating they won
        if(runCount >= 4) {
            if (chipType == 'x')
                System.out.println("Player 1 won the game!");
            else if (chipType == 'o')
                System.out.println("Player 2 won the game!");
            return true;
        }

        //Not possible to get 4 vertical unless row is at least 3 or greater, so will return false automatically if not the case
        if(row < 3)
            return false;
        else{
            for(int i=row; i>row-4; i--){
                if(array[i][col] != chipType)
                    return false; //Will return false as soon as a different chip type is detected
            }


            if (chipType == 'x')
                System.out.println("Player 1 won the game!");
            else if (chipType == 'o')
                System.out.println("Player 2 won the game!");
            return true;
        }
    }
}
