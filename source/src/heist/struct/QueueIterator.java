package heist.struct;

import java.util.Iterator;

class QueueIterator<T> implements Iterator
{
    private Node<T> node;

    public QueueIterator(Node<T> node, int size)
    {
        this.node = node;
    }

    @Override
    public boolean hasNext()
    {
        return node != null;
    }

    @Override
    public T next()
    {
       T value = node.e;
       node = node.next;
       return value; 
    }
}