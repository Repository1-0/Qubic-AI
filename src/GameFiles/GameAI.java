package GameFiles;


import java.util.ArrayList;
import java.util.Random;

public class GameAI {
    
    protected int[][][] winChanceEachSpace;
    protected int token;
    protected double[][][] heuristic;
    protected double[][][] winPercentage;
    protected double[][][] moveFrequency;
    protected double[][][] potWinsEverySpace;
    protected int gameCount = 0;
    protected int levelsToSearch = 0;
    protected int nodeCount = 0;
    
    public GameAI(int token, int levels, int nodecount){
        this.token = token;
        winPercentage = new double[4][4][4];
        moveFrequency = new double[4][4][4];
        for(int level = 0; level < winPercentage.length; level++){
            for(int row = 0; row < winPercentage[0].length; row++){
                for(int col = 0; col < winPercentage[0][0].length; col++){
                    winPercentage[level][row][col] = 0;
                }
            }
        }
        potWinsEverySpace = TicTacToe.potWinsEverySpace;
        levelsToSearch = levels;
        nodeCount = nodecount;
    }
    /*
    Pretty simple compared to the search function as it does most of the heavy
    lifting. Checks to see if there is a move it can do right now to win or
    block the opponent's imminent victory next turn. If no move is found it will
    call the search function to find the best move and use it.
    */
    public void run(){
        Coordinates move = checkWin();
        if(move != null){
            TicTacToe.board[move.getLevel()][move.getRow()][move.getColumn()] = token;
            TicTacToe.available[move.getLevel()][move.getRow()][move.getColumn()] = false;
            return;
        }
        move = checkOpponentWin();
        if(move != null){
            TicTacToe.board[move.getLevel()][move.getRow()][move.getColumn()] = token;
            TicTacToe.available[move.getLevel()][move.getRow()][move.getColumn()] = false;
            return;
        }
        int[][][] boardClone = TicTacToe.board.clone();
        boolean[][][] availableClone = TicTacToe.available.clone();
        move = search(token, levelsToSearch, token, null);
        TicTacToe.board = boardClone;
        TicTacToe.available = availableClone;
        TicTacToe.board[move.level][move.row][move.column] = token;
        TicTacToe.available[move.level][move.row][move.column] = false;
    }
    
