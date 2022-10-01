package pkg3d_tictactoe;


import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author navee
 */
public class game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner myObj = new Scanner(System.in);
        System.out.println("Do you want to play against ME(1) or a FRIEND(2)?");
        int choice = myObj.nextInt();
        if(choice==1){
            play(true);
        }else{
            play(false);
        }
    }
    
    //runs game either against computer, or against another player
    static public void play(boolean computer){
        char[][] layer1 = {{' ',' ',' '}, {' ',' ',' '}, {' ',' ',' '}};
        char[][] layer2 = {{' ',' ',' '}, {' ',' ',' '}, {' ',' ',' '}};
        char[][] layer3 = {{' ',' ',' '}, {' ',' ',' '}, {' ',' ',' '}};
        char[][][] board = {layer1, layer2, layer3};
        if(computer){
            while(emptySpotIn(board)){
                makeHumanMove(board,'X');
                if(checkWinner(board,'X')){
                    System.out.println("You won...");
                    return;
                }
                if(emptySpotIn(board)){
                    int layerChoice;
                    do{
                        layerChoice = (int) Math.floor(Math.random()*(2-0+1)+0);
                    }while(!(emptySpotInLayer(board[layerChoice])));
                    
                    ArrayList<Integer> availableSpots = availableSpots(board, layerChoice);
                    
                    int max = availableSpots.size()-1;
                    int min = 0;
                    int computerChoice = availableSpots.get((int) Math.floor(Math.random()*(max-min+1)+min));
                    board[layerChoice][computerChoice/3][computerChoice%3] = 'O';
                    System.out.println("My move: ");
                    if(checkWinner(board, 'O')){
                        System.out.println("I WONNNN!!!!!!\noh and u lost");
                        return;
                    }
                }
            }
        }else{
            while(emptySpotIn(board)){
                makeHumanMove(board,'X');
                if(checkWinner(board,'X')){
                    System.out.println("Player X has won!");
                    return;
                }
                if(emptySpotIn(board)) {
                    makeHumanMove(board, 'O');
                    if (checkWinner(board, 'O')) {
                        System.out.println("Player O has won!");
                        return;
                    }
                }
            }
        }
        System.out.println("It's a tie.");
    }
    
    
    //returns available spots on given layerChoice board as array list
    static ArrayList<Integer> availableSpots(char[][][]board, int layerChoice){
        ArrayList<Integer> availableSpots = new ArrayList<Integer>();
        for(int i = 0; i<9; i++){
            if(board[layerChoice][i/3][i%3] == ' '){
                availableSpots.add(i);
            }
        }
        return availableSpots;
    }
    
    //check's if player <letter> has won
    static boolean checkWinner(char[][][]board, char letter){
        //check individual layers
        for(int layer = 0; layer < board.length; layer++) {
            //check rows
            for (char[] row : board[layer]) {
                if (row[0] == letter && row[1] == letter && row[2] == letter) {
                    return true;
                }
            }

            //check columns
            for (int i = 0; i < 3; i++) {
                if (board[layer][0][i] == letter && board[layer][1][i] == letter && board[layer][2][i] == letter) {
                    return true;
                }
            }

            //check diagonals
            if (board[layer][0][0] == letter && board[layer][1][1] == letter && board[layer][2][2] == letter) {
                return true;
            }
            if (board[layer][0][2] == letter && board[layer][1][1] == letter && board[layer][2][0] == letter) {
                return true;
            }
        }
        
        //check matches across layers
        for(int spot = 0; spot<9; spot++){
            if(board[0][spot/3][spot%3]==letter && board[1][spot/3][spot%3]==letter && board[2][spot/3][spot%3]==letter){
                return true;
            }
        }
        
        //check cross layer diagonals
        if(board[0][0][0]==letter && board[1][1][1]==letter && board[2][2][2]==letter){
            return true;
        }
        if(board[0][0][2]==letter && board[1][1][1]==letter && board[2][2][0]==letter){
            return true;
        }
        if(board[0][2][0]==letter && board[1][1][1]==letter && board[2][0][2]==letter){
            return true;
        }
        if(board[0][2][2]==letter && board[1][1][1]==letter && board[2][0][0]==letter){
            return true;
        }
        
        //check repeating cross layer diagonals
        for(int i = 0; i<3; i++){
            if(board[0][0][i]==letter && board[1][1][i]==letter && board[2][2][i]==letter){
                return true;
            }
            if(board[0][2][i]==letter && board[1][1][i]==letter && board[2][0][i]==letter){
                return true;
            }
        }
        
        
        return false;
    }
    
    //runs code for letting human player make a move
    static void makeHumanMove(char[][][]board, char letter){
        boolean validSpot = false;
        while(!validSpot){
            for(int i = 0; i<3; i++){
                System.out.println("Layer " + String.valueOf(i+1) + ": ");
                printBoard(board[i]);
            }
            System.out.println("It is " + letter + "\'s turn.");
            Scanner myObj = new Scanner(System.in);
            System.out.println("Which layer would you like to make a move in (1,2, or 3)?");
            int layerChoice = myObj.nextInt();
            layerChoice--;
//            printBoard(board[layerChoice]);
            myObj = new Scanner(System.in);
            System.out.println("Now choose which spot on the layer you would like to make a move in(1-9):");
            int choice = myObj.nextInt();
            choice--;
            
            
            
            if(board[layerChoice][choice/3][choice%3] == ' '){
                board[layerChoice][choice/3][choice%3] = letter;
                validSpot = true;
//                System.out.println("Your move in Layer " + String.valueOf(layerChoice+1) + ": ");
//                printBoard(board[layerChoice]);
            }else{
                System.out.println("Invalid Spot");
            }
        }
    }
    
    //returns true if the board is not full
    private static boolean emptySpotIn(char[][][] board){
        for(int layer = 0; layer < board.length; layer++) {
            for (int row = 0; row < board[layer].length; row++) {
                for (int col = 0; col < board[layer][row].length; col++) {
                    if (board[layer][row][col] == ' ') {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //returns true if layer is not empty
    static boolean emptySpotInLayer(char[][] board){
        for (int row = 0; row < board.length; row++)
            {
                for (int col = 0; col < board[row].length; col++)
                {
                    if(board[row][col] == ' '){
                        return true;
                    }
                }
            }
        return false;
    }
    
    //prints 1-9 board numbers
    static void printBoardNums(){
        char[][] numberBoard ={{'1','2','3'}, {'4','5','6'}, {'7','8','9'}};
        printBoard(numberBoard);
    }
    
    //prints layer out
    static void printBoard(char[][] board){
        for(char[]row:board){
            System.out.print("|");
            for(char spot:row){
                System.out.print(" " +spot);
                System.out.print(" |");
            }
            System.out.println();
        }
    }
}
