/**
 * Ad Soyad: [Ömer Doğan]
 * Öğrenci No: [250541101]
 * Proje: [RestoranSiparisSistemi]
 * Tarih: [03.12.2025]
 */
import java.util.Scanner;

public class RestoranSiparisSistemi {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- AKILLI RESTORAN SİPARİŞ SİSTEMİ ---");

        // 1. GİRDİLERİN ALINMASI (Menü Seçimleri)
        System.out.println("\n--- ANA YEMEKLER ---");
        System.out.println("1. Izgara Tavuk (85 TL)\n2. Adana Kebap (120 TL)\n3. Levrek (110 TL)\n4. Mantı (65 TL)\n0. İstemiyorum");
        System.out.print("Seçiminiz (Sayı girin): ");
        int anaSecim = scan.nextInt();

        System.out.println("\n--- BAŞLANGIÇLAR ---");
        System.out.println("1. Çorba (25 TL)\n2. Humus (45 TL)\n3. Sigara Böreği (55 TL)\n0. İstemiyorum");
        System.out.print("Seçiminiz (Sayı girin): ");
        int araSecim = scan.nextInt();

        System.out.println("\n--- İÇECEKLER ---");
        System.out.println("1. Kola (15 TL)\n2. Ayran (12 TL)\n3. Meyve Suyu (35 TL)\n4. Limonata (25 TL)\n0. İstemiyorum");
        System.out.print("Seçiminiz (Sayı girin): ");
        int icecekSecim = scan.nextInt();

        System.out.println("\n--- TATLILAR ---");
        System.out.println("1. Künefe (65 TL)\n2. Baklava (55 TL)\n3. Sütlaç (35 TL)\n0. İstemiyorum");
        System.out.print("Seçiminiz (Sayı girin): ");
        int tatliSecim = scan.nextInt();

        // 2. DİĞER BİLGİLER (Artık boolean yerine int kullanıyoruz, hata riskini sıfırladık)
        System.out.print("\nSaat kaç? (Örn: 14, 18 gibi tam sayı): ");
        int saat = scan.nextInt();

        System.out.print("Öğrenci misiniz? (1: Evet, 2: Hayır): ");
        int ogrenciCevap = scan.nextInt();
        boolean ogrenci = (ogrenciCevap == 1); // 1 seçilirse true olur

        System.out.print("Hafta içi mi? (1: Evet, 2: Hayır): ");
        int haftaCevap = scan.nextInt();
        boolean haftaIci = (haftaCevap == 1); // 1 seçilirse true olur

        // 3. FİYAT HESAPLAMA ADIMLARI
        
        // Tek tek ürün fiyatlarını metotlardan alıyoruz
        double anaFiyat = getMainDishPrice(anaSecim);
        double araFiyat = getAppetizerPrice(araSecim);
        double icecekFiyat = getDrinkPrice(icecekSecim);
        double tatliFiyat = getDessertPrice(tatliSecim);

        // Özel Durum: Happy Hour İçecek İndirimi
        if (isHappyHour(saat)) {
            icecekFiyat = icecekFiyat * 0.80; 
            System.out.println(">> Happy Hour yakaladınız! İçecek fiyatı düştü.");
        }

        // Ham Toplam
        double toplamTutar = anaFiyat + araFiyat + icecekFiyat + tatliFiyat;

        // Combo Menü Kontrolü
        boolean comboVar = isComboOrder((anaSecim > 0), (icecekSecim > 0), (tatliSecim > 0));

        // Genel İndirimleri Hesapla
        double indirimTutari = calculateDiscount(toplamTutar, comboVar, (ogrenci && haftaIci), saat);
        
        double odenecekTutar = toplamTutar - indirimTutari;

        // Bahşiş Hesapla
        double bahsis = calculateServiceTip(odenecekTutar);

        // 4. FİŞ YAZDIRMA
        System.out.println("\n-------------------------------------");
        System.out.println("          SİPARİŞ FİŞİ");
        System.out.println("-------------------------------------");
        System.out.printf("Ana Yemek   : %.2f TL\n", anaFiyat);
        System.out.printf("Başlangıç   : %.2f TL\n", araFiyat);
        System.out.printf("İçecek      : %.2f TL\n", icecekFiyat);
        System.out.printf("Tatlı       : %.2f TL\n", tatliFiyat);
        System.out.println("-------------------------------------");
        System.out.printf("Ara Toplam  : %.2f TL\n", toplamTutar);
        System.out.printf("İndirimler  : -%.2f TL\n", indirimTutari);
        System.out.printf("Bahşiş (%%10): +%.2f TL\n", bahsis);
        System.out.println("-------------------------------------");
        System.out.printf("GENEL TOPLAM: %.2f TL\n", (odenecekTutar + bahsis));
        
        scan.close();
    }

    // --- ZORUNLU METOTLAR ---

    public static double getMainDishPrice(int secim) {
        double fiyat = 0;
        switch (secim) {
            case 1: fiyat = 85.0; break;
            case 2: fiyat = 120.0; break;
            case 3: fiyat = 110.0; break;
            case 4: fiyat = 65.0; break;
            default: fiyat = 0.0; break;
        }
        return fiyat;
    }

    public static double getAppetizerPrice(int secim) {
        double fiyat = 0;
        switch (secim) {
            case 1: fiyat = 25.0; break;
            case 2: fiyat = 45.0; break;
            case 3: fiyat = 55.0; break;
            default: fiyat = 0.0; break;
        }
        return fiyat;
    }

    public static double getDrinkPrice(int secim) {
        double fiyat = 0;
        switch (secim) {
            case 1: fiyat = 15.0; break;
            case 2: fiyat = 12.0; break;
            case 3: fiyat = 35.0; break;
            case 4: fiyat = 25.0; break;
            default: fiyat = 0.0; break;
        }
        return fiyat;
    }

    public static double getDessertPrice(int secim) {
        double fiyat = 0;
        switch (secim) {
            case 1: fiyat = 65.0; break;
            case 2: fiyat = 55.0; break;
            case 3: fiyat = 35.0; break;
            default: fiyat = 0.0; break;
        }
        return fiyat;
    }

    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return (anaVar && icecekVar && tatliVar);
    }

    public static boolean isHappyHour(int saat) {
        return (saat >= 14 && saat <= 17);
    }

    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int saat) {
        double toplamIndirim = 0.0;
        if (combo) { toplamIndirim += tutar * 0.15; }
        if (tutar > 200) { toplamIndirim += tutar * 0.10; }
        if (ogrenci) { toplamIndirim += tutar * 0.10; }
        return toplamIndirim;
    }

    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
}
