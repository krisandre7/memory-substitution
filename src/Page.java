
// A page in memory
public class Page {
    private int page;
    private int process;
    private int arrivalTime; // for FIFO
    private int lastUsed; // for LRU
    private boolean used; // for Second Chance

    public Page(int page, int process, int arrivalTime) {
        this.page = page;
        this.process = process;
        this.arrivalTime = arrivalTime;
        this.lastUsed = arrivalTime;
        this.used = true;
    }

    public int getProcess() {
        return process;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getLastUsed() {
        return lastUsed;
    }

    public boolean getUsed() {
        return used;
    }

    public int getPage() {
        return page;
    }

    public void changeProcess(int process, int arrivalTime) {
        this.process = process;
        this.arrivalTime = arrivalTime;
        this.lastUsed = arrivalTime;
        this.used = true;
    }

    public void use(int useTime) {
        this.used = true;
        this.lastUsed = useTime;
    }

    public void secondChance() {
        this.used = false;
    }

    @Override
    public String toString() {
        return page + ": " + process;
    }
}
