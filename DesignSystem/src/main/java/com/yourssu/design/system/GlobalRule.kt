package com.yourssu.design.system

/**
 * YDS 를 사용하는 앱은 manifest 에 application theme 를
 * android:theme="@style/Theme.YourssuDesignSystem"
 * 로 지정해야 한다
 */

/**
 * 관리를 위해서 YDS 의 패키지 구조는 Figma 와 동일 혹은 유사해야한다
 */

/**
 * 정적인 값은 가능하면 xml 에 정의하고 적용은 xml 에서 값을 가져와서 적용하는 형태를 취한다
 */

/**
 * 뷰에 대해서 디자이너가 지정해준 (외부에서 정해주는) 속성은 DataBinding 을 지원할 수 있도록 해야한다
 * 일반적으론 get set 메소드를 만들어 줘야 하지만 코틀린은 property 를 통한 set 을 할 수 있으므로
    var value: Int = defaultValue
        set(value) {
            field = typo
        }
 * 위와 같은 형태로 제공한다
 */

/**
 * 뷰를 생성하는 방법은 xml 을 통한 일반적인 방법과 코드를 통해 런타임에서 생성하는법 두가지를 제공해야한다
 * 이때 코드를 통한 런타임 제공 방식은 YDSL(YourssuDesignSystemLanguage) 방식을 따라야한다
 * 이에 대한 자세한 설명은 InfoYDSL 파일에 언급한다
 */