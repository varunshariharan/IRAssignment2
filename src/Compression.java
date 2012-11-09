/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 11/8/12
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Compression {

    public static String gamma(int number) {
        String offset = Integer.toBinaryString(number).substring(1);
        int length = offset.length();
        String unary = "";
        for (int index = 0; index < length; index++) {
            unary = unary + '1';
        }
        unary = unary + '0';
        String gammaCode = unary + offset;
        return gammaCode;
    }

    public static String delta(int number) {
        String binary = Integer.toBinaryString(number);
        int length = binary.length();
        String offset = gamma(length).substring(1);
        String result = offset + binary;
        return result;
    }
}
