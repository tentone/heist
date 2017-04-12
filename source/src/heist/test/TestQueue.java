package heist.test;

import heist.queue.ArrayQueue;
import heist.queue.Queue;
import heist.queue.iterator.Iterator;

public class TestQueue
{
    public static void main(String[] args)
    {
        Queue<Integer> queue = new ArrayQueue<>(10);
        
        queue.push(1);
        queue.push(2);
        queue.push(3);
        
        System.out.println(queue.toString());
        
        queue.pop();
        
        System.out.println(queue.toString());
        
        queue.pop();
        
        System.out.println(queue.toString());
        
        queue.push(4);
        
        System.out.println(queue.toString());

        queue.remove(4);
        
        System.out.println(queue.toString());
        
        queue.push(2);
        queue.push(1);
        
        System.out.println(queue.toString());
        
        queue.popPush();
        queue.popPush();
        
        System.out.println(queue.toString());
        
        queue.remove(2);
        
        System.out.println(queue.toString());
        
        queue.clear();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);
        queue.push(6);
        queue.push(7);
        queue.push(8);
        queue.push(9);
        queue.push(10);
        
        Iterator<Integer> it = queue.iterator();
        while(it.hasNext())
        {
            System.out.println(it.next());
        }
        
    }
}
