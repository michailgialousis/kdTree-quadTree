import java.util.Random;



public class Main {
    public static void main(String[] args) {


        Random random = new Random();
        int N = 262144;
        int searches = 100;

        int[] M = {200, 500, 1000, 10000,30000, 50000, 70000, 100000};

        for (int k = 0; k < M.length; k++) {

            KDtree kdTree = new KDtree();
            Node root = null;

            QuadTree<Integer> st = new QuadTree<Integer>();

            int[][] points = new int[M[k]][2];

            //fill the points with random numbers
            for (int x = 0; x < M[k]; x++) {
                for (int y = 0; y < 2; y++) {
                    points[x][y] = RandomNumberGenerator.generateRandomNumber(N);
                }
                root = kdTree.insert(root, points[x]);
                st.insert(points[x][0],points[x][1]);
            }

            int[][] existingPoints = new int[searches][2];
            int[]depthOfSuccessSearchesKd = new int[searches];
            int[]depthOfSuccessSearchesQuad = new int[searches];
            for (int i = 0; i <searches; i++) {
                int randomIndex = random.nextInt(M[k]);
                existingPoints[i] = points[randomIndex];
                depthOfSuccessSearchesKd[i]= kdTree.search(root,existingPoints[i]);
                depthOfSuccessSearchesQuad[i]= st.search(existingPoints[i][0],existingPoints[i][1]);
            }

            int[][] nonExistingPoints = new int[searches][2];
            int[]depthOfFailedSearchesKd = new int[searches];
            int[]depthOfFailedSearchesQuad = new int[searches];
            for (int i = 0; i < searches; i++) {
                for (int j = 0; j < 2; j++) {
                    int randomNumber;
                    do {
                        randomNumber = RandomNumberGenerator.generateRandomNumber(N);

                    } while (checkArrayElements(points,M[k],2,randomNumber ));
                    nonExistingPoints[i][j] = randomNumber;

                }
                depthOfFailedSearchesKd[i] = kdTree.search(root,nonExistingPoints[i]);
                depthOfFailedSearchesQuad[i] = st.search(nonExistingPoints[i][0],nonExistingPoints[i][1]);
            }






            System.out.println("For M = "+M[k]+" :");
            System.out.println("KD tree successful searches depth :"+findMean(depthOfSuccessSearchesKd));
            System.out.println("KD tree failed searches depth :"+findMean(depthOfFailedSearchesKd));
            System.out.println("Quad tree successful searches depth :"+findMean(depthOfSuccessSearchesQuad));
            System.out.println("Quad tree failed searches depth :"+findMean(depthOfFailedSearchesQuad)+"\n");
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

        for(int i = 0; i<M;i++){
            for(int j = 0; j<N;j ++){
                if(array[i][j] == num)
                    return true;
            }
        }
        return false;
    }

}