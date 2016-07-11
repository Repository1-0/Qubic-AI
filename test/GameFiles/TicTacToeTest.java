
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

//    /**
//     * Test of check3DDiagonal method, of class TicTacToe.
//     */
//    @Test
//    public void testCheck3DDiagonal() {
//        System.out.println("check3DDiagonal");
//        int token = 0;
//        boolean expResult = false;
//        boolean result = TicTacToe.check3DDiagonal(token);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
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
