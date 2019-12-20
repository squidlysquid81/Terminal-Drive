package task6_22;
//@author Mr. Murray
//@editor edwardsdw
public interface Constants {
    
    public static final int ROW = 30;  //size of the array
    public static final int COL = 50;  //size of the array
    
    public static final int RES = 12;  //the size of the resolution.
    public static final int MARGIN = 35;
    
    public static final int WIDE = 2*MARGIN + COL*RES;  //the size of the window
    public static final int HIGH = 2*MARGIN + ROW*RES;  //the size of the window
    
    public static final int SEED = 30; //the percentage of cells that have initial life
    
    public static final int ROCKSEED = 10; //ADDED percentage of cells that are rocks.
           
}