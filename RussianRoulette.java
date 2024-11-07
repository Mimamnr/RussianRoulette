import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Kelas ini mewakili permainan Russian Roulette.
 * Pemain akan mencoba keberuntungannya, dan jika kalah, komputer akan merestart.
 * Permainan dimulai dengan konfirmasi dari pengguna, dan permainan dapat diulang sesuai keinginan pemain.
 */
public class RussianRoulette {
    
    // Jumlah ruang dalam revolver (6 ruang)
    private static final int JUMLAH_RUANG = 6;

    // Pesan peringatan yang ditampilkan sebelum memulai permainan
    private static final String PESAN_PERINGATAN = 
        "Peringatan: Sebelum melanjutkan, pastikan Anda telah menyimpan semua pekerjaan Anda.\n" +
        "Program ini bisa menyebabkan komputer merestart secara tiba-tiba jika Anda kalah dalam permainan.\n" +
        "Dengan melanjutkan, Anda setuju untuk menjalankan program ini dengan risiko Anda sendiri.\n" +
        "Apakah Anda ingin melanjutkan?";

    private Random random;

    /**
     * Konstruktor untuk menginisialisasi objek permainan Russian Roulette.
     * Membuat objek random untuk menghasilkan hasil acak dalam permainan.
     */
    public RussianRoulette() {
        random = new Random();
    }

    /**
     * Menentukan apakah pemain menang atau kalah dalam satu putaran permainan.
     * Pemain kalah jika tembakan pemain mengenai posisi peluru dalam revolver.
     *
     * @return true jika pemain menang, false jika kalah
     */
    public boolean mainPutaran() {
        int posisiPeluru = random.nextInt(JUMLAH_RUANG);
        int tembakanPemain = random.nextInt(JUMLAH_RUANG);

        // Mengembalikan true jika tembakan pemain tidak mengenai posisi peluru
        return tembakanPemain != posisiPeluru;
    }

    /**
     * Merestart komputer jika pemain kalah dalam permainan.
     * Menggunakan perintah sistem untuk merestart komputer.
     * Peringatan: Penggunaan fungsi ini dapat menyebabkan kehilangan data jika ada pekerjaan yang belum disimpan.
     */
    public void restartKomputer() {
        try {
            String command = "shutdown -r -t 15"; // Perintah untuk merestart komputer
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menampilkan dialog konfirmasi untuk memulai permainan.
     * Pemain harus memilih 'Yes' untuk melanjutkan permainan.
     *
     * @return true jika pemain memilih 'Yes', false jika memilih 'No'
     */
    private boolean konfirmasiMulai() {
        int response = JOptionPane.showConfirmDialog(
                null, 
                PESAN_PERINGATAN,
                "Peringatan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return response == JOptionPane.YES_OPTION;
    }

    /**
     * Membuat dan menampilkan jendela utama permainan.
     * Jendela ini memiliki tombol untuk memulai permainan.
     *
     * @return JFrame jendela utama permainan
     */
    private JFrame buatJendelaUtama() {
        JFrame frame = new JFrame("Permainan Russian Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        return frame;
    }

    /**
     * Membuat tombol untuk memulai permainan dan menambahkan aksi pada tombol tersebut.
     *
     * @param frame JFrame tempat tombol akan ditambahkan
     * @return JButton tombol yang digunakan untuk memulai permainan
     */
    private JButton buatTombolMain(JFrame frame) {
        JButton tombolMain = new JButton("Mulai Permainan");
        tombolMain.addActionListener(e -> mulaiPermainan(frame));
        return tombolMain;
    }

    /**
     * Menjalankan permainan Russian Roulette.
     * Pemain akan terus bermain hingga memilih untuk tidak bermain lagi atau kalah.
     *
     * @param frame JFrame tempat permainan akan dijalankan
     */
    private void mulaiPermainan(JFrame frame) {
        boolean mainLagi = true;
        while (mainLagi) {
            if (mainPutaran()) {
                mainLagi = tanyaUntukMainLagi(frame);
            } else {
                restartKomputer();
                return; // Jika kalah, program akan merestart komputer
            }
        }
    }

    /**
     * Menanyakan kepada pemain apakah mereka ingin bermain lagi setelah satu putaran selesai.
     *
     * @param frame JFrame tempat dialog akan ditampilkan
     * @return true jika pemain ingin bermain lagi, false jika tidak
     */
    private boolean tanyaUntukMainLagi(JFrame frame) {
        int replayResponse = JOptionPane.showConfirmDialog(
                frame, 
                "Apakah Anda ingin bermain lagi?",
                "Main Lagi",
                JOptionPane.YES_NO_OPTION
        );
        if (replayResponse != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "Terima kasih telah bermain!");
            return false; // Tidak ingin bermain lagi
        }
        return true; // Ingin bermain lagi
    }

    /**
     * Menampilkan antarmuka permainan dan meminta konfirmasi awal untuk memulai.
     * Jika pemain memilih 'No', permainan dibatalkan.
     */
    public void tampilkan() {
        if (!konfirmasiMulai()) {
            JOptionPane.showMessageDialog(null, "Program dibatalkan oleh pengguna.");
            System.exit(0); // Menghentikan program jika pengguna membatalkan
        }

        JFrame frame = buatJendelaUtama();
        JButton tombolMain = buatTombolMain(frame);

        frame.add(tombolMain);
        frame.setVisible(true);
    }

    /**
     * Titik masuk utama untuk menjalankan permainan Russian Roulette.
     * Membuat objek permainan dan menampilkan antarmuka permainan.
     *
     * @param args Argumen baris perintah (tidak digunakan)
     */
    public static void main(String[] args) {
        RussianRoulette game = new RussianRoulette();
        game.tampilkan();
    }
}
