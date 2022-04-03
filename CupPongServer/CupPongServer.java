public class CupPongServer {

    public static void main(String[] args) {
        try {
            CPSS ss = new CPSS(4444);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}