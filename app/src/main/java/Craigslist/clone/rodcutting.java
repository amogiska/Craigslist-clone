package craigslist.clone;

public class rodcutting {

    public static int rodcutting(int rodLength, int[] prices, int[] lengths){


        if(rodLength == 0 || prices.length == 0 || lengths.length == 0){
            return 0;
        }

        int[][] dp = new int[rodLength+1][lengths.length];

        for(int i = 0; i < lengths.length; i++){
            for(int j = 0; j < rodLength; j++){
                int currPrice = prices[i] + dp[j-lengths[i]][i];
                int currPrict2 = dp[j][i-1];
                dp[j][i] = Math.min(currPrice, currPrict2);
            }
        }

        return dp[rodLength][lengths.length - 1];
    }

    public static void main(String args[]){
        int[] lengths = {1,2,3,4,5};
        int[] prices = {2, 6, 7,10,13};
        int result = rodcutting(5, prices, lengths);
        System.out.println(result);
    }
}
