





public class TicTacToe {

    
    protected static int[][][] board;
    protected static double[][][] potWinsEverySpace;
    protected static boolean[][][] available;
    protected static int gameCount = 1;
    public TicTacToe(){
        board = new int[4][4][4];
        potWinsEverySpace = new double[4][4][4];
        available = new boolean[4][4][4];
        for(int level = 0; level < board.length; level++){
            for(int row = 0; row < board[0].length; row++){
                for(int col = 0; col < board[0][0].length; col++){
                    available[level][row][col] = true;
                }
            }
        }
        
        potWinsEverySpace[0][0][0] = 7;
        potWinsEverySpace[0][0][1] = 4;
        potWinsEverySpace[0][0][2] = 4;
        potWinsEverySpace[0][0][3] = 7;
        potWinsEverySpace[0][1][0] = 4;
        potWinsEverySpace[0][1][1] = 4;
        potWinsEverySpace[0][1][2] = 4;
        potWinsEverySpace[0][1][3] = 4;
        potWinsEverySpace[0][2][0] = 4;
        potWinsEverySpace[0][2][1] = 4;
        potWinsEverySpace[0][2][2] = 4;
        potWinsEverySpace[0][2][3] = 4;
        potWinsEverySpace[0][3][0] = 7;
        potWinsEverySpace[0][3][1] = 4;
        potWinsEverySpace[0][3][2] = 4;
        potWinsEverySpace[0][3][3] = 7;
        
        potWinsEverySpace[1][0][0] = 4;
        potWinsEverySpace[1][0][1] = 3;
        potWinsEverySpace[1][0][2] = 3;
        potWinsEverySpace[1][0][3] = 4;
        potWinsEverySpace[1][1][0] = 3;
        potWinsEverySpace[1][1][1] = 7;
        potWinsEverySpace[1][1][2] = 7;
        potWinsEverySpace[1][1][3] = 3;
        potWinsEverySpace[1][2][0] = 3;
        potWinsEverySpace[1][2][1] = 7;
        potWinsEverySpace[1][2][2] = 7;
        potWinsEverySpace[1][2][3] = 3;
        potWinsEverySpace[1][3][0] = 4;
        potWinsEverySpace[1][3][1] = 3;
        potWinsEverySpace[1][3][2] = 3;
        potWinsEverySpace[1][3][3] = 4;
        
        potWinsEverySpace[2][0][0] = 4;
        potWinsEverySpace[2][0][1] = 3;
        potWinsEverySpace[2][0][2] = 3;
        potWinsEverySpace[2][0][3] = 4;
        potWinsEverySpace[2][1][0] = 3;
        potWinsEverySpace[2][1][1] = 7;
        potWinsEverySpace[2][1][2] = 7;
        potWinsEverySpace[2][1][3] = 3;
        potWinsEverySpace[2][2][0] = 3;
        potWinsEverySpace[2][2][1] = 7;
        potWinsEverySpace[2][2][2] = 7;
        potWinsEverySpace[2][2][3] = 3;
        potWinsEverySpace[2][3][0] = 4;
        potWinsEverySpace[2][3][1] = 3;
        potWinsEverySpace[2][3][2] = 3;
        potWinsEverySpace[2][3][3] = 4;
        
        potWinsEverySpace[3][0][0] = 7;
        potWinsEverySpace[3][0][1] = 4;
        potWinsEverySpace[3][0][2] = 4;
        potWinsEverySpace[3][0][3] = 7;
        potWinsEverySpace[3][1][0] = 4;
        potWinsEverySpace[3][1][1] = 4;
        potWinsEverySpace[3][1][2] = 4;
        potWinsEverySpace[3][1][3] = 4;
        potWinsEverySpace[3][2][0] = 4;
        potWinsEverySpace[3][2][1] = 4;
        potWinsEverySpace[3][2][2] = 4;
        potWinsEverySpace[3][2][3] = 4;
        potWinsEverySpace[3][3][0] = 7;
        potWinsEverySpace[3][3][1] = 4;
        potWinsEverySpace[3][3][2] = 4;
        potWinsEverySpace[3][3][3] = 7;
    }
    
