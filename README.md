# 💧 AquaTrack (Water Intake Tracker)

AquaTrack, kullanıcıların günlük su tüketimlerini takip etmelerini, hedeflerine ulaşmalarını ve sağlıklı bir hidrasyon alışkanlığı kazanmalarını sağlayan modern bir Android uygulamasıdır. 

Kullanıcı dostu arayüzü, Jetpack Compose ile geliştirilmiş olup akıcı animasyonlar (sıvı dalga efekti) ve detaylı istatistiklerle desteklenmektedir.

## 🚀 Özellikler

- **Görsel Dashboard:** Günlük su içme hedefinizi dairesel ve dalgalı bir animasyon eşliğinde takip edin.
- **Hızlı Ekleme (Quick Add):** Tek tıkla Bardak (250ml), Şişe (500ml) veya Termos (750ml) seçenekleriyle su ekleyin.
- **Saatlik Dağılım:** Gün içinde suyu hangi saatlerde tükettiğinizi gösteren dinamik bar grafiği.
- **Geri Alma (Undo):** Yanlışlıkla eklenen son su kaydını tek tuşla geri alma imkanı.
- **Kapsamlı Geçmiş:** Önceki günlerin toplam su tüketimlerini liste halinde görüntüleme.
- **Kişiselleştirilmiş Profil:** Kilo ve yaş bilgilerinize göre otomatik günlük hedef hesaplama veya manuel "Özel Hedef" belirleyebilme.
- **Ana Ekran Widget'ı:** Uygulamaya girmeden su durumunuzu takip edebilmeniz için Jetpack Glance tabanlı modern widget desteği.
- **Hatırlatıcılar:** (WorkManager tabanlı) Gün içinde su içmenizi hatırlatan periyodik bildirim altyapısı.

## 🛠️ Teknolojiler & Mimari

Bu proje, modern Android geliştirme standartlarına (Modern Android Development - MAD) uygun olarak geliştirilmiştir. **Clean Architecture (Temiz Mimari)** ve **MVVM (Model-View-ViewModel)** desenleri kullanılmıştır.

- **[Kotlin](https://kotlinlang.org/):** %100 Kotlin ile yazılmıştır.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** Tamamen modern, bildirimsel (declarative) UI kütüphanesi ile arayüz geliştirilmiştir.
- **[Dagger Hilt](https://dagger.dev/hilt/):** Bağımlılık enjeksiyonu (Dependency Injection) için kullanılmıştır.
- **[Room Database](https://developer.android.com/training/data-storage/room):** Uygulama içi yerel veri tabanı (Kayıtların tutulması).
- **[Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore):** Profil ve ayar verilerinin asenkron olarak saklanması.
- **[Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-overview.html):** Asenkron işlemler ve reaktif veri akışı (Reactive Programming) için.
- **[Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation):** Ekranlar arası (Dashboard, Geçmiş, Profil) yönlendirmeler.
- **[WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager):** Arka planda çalışan periyodik hatırlatıcılar.
- **[Glance](https://developer.android.com/jetpack/compose/glance):** Compose tabanlı uygulama ana ekran widget'ı tasarımı.

## 📱 Ekran Görüntüleri

| Dashboard |              Geçmiş | Profil |

<img width="240" src="https://github.com/user-attachments/assets/ce40ad15-9d3b-4245-94db-68de804e8d5e" />     
<img width="240" src="https://github.com/user-attachments/assets/aff0a9e1-6c0d-4e79-9df9-b721ad28e8d1" />     
<img width="240" src="https://github.com/user-attachments/assets/018cce5a-dca4-4ca9-9b51-81af3867134e" />   



## 💻 Kurulum ve Çalıştırma

Projeyi bilgisayarınızda çalıştırmak için aşağıdaki adımları izleyebilirsiniz:

1. **Repoyu Klonlayın:**
   ```bash
   git clone https://github.com/sumeyraltas/WaterIntakeTracker.git
   ```
2. **Android Studio'da Açın:**
   - Android Studio'yu başlatın.
   - `File > Open` menüsünden klonladığınız dizini seçin.
3. **Gradle Senkronizasyonu:**
   - Android Studio'nun bağımlılıkları indirmesini ve Gradle senkronizasyonunu bitirmesini bekleyin.
4. **Çalıştırın:**
   - Emülatörünüzü veya fiziksel Android cihazınızı (USB Hata Ayıklama açık halde) bağlayın.
   - `Run` (Yeşil Oynat) butonuna tıklayın.

## 🤝 Katkıda Bulunma

Bu proje açık kaynaklıdır. Geliştirmelere destek olmak isterseniz:
1. Projeyi fork'layın.
2. Kendi özellik dalınızı oluşturun (`git checkout -b feature/YeniOzellik`).
3. Değişikliklerinizi commit'leyin (`git commit -m 'feat: Yeni özellik eklendi'`).
4. Dalınızı push'layın (`git push origin feature/YeniOzellik`).
5. Bir Pull Request açın!

---

