import java.util.*;

public class LRUPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];
        System.out.println("Enter page reference sequence: ");
        for (int i = 0; i < n; i++) pages[i] = sc.nextInt();

        System.out.print("Enter number of frames: ");
        int framesCount = sc.nextInt();

        lruPageReplacement(pages, framesCount);
    }

    static void lruPageReplacement(int[] pages, int framesCount) {
        int[] frames = new int[framesCount];
        int[] lastUsed = new int[framesCount]; // stores last used time
        Arrays.fill(frames, -1); // -1 means empty frame
        Arrays.fill(lastUsed, -1);
        int time = 0;
        int pageFaults = 0;

        System.out.println("\nPage Reference\tFrames");
        for (int page : pages) {
            time++;
            boolean hit = false;

            // Check if page already exists in frames
            for (int i = 0; i < framesCount; i++) {
                if (frames[i] == page) {
                    hit = true;
                    lastUsed[i] = time; // update last used time
                    break;
                }
            }

            // If page not found (page fault)
            if (!hit) {
                int lruIndex = -1;

                // Step 1: Find an empty frame if available
                for (int i = 0; i < framesCount; i++) {
                    if (frames[i] == -1) {
                        lruIndex = i;
                        break;
                    }
                }

                // Step 2: If no empty frame, find least recently used frame
                if (lruIndex == -1) {
                    lruIndex = 0;
                    for (int i = 1; i < framesCount; i++) {
                        if (lastUsed[i] < lastUsed[lruIndex]) {
                            lruIndex = i;
                        }
                    }
                }

                // Replace LRU or fill empty frame
                frames[lruIndex] = page;
                lastUsed[lruIndex] = time;
                pageFaults++;
            }

            // Print current frames
            System.out.print(page + "\t\t");
            for (int f : frames) {
                if (f != -1) System.out.print(f + " ");
                else System.out.print("- ");
            }
            System.out.println(hit ? "(Hit)" : "(Fault)");
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }
  
}
Enter number of pages: 12
Enter page reference sequence: 1 2 3 4 1 2 5 1 2 3 4 5
Enter number of frames: 3

Page Reference	Frames
1		1 - - (Fault)
2		1 2 - (Fault)
3		1 2 3 (Fault)
4		4 2 3 (Fault)
1		4 1 3 (Fault)
2		4 1 2 (Fault)
5		5 1 2 (Fault)
1		5 1 2 (Hit)
2		5 1 2 (Hit)
3		3 1 2 (Fault)
4		3 4 2 (Fault)
5		3 4 5 (Fault)

Total Page Faults: 9


