package algorithm.search;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2022/8/19 1:07
 * @Description:
 */
public class BinarySearch {

    public int findFirstPos(int[] num, int target) {
        // 边界
        if(num == null || num.length == 0) { return -1; }
        // 二分查找
        int pos = binarySearch(num, 0, num.length-1, target);
        // 没找到返回-1
        if(pos == -1) { return pos; }
        // 找到第一个
        while(--pos >= 0 && num[pos] == target ) {}
        return ++pos;
    }

    public int findLastPos(int[] num, int target) {
        // 边界
        if(num == null || num.length == 0) { return -1; }
        // 二分查找
        int pos = binarySearch(num, 0, num.length-1, target);
        // 没找到返回-1
        if(pos == -1) { return pos; }
        // 找到第一个
        while(++pos <= num.length-1 && num[pos] == target ) {}
        return --pos;
    }

    public int binarySearchFindFirst(int[] num, int n, int target) {
        if(num == null || num.length == 0) { return -1; }
        // 在[l...r]之中找第一个出现的target的位置
        int l = 0, r = n-1;
        while(l < r) {
            int mid = l + (r-l) / 2;// 地板除, 当 l 和 r 只差1且都指向和target相等的值时, r依然会向l移动1, 就得到了想要的答案
            if(num[mid] == target) {// 找最左边界, 因此移动 r
                r = mid;
            } else if(num[mid] < target) {// 说明 target 在 [mid+1, r]
                l = mid + 1;
            } else if(num[mid] > target) {// 说明 target 在 [l, mid-1]
                r = mid - 1;
            }
        }
        return num[r] == target ? r : -1;// 相等时跳出循环, 此时再判断一下
    }

    public int binarySearchFindLast(int[] num, int n, int target) {
        if(num == null || num.length == 0) { return -1; }
        // 在[l...r]之中找第一个出现的target的位置
        int l = 0, r = n-1;
        while(l < r) {
            int mid = l + (r-l) / 2 + ((r-l)%2 == 0 ? 0 : 1);// 需要向上取整的除法, 才可以让l向r移动1,然后就得到了想要的答案
            if(num[mid] == target) {// 找最右边界, 因此移动 l
                l = mid;
            } else if(num[mid] < target) {// 说明 target 在 [mid+1, r]
                l = mid + 1;
            } else if(num[mid] > target) {// 说明 target 在 [l, mid-1]
                r = mid - 1;
            }
        }
        return num[r] == target ? r : -1;// 相等时跳出循环, 此时再判断一下
    }

    public int binarySearchFindLast(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while(l < r) {
            int mid = l + (r-l)/2;
            if(nums[mid] <= target) {
                l = mid+1;
            }else if(nums[mid] > target) {
                r = mid;
            }
        }
        return nums[l] > target ? l-1 : -1;
    }

    public int searchRangeL(int[] nums, int target) {
        int n = nums.length;

        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int searchRangeH(int[] nums, int target) {
        int n = nums.length;

        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] <= target) {
                l = mid;
            } else if(nums[mid] > target){
                r = mid - 1;
            }
        }
        return l;
    }


    public int binarySearch(int[] num, int l, int r, int target) {
        if(l > r) {
            return -1;
        }
        //int mid = (l + r) >> 1; 存在溢出风险
        int mid = l + ((r - l) >> 1);
        if(target == num[mid]) {
            return mid;
        } else if(target < num[mid]) {
            return binarySearch(num, l, mid-1, target);
        } else {
            return binarySearch(num, mid+1, r, target);
        }
    }



    @Test
    public void test_binarySearch_recursion() {
        int[] num = {1,2,2,2,2,2,2,2,3,3,3,3,3,4,4,4,4,6,6,7,7,7,8,8,8,8,9,9,10};
        int pos = findFirstPos(num, 4);
        System.out.println(String.format("找到后线性查找最先出现,结果: %s", pos));// 13
        pos = findFirstPos(num, 9);
        System.out.println(String.format("找到后线性查找最先出现,结果: %s", pos));// 26
        pos = findFirstPos(num, 2);
        System.out.println(String.format("找到后线性查找最先出现,结果: %s", pos));// 1
        pos = findFirstPos(num, 7);
        System.out.println(String.format("找到后线性查找最先出现,结果: %s", pos));// 19

        pos = binarySearchFindFirst(num, num.length, 4);
        System.out.println(String.format("直接二分查找最先出现,结果: %s", pos));
        pos = binarySearchFindFirst(num, num.length, 9);
        System.out.println(String.format("直接二分查找最先出现,结果: %s", pos));
        pos = binarySearchFindFirst(num, num.length, 2);
        System.out.println(String.format("直接二分查找最先出现,结果: %s", pos));
        pos = binarySearchFindFirst(num, num.length, 7);
        System.out.println(String.format("直接二分查找最先出现,结果: %s", pos));

        pos = findLastPos(num, 4);
        System.out.println(String.format("找到后线性查找最后出现,结果: %s", pos));// 16
        pos = findLastPos(num, 9);
        System.out.println(String.format("找到后线性查找最后出现,结果: %s", pos));// 27
        pos = findLastPos(num, 2);
        System.out.println(String.format("找到后线性查找最后出现,结果: %s", pos));// 7
        pos = findLastPos(num, 7);
        System.out.println(String.format("找到后线性查找最后出现,结果: %s", pos));// 21
        pos = findLastPos(num, 0);
        System.out.println(String.format("找到后线性查找最后出现,结果: %s", pos));

        pos = binarySearchFindLast(num, num.length, 4);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, num.length, 9);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, num.length, 2);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, num.length, 7);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, num.length, 0);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));

        System.out.println("-----------------------------------------------");
        pos = binarySearchFindLast(num, 4);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, 9);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, 2);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, 7);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));
        pos = binarySearchFindLast(num, 0);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));

        pos = searchRangeL(num, 4);
        System.out.println(String.format("直接二分查找最后出现,结果: %s", pos));

        System.out.println(1 + ((9-1) >> 1));
        int[] aa = new int[]{1,1,1,2,2,2,3,3,7,8};
        System.out.println(searchRangeH(aa, 3));

        Arrays.binarySearch(aa, 1);

        int[] numbers = {5, 25, 75};
        int target = 100;
        int[] ans = new int[]{-1, -1};
        int n = numbers.length;
        for(int i = 0; i < n; i++) {
            int index = Arrays.binarySearch(numbers, target-numbers[i]);
            if(index >= 0 && index != i) {
                ans[0] = i+1;
                ans[1] = index+1;
                break;
            }
        }
    }

}
