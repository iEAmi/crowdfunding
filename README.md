###  وقتی برای یک campaign یک  donate انجام میشه ابتدا یک  Donation در وضعیت IN_PROGRESS ایجاد میشه و همون لحظه تلاش میشه که توی service-payment یک Transaction برای  Donation ثبت بشه، این کار با api call انجام میشه. اگر api call موفق باشه وضعیت Donation به PAID تغییر میکنه و مقدار currentAmountRials توی Campaign به اندازه مبلغ Donation افزایش پیدا میکنه.

### اگر به هر دلیلی api call برای ایجاد Transaction با خطا مواجه بشه یک scheduler وجود داره که Donation هایی که در وضعیت IN_PROGRESS هستن رو پیدا میکنه و تلاش میکنه دوباره براشون Transaction ایجاد کنه.

### یک time window برای ثبت Donation وجود داره به ازای هر کاربر. یعنی باید بین Donation های یک کاربر به اندازه time window فاصله باشه.
