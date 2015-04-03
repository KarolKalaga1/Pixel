
package com.project.tools;

import com.project.enums.ColorEnum;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Karol
 */
public class SelectedColor {
    
   Map<ColorEnum,Color> Color = new HashMap<ColorEnum,Color>();

    public Map<ColorEnum, Color> getColor() {
        return Color;
    }

    public void setColor(Map<ColorEnum, Color> Color) {
        this.Color = Color;
    }
    public void setNewColor(ColorEnum enu, Color color)
    {
        this.Color.put(enu, color);
    }
    
    public Color getValue(ColorEnum key)
    {
        return Color.get(key);
    }
    
}
