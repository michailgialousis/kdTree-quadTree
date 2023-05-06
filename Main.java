import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        int N = 65536;
        //int randomNumber = random.nextInt(N - 1);

        int searches = 100;


        int[] M = {200, 500, 1000, 10000};//30000};// 50000, 70000, 100000}; stackOverflow Error at 30000>> at line 19 KDtree

        for (int k = 0; k < M.length; k++) {

            KDtree kdTree = new KDtree();
            Node root = null;

            int[][] points = new int[M[k]][2];

            //fill the points with random numbers
            for (int x = 0; x < M[k]; x++) {
                for (int y = 0; y < 2; y++) {
                    points[x][y] = RandomNumberGenerator.generateRandomNumber(N);
                    //System.out.println(points[x][y]);
                }
                root = kdTree.insert(root, points[k]);
            }

            int[][] existingPoints = new int[searches][2];
            int[]depthOfSuccessSearches = new int[searches];
            for (int i = 0; i <searches; i++) {
                int randomIndex = random.nextInt(M[k]);
                existingPoints[i] = points[randomIndex];
                depthOfSuccessSearches[i]= kdTree.search(root,existingPoints[i]);
            }

            int[][] nonExistingPoints = new int[100][2];
            int[]depthOfFailedSearches = new int[100];
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 2; j++) {
                    int randomNumber;
                    do {
                        randomNumber = RandomNumberGenerator.generateRandomNumber(N);

                    } while (checkArrayElements(points,M[k],2,randomNumber ));
                    nonExistingPoints[i][j] = randomNumber;
                    depthOfFailedSearches[i] = kdTree.search(root,nonExistingPoints[i]);
                }
            }


            System.out.println("For M = "+M[k]+" :");
            System.out.println("KD tree successful searches depth :"+findMean(depthOfSuccessSearches));
            System.out.println("KD tree failed searches depth :"+findMean(depthOfFailedSearches));
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

    public static boolean checkArrayElements(int [][]array,int M,int N,int num) {
         array = new int[M][N];
        for(int i = 0; i<M;i++){
            for(int j = 0; j<N;j ++){
                if(array[i][j] == num)
                    return true;
            }
        }
        return false;
    }

}