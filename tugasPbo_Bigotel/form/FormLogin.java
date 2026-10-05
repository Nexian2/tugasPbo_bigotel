package form;

import koneksi.Koneksi;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FormLogin extends JFrame {

    private JLabel lblJudul, lblUser, lblPass;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public FormLogin() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LOGIN BI GOT TALENT");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblJudul = new JLabel("LOGIN BI GOT TALENT");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBounds(90, 20, 250, 30);
        add(lblJudul);

        lblUser = new JLabel("Username");
        lblUser.setBounds(40, 70, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(140, 70, 200, 25);
        add(txtUsername);

        lblPass = new JLabel("Password");
        lblPass.setBounds(40, 110, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 110, 200, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 160, 200, 30);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
    }

    private void btnLoginActionPerformed(ActionEvent evt) {                                         
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            
            String sql = "SELECT * FROM users WHERE username='" + txtUsername.getText() 
                    + "' AND password='" + new String(txtPassword.getPassword()) + "'";
            ResultSet r = s.executeQuery(sql);
            
            if (r.next()) {
                String role = r.getString("role");
                int idUser = r.getInt("id");
                
                if (role.equals("admin")) {
                    JOptionPane.showMessageDialog(this, "Login Berhasil sebagai Admin!");
                    new FormAdmin().setVisible(true);
                    this.dispose();
                } else if (role.equals("siswa")) {
                    JOptionPane.showMessageDialog(this, "Login Berhasil sebagai Siswa!");
                    new FormSiswa(idUser).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!");
                txtPassword.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }
}
