public class Livrator extends Thread {

    private CoadaLivrare delivery_queue;

    public Livrator(CoadaLivrare delivery_queue, int j) {
        this.delivery_queue = delivery_queue;
        setName("Delivery man " + j);
    }

    @Override
    public void run() {

        try {

            while (true) {

                Livrare obj = delivery_queue.take();
                Thread.sleep(obj.getDelivery_time());
                System.out.println(Thread.currentThread().getName() + " delivered cake to " + obj.getCommand_name());

            }


        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
