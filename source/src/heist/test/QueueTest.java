package heist.test;

import heist.queue.Queue;
import java.util.Iterator;

class QueueTest
{
    private static Queue<String> queue;
    
    public static void main(String[] args)
    {
        queue = new Queue<>();
        queue.push("a");
        queue.push("b");
        queue.push("c");
        queue.push("d");
        queue.push("e");
        
        queue.remove("a");
        queue.remove("e");
        queue.remove("b");
        queue.remove("h");
        
        printQueue();
        
    }
    
    private static void printQueue()
    {
        Iterator<String> it = queue.iterator();
        while(it.hasNext())
        {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}
