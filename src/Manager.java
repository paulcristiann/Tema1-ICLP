import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Manager extends Thread {

    private final BlockingQueue<String> order_queue;
    private final ThreadPoolExecutor blatari_pool;
    private final ThreadPoolExecutor cremari_pool;
    private final ThreadPoolExecutor decorationari_pool;
    private final CoadaLivrare delivery_queue;

    public Manager(BlockingQueue<String> order_queue, ThreadPoolExecutor blatari_pool, ThreadPoolExecutor cremari_pool, ThreadPoolExecutor decorationari_pool, CoadaLivrare delivery_queue, int j) {
        this.order_queue = order_queue;
        this.blatari_pool = blatari_pool;
        this.cremari_pool = cremari_pool;
        this.decorationari_pool = decorationari_pool;
        this.delivery_queue = delivery_queue;
        setName("Manager " + j);
    }

    public void run() {
        try {

            while (true) {

                String task = order_queue.take();

                //Split task by duration
                String[] objects = task.split(" ");

                String nume_comanda = objects[0];
                int blat_time = Integer.parseInt(objects[1]);
                int crema_time = Integer.parseInt(objects[2]);
                int decoratiuni_time = Integer.parseInt(objects[3]);
                int delivery_time = Integer.parseInt(objects[4]);

                System.err.println(Thread.currentThread().getName() + " was assigned command for " + nume_comanda);

                Cofetar blat = new Cofetar(blat_time);
                Future<?> blat_task = blatari_pool.submit(blat);

                Cofetar crema = new Cofetar(crema_time);
                Future<?> crema_task = cremari_pool.submit(crema);

                Cofetar decoratiuni = new Cofetar(decoratiuni_time);
                Future<?> decoratiuni_task = decorationari_pool.submit(decoratiuni);

                blat_task.get();
                crema_task.get();
                decoratiuni_task.get();

                //Send them to delivery
                Livrare delivery_item = new Livrare(delivery_time, this, nume_comanda);
                delivery_queue.put(delivery_item);
                synchronized (this) {
                    this.wait();
                }

                System.err.println(Thread.currentThread().getName() + " handed command for " + nume_comanda + " to delivery");

            }

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

}
