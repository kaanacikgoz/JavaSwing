package view;

import business.UserController;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class EditView extends JFrame {
    private JPanel container;
    private JTextField fld_name;
    private JTextField fld_mail;
    private JPasswordField fld_password;
    private JComboBox<User.Type> cmb_type;
    private JRadioButton radio_female;
    private JRadioButton radio_male;
    private JCheckBox check_agree;
    private JButton btn_save;
    private JLabel lbl_name;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JLabel lbl_type;
    private JLabel lbl_gender;
    private final User user;
    private final UserController userController;

    public EditView(User user) {
        this.add(container);
        this.setTitle("Kullanici Ekle/Düzenle");
        this.setSize(300,500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
        this.setLocation(x,y);
        setVisible(true);

        this.user = user;
        this.userController = new UserController();
        DefaultComboBoxModel<User.Type> cmdl_type = new DefaultComboBoxModel<>(User.Type.values());
        this.cmb_type.setModel(cmdl_type);

        if (this.user.getId() != 0) {
            this.fld_name.setText(this.user.getName());
            this.fld_mail.setText(this.user.getMail());
            this.fld_password.setText(this.user.getPassword());
            this.cmb_type.getModel().setSelectedItem(this.user.getType());
            if (user.getGender() == User.Gender.FEMALE) {
                this.radio_female.setSelected(true);
            } else {
                this.radio_male.setSelected(true);
            }
        }

        check_agree.addActionListener(_ -> this.btn_save.setEnabled(this.check_agree.isSelected()));

        btn_save.addActionListener(_ -> {
            if (this.fld_password.getText().isEmpty() ||
                    this.fld_mail.getText().isEmpty() ||
                    this.fld_name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Lutfen tum alanlari doldurunuz.",
                        "Eksik veri",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                this.user.setName(this.fld_name.getText());
                this.user.setMail(this.fld_mail.getText());
                this.user.setPassword(this.fld_password.getText());
                this.user.setType((User.Type) this.cmb_type.getSelectedItem());
                if (this.radio_female.isSelected()) {
                    this.user.setGender(User.Gender.FEMALE);
                } else {
                    this.user.setGender(User.Gender.MALE);
                }

                if (this.user.getId() != 0) {
                    if (this.userController.update(this.user)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Veri güncellendi",
                                "Basarili",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    }
                } else {
                    if (this.userController.save(this.user)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Veri eklendi",
                                "Basarili",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    }
                }
            }
        });
    }

}