    //Creates clean game board and resets availability table
    private static void resetBoard(){
        board = new int[4][4][4];
        available = new boolean[4][4][4];
        for(int level = 0; level < board.length; level++){
            for(int row = 0; row < board[0].length; row++){
                for(int col = 0; col < board[0][0].length; col++){
                    available[level][row][col] = true;
                }
            }
        }
    }
    
    public static boolean checkWin(int x){
        if(checkLevel(x)){
            return true;
        }
        if(checkVertical(x)){
            return true;
        }
        if(check3DDiagonal(x)){
            return true;
        }
        return false;
    }
    /*
    Checks each level of the board for all possible victory combinations.
    Logic flows the same way for all three posibilities. So for example, when it
    checks for a horizontal victory, it will start on the left side of the board
    and work its way right, comparing each space to the current player's token.
    If any of the spaces do not contain the current player's token, it will move
    on to the next possible row. 
    */
    public static boolean checkLevel(int token){
        //check horizontal 2D
        for(int level = 0; level < board.length; level++){
            for(int row = 0; row < board[0].length; row++){
                if(board[level][row][0] == token){
                    for(int col = 1; col < board[0][0].length; col++){
                        if(board[level][row][col] != token){
                            break;
                        }
                        else if(board[level][row][col] == token && col == (board[0][0].length - 1)){
                            return true;
                        }
                    }
                }
            }
        }
        //check vertical 2D
        for(int level = 0; level < board.length; level++){
            for(int col = 0; col < board[0].length; col++){
                if(board[level][0][col] == token){
                    for(int row = 1; row < board[0][0].length; row++){
                        if(board[level][row][col] != token){
                            break;
                        }
                        else if(board[level][row][col] == token && row == (board[0][0].length - 1)){
                            return true;
                        }
                    }
                }
            }
        }
        //check diagonal 2D for each level
        for(int level = 0; level < board.length; level++){
           int row = 0, col = 0;
           boolean flag = false;
           //Checks for diagonal victory starting in upper left corner
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && row == (board[0][0].length - 1)){
                   return true;
               }
               row++; 
               col++;
           }
           row = 0; 
           col = board[0][0].length - 1;
           //Checks for diagonal victory starting in upper right corner
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && row == (board[0][0].length - 1)){
                   return true;
               }
               row++; 
               col--;
           }
        }
        return false;
    }
    
    /*
    Similar to checkLevel's setup, iterates over each of the sixteen possible
    win conditions for a four-in-a-row.
    */
    public static boolean checkVertical(int token){
        for(int row = 0; row < board[0].length; row++){
            for(int col = 0; col < board[0][0].length; col++){
                if(board[0][row][col] == token){
                    for(int level = 1; level < board.length; level++){
                        if(board[level][row][col] != token){
                            break;
                        }
                        else if(board[level][row][col] == token && level == (board[0][0].length - 1)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean check3DDiagonal(int token){
        boolean flag = false;
        //Checks for row diagonals, starting on one side of the board and working
        //up(level) and across.
        for(int row = 0; row < board.length; row++){
            //Starts on left side working up and to the right.
           int level = 0, col = 0;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   return true;
               }
               level++; 
               col++;
           }
           
           //starts on right side working up and to the left
           level = 0; 
           col = board[0][0].length - 1;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   return true;
               }
               level++; 
               col--;
           }
        }
        //Similar to checking for row diagonals, but instead searching each 
        //column.
        for(int col = 0; col < board.length; col++){
           int level = 0, row = 0;
           //Starting on upper side of board working up(level) and down(row)
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   return true;
               }
               level++; 
               row++;
           }
           
           //Starting on lower side of board working up(both level and row)
           level = 0; 
           row = board[0][0].length - 1;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   return true;
               }
               level++; 
               row--;
           }
        }
        int level = 0; int row = 0; int col = 0;
        
        /*
        The four remaining while loops check for the diagonals starting from
        the corners of the cube. Each while loop only checks for one diagonal as 
        each diagonal will take up two corners.
        */
        //Starting on bottom level in upper left corner
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                return true;
            }
            level++; 
            row++;
            col++;
        }
        //Starting on bottom level in upper right corner
        level = 0; row = 0; col = board[0][0].length - 1;
        flag = false;
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                return true;
            }
            level++; 
            row++;
            col--;
        }
        //Starting bottom level in lower left corner
        level = 0; row = board[0].length - 1; col = 0;
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                return true;
            }
            level++; 
            row--;
            col++;
        }
        //Starting bottom level in lower right corner
        level = 0; row = board[0].length - 1; col = board[0][0].length - 1;
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                return true;
            }
            level++; 
            row--;
            col--;
        }
        return false;
    }
    /*
    Simply checks for draws by looking for any available space on the board
    */
    public static boolean isDraw(){
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    if(board[level][row][col] == 0){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /*
    Custom print function to have all four levels print on one "line" with labels
    */
    @Override
    public String toString(){
        String returnString;
        returnString = "Level: 1        Level: 2        Level: 3        Level: 4\n";
        
            for(int row = 0; row < board[0].length; row++){
                for(int l = 0; l < board.length; l++){
                    for(int col = 0; col < board[0][0].length; col++){
                        returnString += board[l][row][col] + "  ";
                    }
                    returnString += "    ";
                }
                returnString += "\n";
            }
            returnString += "\n";
            return returnString;
        
    }
    
    /*
    Driver method for the game consists of two main components: the setup and 
    the actual execution of the game. Setup should contain at the miniumum 
    the creation of a new TicTacToe object to perform initial board setup, two
    AIs to play the game, and a variable that holds how many games are to be
    played.
    
    After setup is complete, the game is started, with the AI executing a move
    and checking to see if it has won. If not, the other AI moves and checks
    for a victory state for itself. Play continues until either an AI wins or
    there is a draw, at which point the board is reset along with any relevant
    counters and the AI's analyze the board for piece location.
    
    Print statements are left in but commented out if you want to see every turn
    and who wins (or draws) each game.
    */
    public static void main(String[] args) {
        int a1Wins = 0; 
        int a2Wins = 0;
        TicTacToe game = new TicTacToe();
        GameAI a1 = new GameAI(1, 5, 4);
        GameAI a2 = new GameAI(2, 5, 4);
        int turn = 1;
        int numGames = 100;
        while(gameCount <= numGames){
            
            //System.out.println("Turn: " + turn);
            turn++;
            a1.run();
            //System.out.println(game);
            if(checkWin(a1.token)){
                System.out.println("Game: " + gameCount + "    ");
                //System.out.println("Player 1 wins");
                a1.update(a1.token);
                a2.update(a1.token);
                resetBoard();
                turn = 1;
                a1Wins++;
                gameCount++;
                continue;
            }
            a2.run();
            //System.out.println(game);
            if(checkWin(a2.token)){
                System.out.println("Game: " + gameCount + "    ");
                //System.out.println("Player 2 wins");
                a1.update(a2.token);
                a2.update(a2.token);
                resetBoard();
                turn = 1;
                gameCount++;
                a2Wins++;
            }
            else if(isDraw()){
                System.out.println("Game: " + gameCount + "    ");
                //System.out.println("draw");
                a1.update(-1);
                a2.update(-1);
                resetBoard();
                turn = 1;
                gameCount++;
            }

        }
        System.out.println("A1 Wins: " + a1Wins);
        System.out.println("A2 Wins: " + a2Wins);
        System.out.println("Draws: " + (numGames - a1Wins - a2Wins));

    }
    
}
