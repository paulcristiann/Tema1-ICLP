public class Livrare {

    private Manager manager;
    private int delivery_time;
    private String command_name;

    public Livrare(int i, Manager manager, String nume_comanda) {
        delivery_time = i;
        this.manager = manager;
        this.command_name = nume_comanda;
    }

    public String getCommand_name() {
        return command_name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(int delivery_time) {
        this.delivery_time = delivery_time;
    }

}
