import java.util.*;
import java.lang.StringBuilder;
import java.io.*;
import java.net.*;
public class makeUpc{
    public static void main(String[]arg)throws Exception{
        StringBuilder sb = new StringBuilder();
        StringBuilder FinalUpc = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        URL url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc=");
        File file = new File("upc1.text");
        file.createNewFile();
        PrintStream out = new PrintStream(file);
        Integer starting;     
        String startingLenght;
                if(arg.length==0){ 
                    System.out.println("Please enter the first 6 digits of the upc");
                    starting = sc.nextInt();
                    startingLenght = starting.toString();
                        while(startingLenght.length()!=6){
                            System.out.println("Invalid entry you entered "+ startingLenght.length() +" digits, Please enter 6 digits");
                            starting = sc.nextInt();
                            startingLenght = starting.toString();
                        }
                    sc.close();
                }else{
                    starting = Integer.parseInt(arg[0]);
                    startingLenght = arg[0];
                        while(startingLenght.length()!=6){
                            System.out.println("Invalid entry you entered an invalid amount of digits , Please enter 6 digits using your keypad");
                            starting = sc.nextInt();
                            startingLenght = starting.toString();
                        }
                }
        int ending = 11111;
        //String endingPart = Integer.toString(ending);

        for(int i = 0 ; i < 2; i++){
            String upc = starting+""+ending;
            //String FinalUpc = upc +""+calculateUPC(upc);
            FinalUpc.append(starting+""+ending++ +""+calculateUPC(upc));
            String check = FinalUpc.toString();
            url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc="+check);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(sb.toString()+ check);
            
            out.println(sb.toString()+ check);
            sb.setLength(0);
            FinalUpc.setLength(0);
            
        }
        out.close();
        
    }
            public static String calculateUPC(String code) {
                if (code.length() != 11) {
                    throw new IllegalArgumentException("Code must be 11 digits");
                }

                // Convert the code to an array of digits
                int[] digits = new int[11];
                for (int i = 0; i < 11; i++) {
                    digits[i] = Character.getNumericValue(code.charAt(i));
                }

                // Calculate the check digit
                int sumOdd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8] + digits[10];
                int sumEven = digits[1] + digits[3] + digits[5] + digits[7] + digits[9];
                int totalSum = (sumOdd * 3) + sumEven;
                int remainder = totalSum % 10;
                int checkDigit = remainder == 0 ? 0 : 10 - remainder;

                return Integer.toString(checkDigit);
            }
    
}