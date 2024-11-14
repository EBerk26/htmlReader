import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class htmlReader implements ActionListener {
    JFrame frame;
    JPanel backPanel;
    JPanel bottomPanel;
    JPanel urlAndKeyword;
    JButton go;
    TextArea output;
    TextArea keywordTextArea;
    TextArea linkTextArea;
    public static void main(String[] args) {
        new htmlReader();
    }
    public htmlReader() {
        frame = new JFrame("Link Finder");
        frame.setSize(509,330);
        backPanel = new JPanel(new GridLayout(2,1));
        bottomPanel=new JPanel(new GridLayout(1,2));
        urlAndKeyword = new JPanel(new GridLayout(2,1));
        go = new JButton("GO");
        go.addActionListener(this);
        output = new TextArea();
        output.setEditable(false);
        frame.add(backPanel);
        backPanel.add(output);
        backPanel.add(bottomPanel);
        bottomPanel.add(urlAndKeyword);
        bottomPanel.add(go);
        keywordTextArea = new TextArea("Put your keyword here...");
        linkTextArea = new TextArea("Put your link here...");
        urlAndKeyword.add(keywordTextArea);
        urlAndKeyword.add(linkTextArea);
        frame.setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = keywordTextArea.getText();
        boolean capsSensitive = false;
        try{
            URL url = new URL(linkTextArea.getText());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while(line!=null){
                while(line.contains("href")){
                    String link = getLink(line, url);
                    //noinspection ConstantValue
                    if((!capsSensitive&&link.toLowerCase().contains(keyword.toLowerCase()))||(capsSensitive&&link.contains(keyword))){
                        output.setText(output.getText()+link+"\n");
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
}
