
// implement lru here
public class LRU extends Allocator {
    private int[] lastUsed = new int[MAX_MEMORY];
    private int time = 0;

    public LRU() {
        super();
    }

    public void allocatePage(Page page) {
        int id = isPageInMemory(page);
        if (id != -1) {
            lastUsed[id] = time++;
            return;
        }

        if (pagesAdded < MAX_MEMORY) {
            memory[pagesAdded] = page;
            lastUsed[pagesAdded++] = time++;
        } else {
            int oldest = 0;
            for (int i = 1; i < MAX_MEMORY; i++) {
                if (lastUsed[i] < lastUsed[oldest]) {
                    oldest = i;
                }
            }

            memory[oldest] = page;
            lastUsed[oldest] = time++;
        }

        pageFaults++;
    }
}
