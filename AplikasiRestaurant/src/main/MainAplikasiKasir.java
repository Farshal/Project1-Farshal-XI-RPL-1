/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import classes.DaftarMenu;
import classes.Ramen;
import classes.Kuah;
import classes.Toping;
import classes.Minuman;
import classes.Transaksi;
import classes.Menu;
import classes.Pesanan;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * @author Farshal-Thinkpad
 */
public class MainAplikasiKasir {
    public DaftarMenu daftarmenu;
    
    public static double PAJAK_PPN = 0.10;
    public static double BIAYA_SERVICE = 0.05;
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
        String no_transaksi, nama_pemesan, tanggal,no_meja = "";
        String transaksi_lagi = "",pesan_lagi = "", keterangan = "",makan_ditempat = "";
        int jumlah_pesanan,no_menu;
        MainAplikasiKasir app = new MainAplikasiKasir();
        app.generateDaftarmenu();
          System.out.println("======== TRANSAKSI ========");        
        //ambil data transaksi
        System.out.print("No Transaksi : ");
        no_transaksi = input.next();
        System.out.print("Pemesan : ");
        nama_pemesan = input.next();
        System.out.print("Tanggal : [dd-mm-yyyy]");
        tanggal = input.next();
        System.out.print("Makan di tempat? [Y/N]");
        makan_ditempat = input.next();
        
        if (makan_ditempat.equalsIgnoreCase("Y")) {
            System.out.print("Nomor Meja : ");
            no_meja = input.next();
        }
        
        Transaksi trans = new Transaksi(no_transaksi,nama_pemesan,tanggal,no_meja);
        System.out.println("===========PESANAN========");
        int no_kuah;
        do{
            Menu menu_yang_dipilih = app.daftarmenu.pilihMenu();
            jumlah_pesanan = (int) app.cekInputNumber("Jumlah : ");
            
            Pesanan pesanan = new Pesanan(menu_yang_dipilih,jumlah_pesanan);
            trans.tambahPesanan(pesanan);
            if(menu_yang_dipilih.getKategori().equals("Ramen")){
            int jumlah_ramen = jumlah_pesanan;
            do{
                Menu kuah_yang_dipilih = app.daftarmenu.pilihKuah();
                System.out.print("Level : [0-5] : ");
                String level = input.next();
                int jumlah_kuah = 0;
                
                do{
                    jumlah_kuah = (int) app.cekInputNumber("Jumlah : ");
                    if(jumlah_kuah>jumlah_ramen){
                        System.out.print("[Err] jumlah kuah melebihi jumlah kuah ramen");
                    }else{
                        break;
                    }
                     
                }while(jumlah_kuah>jumlah_ramen);
                Pesanan pesanan_kuah = new Pesanan(kuah_yang_dipilih,jumlah_kuah);
                pesanan_kuah.setKeterangan("Level "+ level);
                trans.tambahPesanan(pesanan_kuah);
                jumlah_ramen -= jumlah_kuah;
            }while(jumlah_ramen>0);
                
                
            }else {
                System.out.print("Keterangan [- jika kosong]:");
            }
            
            if(!keterangan.equals("-")){
                pesanan.setKeterangan(keterangan);
            }
            
            System.out.print("Tambah Pesanan Lagi? [Y/N] : ");
            pesan_lagi=input.next();
            
            
        }while(pesan_lagi.equalsIgnoreCase("Y"));
        trans.cetakStruk();
        
        double total_pesanan = trans.hitungTotalPesanan();
        System.out.print("============");
        System.out.print("Total : \t\t");
        trans.setPajak(PAJAK_PPN);
        double ppn = trans.hitungPajak();
        System.out.println("Pajak 10% : \t\t" + ppn);
        double biaya_service = 0;
        if(makan_ditempat.equals("Y")){
            trans.setBiayaService(BIAYA_SERVICE);
            biaya_service = trans.hitungBiayaService();
            System.out.println("Biaya Service 5% = \t\t"+biaya_service);
        }
        System.out.println("Total : \t\t"+trans.hitungTotalBayar(ppn, biaya_service));
        
        double kembalian = 0;
        
        do{
            double uang_bayar =app.cekInputNumber("Uang Bayar : \t\t");
            kembalian = trans.hitungKembalian(uang_bayar);
            
            if(kembalian < 0){
                System.out.println("[Err] Uang kembalian anda kurang");
            }else{
                System.out.println("Kembalian : \t\t"+kembalian);
                break;
            }
            
        }while(kembalian<0);
        System.out.println("=========TERIMAKASIH=========");
        }
        
    
 
    
  
    public void generateDaftarmenu(){
        daftarmenu = new DaftarMenu();
        daftarmenu.tambahMenu(new Ramen("Ramen Seafood", 25000));
        daftarmenu.tambahMenu(new Ramen("Ramen Original", 18000));
        daftarmenu.tambahMenu(new Ramen("Ramen Vegetarian", 22000));
        daftarmenu.tambahMenu(new Ramen("Ramen Karnivor", 28000));
        daftarmenu.tambahMenu(new Kuah("Kuah Orisinil"));
        daftarmenu.tambahMenu(new Kuah("Kuah Internasional"));
        daftarmenu.tambahMenu(new Kuah("Kuah Spicy Lada"));
        daftarmenu.tambahMenu(new Kuah("Kuah Soto Padang"));
        daftarmenu.tambahMenu(new Toping("Crab Stick Bakar", 6000));
        daftarmenu.tambahMenu(new Toping("Chicken Katsu", 8000));
        daftarmenu.tambahMenu(new Toping("Gyoza Goreng", 4000));
        daftarmenu.tambahMenu(new Toping("Bakso Goreng", 7000));
        daftarmenu.tambahMenu(new Toping("Enoki Goreng", 5000));
        daftarmenu.tambahMenu(new Minuman("Jus Alpukat SPC", 10000));
        daftarmenu.tambahMenu(new Minuman("Jus Stroberi", 11000));
        daftarmenu.tambahMenu(new Minuman("Cappucino COffee", 15000));
        daftarmenu.tambahMenu(new Minuman("Vietnam Dripp", 14000));
        
        daftarmenu.tampilDaftarMenu();
               
}
    
    public double cekInputNumber(String Label){
        try{
            Scanner get_input = new Scanner(System.in);
            System.out.print(Label);
            double nilai = get_input.nextDouble();
           
        }catch(InputMismatchException ex){
            System.out.println("[Err] Harap Masukkan Angka");
           
        }
         return cekInputNumber(Label);
    }

}

    
