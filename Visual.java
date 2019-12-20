package task6_22;
import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Graphics;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.Timer; 
//@author Mr. Murray
//@editor edwardsdw
 
public class Visual implements ActionListener, KeyListener  {
    public JFrame frame;        //REQUIRED! The outside shell of the window
    public DrawingPanel panel;  //REQUIRED! The interior window
    public Timer visualtime;    //REQUIRED! Runs/Refreshes the screen. 
    public int delaycount;      //Used to prevent errors during load&run    
    
    public final int MAX = 30;  //size of the array
    public final int RES = 15;  //the size of the resolution.
    public final int WIDE = 100 + MAX*RES;  //the size of the window
    public final int HIGH = 100 + MAX*RES;  //the size of the window
    public final int SEED = 30; //the percentage of cells that have initial life


    //all of your other variables...
    public Cell[][] actual;
    public Cell[][] working;
    public int selfDelay;

    
    public Visual()
    {
        frame = new JFrame("  Task 6.22 -- Conway's Game of Life 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(Cell.WIDE, Cell.HIGH)); 
        frame.getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
 
        //Initialize all global variables here:
//        actual = new boolean[MAX][MAX];
//        working = new boolean[MAX][MAX];
        Reset();

               
        visualtime = new Timer(20, this);     
        visualtime.start();
    } 


    public void Reset()    
    {        
        delaycount = 0;
        selfDelay = 0;
        actual = new Cell[Cell.ROW][Cell.COL];
        working = new Cell[Cell.ROW][Cell.COL];
        
        for(int r = 0; r < Cell.ROW; r++)
            for(int c = 0; c < Cell.COL; c++)  {
                actual[r][c] = new Cell();
                working[r][c] = new Cell();
            }
        
    }
    
    private boolean isEdge(int ind1, int ind2)  {
        boolean isEdge = false;
        if (ind1 == 0 || ind1 == Cell.ROW - 1)
            isEdge = true;
        if (ind2 == 0 || ind2 == Cell.COL - 1)
            isEdge = true;
        return isEdge;
    }
    private int adjLife(int ind1, int ind2, Cell[][] board)  {
        int adjLife = 0;
        if (isEdge(ind1, ind2))  {
            if (ind1 == 0)
                if (ind2 == 0)  {
                    for (int x = 0; x < 2; x++)
                        for (int y = 0; y < 2; y++)
                            if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                adjLife++;
                }
                else  {
                    if (ind2 == Cell.COL - 1)  {
                        for (int x = 0; x < 2; x++)
                            for (int y = -1; y < 1; y++)
                                if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                    adjLife++;
                    }
                    else  {
                        for (int x = 0; x < 2; x++)
                            for (int y = -1; y < 2; y++)
                                if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                    adjLife++;
                    }
                }
            else  {
                if (ind1 == Cell.ROW - 1)  {
                    if (ind2 == 0)  {
                        for (int x = -1; x < 1; x++)
                            for (int y = 0; y < 2; y++)
                                if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                    adjLife++;
                    }
                    else  {
                        if (ind2 == Cell.COL - 1)  {
                            for (int x = -1; x < 1; x++)
                                for (int y = -1; y < 1; y++)
                                    if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                        adjLife++;
                        }
                        else  {
                            for (int x = -1; x < 1; x++)
                                for (int y = -1; y < 2; y++)
                                    if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                        adjLife++;
                        }
                    }
                }
                else  {
                    if (ind2 == 0)  {
                        for (int x = -1; x < 2; x++)
                            for (int y = 0; y < 2; y++)
                                if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                    adjLife++;
                    }
                    else  {
                        for (int x = -1; x < 2; x++)
                            for (int y = -1; y < 1; y++)
                                if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                                    adjLife++;
                    }
                
                }
            }
        }
        else
            for (int x = -1; x < 2; x++)
                for (int y = -1; y < 2; y++)
                    if ((x != 0 || y != 0) && board[ind1 + x][ind2 + y].state)
                        adjLife++;
        return adjLife;
    }
    private void update()  {
        copy(actual, working);
        for (int x = 0; x < Cell.ROW; x++)
            for (int y = 0; y < Cell.COL; y++)
                if (actual[x][y].state)  {
                    if (adjLife(x, y, actual) < 2 || adjLife(x, y, actual) >= 4)
                        working[x][y].state = false;
                    else
                        working[x][y].state = true;
                }
                else  {
                    if (adjLife(x, y, actual) == 3)
                        working[x][y].state = true;
                    else
                        working[x][y].state = false;
                }
        save(actual, working);
    }
    private void printBoard(Cell[][] board)  {
        String printBoard = new String("");
        for (int x = 0; x < Cell.ROW; x++)  {
            for (int y = 0; y < Cell.COL; y++)  {
                if (board[x][y].state)
                    printBoard += "O";
                else
                    printBoard += " ";
                if (y != Cell.COL - 1)
                    printBoard += " ";
            }
            printBoard += "\n";
        }
        System.out.println(printBoard);
    }
    private void prepBoard(Cell[][] board)  { //No longer necessary
        //final int SEED = 30;
        for (int x = 0; x < Cell.ROW; x++)
            for (int y = 0; y < Cell.COL; y++)
                if ((int)(Math.random()*100) < Cell.SEED)
                    board[x][y].state = true;
                else
                    board[x][y].state = false;
    }
    private void copy(Cell[][] board, Cell[][] work)  {
        for (int x = 0; x < Cell.ROW; x++)
            for (int y = 0; y < Cell.COL; y++)
                work[x][y] = Cell.copy(board[x][y]);
    }
    private void save(Cell[][] board, Cell[][] work)  {
        for (int x = 0; x < Cell.ROW; x++)
            for (int y = 0; y < Cell.COL; y++)
                board[x][y] = Cell.copy(work[x][y]);
    }
        
    public void actionPerformed(ActionEvent e)
    {   
        if(delaycount < 50) delaycount++;   //keep this... it just delays the program while it loads
            
        selfDelay++;
        if (selfDelay > 5)  {
        
	//Your actual Game-Of-Life code goes here.
	//Replace the following code with the code inside your while(true) loop.
        //No System.out's or System.in's - you don't need those.
        
            
        //printBoard(actual);
            update();
        //printBoard(actual);

            selfDelay = 0;
        }
        
	//This code just randomizes cells... not what you want, but it gives you a picture.

//        working = new boolean[MAX][MAX];
//        
//        for(int r = 0; r < MAX; r++)
//            for(int c = 0; c < MAX; c++)
//            {   
//                if((int)(Math.random()*100) < SEED) 
//                    working[r][c] = !actual[r][c];
//                else
//                    working[r][c] = actual[r][c];
//
//
//                if((int)(Math.random()*100) < SEED)
//                    working[r][c] = false;
//            }
//        
//        for(int r = 0; r < MAX; r++)
//            for(int c = 0; c < MAX; c++)
//                actual[r][c] = working[r][c];   
        
        
        panel.repaint();  //keep this... it allows the window to draw itself...
    }
 
    public void keyPressed(KeyEvent e)  
    {            

        if(e.getKeyCode() == KeyEvent.VK_HOME)
            Reset();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
           System.exit(0); 
    }

    public void keyTyped(KeyEvent e) {} 
    public void keyReleased(KeyEvent e) {}
 
    private class DrawingPanel extends JPanel { 
        
        
        public void paintComponent(Graphics g)         
        {
            super.paintComponent(g);
            panel.setBackground(Color.BLACK);
          
            
            if(delaycount < 20) return;

            g.setColor(Color.GREEN);
            for(int r = 0; r < Cell.ROW; r++)
                for(int c = 0; c < Cell.COL; c++)
                {
                    if(actual[r][c].state)
                        g.fillRect(Cell.MARGIN + c*Cell.RES, 50 + r*Cell.RES, Cell.RES, Cell.RES);
                }
            
            g.setColor(new Color(100,100,100));
            for(int r = 0; r < Cell.ROW; r++)
                for(int c = 0; c < Cell.COL; c++)
                    g.drawRect(Cell.MARGIN + c*Cell.RES, 50 + r*Cell.RES, Cell.RES, Cell.RES);
        }
        
    }
}