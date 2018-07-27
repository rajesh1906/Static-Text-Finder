package statictext.hcl.com.statictextproject;

public class StaticText {

    String fileName, line, staticText, filePath;

    public StaticText(String fileName,String filePath, String line, String staticText) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.line = line;
        this.staticText = staticText;
    }
}
