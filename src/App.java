import java.io.File;
import java.util.Scanner;

// this code is meant to simulate a memory page allocator
// the memory is represented by an array of integers of size 8000 numbers
// the user inputs a string of pairs of numbers separated by ;
// for example, 1,0;1,1 means "allocate process 1 to apge 0 and process 1 to page 1"
// the program will then allocate the processes to the pages and print the memory
// it will also count the number of page faults and print it

public class App {

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      throw new IllegalArgumentException("No file specified");
    }

    // Leitura do arquivo
    String filepath = args[0];
    File file = new File(filepath);
    if (!file.exists()) {
      throw new Exception("File does not exist");
    }

    Scanner scanner = new Scanner(file, "UTF-8");
    scanner.useDelimiter(";");

    String firstLine = scanner.next();

    FIFO fifo = new FIFO();
    LRU lru = new LRU();
    SecondChance secondChance = new SecondChance();
    fifo.addFirst(firstLine);
    lru.addFirst(firstLine);
    secondChance.addFirst(firstLine);

    while (scanner.hasNext()) {
      String[] pair = scanner.next().split(",");
      int process = Integer.parseInt(pair[0]);
      int pageNum = Integer.parseInt(pair[1]);
      
      if (process == 0 && pageNum == 0) {
        break;
      }

      Page page = new Page(process, pageNum);
      fifo.allocatePage(page);
      lru.allocatePage(page);
      secondChance.allocatePage(page);
    }

    scanner.close();
    System.out.println("(FIFO) Page Faults: " + fifo.pageFaults);
    System.out.println("(LRU) Page Faults: " + lru.pageFaults);
    System.out.println("(Second Chance) Page Faults: " + secondChance.pageFaults);
  }
}
