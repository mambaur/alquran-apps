package com.example.alquran_apps.util;

public class Configuration {
    // URL get data
    public static final String baseURLSurat = "https://al-quran-8d642.firebaseio.com/data.json?print=pretty";
    public static final String baseURLJadwal = "https://raw.githubusercontent.com/lakuapik/jadwalsholatorg/master/adzan/";
    public static final String baseURLDetailSurat = "https://raw.githubusercontent.com/penggguna/QuranJSON/master/surah/";
    public static final String baseURLDoa = "https://alquran-93bec.web.app/doa/listdoa.json";
    public static final String baseURLDetailDoa = "https://alquran-93bec.web.app/doa/byId/";
    public static final String webViewURL = "https://caraguna.com";
    public static final String baseURLDonasi = "https://saweria.co/alquranapps";

    // Intent
    public static final String NOMOR_SURAT = "no_surat";
    public static final String ID_DOA = "id_doa";
    public static final String NAMA_SURAT = "nama_surat";

    // Google Map
    public static final String baseURLmap = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    // AutoCompleteTextView Data
    public static final String[] SURAT = new String[]{
            "Al Fatihah - 1", "Al Baqarah - 2", "Ali Imran - 3", "An Nisaa - 4", "Al Maidah - 5", "Al An'am - 6", "Al A'raf - 7", "Al Anfaal - 8",
            "At Taubah - 9", "Yunus - 10", "Huud - 11", "Yusuf - 12", "Ar Ra'du - 13", "Ibrahim - 14", "Al Hijr - 15", "An Nahl - 16", "Al Israa' - 17",
            "Al Kahfi - 18", "Maryam - 19", "Thaahaa - 20", "Al Anbiyaa - 21", "Al Hajj - 22", "Al Mu'minun - 23", "An Nuur - 24", "Al Furqaan - 25",
            "Asy Syu'ara - 26", "An Naml - 27", "Al Qashash - 28", "Al 'Ankabut - 29", "Ar Ruum - 30", "Luqman - 31", "As Sajdah - 32", "Al Ahzab - 33",
            "Saba' - 34", "Faathir - 35", "Yaa Siin - 36", "Ash Shaaffat - 37", "Shaad - 38", "Az Zumar - 39", "Al Ghaafir - 40", "Al Fushilat - 41",
            "Asy Syuura - 42", "Az Zukhruf - 43", "Ad Dukhaan - 44", "Al Jaatsiyah - 45", "Al Ahqaaf - 46", "Muhammad - 47", "Al Fath - 48", "Al Hujuraat - 49",
            "Qaaf - 50", "Adz Dzaariyaat - 51", "Ath Thuur - 52", "An Najm - 53", "Al Qamar - 54", "Ar Rahmaan - 55", "Al Waaqi'ah - 56", "Al Hadiid - 57",
            "Al Mujaadalah - 58", "Al Hasyr - 59", "Al mumtahanah - 60", "Ash Shaff - 61", "Al Jumuah - 62", "Al Munafiqun - 63", "Ath Taghabun - 64",
            "Ath Thalaaq - 65", "At Tahriim - 66", "Al Mulk - 67", "Al Qalam - 68", "Al Haaqqah - 69", "Al Ma'aarij - 70", "Nuh - 71", "Al Jin - 72",
            "Al Muzammil - 73", "Al Muddastir - 74", "Al Qiyaamah - 75", "Al Insaan - 76", "Al Mursalaat - 77", "An Naba' - 78", "An Naazi'at - 79",
            "Abasa - 80", "At Takwiir - 81", "Al Infithar - 82", "Al Muthaffifin - 83", "Al Insyiqaq - 84", "Al Buruuj - 85", "Ath Thariq - 86",
            "Al A'laa - 87", "Al Ghaasyiah - 88", "Al Fajr - 89", "Al Balad - 90", "Asy Syams - 91", "Al Lail - 92", "Adh Dhuhaa - 93", "Asy Syarh - 94",
            "At Tiin - 95", "Al 'Alaq - 96", "Al Qadr - 97", "Al Bayyinah - 98", "Az Zalzalah - 99", "Al 'Aadiyah - 100", "Al Qaari'ah - 101",
            "At Takaatsur - 102", "Al 'Ashr - 103", "Al Humazah - 104", "Al Fiil - 105", "Quraisy - 106", "Al Maa'uun - 107", "Al Kautsar - 108",
            "Al Kafirun - 109", "An Nashr - 110", "Al Lahab - 111", "Al Ikhlash - 112", "Al Falaq - 113", "An Naas - 114"
    };

    // Error message volley
    public static final String VOLLEY_ERROR_CONNECTION = "Koneksi kamu bermasalah sob!";
    public static final String VOLLEY_SERVER_ERROR = "Sorry sob, server kami sedang bermasalah!";
    public static final String VOLLEY_AUTH_ERROR = "Sorry sob, aplikasi sedang maintenance.";
    public static final String VOLLEY_PARSE_ERROR = "Sorry sob, aplikasi masih dalam perbaikan.";
    public static final String VOLLEY_NO_INTERNET = "Coba cek koneksi internet kamu sob!";
    public static final String VOLLEY_TIME_OUT = "Koneksi internet kamu kayaknya agak lambat deh, coba muat ulang sob!";
}
