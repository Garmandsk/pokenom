package font;

import main.UtilityTool;

public class FontManager {
    public Fonts[] fonts;

    public FontManager(){
        fonts = new Fonts[10];
        setFont();
    }

    public void setFont(){
        UtilityTool uTool = new UtilityTool();

        uTool.setUp(fonts, 0, "Cinzel/static/Cinzel-Medium");
        uTool.setUp(fonts, 1, "Open_Sans/static/OpenSans-Regular");
    }
}