    public Coordinates search(int curToken, int searchLevel, int originalToken, Coordinates parent){
        //last move in the search and it's the AI's opponent
        if(searchLevel <= 0 && curToken != originalToken){
            Coordinates move = checkWin();
            if(move != null){
                move.adjustHeuristic(-1000);
                move.setParent(parent);
                return move;
            }
        }
        //Setting what the opponent's token is
        token = curToken;
        int oppToken = getOppToken();
        
        
        //Check to see if the current player has a winning move
        Coordinates move = checkWin();
        int level,row,col;
        
        if(move != null){
            move.setParent(parent);
            if(curToken == originalToken){
                move.adjustHeuristic(1000);
            }
            else{
                move.adjustHeuristic(-1000);
            }
            return move;
        }
        //Need to check to see if the opponent wins, method handles both players
        //and performs recursive calls
        Coordinates oppPotentialWin = ifOpponentWins(originalToken,curToken,searchLevel,parent);
        if(oppPotentialWin != null){
            return oppPotentialWin;
        }
        
        if(TicTacToe.isDraw()){
            return new Coordinates(0,0,0, parent, 1);
        }
        
        heuristic = new double[4][4][4];
        for(level = 0; level < TicTacToe.board.length; level++){
            for(row = 0; row < TicTacToe.board[0].length; row++){
                for(col = 0; col < TicTacToe.board[0][0].length; col++){
                    heuristic[level][row][col] = potWinsEverySpace[level][row][col];
                }
            }
        }
        
        checkPlacement();
        ArrayList<Coordinates> coordinateList = moveListGeneration(originalToken, curToken, parent);
        
        if(originalToken != curToken){
            Coordinates tmp = coordinateList.get(0);
            int l = tmp.getLevel();
            int r = tmp.getRow();
            int c = tmp.getColumn();
            searchLevel--;
            TicTacToe.board[l][r][c] = token;
            TicTacToe.available[l][r][c] = false;
            tmp = search(oppToken, searchLevel, originalToken, parent);
            TicTacToe.board[l][r][c] = 0;
            TicTacToe.available[l][r][c] = true;
            return tmp;
        }
        if(searchLevel == 0){
            double maxHeuristic = -1000000;
            Coordinates maxCoord = null;
            for(Coordinates coordinate : coordinateList){
                if(heuristic[coordinate.level][coordinate.row][coordinate.column] > maxHeuristic){
                    maxHeuristic = heuristic[coordinate.level][coordinate.row][coordinate.column];
                    maxCoord = coordinate;
                }
            }
            return maxCoord;
        }
        ArrayList<Coordinates> returnList = new ArrayList();
        searchLevel--;
        for(Coordinates coordinate : coordinateList){
            TicTacToe.board[coordinate.level][coordinate.row][coordinate.column] = token;
            TicTacToe.available[coordinate.level][coordinate.row][coordinate.column] = false;
            if(searchLevel >= 1){
                returnList.add(search(oppToken, searchLevel, originalToken, coordinate));
            }
            TicTacToe.board[coordinate.level][coordinate.row][coordinate.column] = 0;
            TicTacToe.available[coordinate.level][coordinate.row][coordinate.column] = true;
        }
        double maxHeuristic = -1000000;
        Coordinates maxCoord = null;
        for(int i = 0; i < returnList.size(); i++){
            Coordinates coordinate = returnList.get(i);
            if(coordinate.parent == null){
                System.out.println("Missing parent for coordinate");
                System.exit(0);
            }
            for(Coordinates coord : coordinateList){
                if(coordinate.parent.level == coord.level && coordinate.parent.row == coord.row && coordinate.parent.column == coord.column){
                    coord.heuristic += coordinate.heuristic;
                    break;
                }
            }
        }
        for(Coordinates maxC : coordinateList){
            if(maxC.heuristic > maxHeuristic){
                maxHeuristic = maxC.heuristic;
                maxCoord = maxC;
            }
        }
        if(maxCoord == null){
            System.out.println("Missing best move selection");
            System.exit(0);
        }
        token = curToken;
        return maxCoord;
        //remove randomness here
//        if(coordinateList.size() > 1){
//            int length = coordinateList.size();
//            Random random = new Random();
//            int chosen = random.nextInt(length);
//            Coordinates chosenCoordinate = coordinateList.remove(chosen);
//            l = chosenCoordinate.level;
//            r = chosenCoordinate.row;
//            c = chosenCoordinate.column;
//        }
//        TicTacToe.board[l][r][c] = token;
//        TicTacToe.available[l][r][c] = false;
    }
    
