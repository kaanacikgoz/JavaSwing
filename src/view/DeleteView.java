package view;

import business.UserController;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class DeleteView extends JFrame {
    private JPanel container;
    private JButton btn_yes;
    private JButton btn_cancel;
    private JLabel fld_lbl;
    private final User user;
    private final UserController userController;

    public DeleteView(User user) {
        this.add(container);
        this.setTitle("Kullanici Sil");
        this.setSize(250,250);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
        this.setLocation(x,y);
        setVisible(true);

        this.user = user;
        this.userController = new UserController();

        btn_yes.addActionListener(_ -> {
            if (this.userController.delete(this.user)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Veri silindi",
                        "Basarili",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
        });

        btn_cancel.addActionListener(_ -> dispose());
    }

}
