/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        int[][] array = new int[9][9];
        int[][] store = new int[9][9];
        ArrayList<Integer> storeX= new ArrayList<Integer>();
        ArrayList<Integer> storeY= new ArrayList<Integer>();
        int index =0;
        ArrayList<Integer> startPoint = new ArrayList<Integer>();
        int startRow = 0;
        int startColumn =0;
        int start =1;
        setValue(array);
        setValue(store);
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
        //enterValue(array, store, reader);
        
        print(array);
        System.out.println("Solving...");
        while(!completed(array))
        {
            outerloop:
            {
                for(int row =startRow; row< 9; row++)
                {
                    for(int column= startColumn; column< 9; column++)
                    {
                        for(int value = start; value< 10; value++)
                        {
                            if(!checkColumn(column, value, array)&& !checkRow(row, value, array)&& !checkQuadrat(column, row, value, array) && !checkValue(store, row, column))
                            {
                                array[row][column]=value;
                                storeX.add(column);
                                storeY.add(row);
                                startPoint.add(value);
                                index++;
                                break;
                            }
                            print(array);
                        }
                        if(array[row][column]==0)
                                break outerloop;
                        start =1;
                    }
                    startColumn =0;
                }
            }
            if(!completed(array)&& !storeY.isEmpty())
            {
                if(index-1 >=0)
                   index--;
                start = startPoint.get(index) +1;
                startColumn= storeX.get(index);
                startRow = storeY.get(index);
                array[startRow][startColumn]=0;
                storeX.remove(index);
                storeY.remove(index);
                startPoint.remove(index);
            }
        }
        print (array);
    }
    
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
    
    public static void setValue(int[][] array)
    {
        for(int i=0; i<9;i++)
            for(int j= 0; j< 9; j++)
                array[i][j] = 0;
    }
    
    public static boolean checkRow(int row, int value, int[][] array)
    {
        for(int i=0; i<9;i++)
        {
            if(array[row][i] == value)
                return true;
        }
        return false;
    }
    
    public static boolean checkColumn(int column, int value, int[][] array)
    {
        for(int i=0; i < 9; i++)
        {
            if(array[i][column]== value)
                return true;
        }
        return false;
    }
    
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
    
    public static boolean completed(int[][] array)
    {
        for(int i =0; i< 9;i++)
            for(int j=0; j<9; j++)
                if(array[i][j]==0)
                    return false;
        return true;
    }
    
    public static boolean checkValue(int[][] store, int row, int column)
    {
            if(store[row][column] != 0)
                return true;
            return false;
    }
    
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
