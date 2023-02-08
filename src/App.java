import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// this code is meant to simulate a memory page allocator
// the memory is represented by an array of integers of size 8000 numbers
// the user inputs a string of pairs of numbers separated by ;
// for example, 1,0;1,1 means "allocate process 1 to apge 0 and process 1 to page 1"
// the program will then allocate the processes to the pages and print the memory
// it will also count the number of page faults and print it

public class App {

  // public static void printMemory(Page[] memory) {
  // Page[] sorted = Arrays.copyOf(memory, memory.length);
  // Arrays.sort(sorted, new PageComparator(PageAlgs.ASCENDING));

  // for (int i = 0; i < sorted.length; i++) {
  // if (sorted[i].getProcess() != 0) {
  // System.out.println(sorted[i].toString());
  // }
  // }
  // }

  // reads the input file and returns an array of pairs of integers
  public static ArrayList<Integer[]> readInput(File file) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    scanner.useDelimiter(";");

    ArrayList<Integer[]> input = new ArrayList<Integer[]>();
    while (scanner.hasNext()) {
      String[] temp = scanner.next().split(",");

      // clean up the input to allow only numbers
      int process = Integer.parseInt(temp[0].replaceAll("[^0-9]", ""));
      int page = Integer.parseInt(temp[1].replaceAll("[^0-9]", ""));

      if (process == 0)
        break;

      input.add(new Integer[] { process, page });
    }
    scanner.close();

    return input;
  }

  public static int indexToReplace(ArrayList<Page> memory, PageAlgs alg) {
    // copy the array to sort it
    ArrayList<Page> sorted = new ArrayList<Page>(memory);
    sorted.sort(new PageComparator(alg));
    return memory.indexOf(sorted.get(0));
  }

  public static void main(String[] args) throws Exception {

    // args = new String[1];
    // args[0] = "../tests/teste.txt";
    // args[0] = "../tests/StringReferenciaMC.txt";

    if (args.length < 1) {
      throw new IllegalArgumentException("No file specified");
    }

    // Leitura do arquivo
    String filepath = args[0];
    File file = new File(filepath);
    if (!file.exists()) {
      throw new Exception("File does not exist");
    }

    
    ArrayList<Integer[]> input = readInput(file);
    
    // itera sobre os algoritmos
    for (PageAlgs currentAlg : PageAlgs.values()) {
      int memorySize = 8000;
      ArrayList<Page> memory = new ArrayList<Page>();
      int pageFaults = 0;
      int time = 0;
      
      // itera sobre o input
      for (Integer[] integers : input) {
        int process = integers[0];
        int page = integers[1];

        // se o processo for 0, para a iteração
        if (process == 0)
          break;

        // itera sobre a memoria e checa se a página e o processo já estão alocados
        boolean found = false;
        for (int i = 0; i < memory.size(); i++) {
          Page current = memory.get(i);
          if (current.getProcess() == process && current.getPage() == page) {
            found = true;
            current.use(time);
            break;
          }
        }

        // se não tiver encontrado, aloca o processo
        if (!found) {
          pageFaults++;

          // se a memória já estiver cheia, substitui a página
          if (memory.size() == memorySize) {

            if (currentAlg == PageAlgs.SECOND_CHANCE) {
              // itera sobre a memória e checa se a página já foi usada
              // se não tiver sido usada, substitui
              // se tiver sido usada, marca como não usada e continua a iteração
              for (int i = 0; i < memory.size(); i++) {
                Page current = memory.get(i);
                if (!current.getUsed()) {
                  current.changeProcess(process, time);
                  found = true;
                  break;
                } else {
                  current.secondChance();
                }
              }
            } else { // se não for o algoritmo de segunda chance, substitui a página
              int index = indexToReplace(memory, currentAlg);
              memory.get(index).changeProcess(process, time);
            }
          } else {
            // se a memória não estiver cheia, aloca a página
            memory.add(new Page(page, process, time));
          }
        }

        time++;
      }

      // print current algorithm page faults
      System.out.println("(" + currentAlg + ") Page Faults: " + pageFaults);
    }
  }
}
