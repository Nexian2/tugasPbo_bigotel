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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FormAdmin extends JFrame {
    private DefaultTableModel model;
    private JLabel lblJudul, lblNamaLomba;
    private JTextField txtNamaLomba;
    private JButton btnSimpanLomba, btnUbahStatus, btnLogout;
    private JTable tblPendaftar;
    private JScrollPane scrollPane;

    public FormAdmin() {
        initComponents();
        setLocationRelativeTo(null);
        loadDataPendaftar();
    }

    private void initComponents() {
        setTitle("Panel Admin - BI GOT TALENT");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblJudul = new JLabel("PANEL ADMIN - BI GOT TALENT");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBounds(200, 15, 350, 30);
        add(lblJudul);

        lblNamaLomba = new JLabel("Nama Mata Lomba Baru:");
        lblNamaLomba.setBounds(30, 65, 180, 25);
        add(lblNamaLomba);

        txtNamaLomba = new JTextField();
        txtNamaLomba.setBounds(200, 65, 280, 25);
        add(txtNamaLomba);

        btnSimpanLomba = new JButton("Tambah Lomba");
        btnSimpanLomba.setBounds(490, 65, 170, 25);
        add(btnSimpanLomba);

        tblPendaftar = new JTable();
        scrollPane = new JScrollPane(tblPendaftar);
        scrollPane.setBounds(30, 110, 630, 300);
        add(scrollPane);

        btnUbahStatus = new JButton("Ubah Status Peserta Terpilih");
        btnUbahStatus.setBounds(30, 425, 230, 35);
        add(btnUbahStatus);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(540, 425, 120, 35);
        add(btnLogout);

        btnSimpanLomba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSimpanLombaActionPerformed(evt);
            }
        });

        btnUbahStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnUbahStatusActionPerformed(evt);
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

    public void loadDataPendaftar() {
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Siswa");
        model.addColumn("Mata Lomba");
        model.addColumn("Status Tahapan");
        tblPendaftar.setModel(model);
        
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            String sql = "SELECT pendaftaran.id, users.username, lomba.nama_lomba, pendaftaran.status " +
                         "FROM pendaftaran " +
                         "JOIN users ON pendaftaran.user_id = users.id " +
                         "JOIN lomba ON pendaftaran.lomba_id = lomba.id";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                model.addRow(new Object[]{
                    r.getString("id"),
                    r.getString("username"),
                    r.getString("nama_lomba"),
                    r.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void btnSimpanLombaActionPerformed(ActionEvent evt) {                                               
        try {
            if (txtNamaLomba.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama lomba tidak boleh kosong!");
                return;
            }
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            String sql = "INSERT INTO lomba (nama_lomba) VALUES ('" + txtNamaLomba.getText() + "')";
            s.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Mata Lomba berhasil ditambahkan!");
            txtNamaLomba.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal tambah lomba: " + e.getMessage());
        }
    }                                              

    private void btnUbahStatusActionPerformed(ActionEvent evt) {                                              
        int baris = tblPendaftar.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data siswa di tabel terlebih dahulu!");
            return;
        }
        String idPendaftaran = model.getValueAt(baris, 0).toString();
        String statusBaru = JOptionPane.showInputDialog(this, "Masukkan Status Baru (Contoh: Tahap Briefing / Pelatihan / Selesai):");
        
        if (statusBaru != null && !statusBaru.trim().isEmpty()) {
            try {
                Connection c = Koneksi.configDB();
                Statement s = c.createStatement();
                String sql = "UPDATE pendaftaran SET status='" + statusBaru + "' WHERE id='" + idPendaftaran + "'";
                s.executeUpdate(sql);
                JOptionPane.showMessageDialog(this, "Status berhasil diperbarui!");
                loadDataPendaftar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal update status: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAdmin().setVisible(true);
            }
        });
    }
}
