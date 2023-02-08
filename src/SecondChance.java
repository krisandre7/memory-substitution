public class SecondChance extends Allocator {
    private boolean[] secondChance = new boolean[MAX_MEMORY];
    int pointer = 0;

    public SecondChance() {
        super();
    }

    public void allocatePage(Page page) {
        int id = isPageInMemory(page);
        if (isPageInMemory(page) != -1) {
            secondChance[id] = true;
            return;
        }

        if (pagesAdded < MAX_MEMORY) {
            memory[pagesAdded] = page;
            secondChance[pagesAdded++] = false;
        } else {
            while (secondChance[pointer]) {
                secondChance[pointer] = false;
                pointer = (pointer + 1) % MAX_MEMORY;
            }
            if(pointer == -1) return;


            memory[pointer] = page;
            secondChance[pointer] = false;
            pointer++;
        }

        pageFaults++;
    }
}
