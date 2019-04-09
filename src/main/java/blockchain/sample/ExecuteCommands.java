package blockchain.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteCommands {

    public String execute(String command) {
        ExecuteCommands obj = new ExecuteCommands();

        String output = obj.executeCommand(command);

        System.out.println(output);
        return output;
    }

    private String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();

    }

}
