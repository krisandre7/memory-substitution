
// A page in memory
public class Page {
    private int id;
    private int page;
    private int process;
    private int lastUsed; // for LRU
    private boolean used; // for Second Chance

    public Page(int page, int process) {
        this.page = page;
        this.process = process;
        this.used = true;
    }

    public int getProcess() {
        return process;
    }

    public int getId() {
        return id;
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

    public void use(int useTime) {
        this.used = true;
        this.lastUsed = useTime;
    }

    public void secondChance() {
        this.used = false;
    }

    @Override
    public String toString() {
        return "Page [id=" + id + ", page=" + page + ", process=" + process + ", lastUsed=" + lastUsed + ", used=" + used
                + "]";
    }
}
