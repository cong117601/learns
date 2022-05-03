package com.cgm.algorithm.base.bit;

public class BitSubject {

    /**
     * 打印int型数的二进制位
     *
     * @param i
     */
    public static void printIntBit(int i) {
        for (int j = 31; j >= 0; j--) {
            System.out.print((i & (1 << j)) == 0 ? "0" : "1");
        }
    }

    /**
     * 一个数组中 有一种数出现奇数次，其它数出现偶数次，找到并打印这种数
     * ^ 无符号相加
     * a ^ a = 0
     * a ^ a ^ a = a
     */


    public static void findNum(int[] arr) {
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            num ^= arr[i];
        }
        System.out.println(num);
    }

    /**
     * 找出整形数的最右侧的1
     * a & (~a +1 ) -> a& (-a)
     *
     * @param i
     */
    public static void printMoreRightOne(int i) {
        System.out.println(i & (-i));
    }

    /**
     * 一个数组中有两种数出现了奇数次，其他数都出现了 偶数次，找到这两种数
     */

    public static void findTwoNum(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //提取出来最右侧的1
        int rightOne = eor & (-eor);
        int onlyOne = 0;
        //最右侧有1的才^,偶数会抵消，所以找到了其中一个
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) != 0) {  //代表数组中的数，在最右侧为1的数进行^
                onlyOne ^= arr[i];

            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    /**
     * 一个数组中有一种数出现K次，其他数都出现了M次，找出出现k次的数
     * k>1 k<M
     * bitMap
     *
     * @param arr
     */
    public static void findKTimeNum(int[] arr, int k, int m) {
        int[] t = new int[32];
        //t[0] 代表 0位置1出现了次数
        //t[i] 代表 i位置1出现的次数
        for (int num : arr) {
            for (int i = 0; i <= 31; i++) {
                if ((num >> i & 1) != 0) {
                    t[i]++;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if(t[i] % m == 0){
                continue;
            }
            if (t[i] % m == k) {
                ans |= (1 << i);
            }else{
                return ;
            }
        }
        System.out.println(ans);
    }


    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 3, 3, 3};
        //printIntBit(4);
        printMoreRightOne(8);
        //findNum(arr);
        //System.out.println(5 ^ 5 ^ 5);

    }
}
