package algorithm.dp;

import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/5/16 0:21
 * @Description:
 */
public class Test {

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.get(triangle.size()-1).size();
        Integer[] dpCur = new Integer[n];
        Integer[] dpPre = new Integer[n];

        if(n == 1) {
            return triangle.get(0).get(0);
        } else if(n == 2) {
            return triangle.get(0).get(0) + Math.min(triangle.get(1).get(0), triangle.get(1).get(1));
        }
        dpPre[0] = triangle.get(0).get(0);
        dpCur[0] = triangle.get(0).get(0) + triangle.get(1).get(0);
        dpCur[1] = triangle.get(0).get(0) + triangle.get(1).get(1);

        Integer ans = Integer.MAX_VALUE;
        for(int i = 2; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            for(int j = 0; j < row.size(); j++) {
                if(j == 0) {
                    dpCur[j] = dpPre[j] + row.get(j);
                } else {
                    dpCur[j] = Math.min(dpPre[j-1], dpPre[j]) + row.get(j);
                }
                if(i == (triangle.size()-1)) {
                    ans = dpCur[j] < ans ? dpCur[j] : ans;
                }
            }
            Integer[] temp = dpCur;
            dpCur = dpPre;
            dpPre = temp;
        }

        return ans;
    }


}
