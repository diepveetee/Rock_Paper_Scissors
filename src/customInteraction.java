import javax.swing.*;
import java.awt.*;

public class customInteraction {

    public static JLabel createLabel(
            String text,
            String fontName,
            int fontStyle,
            int fontSize,
            Color textColor,
            int hAlign,
            int vAlign
    ) {

        JLabel label = new JLabel(text);
        label.setFont(new Font(fontName, fontStyle, fontSize));
        label.setForeground(textColor);
        label.setHorizontalAlignment(hAlign);
        label.setVerticalAlignment(vAlign);

        return label;
    }

    public static JButton createButton(
            String text,
            String fontName,
            int fontStyle,
            int fontSize,
            Color textColor,
            Color backgroundColor
    ) {
        JButton button = new JButton(text);
        button.setFont(new Font(fontName, fontStyle, fontSize));
        button.setForeground(textColor);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false); // optional: removes the focus rectangle
        return button;
    }

}
