public class Allocator {
    protected final int MAX_MEMORY = 8000;
    protected Page memory[] = new Page[MAX_MEMORY];
    protected int pageFaults = 0;
    protected int pagesAdded = 0;

    public Allocator() {
    }

    public int isPageInMemory(Page page) {
        for (int i = 0; i < pagesAdded; i++) {
            if (memory[i].getProcess() == page.getProcess() && memory[i].getPage() == page.getPage()) {
                return i;
            }
        }
        return -1;
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public void addFirst(String firstLine) {
        // remove BOM from first line
        if (firstLine.startsWith("\uFEFF")) {
            firstLine = firstLine.substring(1);
        }

        String[] firstPair = firstLine.split(",");
        int firstProcess = Integer.parseInt(firstPair[0]);
        int firstPage = Integer.parseInt(firstPair[1]);

        memory[pagesAdded] = new Page(firstPage, firstProcess);
        // pagesAdded++;
        pageFaults++;
    }

}
