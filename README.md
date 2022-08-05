![Thumbnail](https://user-images.githubusercontent.com/39309953/141672549-f87d542d-27ed-450f-bbce-fc4ab9942e39.png)

![Platform](https://img.shields.io/badge/Platform-Android-orange.svg)
![API](https://img.shields.io/badge/API-23%2B-green.svg)
[![JitPack](https://jitpack.io/v/yourssu/YDS-Android.svg)](https://jitpack.io/#yourssu/YDS-Android)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 🎨 YDS란
YDS는 숭실대학교 동아리 유어슈에서 사용하는 디자인 시스템입니다. 뷰 컴포넌트 재사용성을 높여 코드 작성에 걸리는 시간을 단축함과 동시에 일관된 디자인 퀄리티를 보장하기 위해 고안됐습니다.

## 📝 문서
[YDS Wiki](https://yourssu.notion.site/Yourssu-Design-System-00577fab034e46cb8aeb330247376a15)  
YDS 문서는 노션 페이지에서 관리됩니다.  
컴포넌트 정보와 사용 예시 등을 확인할 수 있습니다.

## 🛠 설치 방법
#### gradle Project
```groovy
allprojects {
    repositories {
        '...'
        maven { url 'https://jitpack.io' }
    }
}
```
#### gradle app
```groovy
implementation "com.github.yourssu:YDS-Android:${YDSVersion}"
```
#### theme

<img width="420" src="https://user-images.githubusercontent.com/39309953/141673816-df48fdd2-4480-45b4-9f30-ce7f6f4ed86c.png">

다크 테마는 추가예정

## 🧪 샘플 어플리케이션
<img src="https://user-images.githubusercontent.com/39309953/141673413-0e76bb0b-3d52-4f06-af15-5a070e20ebad.gif" width=300 />
<a href='https://play.google.com/store/apps/details?id=com.yourssu.storybook&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='다운로드하기 Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/ko_badge_web_generic.png' width=200 /></a>

## 🖥 YDS 개발자 규칙
[YDS 개발 수칙](https://www.notion.so/yourssu/Android-309726587b1943a4bfb3f501e3ed672a)

## 💻 타 버전 저장소
[YDS-iOS](https://github.com/yourssu/YDS-iOS)  
[YDS-Web](https://github.com/yourssu/YDS-Web)
