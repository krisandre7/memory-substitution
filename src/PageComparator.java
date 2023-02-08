import java.util.Comparator;

public class PageComparator implements Comparator<Page> {
    
    private PageAlgs alg;

    public PageComparator(PageAlgs alg) {
        this.alg = alg;
    }

    @Override
    public int compare(Page p1, Page p2) {
        switch (alg) {
            case FIFO:
                return p1.getArrivalTime() - p2.getArrivalTime();
            case LRU:
                return p1.getLastUsed() - p2.getLastUsed();
            // case ASCENDING:
            //     return p1.getPage() - p2.getPage();
            default:
                return 0;
        }
    }
}
