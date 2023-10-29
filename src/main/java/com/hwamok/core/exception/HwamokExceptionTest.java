package com.hwamok.core.exception;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableTypeAssert;

import static org.assertj.core.api.ThrowableAssert.*;

// CustomException 만들기
// assertThatHwamokException 만들기
// assertThatIllegalArgumentException 처럼 만드는 것

public class HwamokExceptionTest {
    private final ExceptionCode exceptionCode; // 출력할 코드랑 메시지를 전달하지 위해 추가
    private final ThrowableTypeAssert<HwamokException> throwableTypeAssert;

    public HwamokExceptionTest(ExceptionCode exceptionCode, ThrowableTypeAssert<HwamokException> throwableTypeAssert) {
        this.exceptionCode = exceptionCode;
        this.throwableTypeAssert = throwableTypeAssert;
    }

    public static HwamokExceptionTest assertThatHwamokException(ExceptionCode exceptionCode) {
        return new HwamokExceptionTest(exceptionCode, Assertions.assertThatExceptionOfType(HwamokException.class));
    }

    // public static ThrowableTypeAssert<IllegalArgumentException> assertThatIllegalArgumentException() {
    //    return assertThatExceptionOfType(IllegalArgumentException.class);
    //  }

    public void isThrownBy(ThrowingCallable throwingCallable) {
        this.throwableTypeAssert.isThrownBy(throwingCallable).withMessage(exceptionCode.name());
    }

    //  public ThrowableAssertAlternative<T> isThrownBy(final ThrowingCallable throwingCallable) {
    //    Throwable throwable = ThrowableAssert.catchThrowable(throwingCallable);
    //    checkThrowableType(throwable);
    //    @SuppressWarnings("unchecked")
    //    T castThrowable = (T) throwable;
    //    return buildThrowableTypeAssert(castThrowable).as(description);
    //  }
}
