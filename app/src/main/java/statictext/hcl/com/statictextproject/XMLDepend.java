package statictext.hcl.com.statictextproject;

import java.util.List;

public class XMLDepend {
    String fileName;
    List<String> dependency;

    public XMLDepend(String fileName, List<String> dependency) {
        this.fileName = fileName;
        this.dependency = dependency;
    }
}
