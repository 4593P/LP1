import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];
        System.out.println("Enter page reference sequence: ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter number of frames: ");
        int framesCount = sc.nextInt();

        optimalPageReplacement(pages, framesCount);
    }

    static void optimalPageReplacement(int[] pages, int framesCount) {
        int[] frames = new int[framesCount];
        Arrays.fill(frames, -1); // -1 means empty frame
        int pageFaults = 0;

        System.out.println("\nPage Reference\tFrames");
        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean hit = false;

            // Check if page is already present
            for (int j = 0; j < framesCount; j++) {
                if (frames[j] == page) {
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                int replaceIndex = -1;

                // Step 1: check for an empty frame
                for (int j = 0; j < framesCount; j++) {
                    if (frames[j] == -1) {
                        replaceIndex = j;
                        break;
                    }
                }

                // Step 2: if no empty frame, find the optimal one to replace
                if (replaceIndex == -1) {
                    int farthestUse = -1;
                    int indexToReplace = -1;

                    for (int j = 0; j < framesCount; j++) {
                        int nextUse = Integer.MAX_VALUE; // assume not used again
                        for (int k = i + 1; k < pages.length; k++) {
                            if (frames[j] == pages[k]) {
                                nextUse = k;
                                break;
                            }
                        }
                        if (nextUse > farthestUse) {
                            farthestUse = nextUse;
                            indexToReplace = j;
                        }
                    }

                    replaceIndex = indexToReplace;
                }

                frames[replaceIndex] = page;
                pageFaults++;
            }

            // Print current frame state
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
4		4 3 2 (Fault)
5		5 4 2 (Fault)

Total Page Faults: 9

