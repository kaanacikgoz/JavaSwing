package view;

import business.UserController;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class UserView extends JFrame {
    private JPanel container;
    private JTabbedPane tab_menu;
    private JPanel pnl_user;
    private JScrollPane scrl_user;
    private JTable tbl_user;
    private JButton btn_user_new;
    private final UserController userController;
    private final JPopupMenu user_popup;
    private final DefaultTableModel mdl_user;

    public UserView() {
        this.add(container);
        this.setTitle("Kullanıcı Yönetimi");
        this.setSize(500, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
        this.setLocation(x, y);
        setVisible(true);
        this.userController = new UserController();
        mdl_user = new DefaultTableModel();
        this.user_popup = new JPopupMenu();

        Object[] columnUser = {"ID", "Name", "Mail", "Password", "Type", "Gender"};
        mdl_user.setColumnIdentifiers(columnUser);
        ArrayList<User> users = this.userController.findAll();
        for (User user : users) {
            Object[] row = {
                    user.getId(),
                    user.getName(),
                    user.getMail(),
                    user.getPassword(),
                    user.getType(),
                    user.getGender()
            };
            mdl_user.addRow(row);
        }
        this.tbl_user.setModel(mdl_user);
        this.tbl_user.setEnabled(false);
        this.tbl_user.getTableHeader().setReorderingAllowed(false);
        this.tbl_user.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_user.rowAtPoint(e.getPoint());
                if (selectedRow != -1) {
                    tbl_user.setRowSelectionInterval(selectedRow, selectedRow);
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    user_popup.show(tbl_user, e.getX(), e.getY());
                }
            }
        });
        this.user_popup.add("Update").addActionListener(_ -> {
            int selectedId = Integer.parseInt(tbl_user.getValueAt(tbl_user.getSelectedRow(), 0).toString());
            User selectedUser = this.userController.getById(selectedId);
            EditView editView = new EditView(selectedUser);
            editView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTableData();
                }
            });
        });
        this.user_popup.add("Delete").addActionListener(_ -> {
            int selectedId = Integer.parseInt(tbl_user.getValueAt(tbl_user.getSelectedRow(), 0).toString());
            User selectedUser = this.userController.getById(selectedId);
            DeleteView deleteView = new DeleteView(selectedUser);
            deleteView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTableData();
                }
            });
        });

        this.tbl_user.setComponentPopupMenu(user_popup);

        btn_user_new.addActionListener(_ -> {
            EditView editView = new EditView(new User());
            editView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTableData();
                }
            });
        });
    }

    private void refreshTableData() {
        this.mdl_user.setRowCount(0);
        ArrayList<User> users = this.userController.findAll();
        for (User user : users) {
            Object[] row = {
                    user.getId(),
                    user.getName(),
                    user.getMail(),
                    user.getPassword(),
                    user.getType(),
                    user.getGender()
            };
            this.mdl_user.addRow(row);
        }
    }

}