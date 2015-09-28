/**
 * Sudoku solver algorithms. 
 * Solves the sudoku using some kind of brute-force/backtracking.
 * Sudoku values are stored in an multidimensional array.
 * We assing 0 to indicate and empty box in sudoku.
 * 
 */
package sudoku;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ameljo
 */
public class Sudoku 
{


    public static void main(String[] args) 
    {
        Scanner reader = new Scanner(System.in);
        
        // array is the array where we store the sudoku values. Store is the array
        // where we store the values which came with the sudoku.
        int[][] array = new int[9][9];
        // We store them in a seperate array to make sure that we don't change them.
        int[][] store = new int[9][9];
        
        //storeX stores the row, storeY stores the column. We will use these to go back.
        ArrayList<Integer> storeX= new ArrayList<Integer>();
        ArrayList<Integer> storeY= new ArrayList<Integer>();
        
        //We use an index to record every step we make so that we can go back.
        int index =0;
        //We use this to store the values where we should start if we go back.
        ArrayList<Integer> startPoint = new ArrayList<Integer>();
        
        // Variables used in loops
        int startRow = 0;
        int startColumn =0;
        int start =1;
        
        //Initialise both arrays.
        setValue(array);
        setValue(store);
        
        /*
         * These are values that i used to test the algoruthms while I was writing it
         * It's faster then entering them again and again.
        array[0][0]=5; array[0][1]=3;array[0][4]=7; array[1][0]=6; array[1][3]=1;array[1][4]=9;array[1][5]=5;
        array[2][1]=9; array[2][2]=8;array[2][7]=6; array[3][0]=8; array[3][4]=6;array[3][8]=3;array[4][0]=4;
        array[4][3]=8; array[4][5]=3;array[4][8]=1; array[5][0]=7; array[5][4]=2;array[5][8]=6;array[6][1]=6;
        array[6][6]=2; array[6][7]=8;array[7][3]=4; array[7][4]=1; array[7][5]=9;array[7][8]=5;array[8][4]=8;
        array[8][7]=7; array[8][8]=9;
        store[0][0]=5; store[0][1]=3;store[0][4]=7; store[1][0]=6; store[1][3]=1;store[1][4]=9;store[1][5]=5;
        store[2][1]=9; store[2][2]=8;store[2][7]=6; store[3][0]=8; store[3][4]=6;store[3][8]=3;store[4][0]=4;
        store[4][3]=8; store[4][5]=3;store[4][8]=1; store[5][0]=7; store[5][4]=2;store[5][8]=6;store[6][1]=6;
        store[6][6]=2; store[6][7]=8;store[7][3]=4; store[7][4]=1; store[7][5]=9;store[7][8]=5;store[8][4]=8;
        store[8][7]=7; store[8][8]=9;
        */
        //enterValue(array, store, reader);
        
        //First we print the sudoku unsolved.
        print(array);
        System.out.println("Solving...");
        
        //We loop until the sudoku is solved.
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
        
        // Prints the solved Sudoku.
        print (array);
    }
    
    // Method to print the sudoku array.
    public static void print(int[][] array)
    {
        for(int i =0; i < 9; i++)
        {
            for(int  j =0; j< 9; j++) 
            {
                System.out.print(array[i][j]);
                if(j==2 || j==5)
                    System.out.print(" ");
            }
            System.out.println("");
            if(i == 2 || i==5)
                System.out.println("");
        }
        System.out.println("");
    }
    
    //Use this method to initialize all elements to empty in the sudoku box
    public static void setValue(int[][] array)
    {
        for(int i=0; i<9;i++)
            for(int j= 0; j< 9; j++)
                array[i][j] = 0;
    }
    
    
    //This method check if a value beetwen 1-9 is already in a given row of sudoku.
    //Check sudoku rules.
    public static boolean checkRow(int row, int value, int[][] array)
    {
        for(int i=0; i<9;i++)
        {
            if(array[row][i] == value)
                return true;
        }
        return false;
    }
    
    //The same thing as checkRow but this checks the in a given Column.
    public static boolean checkColumn(int column, int value, int[][] array)
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
    public static boolean checkQuadrat(int column, int row, int value, int[][]array)
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
    public static boolean completed(int[][] array)
    {
        for(int i =0; i< 9;i++)
            for(int j=0; j<9; j++)
                if(array[i][j]==0)
                    return false;
        return true;
    }
    
    //Check if the value we want to enter in the given row and column is already a 
    //value which came with the sudoku.
    public static boolean checkValue(int[][] store, int row, int column)
    {
            if(store[row][column] != 0)
                return true;
            return false;
    }
    
    
    //Used this method to enter the values which came with sudoku.
    public static void enterValue(int[][] array, int[][] store, Scanner reader)
    {
        int row;
        int value=1;
        int column;
        while(value != 0)
        {
            System.out.print("Enter row: ");
            row = Integer.parseInt(reader.nextLine());
            System.out.print("Enter column: ");
            column = Integer.parseInt(reader.nextLine());
            System.out.print("Enter value: ");
            value = Integer.parseInt(reader.nextLine());
            System.out.println("");
            array[row-1][column-1] =value;
            store[row-1][column-1]= value;
        }
    }
}
