package form;

import koneksi.Koneksi;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FormSiswa extends JFrame {
    private int idLoginSiswa;
    private JLabel lblJudul, lblPilih, lblStatus;
    private JComboBox<String> cmbLomba;
    private JButton btnDaftar, btnLogout;

    public FormSiswa() {
        this(2);
    }

    public FormSiswa(int idUser) {
        this.idLoginSiswa = idUser;
        initComponents();
        setLocationRelativeTo(null);
        loadPilihanLomba();
        cekStatusSiswa();
    }

    private void initComponents() {
        setTitle("Dashboard Siswa - BI GOT TALENT");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblJudul = new JLabel("DASHBOARD SISWA - BI GOT TALENT");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        lblJudul.setBounds(90, 20, 350, 30);
        add(lblJudul);

        lblPilih = new JLabel("Pilih Mata Lomba:");
        lblPilih.setBounds(40, 75, 140, 25);
        add(lblPilih);

        cmbLomba = new JComboBox<>();
        cmbLomba.setBounds(180, 75, 270, 25);
        add(cmbLomba);

        btnDaftar = new JButton("Daftar Sekarang");
        btnDaftar.setBounds(180, 115, 270, 35);
        add(btnDaftar);

        lblStatus = new JLabel("Status Tahapan Anda: Memuat data...");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        lblStatus.setBounds(40, 175, 420, 30);
        add(lblStatus);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(330, 225, 120, 35);
        add(btnLogout);

        btnDaftar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new FormLogin().setVisible(true);
                dispose();
            }
        });
    }

    private void loadPilihanLomba() {
        cmbLomba.removeAllItems();
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM lomba");
            while (r.next()) {
                cmbLomba.addItem(r.getString("id") + " - " + r.getString("nama_lomba"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat lomba: " + e.getMessage());
        }
    }

    private void cekStatusSiswa() {
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM pendaftaran WHERE user_id='" + idLoginSiswa + "'");
            if (r.next()) {
                String status = r.getString("status");
                lblStatus.setText("Status Tahapan Anda: " + status);
            } else {
                lblStatus.setText("Status Tahapan Anda: Belum Mendaftarkan Diri");
            }
        } catch (Exception e) {
            lblStatus.setText("Status: Gagal memuat data");
        }
    }

    private void btnDaftarActionPerformed(ActionEvent evt) {                                        
        try {
            if (cmbLomba.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Pilihan lomba tidak tersedia!");
                return;
            }
            String selectedItem = cmbLomba.getSelectedItem().toString();
            String idLomba = selectedItem.split(" - ")[0];
            
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            
            ResultSet r = s.executeQuery("SELECT * FROM pendaftaran WHERE user_id='" + idLoginSiswa + "'");
            if (r.next()) {
                JOptionPane.showMessageDialog(this, "Anda sudah terdaftar di lomba!");
                return;
            }
            
            String sql = "INSERT INTO pendaftaran (user_id, lomba_id, status) VALUES ('" 
                    + idLoginSiswa + "', '" + idLomba + "', 'Dokumen dalam Tinjauan')";
            s.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Pendaftaran Berhasil! Menunggu verifikasi admin.");
            cekStatusSiswa();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mendaftar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSiswa().setVisible(true);
            }
        });
    }
}
