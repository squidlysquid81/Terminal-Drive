package task6_22;
//@author edwardsdw
public class Cell implements Constants {
    public boolean state;
    public boolean isRock;
    public Cell()  {
        int rand = (int)(Math.random()*100);
        if (rand < SEED)  {
            state = true;
            isRock = false;
        }
        else  {
            if (rand < SEED + ROCKSEED)  {
                state = true;
                isRock = true;
            }
            else  {
                state = false;
                isRock = false;
            }
        }
    }
    public static Cell copy(Cell old)  {
        Cell temp = new Cell();
        temp.state = old.state;
        return temp;
    }
}