# Computer Room Monitoring System

Hệ thống giám sát phòng máy tính sử dụng ứng dụng Android và IoT (ESP32 + Firebase).

## Tổng quan

Hệ thống gồm 2 phần:
- **Ứng dụng Android (Kotlin)**: Hiển thị dữ liệu cảm biến, điều khiển thiết bị, cảnh báo
- **ESP32 + Cảm biến**: Thu thập dữ liệu nhiệt độ, độ ẩm, chuyển động và gửi lên Firebase

### Kiến trúc hệ thống

```
ESP32 + Sensors  →  WiFi  →  Firebase Realtime Database  →  Android App
                                      ↕
                              Device Control Commands
```

## Tính năng

### Ứng dụng Android
- Đăng nhập / Đăng ký tài khoản (Firebase Authentication)
- **Trang chủ**: Hiển thị tổng quan nhiệt độ, độ ẩm, trạng thái chuyển động
- **Cảm biến**: Chi tiết từng cảm biến với thanh tiến trình
- **Điều khiển**: Bật/tắt quạt, đèn, còi báo động từ xa
- **Lịch sử**: Xem lịch sử dữ liệu cảm biến
- **Cảnh báo**: Thông báo khi nhiệt độ quá cao/thấp hoặc phát hiện chuyển động

### Phần cứng (ESP32)
- Đọc nhiệt độ & độ ẩm (DHT22/DHT11)
- Phát hiện chuyển động (PIR)
- Hiển thị LCD I2C 16x2
- LED trạng thái (xanh: hoạt động, đỏ: cảnh báo)
- Còi buzzer
- Relay điều khiển quạt & đèn
- Gửi dữ liệu lên Firebase qua WiFi
- Nhận lệnh điều khiển từ Firebase

## Cấu trúc thư mục

```
computer-room-monitoring/
├── app/                          # Android App
│   └── src/main/
│       ├── java/.../monitoring/
│       │   ├── ui/
│       │   │   ├── login/        # Màn hình đăng nhập
│       │   │   ├── home/         # Trang chủ + MainActivity
│       │   │   ├── sensor/       # Chi tiết cảm biến
│       │   │   ├── control/      # Điều khiển thiết bị
│       │   │   └── history/      # Lịch sử
│       │   ├── data/
│       │   │   ├── model/        # Data models
│       │   │   └── repository/   # Firebase Repository
│       │   └── viewmodel/        # ViewModels (MVVM)
│       └── res/                  # Layouts, drawables, values
├── esp32/                        # Code ESP32 Arduino
│   └── esp32_firebase.ino
├── build.gradle.kts
└── README.md
```

## Hướng dẫn cài đặt

### 1. Tạo Firebase Project

1. Truy cập [Firebase Console](https://console.firebase.google.com/)
2. Tạo project mới (ví dụ: `computer-room-monitoring`)
3. Bật **Authentication** → **Email/Password**
4. Tạo **Realtime Database** → chọn region → bắt đầu ở **test mode**
5. Vào **Project Settings** → **Add app** → chọn **Android**
   - Package name: `com.computerroom.monitoring`
   - Download file `google-services.json`
   - Đặt file vào thư mục `app/`

### 2. Cài đặt Android App

1. Mở project bằng **Android Studio**
2. Đặt file `google-services.json` vào thư mục `app/`
3. Sync Gradle
4. Chạy app trên thiết bị hoặc emulator

### 3. Cài đặt ESP32

1. Cài **Arduino IDE** và thêm board **ESP32**
2. Cài thư viện:
   - `Firebase ESP32 Client` (by Mobizt)
   - `DHT sensor library` (by Adafruit)
   - `LiquidCrystal_I2C`
3. Mở file `esp32/esp32_firebase.ino`
4. Thay đổi cấu hình:
   ```cpp
   #define WIFI_SSID     "Tên_WiFi"
   #define WIFI_PASSWORD "Mật_khẩu_WiFi"
   #define FIREBASE_HOST "your-project.firebaseio.com"
   #define FIREBASE_AUTH "your-database-secret"
   ```
5. Upload lên ESP32

### 4. Sơ đồ kết nối phần cứng

```
ESP32 Pin    →  Component
─────────────────────────
GPIO 4       →  DHT22 (Data)
GPIO 27      →  PIR (Output)
GPIO 18      →  LED Xanh
GPIO 19      →  LED Đỏ
GPIO 26      →  Buzzer
GPIO 25      →  Relay Quạt
GPIO 33      →  Relay Đèn
GPIO 21      →  LCD SDA
GPIO 22      →  LCD SCL
```

## Firebase Database Structure

```json
{
  "sensor": {
    "temperature": 28.5,
    "humidity": 65,
    "motion": false,
    "timestamp": 1234567890
  },
  "devices": {
    "fan": false,
    "light": false,
    "buzzer": false
  },
  "history": {
    "record_id": {
      "temperature": 28.5,
      "humidity": 65,
      "motion": false,
      "timestamp": 1234567890
    }
  }
}
```

## Kiến trúc phần mềm (MVVM)

```
View (Activity/Fragment)
    ↓ observes LiveData
ViewModel
    ↓ calls
Repository (Singleton)
    ↓ reads/writes
Firebase Realtime Database
```

## Công nghệ sử dụng

| Thành phần | Công nghệ |
|-----------|-----------|
| Ngôn ngữ Android | Kotlin |
| IDE | Android Studio |
| Database | Firebase Realtime Database |
| Authentication | Firebase Auth |
| Architecture | MVVM |
| UI | Material Design Components |
| Vi điều khiển | ESP32 |
| Cảm biến | DHT22, PIR |
| Giao tiếp | WiFi |

## Thành viên nhóm

- Phát triển ứng dụng Android (Kotlin)
- Phát triển phần cứng ESP32
- Thiết kế mạch và kết nối cảm biến

## License

MIT License
