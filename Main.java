import java.util.Random;


public class Main {

    public static final int N = 262144;
    public static int SEARCHES = 100;
    public static int[] M = {200, 500, 1000, 10000, 30000, 50000, 70000, 100000};
    public static int L = 2;

    public static void main(String[] args) {

        Random random = new Random();

        for (int k = 0; k < M.length; k++) {


            KDtree kdTree = new KDtree();
            Node root = null;
            QuadTree<Integer> quadTree = new QuadTree<>();


            int[][] points = new int[M[k]][L];

            //fill the points with random numbers from 0 to 2^18
            for (int x = 0; x < M[k]; x++) {
                for (int y = 0; y < L; y++) {
                    points[x][y] = RandomNumberGenerator.generateRandomNumber(N);
                }
                // insert at kd and quad trees
                root = kdTree.insert(root, points[x]);
                quadTree.insert(points[x][0], points[x][1]);
            }

            //getting points that exist to make successful searches
            int[][] existingPoints = new int[SEARCHES][L];
            int[] depthOfSuccessSearchesKd = new int[SEARCHES];
            int[] depthOfSuccessSearchesQuad = new int[SEARCHES];
            for (int i = 0; i < SEARCHES; i++) {
                //getting random of those existing points
                int randomIndex = random.nextInt(M[k]);
                existingPoints[i] = points[randomIndex];
                //storing the values the depths in arrays
                depthOfSuccessSearchesKd[i] = kdTree.search(root, existingPoints[i]);
                depthOfSuccessSearchesQuad[i] = quadTree.search(existingPoints[i][0], existingPoints[i][1]);
            }


            //making non-existing points to make failing searches
            int[][] nonExistingPoints = new int[SEARCHES][L];
            int[] depthOfFailedSearchesKd = new int[SEARCHES];
            int[] depthOfFailedSearchesQuad = new int[SEARCHES];
            for (int i = 0; i < SEARCHES; i++) {
                for (int j = 0; j < L; j++) {
                    int randomNumber;
                    do {
                        randomNumber = RandomNumberGenerator.generateRandomNumber(N);

                    } while (checkIfPointExists(points, M[k], L, randomNumber));
                    nonExistingPoints[i][j] = randomNumber;

                }
                depthOfFailedSearchesKd[i] = kdTree.search(root, nonExistingPoints[i]);
                depthOfFailedSearchesQuad[i] = quadTree.search(nonExistingPoints[i][0], nonExistingPoints[i][1]);
            }

            System.out.println("For M = " + M[k] + " :");
            System.out.println("KD tree successful searches depth :" + findMean(depthOfSuccessSearchesKd));
            System.out.println("KD tree failed searches depth :" + findMean(depthOfFailedSearchesKd));
            System.out.println("Quad tree successful searches depth :" + findMean(depthOfSuccessSearchesQuad));
            System.out.println("Quad tree failed searches depth :" + findMean(depthOfFailedSearchesQuad) + "\n");
        }


    }

    public static String findMean(int[] arr) {
        double sum = 0.0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        double mean = sum / arr.length;
        return String.format("%.2f", mean);
    }

    public static boolean checkIfPointExists(int[][] array, int M, int N, int num) {

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (array[i][j] == num)
                    return true;
            }
        }
        return false;
    }

}