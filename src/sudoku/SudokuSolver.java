/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ameljo
 */
public class SudokuSolver
{
    private List<javax.swing.JTextField> sudokuArray;
    private int[][] array;
    private int[][] store;
    private final int last = 9;
    private ArrayList<Integer> storeX;
    private ArrayList<Integer> storeY;
    private ArrayList<Integer> startPoint;
    private int index;
    private int startColumn;
    private int start;
    private int startRow;
    
    public SudokuSolver( List<javax.swing.JTextField> sudokuArray)
    {
        this.sudokuArray = sudokuArray;
        this.array = new int[9][9];
        this.store = new int[9][9];
        this.storeX = new ArrayList<Integer>();
        this.storeY = new ArrayList<Integer>();
        this.startPoint = new ArrayList<Integer>();
        this.index = 0;
        this.start = 1;
        this.startColumn = 0;
        this.startRow = 0;
    }
    
    private void setValue()
    {
        int indx = 0;
        for (int i = 0; i < last; i++)
            for (int j = 0; j < last ; j++)
        {
            if ( this.sudokuArray.get(indx).getText().equals("") ||
                   this.sudokuArray.get(indx).getText() == null )
            {
                array[i][j] = 0;
                store [i][j] = 0;
                indx++;
            }
            else
            {
                array[i][j] = Integer.parseInt(sudokuArray.get(indx).getText());
                store [i][j] = Integer.parseInt(sudokuArray.get(indx).getText());
                indx++;
            }
        }
    }
    
    public void solve()
    {
        this.setValue();
        while(!completed(array))
        {
            outerloop:
            {
                // Loop for the rows
                for(int row =startRow; row< 9; row++)
                {
                    //Loop for the columns
                    for(int column= startColumn; column< 9; column++)
                    {
                        //In this loop we check if any value fits in the given box of the sudoku.
                        for(int value = start; value< 10; value++)
                        {
                            if(!checkColumn(column, value, array)&& !checkRow(row, value, array)&& !checkQuadrat(column, row, value, array) && !checkValue(store, row, column))
                            {
                                /*
                                If it fits. we put the value. We record the column and row
                                we add 1 to the startPoint so if we came back we use another value 
                                not the one we just stored.
                                We increment the index. We see that both column,row and startPoint 
                                have the same index.
                                */
                                array[row][column]=value;
                                storeX.add(column);
                                storeY.add(row);
                                startPoint.add(value);
                                index++;
                                break;
                            }
                        }
                        /*
                        If the value does not fit. Be break from both the 3 loop
                        because we know that one element will remain 0.
                        */
                        if(array[row][column]==0)
                                break outerloop;
                        
                        //Reset the start for the next column in the row.
                        start =1;
                    }
                    //Reset the column for the next row.
                    startColumn =0;
                }
            }
            
            // We check if the sudoku is completed.
            if(!completed(array)&& !storeY.isEmpty())
            {
                // If not we decrement the index but first make sure it stays positive.
                if(index-1 >=0)
                   index--;
                
                /*
                Decrementing the index mean we access the previos column and row.
                We get them, to these. And because we hit a dead end we know that the 
                value there is in the wrond place. So we start iterating for another 
                value but start from +1 of the value that was wrong so that we don't
                place the same value.
                */
                start = startPoint.get(index) +1;
                startColumn= storeX.get(index);
                startRow = storeY.get(index);
                array[startRow][startColumn]=0;
                
                // We remove the values that were wrong so that we make space for 
                // other steps.
                storeX.remove(index);
                storeY.remove(index);
                startPoint.remove(index);
            }
        }
        setText();
    }
    
    public void setText()
    {
        int ind = 0;
        for (int i = 0; i < last ; i++)
            for (int j = 0; j < last ; j++)
            {
                sudokuArray.get(ind).setText(array[i][j]+"");
                ind++;
            }
    }
    
     private boolean checkRow(int row, int value, int[][] array)
    {
        for(int i=0; i<9;i++)
        {
            if(array[row][i] == value)
                return true;
        }
        return false;
    }
    
    //The same thing as checkRow but this checks the in a given Column.
    private boolean checkColumn(int column, int value, int[][] array)
    {
        for(int i=0; i < 9; i++)
        {
            if(array[i][column]== value)
                return true;
        }
        return false;
    }
    
    
    /**
    *This method checks if a value is already in a 3 by 3 grid of sudoku.
    * Again check sudoku rules.
    * Its subtracks 2 to the row and column to find out in which is the given grid.
    * Return true if the value is found.
    */
    private boolean checkQuadrat(int column, int row, int value, int[][]array)
    {
        int endRow=0;
        int endColumn=0;
        if(row -2 <=0)
            endRow =0;
        else if(row-2 <=3)
            endRow=3;
        else if(row-2 <=6)
            endRow =6;
        
        if(column -2 <=0)
            endColumn =0;
        else if(column-2 <=3)
            endColumn=3;
        else if(column-2 <=6)
            endColumn =6;
        for(int i = endRow; i < endRow+3;i++)
        {
            for(int j = endColumn; j < endColumn+3;j++)
                if(array[i][j]== value)
                    return true;
        }
        return false;
    }
    
    //Check if there is any empty box in sudoku. 
    //(If there is any element = 0 in the sudoku array.
    private boolean completed(int[][] array)
    {
        for(int i =0; i< 9;i++)
            for(int j=0; j<9; j++)
                if(array[i][j]==0)
                    return false;
        return true;
    }
    
    //Check if the value we want to enter in the given row and column is already a 
    //value which came with the sudoku.
    private boolean checkValue(int[][] store, int row, int column)
    {
            if(store[row][column] != 0)
                return true;
            return false;
    }
    
}
