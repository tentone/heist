package heist.struct;

import java.util.Iterator;

public class Queue<T> 
{
    private Node<T> first = null, last = null;
    private int size = 0;
    
    public void push(T e)
    {
        Node<T> node = new Node<>();
        node.e = e;
        node.next = null;

        if(isEmpty())
        {
            first = node;
        }
        else
        {
            last.next = node;
        }
        
        last = node;
        size++;
    }

    public void pop() 
    {
        assert !isEmpty();
        
        size--;
        first = first.next;
        if(first == null)
        {
            last = null;
        }
    }

    public T peek() 
    {
        assert !isEmpty();
        
        return first.e;
    }

    public int size() 
    {
        return size;
    }

    public boolean isEmpty() 
    {
        return size() == 0;
    }
    
    public Iterator<T> iterator()
    {
        return new QueueIterator<T>(first, size);
    }
}