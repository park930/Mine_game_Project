package MineGame.listCallRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DoubleClickListener extends MouseAdapter {
    private Runnable onDoubleClick;

    public DoubleClickListener(Runnable onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
            onDoubleClick.run();
        }
    }
}