    private Coordinates ifOpponentWins(int originalToken, int curToken, int searchLevel, Coordinates parent){
        Coordinates move = checkOpponentWin();
        int level, row, col;
        int oppToken = getOppToken();
        if(move != null){
           
            level = move.getLevel();
            row = move.getRow();
            col = move.getColumn();
            if(searchLevel != 0){
                searchLevel--;
            }
            TicTacToe.board[level][row][col] = token;
            TicTacToe.available[level][row][col] = false;
            Coordinates tmp;
            if(curToken == originalToken){
                    Coordinates currentCoor = new Coordinates(level,row,col,parent,0);
                    tmp = search(oppToken,searchLevel, originalToken, currentCoor);
                    
                    currentCoor.adjustHeuristic(potWinsEverySpace[level][row][col]);
                    currentCoor.adjustHeuristic(checkPotentialPaths(level,row,col));
                    currentCoor.adjustHeuristic(4 * winPercentage[level][row][col]);
                    currentCoor.adjustHeuristic(tmp.heuristic);
                    
                    TicTacToe.board[level][row][col] = 0;
                    TicTacToe.available[level][row][col] = true;
                    return currentCoor;
            }
            else{
                tmp = search(oppToken,searchLevel, originalToken, parent);
                TicTacToe.board[level][row][col] = 0;
                TicTacToe.available[level][row][col] = true;
                return tmp;
            }
            
        }
        return null;
    }

    
    private ArrayList<Coordinates> moveListGeneration(int originalToken, int curToken, Coordinates parent){
        double minMax = 1000000;
        double max = 0;
        ArrayList<Coordinates> coordinateList = new ArrayList();
        Coordinates oppMove = null;
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    if(TicTacToe.board[level][row][col] == 0){
                        heuristic[level][row][col] += checkPotentialPaths(level,row,col);
                        //potWinsEverySpace = potWinsClone.clone();
                        heuristic[level][row][col] += 4 * winPercentage[level][row][col];
                        if(coordinateList.size() < nodeCount && originalToken == curToken && heuristic[level][row][col] >= 0){
                            if(heuristic[level][row][col] < minMax){
                                minMax = heuristic[level][row][col];
                            }
                            coordinateList.add(new Coordinates(level,row,col, parent, heuristic[level][row][col]));
                        }
                        else if(heuristic[level][row][col] > minMax && originalToken == curToken){
                            double newMinMax = heuristic[level][row][col];
                            boolean flag = false;
                            for(int i = 0; i < coordinateList.size(); i++){
                                Coordinates tmp = coordinateList.get(i);
                                double h = heuristic[tmp.level][tmp.row][tmp.column];
                                if(h == minMax && !flag){
                                    coordinateList.remove(i);
                                    flag = true;
                                }
                                else if(h < newMinMax){
                                    newMinMax = h;
                                }
                            }
                            minMax = newMinMax;
                            coordinateList.add(new Coordinates(level,row,col,parent, heuristic[level][row][col]));
                        }
                        else if(originalToken != curToken){
                            if(heuristic[level][row][col] > max ){
                                max = heuristic[level][row][col];
                                oppMove = new Coordinates(level,row,col,parent, max);
                            }
                        }
                    }
                }
            }
        }
        if(oppMove != null){
            coordinateList.clear();
            coordinateList.add(oppMove);
            return coordinateList;
        }
        return coordinateList;
    }
    private int getOppToken(){
        int oppToken;
        if(token == 1){
            oppToken = 2;
        }
        else{
            oppToken = 1;
        }
        return oppToken;
    }
    private void checkPlacement(){
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    if( TicTacToe.available[level][row][col] == false){
                        heuristic[level][row][col] = -100;
                    }
                }
            }
        }
    }
    
    private Coordinates checkOpponentWin(){
        int oppToken = getOppToken();
        Coordinates coordinate;
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    if(TicTacToe.board[level][row][col] == 0){
                        TicTacToe.board[level][row][col] = oppToken;
                        if(TicTacToe.checkWin(oppToken)){
                            coordinate = new Coordinates();
                            coordinate.setLevel(level);
                            coordinate.setRow(row);
                            coordinate.setColumn(col);
                            TicTacToe.board[level][row][col] = 0;
                            return coordinate;
                        }
                        TicTacToe.board[level][row][col] = 0;
                    }
                }
            }
        }
        return null;
    }
    
    private Coordinates checkWin(){
        Coordinates coordinate;
        
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    if(TicTacToe.board[level][row][col] == 0){
                        TicTacToe.board[level][row][col] = token;
                        if(TicTacToe.checkWin(token)){
                            coordinate = new Coordinates();
                            coordinate.setLevel(level);
                            coordinate.setRow(row);
                            coordinate.setColumn(col);
                            TicTacToe.board[level][row][col] = 0;
                            return coordinate;
                        }
                        TicTacToe.board[level][row][col] = 0;
                    }
                }
            }
        }
        return null;
    }
    
    private int checkPotentialPaths(int level, int row, int col){
        
        int total = 0;
        total += check2DHorizontal(level, row, col);
        total += check2DVertical(level, row, col);
        total += check3DVertical(level, row, col);
        if(row == col || row == (TicTacToe.board.length - (col + 1) )){
            total += check2DDiagonal(level,row,col);
        }
        if(level == row || level == (TicTacToe.board.length - (row + 1) )){
            total += check3DDiagonalCol(level,row,col);
        }
        if(level == col || level == (TicTacToe.board.length - (col + 1) )){
            total += check3DDiagonalRow(level,row,col);
        }
        if(level == col && level == row || level == (TicTacToe.board.length - (col + 1) ) && level == row){
            total += check3DDiagonalCross(level,row,col);
        }
        if(level == (TicTacToe.board.length - (row + 1) ) && level == col || level == (TicTacToe.board.length - (col + 1) ) && level == (TicTacToe.board.length - (row + 1) )){
            total += check3DDiagonalCross(level,row,col);
        }
        return total;
    }
    
    private int check2DHorizontal(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        
        oppToken = getOppToken();
        for(int col = 0; col < TicTacToe.board[0][0].length; col++){
               if(TicTacToe.board[l][r][col] == token){
                   num++;
                   havePiece = true;
               }
               else if(TicTacToe.board[l][r][col] == oppToken){
                   if(havePiece){
                       heuristic[l][r][c]--;
                       return 0;
                   }
                   num++;
                   oppPiece=true;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check2DVertical(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        for(int row = 0; row < TicTacToe.board[0][0].length; row++){
               if(TicTacToe.board[l][row][c] == token){
                   num++;
                   havePiece = true;
               }
               else if(TicTacToe.board[l][row][c] == oppToken){
                   if(havePiece){
                       heuristic[l][r][c]--;
                       return 0;
                   }
                   oppPiece = true;
                   num++;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check2DDiagonal(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        if(r == c){
            int row = 0;
            int col = 0;
               while(row < TicTacToe.board.length){
                   if(TicTacToe.board[l][row][col] == token){
                       num++;
                       havePiece =true;
                   }
                   else if(TicTacToe.board[l][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                       oppPiece = true;
                        num++;
                   }
                   row++; 
                   col++;
               }
        }
        else{
            int row = 0;
            int col = TicTacToe.board[0].length - 1;
               while(row < TicTacToe.board.length){
                   if(TicTacToe.board[l][row][col] == token){
                       num++;
                       havePiece = true;
                   }
                   else if(TicTacToe.board[l][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                       oppPiece = true;
                        num++;
                   }
                   row++; 
                   col--;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check3DDiagonalCol(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        if(l == r){
            int level = 0;
            int row = 0;
               while(row < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][c] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][c] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   row++; 
                   level++;
               }
        }
        else{
            int level = 0;
            int row = TicTacToe.board[0].length-1;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][c] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][c] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   row--; 
                   level++;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check3DDiagonalRow(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        if(l == c){
            int level = 0;
            int col = 0;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][r][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][r][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   col++; 
                   level++;
               }
        }
        else{
            int level = 0;
            int col = TicTacToe.board[0].length - 1;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][r][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][r][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   col--; 
                   level++;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check3DDiagonalCross(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        if(l == c && l == r){
            int level = 0;
            int col = 0;
            int row = 0;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                       oppPiece = true;
                        num++;
                   }
                   col++; 
                   level++;
                   row++;
               }
        }
        else if(l == r && l == (TicTacToe.board.length - (c + 1) )) {
            int level = 0;
            int col = TicTacToe.board[0].length - 1;
            int row = 0;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                       oppPiece = true;
                        num++;
                   }
                   col--; 
                   level++;
                   row++;
               }
        }
        else if(l == c && l == (TicTacToe.board.length - (r + 1) )) {
            int level = 0;
            int col = 0;
            int row = TicTacToe.board[0].length - 1;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   col++; 
                   level++;
                   row--;
               }
        }
        else{
            int level = 0;
            int col = TicTacToe.board[0].length - 1;
            int row = TicTacToe.board[0].length - 1;
               while(level < TicTacToe.board.length){
                   if(TicTacToe.board[level][row][col] == token){
                       num++;
                       havePiece=true;
                   }
                   else if(TicTacToe.board[level][row][col] == oppToken){
                       if(havePiece){
                           heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                   }
                   col--; 
                   level++;
                   row--;
               }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    private int check3DVertical(int l, int r, int c){
        int oppToken = 0;
        int num = 0;
        boolean havePiece = false;
        boolean oppPiece = false;
        oppToken = getOppToken();
        for(int level = 0; level < TicTacToe.board[0][0].length; level++){
               if(TicTacToe.board[level][r][c] == token){
                   num++;
                   havePiece=true;
               }
               else if(TicTacToe.board[level][r][c] == oppToken){
                   if(havePiece){
                       heuristic[l][r][c]--;
                            return 0;
                        }
                        num++;
                        oppPiece = true;
                  }
        }
        if(oppPiece){
            heuristic[l][r][c]--;
            return num;
        }
        return num + 1;
    }
    public void update(int t){
        gameCount++;
        potWinsEverySpace = TicTacToe.potWinsEverySpace;
        for(int level = 0; level < TicTacToe.board.length; level++){
            for(int row = 0; row < TicTacToe.board[0].length; row++){
                for(int col = 0; col < TicTacToe.board[0][0].length; col++){
                    
                    if(TicTacToe.board[level][row][col] == t){
                        moveFrequency[level][row][col]++;
                        winPercentage[level][row][col] = moveFrequency[level][row][col] / gameCount;
                    }
                    else{
                        winPercentage[level][row][col] = moveFrequency[level][row][col] / gameCount;
                    }
                }
            }
        }
    }
    public void printPercentage(){
        for(int level = 0; level < winPercentage.length; level++){
            System.out.println("Level: " + level);
            for(int row = 0; row < winPercentage[0].length; row++){
                for(int col = 0; col < winPercentage[0][0].length; col++){
                    System.out.print(winPercentage[level][row][col] + "  ");
                }
                System.out.println("");
            }
            System.out.println("");
            System.out.println("");
        }
    }
    public void setWinPercentage(double[][][] x){
        winPercentage = x;
    }
}