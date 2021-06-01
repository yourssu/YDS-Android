package com.yourssu.yds_android.exception

import java.lang.Exception

class InvalidTypeException(causeMessage: String): Exception("디자인 시스템에 등록되지 않은 Type 입니다.\n$causeMessage")