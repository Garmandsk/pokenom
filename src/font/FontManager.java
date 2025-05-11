package font;

import main.UtilityTool;

public class FontManager {

    public FontManager(){

    }

    public void setFont(Fonts[] fonts){
        UtilityTool uTool = new UtilityTool();

        uTool.setUp(fonts, 0, "Cinzel/static/Cinzel-Medium");
        uTool.setUp(fonts, 1, "Open_Sans/static/OpenSans-Regular");
        uTool.setUp(fonts, 2, "Oswald/static/Oswald-Light");
    }
}
