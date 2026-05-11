# OceanX Grocery — Mini Grocery Delivery App

A **Blinkit-style** mini grocery delivery app built with **Kotlin + XML Layouts**, following **MVVM Architecture** with Room DB persistence.

---

## 📱 Features

| # | Screen | Features |
|---|---|---|
| 1 | **Login** | 10-digit mobile validation, shake animation on error |
| 2 | **OTP Verification** | 4-box OTP input, auto-focus, countdown timer, fake OTP: `1234` |
| 3 | **Home** | Product grid (2-col), search, category filter chips, cart badge |
| 4 | **Cart** | Add/remove/qty control via Room DB, live bill summary |
| 5 | **Checkout** | Delivery address form, COD/Online payment, field validation |
| 6 | **Order Success** | Random Order ID, estimated delivery, cart cleared, animation |

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | XML Layouts + ViewBinding |
| Architecture | MVVM + Repository Pattern |
| State | LiveData + StateFlow |
| Local DB | Room (cart persistence) |
| Navigation | Navigation Component + SafeArgs |
| Image Loading | Glide |
| UI Components | Material Design 3 |
| Build | Gradle 8.9 (Kotlin DSL) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 (Android 15) |

---

## 🚀 How to Run

### Prerequisites
- Android Studio Ladybug (2024.2.x) or newer
- JDK 17+
- Android SDK with API 35 installed

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/OceanX.git
   cd OceanX
   ```

2. **Open in Android Studio**
   - File → Open → Select the `OceanX` folder

3. **Sync Gradle**
   - Android Studio will auto-sync. If not: File → Sync Project with Gradle Files

4. **Run the app**
   - Select an emulator (API 35 recommended) or physical device
   - Press ▶ Run

5. **Login credentials**
   - Any valid 10-digit Indian mobile number (starts with 6–9)
   - OTP: `1234`

### Build APK
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📁 Project Structure

```
app/src/main/java/com/oceanx/grocery/
├── data/
│   ├── local/          # Room DB: CartItem, CartDao, AppDatabase
│   ├── model/          # Product data class
│   ├── repository/     # CartRepository, ProductRepository
│   └── FakeProductData # 15 hardcoded products (4 categories)
├── ui/
│   ├── auth/           # LoginActivity, LoginFragment, OtpFragment, AuthViewModel
│   ├── home/           # HomeActivity, HomeFragment, HomeViewModel, ProductAdapter
│   ├── cart/           # CartFragment, CartViewModel, CartAdapter
│   ├── checkout/       # CheckoutFragment, CheckoutViewModel
│   └── order/          # OrderSuccessFragment
└── utils/              # Extensions, Constants
```

---

## ✅ Bonus Features Implemented

- [x] MVVM Architecture (ViewModel + Repository + LiveData/StateFlow)
- [x] Room DB for persistent cart
- [x] Smooth animations (slide transitions, fade, shake on OTP error)
- [x] Dark Mode support (`values-night/colors.xml`)
- [x] Android 15/16 edge-to-edge compatibility

---

## 📦 Submission

- **GitHub**: [Repository Link]
- **APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Screen Recording**: [Recorded manually]

---

*Built for OceanX Agency — Kotlin Android Assignment*
