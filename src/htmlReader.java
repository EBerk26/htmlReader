import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class htmlReader {
    public static void main(String[] args) {
        //noinspection InstantiationOfUtilityClass
        new htmlReader();
    }
    public htmlReader() {
        String keyword = "japan";
        boolean capsSensitive = false;
        try{
            URL url = new URL("https://en.wikipedia.org/wiki/Shingo_Adachi");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while(line!=null){
                while(line.contains("href")){
                    String link = getLink(line, url);
                    //noinspection ConstantValue
                    if((!capsSensitive&&link.toLowerCase().contains(keyword.toLowerCase()))||(capsSensitive&&link.contains(keyword))){
                        System.out.println(link);
                    }
                    line = line.substring(line.indexOf("\"",line.indexOf("href=\"")+6));
                }
                line = reader.readLine();
            }
        } catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private static String getLink(String line, URL url) {
        String link = line.substring(line.indexOf("href=\"")+6, line.indexOf("\"", line.indexOf("href=\"")+6));
        if(!link.isEmpty()) {
            if (link.charAt(0) == '/') {
                if (link.charAt(1) == '/') {
                    link = link.substring(2);
                } else {
                    link = url.getHost() + link;
                }
            }
        }
        if(link.indexOf("https://")!=0){
            link="https://"+link;
        }
        return link;
    }
}
