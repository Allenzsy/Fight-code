package algorithm.sort;

public class BinarySearch {

    /**
     * 要理解二分查找的搜索区间:
     *     1. 搜索区间为 [l, r) 时, l = 0, r = nums.length,   此时 while(l < r), 终止时 l=r, [r, r) 区间为空没有漏掉数据
     *        每次分割完应该 [l, mid) 和 [mid+1, r)
     *     2. 搜索区间为 [l, r] 时, l = 0, r = nums.length-1, 此时 while(l <= r),终止时 l=r+1, [r+1, r] 区间为空没漏掉数
     *        每次分割完区间 [l, mid-1] mid [mid+1, r]
     */
    public static int findTarget(int[] nums, int target) {
        int l = 0, r = nums.length-1; // [l, r]
        while(l <= r) {
            int mid = l + (r-l)/2;
            if(nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] == target) {
                return mid;
            }
        }
        return -1;
    }

    public static int findTarget1(int[] nums, int target) {
        int l = 0, r = nums.length; // [l, r)
        while(l < r) {
            int mid = l + (r-l)/2;
            if(nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) { // 注意此时区间赋值 mid 才不会遗漏缺数, 如果给mid-1就会遗漏缺数
                r = mid;
            } else if (nums[mid] == target) {
                return mid;
            }
        }
        return -1;
    }

    // 查找数字第一次出现的位置, 也就是左边界, 其实和上面的区别就是在 num[mid] == target 的处理和边界的处理上
    public static int findTargetFirst(int[] nums, int target){
        // 以下面的模板为例, 因为看起来更舒服一些
        int l = 0, r = nums.length; // [l, r)
        while(l < r) {
            int mid = l + (r-l)/2;
            if(nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid;
            } else if (nums[mid] == target) {
                r = mid; // 相等时不能直接退出, 需要收缩右边界
            }
        }
        // 处理边界情况, 当 target 超过最大值时, l和数组长度一致, 此时需要单独处理
        if(l == nums.length) return -1;
        return nums[l] == target ? l : -1;
    }

    // 查找数字最后一次出现的位置, 也就是右边界, 其实和上面的区别就是在 num[mid] == target 的处理和边界的处理上
    public static int findTargetLast(int[] nums, int target){
        // 以下面的模板为例, 因为看起来更舒服一些
        int l = 0, r = nums.length; // [l, r)
        while(l < r) {
            int mid = l + (r-l)/2;
            if(nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid;
            } else if (nums[mid] == target) {
                l = mid + 1; // 相等时不能直接退出, 需要收缩左边界
            }
        }
        // 处理边界情况, 当 target 小于最小值时, l=0, 由于下面要-1因此提前做判断
        if(l == 0) return -1;
        return nums[l-1] == target ? l-1 : -1;
    }


    public static void main(String[] args) {
                    //0,1,2,3,4,5,6,7,  8, 9,10,11
        int[] nums = {1,2,3,4,7,8,9,11,14,16,18,19};
        System.out.println(findTarget(nums, 9));
        System.out.println(findTarget1(nums, 9));
        System.out.println(findTarget(nums, 16));
        System.out.println(findTarget1(nums, 16));
        System.out.println(findTarget(nums, 5));
        System.out.println(findTarget1(nums, 5));
                    // 0,1,2,3,4,5,6,7,8,9,10,11
        int[] nums1 = {1,2,3,4,5,5,5,5,5,5,5,7,9,10,15};
        System.out.println(findTargetFirst(nums1, 5));
        System.out.println(findTargetFirst(nums1, 7));
        System.out.println(findTargetFirst(nums1, 16));
        System.out.println(findTargetFirst(nums1, 0));
        System.out.println("查找最后一次出现的位置");
        System.out.println(findTargetLast(nums1, 5));
        System.out.println(findTargetLast(nums1, 7));
        System.out.println(findTargetLast(nums1, 16));
        System.out.println(findTargetLast(nums1, 0));

        Integer i = Integer.valueOf("123");
//        Integer aa = "123";
        float v = i.floatValue();

    }
}
