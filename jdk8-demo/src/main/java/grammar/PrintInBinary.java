package grammar;

public class PrintInBinary {

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;


    public static void main(String[] args) {
        printInBinary(SHARED_UNIT);
        printInBinary(MAX_COUNT);
        printInBinary(EXCLUSIVE_MASK);
    }


    public static void printInBinary(int num) {
        for(int i=0; i<32; i++){
            int t = (num & 0x80000000 >>> i) >>> (31-i);
            System.out.print(t);
        }
        System.out.println();
    }

}
