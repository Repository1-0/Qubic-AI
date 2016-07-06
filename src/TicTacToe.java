





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
    
    public static void resetBoard(){
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
                            //System.out.println("Horizontal Win");
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
                            //System.out.println("Vertical 2D Win");
                            return true;
                        }
                    }
                }
            }
        }
        //check diagonal 2D
        for(int level = 0; level < board.length; level++){
           int row = 0, col = 0;
           boolean flag = false;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && row == (board[0][0].length - 1)){
                   //System.out.println("Diagonal 2D Win");
                   return true;
               }
               row++; 
               col++;
           }
           flag = false;
           row = 0; 
           col = board[0][0].length - 1;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && row == (board[0][0].length - 1)){
                   //System.out.println("Diagonal 2D Win");
                   return true;
               }
               row++; 
               col--;
           }
        }
        return false;
    }
    
    public static boolean checkVertical(int token){
        for(int row = 0; row < board[0].length; row++){
            for(int col = 0; col < board[0][0].length; col++){
                if(board[0][row][col] == token){
                    for(int level = 1; level < board.length; level++){
                        if(board[level][row][col] != token){
                            break;
                        }
                        else if(board[level][row][col] == token && level == (board[0][0].length - 1)){
                            //System.out.println("Vertical Win");
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
        for(int row = 0; row < board.length; row++){
           int level = 0, col = 0;
           
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   //System.out.println("Diagonal 3D Win");
                   return true;
               }
               level++; 
               col++;
           }
           
           level = 0; 
           col = board[0][0].length - 1;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   //System.out.println("Diagonal 3D Win");
                   return true;
               }
               level++; 
               col--;
           }
        }
        for(int col = 0; col < board.length; col++){
           int level = 0, row = 0;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   //System.out.println("Diagonal 3D Win");
                   return true;
               }
               level++; 
               row++;
           }
           
           level = 0; 
           row = board[0][0].length - 1;
           while(!flag){
               if(board[level][row][col] != token){
                   break;
               }
               else if(board[level][row][col] == token && level == (board.length - 1)){
                   //System.out.println("Diagonal 3D Win");
                   return true;
               }
               level++; 
               row--;
           }
        }
        int level = 0; int row = 0; int col = 0;
        
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                //System.out.println("Diagonal 3D Win 1");
                return true;
            }
            level++; 
            row++;
            col++;
        }
        level = 0; row = 0; col = board[0][0].length - 1;
        flag = false;
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                //System.out.println("Diagonal 3D Win 2");
                return true;
            }
            level++; 
            row++;
            col--;
        }
        level = 0; row = board[0].length - 1; col = 0;
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                //System.out.println("Diagonal 3D Win 3");
                return true;
            }
            level++; 
            row--;
            col++;
        }
        level = 0; row = board[0].length - 1; col = board[0][0].length - 1;
        
        while(!flag){
            if(board[level][row][col] != token){
                break;
            }
            else if(board[level][row][col] == token && level == (board.length - 1)){
                //System.out.println("Diagonal 3D Win 4");
                return true;
            }
            level++; 
            row--;
            col--;
        }
        return false;
    }
    
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
    
    public static void printBoard(){
        System.out.println("Level: 1        Level: 2        Level: 3        Level: 4");
        
            for(int row = 0; row < board[0].length; row++){
                for(int l = 0; l < board.length; l++){
                    for(int col = 0; col < board[0][0].length; col++){
                        System.out.print(board[l][row][col] + "  ");
                    }
                    System.out.print("    ");
                }
                System.out.println("");
            }
            System.out.println("");
            System.out.println("");
        
    }
    
    public static void main(String[] args) {
        int a1Wins = 0; 
        int a2Wins = 0;
        TicTacToe game = new TicTacToe();
        GameAI a1 = new GameAI(1, 5, 4);
        GameAI a2 = new GameAI(2, 5, 4);
        int turn = 1;
        int numGames = 200;
        while(gameCount <= numGames){
            
            //System.out.println("Turn: " + turn);
            turn++;
            a1.run();
            //TicTacToe.printBoard();
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
            //TicTacToe.printBoard();
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
//        a1.printPercentage();
//        a2.printPercentage();
//        a1 = new GameAI(1,5,4);
//        a1Wins = 0;
//        a2Wins = 0;
//        gameCount = 1;
//        while(gameCount <= numGames){
//            //System.out.println("Turn: " + turn);
//            turn++;
//            a1.run();
//            //TicTacToe.printBoard();
//            if(checkWin(a1.token)){
//                //System.out.println("Player 1 wins");
//                a1.update(a1.token);
//                a2.update(a1.token);
//                resetBoard();
//                turn = 1;
//                a1Wins++;
//                gameCount++;
//                continue;
//            }
//            a2.run();
//            //TicTacToe.printBoard();
//            if(checkWin(a2.token)){
//                //System.out.println("Player 2 wins");
//                a1.update(a2.token);
//                a2.update(a2.token);
//                resetBoard();
//                turn = 1;
//                gameCount++;
//                a2Wins++;
//            }
//            else if(isDraw()){
//                //System.out.println("draw");
//                a1.update(-1);
//                a2.update(-1);
//                resetBoard();
//                turn = 1;
//                gameCount++;
//            }
//
//       }
//        System.out.println("A1 Wins: " + a1Wins);
//        System.out.println("A2 Wins: " + a2Wins);
//        System.out.println("Draws: " + (numGames - a1Wins - a2Wins));
//        a1.printPercentage();
//        a2.printPercentage();
    }
    
}
