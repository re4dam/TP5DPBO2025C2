import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    // database untuk program ini
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JRadioButton aktifRadioButton;
    private JRadioButton cutiRadioButton;
    private ButtonGroup statusGroup;
    private JLabel statusField;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // Buat grup agar hanya satu yang bisa dipilih
        statusGroup = new ButtonGroup();
        statusGroup.add(aktifRadioButton);
        statusGroup.add(cutiRadioButton);

        // Set default status ke kosong
        aktifRadioButton.setSelected(false);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                } else {
                    clearForm();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol ditekan
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedStatus = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                if (selectedStatus.equals("Aktif")) {
                    aktifRadioButton.setSelected(true);
                } else {
                    cutiRadioButton.setSelected(true);
                }

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama","Jenis Kelamin", "Status"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * from mahasiswa");

            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[5];

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("status_mahasiswa");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return temp; // return juga harus diganti
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String status = aktifRadioButton.isSelected() ? "Aktif" : "Cuti";

        // tambahkan data ke dalam database
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + status + "')";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

    }

    public void updateData() {
        // Ambil data dari form
        String nimLama = mahasiswaTable.getValueAt(mahasiswaTable.getSelectedRow(), 1).toString();
        String nimBaru = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String status = aktifRadioButton.isSelected() ? "Aktif" : "Cuti";
        int id = -1;

        // Cari ID berdasarkan NIM
        try {
            ResultSet resultSet = database.selectQuery("SELECT id FROM mahasiswa WHERE nim = '" + nimLama + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            } else {
                System.out.println("NIM tidak ditemukan!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("ID Mahasiswa: " + id);

        // mengupdate data ke dalam database
        String sql = "UPDATE mahasiswa SET nim = '" + nimBaru + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', status_mahasiswa = '" + status + "' WHERE id = " + id;
        System.out.println(sql);
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");

    }

    public void deleteData() {
        String nim = mahasiswaTable.getValueAt(mahasiswaTable.getSelectedRow(), 1).toString();
        int id;

        // Cari ID berdasarkan NIM
        try {
            ResultSet resultSet = database.selectQuery("SELECT id FROM mahasiswa WHERE nim = '" + nim + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            } else {
                System.out.println("NIM tidak ditemukan!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // menghapus data dari database
        String sql = "DELETE FROM mahasiswa WHERE id = " + id + "";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // kosongkan pilihan dari radio button
        statusGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
