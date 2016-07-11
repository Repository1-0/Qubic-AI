
package GameFiles;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TicTacToeTest {

    /**
     * Test of checkLevel method, of class TicTacToe.
     */
    @Test
    public void testCheckLevel() {
        System.out.println("checkLevel");
        TicTacToe game = new TicTacToe();
        int token = 1;
        boolean expResult = true;
        boolean result = false;
        int[][][] board = TicTacToe.board;
        
        //Tests all horizontal victories across a level
        for(int level = 0; level < board.length; level++){
            for(int row = 0; row < board[0].length; row++){
                int column = 0;
                while(column < board[0][0].length){
                    board[level][row][column] = token;
                    column++;
                }
                result = TicTacToe.checkLevel(token);
                assertEquals(expResult, result);
                while(column > 0){
                    column--;
                    board[level][row][column] = 0;
                }
            }
        }
        //Tests all vertical(2D) victories across a level
        for(int level = 0; level < board.length; level++){
            for(int column = 0; column < board[0][0].length; column++){
                int row = 0;
                while(row < board[0][0].length){
                    board[level][row][column] = token;
                    row++;
                }
                result = TicTacToe.checkLevel(token);
                assertEquals(expResult, result);
                while(row > 0){
                    row--;
                    board[level][row][column] = 0;
                }
            }
        }
    }
    /**
     * Test of checkLevel method, of class TicTacToe. Testing for Diagonals
     */
    @Test
    public void testCheckLevel2DDiagonals() {
        System.out.println("checkLevel2DDiagonals");
        TicTacToe game = new TicTacToe();
        int token = 1;
        boolean expResult = true;
        boolean result = false;
        int[][][] board = TicTacToe.board;
        //Tests diagonal path on level starting in upper left corner going to
        //the bottom right corner
        for(int level = 0; level < board.length; level++){
            int row = 0;
            int column = 0;
            while(row < board[0].length && column < board[0][0].length){
                board[level][row][column] = token;
                row++;
                column++;
            }
            result = TicTacToe.checkLevel(token);
            assertEquals(expResult, result);
            while(row > 0 && column > 0){
                row--;
                column--;
                board[level][row][column] = 0;
            }
        }
        //Tests diagonal path on level starting in upper right corner going to
        //the bottom left corner
        for(int level = 0; level < board.length; level++){
            int row = 0;
            int column = board[0][0].length;
            while(row < board[0].length && column > 0){
                column--;
                board[level][row][column] = token;
                row++;
            }
            result = TicTacToe.checkLevel(token);
            assertEquals(expResult, result);
            while(row > 0 && column < board[0][0].length){
                row--;
                board[level][row][column] = 0;
                column++;
            }
        }
    }
    /**
     * Test of checkVertical method, of class TicTacToe.
     */
    @Test
    public void testCheckVertical() {
        System.out.println("checkVertical");
        TicTacToe game = new TicTacToe();
        int token = 1;
        boolean expResult = true;
        boolean result = false;
        int[][][] board = TicTacToe.board;
        
        //Tests all vertical(3D) victory paths
        for(int row = 0; row < board[0].length; row++){
            for(int column = 0; column < board[0][0].length; column++){
                int level = 0;
                while(level < board[0][0].length){
                    board[level][row][column] = token;
                    level++;
                }
                result = TicTacToe.checkVertical(token);
                assertEquals(expResult, result);
                while(level > 0){
                    level--;
                    board[level][row][column] = 0;
                }
            }
        }
        
        
    }

    /**
     * Test of check3DDiagonal method, of class TicTacToe.
     */
    @Test
    public void testCheck3DDiagonal() {
        System.out.println("check3DDiagonal");
        TicTacToe game = new TicTacToe();
        int token = 1;
        boolean expResult = true;
        boolean result = false;
        int[][][] board = TicTacToe.board;
        for(int row = 0; row < board.length; row++){
            //Starts on left side working up and to the right.
            int level = 0, column = 0;
            while(level < board.length && column < board[0][0].length){
                board[level][row][column] = token;
                level++; 
                column++;
            }
            result = TicTacToe.check3DDiagonal(token);
            assertEquals(expResult, result);
            while(level > 0 && column > 0){
                level--; 
                column--;
                board[level][row][column] = 0;
                
            }
            //starts on right side working up and to the left
            level = 0; 
            column = board[0][0].length;
            while(level < board.length && column > 0){
                column--;
                board[level][row][column] = token;
                level++; 
            }
            result = TicTacToe.check3DDiagonal(token);
            assertEquals(expResult, result);
            while(level > 0 && column < board[0][0].length){
                level--; 
                board[level][row][column] = 0;
                column++;
            }
        }
    }

//    /**
//     * Test of isDraw method, of class TicTacToe.
//     */
//    @Test
//    public void testIsDraw() {
//        System.out.println("isDraw");
//        boolean expResult = false;
//        boolean result = TicTacToe.isDraw();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

    
}
