import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoadaLivrare {

    private Queue<Livrare> queue = new LinkedList<Livrare>();
    private int capacity;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public CoadaLivrare(int capacity) {
        this.capacity = capacity;
    }

    public void put(Livrare element) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Livrare take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            Livrare item = queue.remove();
            notFull.signal();
            Manager manager = item.getManager();
            synchronized (manager) {
                item.getManager().notify();
            }
            return item;
        } finally {
            lock.unlock();
        }
    }
}