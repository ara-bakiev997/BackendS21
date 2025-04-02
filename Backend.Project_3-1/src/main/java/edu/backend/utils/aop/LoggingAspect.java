package edu.backend.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && within(edu.backend.controllers..*)")
    public void restControllers() {
        // срез для получения всех контроллеров
    }

    @Around("restControllers()")
    public Object invoke(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var startTime = System.nanoTime();

        final var proceed = joinPoint.proceed();

        final var endTime = System.nanoTime();

        loggingMethodProceed(startTime, endTime, proceed, joinPoint);

        return proceed;
    }

    private void loggingMethodProceed(
            final long startTime,
            final long endTime,
            final Object proceed,
            final ProceedingJoinPoint joinPoint
    ) {
        final var proceedMilliSeconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        final var signature = (MethodSignature) joinPoint.getSignature();

        final var args = Optional.ofNullable(signature.getParameterNames())
                                 .map(Arrays::stream)
                                 .map(argsStream -> argsStream.map(Objects::toString).collect(Collectors.joining(", ")))
                                 .orElse("");
        final var isImageController = signature.getDeclaringType().getSimpleName().contains("ImageController");

        log.info(
                "Метод '{}' с аргументами '{}' отработал за '{}' мили-сек. С результатом '{}'",
                signature.getName(),
                args,
                proceedMilliSeconds,
                isImageController ? "image" : proceed
        );
    }
}
