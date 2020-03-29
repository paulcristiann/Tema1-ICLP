public class Cofetar implements Runnable{

    private int time_to_work;

    public Cofetar(int time_to_work){
        this.time_to_work = time_to_work;
    }

    @Override
    public void run() {
        try {

            Thread.sleep(time_to_work);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
