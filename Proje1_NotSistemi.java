/**
 * Ad Soyad: [Ömer Doğan]
 * Öğrenci No: [250541101]
 * Proje: [SinemaBileti]
 * Tarih: [03.12.2025]
 */

import java.util.Scanner;

public class OgrenciNotSistemi {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- ÖĞRENCİ NOT DEĞERLENDİRME SİSTEMİ ---");

        // 1. GİRDİLERİN ALINMASI
        // Not: Java'da "final" özel bir kelime olduğu için değişken adını "finalNot" yaptık.
        
        System.out.print("Vize Notunuz (0-100): ");
        int vize = scan.nextInt();

        System.out.print("Final Notunuz (0-100): ");
        int finalNot = scan.nextInt();

        System.out.print("Ödev Notunuz (0-100): ");
        int odev = scan.nextInt();

        // 2. HESAPLAMALAR VE KONTROLLER
        double ortalama = calculateAverage(vize, finalNot, odev);
        char harfNotu = getLetterGrade(ortalama);
        boolean gectiMi = isPassingGrade(ortalama);
        boolean onurListesi = isHonorList(ortalama, vize, finalNot, odev);
        boolean butunlemeHakki = hasRetakeRight(ortalama);

        // 3. RAPORLAMA (ÇIKTI)
        System.out.println("\n--- SONUÇ RAPORU ---");
        System.out.printf("Ortalama      : %.2f\n", ortalama);
        System.out.println("Harf Notu     : " + harfNotu);
        
        if (gectiMi) {
            System.out.println("Durum         : GEÇTİ");
            
            // Sadece geçenler onur listesine girebilir
            if (onurListesi) {
                System.out.println("Tebrikler! ONUR LİSTESİNE GİRDİNİZ! ★★★");
            }
        } else {
            System.out.println("Durum         : KALDI");
            
            // Sadece kalanlar için bütünleme kontrolü
            if (butunlemeHakki) {
                System.out.println("Uyarı         : BÜTÜNLEME HAKKINIZ VAR (Ortalama 40-50 arası)");
            } else {
                System.out.println("Uyarı         : Bütünleme hakkınız yok, dersi tekrar almalısınız.");
            }
        }

        System.out.println("--------------------");
        scan.close();
    }

    // --- ZORUNLU METOTLAR ---

    // 1. Ortalama Hesaplama (Vize %30 + Final %40 + Ödev %30)
    public static double calculateAverage(int vize, int finalNot, int odev) {
        return (vize * 0.30) + (finalNot * 0.40) + (odev * 0.30);
    }

    // 2. Geçme Kontrolü (Ortalama 50 ve üzeri)
    public static boolean isPassingGrade(double ortalama) {
        return ortalama >= 50;
    }

    // 3. Harf Notu Belirleme
    public static char getLetterGrade(double ortalama) {
        if (ortalama >= 90) {
            return 'A';
        } else if (ortalama >= 80) {
            return 'B';
        } else if (ortalama >= 70) {
            return 'C';
        } else if (ortalama >= 50) {
            return 'D'; // Geçme notu sınırı
        } else {
            return 'F'; // Kalma
        }
    }

    // 4. Onur Listesi Kontrolü
    // Kural: Ortalama >= 85 VE tüm notlar >= 70 olmalı
    public static boolean isHonorList(double ortalama, int vize, int finalNot, int odev) {
        boolean yuksekOrtalama = (ortalama >= 85);
        boolean notlarYuksek = (vize >= 70 && finalNot >= 70 && odev >= 70);
        
        return (yuksekOrtalama && notlarYuksek);
    }

    // 5. Bütünleme Hakkı Kontrolü
    // Kural: 40 <= Ortalama < 50
    public static boolean hasRetakeRight(double ortalama) {
        return (ortalama >= 40 && ortalama < 50);
    }
}
