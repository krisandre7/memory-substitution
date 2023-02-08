import java.util.LinkedList;
import java.util.Queue;

public class FIFO extends Allocator {
    private Queue<Integer> queue = new LinkedList<Integer>();

    public FIFO() {
        super();
    }

    public void allocatePage(Page page) {
        if (isPageInMemory(page) != -1) {
            return;
        }

        if (pagesAdded < MAX_MEMORY) {
            memory[pagesAdded] = page;
            pagesAdded++;
            queue.add(pagesAdded);
        } else {
            int pageToReplace = queue.remove();
            memory[pageToReplace] = page;
            queue.add(pageToReplace);
        }

        pageFaults++;
    }
}
