package Main;

public class BEncryption {
    // غباء متعوب عليه (الغباء الذكي)

//    public static void main(String[] args) {
//                test("Ahmed");
//        System.out.println(BDecrypt("k{jj<9")); // braa3
//    }

    public static void test(String s){
        int counter = 0;

        for (int i = 0; i < 100; i++) {
            String code = BEncrypt(s);
            System.out.println(code);

            String name = BDecrypt(code);
            System.out.println(name);
            if (!name.equals(s)){
                counter++;
            }
            System.out.println("==================");
        }
        System.out.println("Num of Errors: " + counter);
    }

    public static synchronized String BEncrypt(String txt){
        StringBuilder code = new StringBuilder();
        int key = (int) (Math.random() * 9) + 1;
        char[] chars = txt.toCharArray();
        for (char c: chars){
            c+=key;
            code.append(c);
        }
        code.append(key);
        return code.toString();
    }

    public static synchronized String BDecrypt(String txt){
        String str = "";
        int key = Integer.parseInt(String.valueOf(txt.charAt(txt.length() - 1)));
//        System.out.println("key: " + key);
        char[] chars = txt.toCharArray();
        for(int i = 0; i < chars.length - 1; i++){
            chars[i] -= key;
            str += chars[i];
        }
        return str;
    }
}















