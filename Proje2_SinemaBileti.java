/**
 * Ad Soyad: [Ömer Doğan]
 * Öğrenci No: [250541101]
 * Proje: [SinemaBileti]
 * Tarih: [03.12.2025]
 */


import java.util.Scanner;

public class SinemaBiletSistemi {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- SİNEMA BİLETİ FİYATLANDIRMA SİSTEMİ ---");

        // 1. GİRDİLERİN ALINMASI
        System.out.println("Lütfen günü seçiniz (1-7):");
        System.out.println("1:Pzt 2:Sal 3:Çar 4:Per 5:Cum 6:Cmt 7:Paz");
        System.out.println("lütfen 1 ile 7 arasında bir dayı giriniz ");
        int gun = scan.nextInt();

        System.out.print("Filmin saati (0-23 arası tam sayı): ");
        int saat = scan.nextInt();

        System.out.print("Yaşınız: ");
        int yas = scan.nextInt();

        System.out.println("Mesleğiniz:");
        System.out.println("1: Öğrenci\n2: Öğretmen\n3: Diğer");
        int meslek = scan.nextInt();

        System.out.println("Film Türü:");
        System.out.println("1: 2D\n2: 3D\n3: IMAX\n4: 4DX");
        int filmTuru = scan.nextInt();

        // 2. ÇIKTI ÜRETİMİ (Tüm hesaplamalar bu metodun içinde zincirleme çağrılacak)
        System.out.println("\n--------------------------------");
        generateTicketInfo(gun, saat, yas, meslek, filmTuru);
        System.out.println("--------------------------------");
        
        scan.close();
    }

    // --- ZORUNLU METOTLAR ---

    // 1. Hafta sonu kontrolü (Cumartesi: 6, Pazar: 7)
    public static boolean isWeekend(int gun) {
        // İlişkisel ve Mantıksal operatör kullanımı
        return (gun == 6 || gun == 7);
    }

    // 2. Matine kontrolü (12:00 öncesi)
    public static boolean isMatinee(int saat) {
        return (saat < 12);
    }

    // 3. Temel Fiyat Hesaplama
    public static double calculateBasePrice(int gun, int saat) {
        boolean haftaSonu = isWeekend(gun);
        boolean matine = isMatinee(saat);

        // İç içe if-else yapısı ile temel fiyat belirleme
        if (haftaSonu) {
            if (matine) {
                return 55.0; // Hafta sonu matine
            } else {
                return 85.0; // Hafta sonu normal
            }
        } else {
            if (matine) {
                return 45.0; // Hafta içi matine
            } else {
                return 65.0; // Hafta içi normal
            }
        }
    }

    // 4. İndirim Oranı Hesaplama
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double indirimOrani = 0.0;

        // Switch-case zorunluluğu: Meslek seçimi
        switch (meslek) {
            case 1: // Öğrenci
                // Öğrenci indirimi günlere göre değişiyor
                // Pzt(1) - Prş(4): %20, Cum(5) - Paz(7): %15
                if (gun >= 1 && gun <= 4) {
                    indirimOrani = 0.20;
                } else {
                    indirimOrani = 0.15;
                }
                break;

            case 2: // Öğretmen
                // Sadece Çarşamba (3. gün) indirim var
                if (gun == 3) {
                    indirimOrani = 0.35;
                } else {
                    indirimOrani = 0.0; // Diğer günler indirim yok
                }
                break;

            case 3: // Diğer (Meslek indirimi yok, yaşa bakılacak)
            default:
                // Meslek indirimi yoksa yaş indirimlerine bakılır
                if (yas >= 65) {
                    indirimOrani = 0.30;
                } else if (yas < 12) {
                    indirimOrani = 0.25;
                } else {
                    indirimOrani = 0.0;
                }
                break;
        }
        
        // Not: Eğer bir öğrenci 12 yaşından küçükse, yukarıdaki mantıkta
        // meslek "Öğrenci" seçildiği için öğrenci indirimi uygulanır.
        // Bu, genelde istenen davranıştır (kategorik indirim önceliklidir).
        
        return indirimOrani;
    }

    // 5. Film Türü Ekstra Ücreti
    public static double getFormatExtra(int filmTuru) {
        double ekstra = 0.0;
        
        // Switch-case zorunluluğu: Film türü
        switch (filmTuru) {
            case 1: ekstra = 0.0;  break; // 2D
            case 2: ekstra = 25.0; break; // 3D
            case 3: ekstra = 35.0; break; // IMAX
            case 4: ekstra = 50.0; break; // 4DX
            default: ekstra = 0.0;
        }
        
        return ekstra;
    }

    // 6. Son Fiyat Hesaplama
    public static double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double temelFiyat = calculateBasePrice(gun, saat);
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double formatEkstra = getFormatExtra(filmTuru);

        // Matematiksel mantık: (Temel Fiyat - İndirim) + Ekstralar
        double indirimTutari = temelFiyat * indirimOrani;
        double araToplam = temelFiyat - indirimTutari;
        
        return araToplam + formatEkstra;
    }

    // 7. Bilet Bilgisi Yazdırma
    public static void generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        double temel = calculateBasePrice(gun, saat);
        double ekstra = getFormatExtra(filmTuru);
        double indirimOran = calculateDiscount(yas, meslek, gun);
        double toplam = calculateFinalPrice(gun, saat, yas, meslek, filmTuru);

        // Gün ismini yazdırmak için switch (Zorunluluk: Gün seçimi switch)
        String gunIsmi = "";
        switch (gun) {
            case 1: gunIsmi = "Pazartesi"; break;
            case 2: gunIsmi = "Salı"; break;
            case 3: gunIsmi = "Çarşamba"; break;
            case 4: gunIsmi = "Perşembe"; break;
            case 5: gunIsmi = "Cuma"; break;
            case 6: gunIsmi = "Cumartesi"; break;
            case 7: gunIsmi = "Pazar"; break;
            default: gunIsmi = "Hatalı Gün";
        }

        // printf ile formatlı çıktı
        System.out.println("=== BİLET DETAYLARI ===");
        System.out.printf("Gün/Saat      : %s / %02d:00\n", gunIsmi, saat);
        System.out.printf("Film Formatı  : +%.2f TL Ekstra\n", ekstra);
        System.out.printf("Temel Fiyat   : %.2f TL\n", temel);
        System.out.printf("Uyg. İndirim  : %% %.0f \n", (indirimOran * 100));
        System.out.println("--------------------------------");
        System.out.printf("TOPLAM TUTAR  : %.2f TL\n", toplam);
    }
}
