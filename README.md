# This App

This application get SIM information.  
This application created to confirm the procedure to release to the store.

# Store URL
https://play.google.com/store/apps/details?id=jp.co.integrityworks.mysiminfo

copyright-free

----
# 作った背景
- Google Play Store.へアプリをリリースする際の手順や入力項目、画面情報などについて、実際にやってみないとわからない箇所があった。
- それなりの規模の企業さんとAndroidアプリ開発の仕事をしたとき、公式情報だけでは、お客さんの理解を得ることが難しいところがあった。
これらを解決するために、作成しました。

- ベースプロジェクトにしていけたらと思い、細々と更新しています。

# その他の更新
- Javaでリリース後、Kotlinに変換
- Google でアプリ署名鍵の管理、保護を行った場合のリリース
- AndroidX対応
- targetSdkVersion 31 対応
- AdMob導入

# 追記したいこと
- テスト周り
  jacoco-android-gradle-pluginとRobolectric をひとまず追加しました。（Kotlinで動くかは未検証）
  次のステップとして、テストコードを書いていきたい。
- 難読化（proguard-rules.pro）周り

# 機能
SIM情報にアクセスして表示するだけです。
※ただ、年々セキュリティ要件が高まってきていることもあり、SIM情報はもうほとんど取得できません。。。

