package basics;

public class StaticBlocks
{
    public static StaticBlocks t1 = new StaticBlocks();
    public static StaticBlocks t2 = new StaticBlocks();
    {
        System.out.println("prue init block");
    }
    static
    {
        System.out.println("static init block");
    }
    public static void main(String[] args)
    {
    	StaticBlocks t = new StaticBlocks();
    }
}
