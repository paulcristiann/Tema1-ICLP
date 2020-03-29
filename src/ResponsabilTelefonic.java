import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

public class ResponsabilTelefonic {

    static ThreadPoolExecutor blatari_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    static ThreadPoolExecutor cremari_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    static ThreadPoolExecutor decorationari_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    static private BlockingQueue<String> order_queue = new LinkedBlockingDeque<>(4);
    static private CoadaLivrare delivery_queue = new CoadaLivrare(5);

    public static void main(String[] args) throws InterruptedException {

        System.err.println("===Day Started===");
        System.err.println("Ready to receive orders");

        Scanner in = new Scanner(System.in);
        String order = in.nextLine();

        //Start Manager processes
        for (int j = 0; j < 6; j++) {
            new Manager(order_queue, blatari_pool, cremari_pool, decorationari_pool, delivery_queue, j + 1).start();
        }

        for (int j = 0; j < 5; j++) {
            new Livrator(delivery_queue, j + 1).start();
        }

        while (!order.equalsIgnoreCase("STOP")) {

            //System.out.println("Received order: " + order);

            //add in queue
            order_queue.put(order);

            order = in.nextLine();

        }

        in.close();
        System.err.println("===Day finished==");

    }

}
