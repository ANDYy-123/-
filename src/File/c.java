package File;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class c {
    public static void main(String[] args) throws IOException {
        String s = "1\r\n2\r\n3\r\n \r\nabd\r\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        StringBuffer strbuf = new StringBuffer();
        while ((line = br.readLine()) != null) {
            if (!line.trim().equals("")) {
                line = "<br>" + line;//每行可以做加工
                strbuf.append(line + "\r\n");
            }
        }
        System.out.println(s);
        System.out.println(strbuf.toString());
    }
}
