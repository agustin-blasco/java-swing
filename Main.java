import javax.swing.SwingUtilities;

import src.model.EventoManager;
import src.view.MenuPrincipal;
import src.controller.MenuPrincipalController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventoManager model = new EventoManager();
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            MenuPrincipalController menuPrincipalController = new MenuPrincipalController(menuPrincipal, model);

            menuPrincipal.setVisible(true);
        });
        
    }
}
