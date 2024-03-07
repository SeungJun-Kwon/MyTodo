package com.sparta.mytodo.global.aop;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Transaction AOP")
@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAspect {

    @Pointcut("execution(* com.sparta.mytodo.domain.user.service.UserService.signup(..))")
    private void userTransaction() {

    }

    @Pointcut("execution(* com.sparta.mytodo.domain.todo.service.TodoService.createTodo(..))"
        + "|| execution(* com.sparta.mytodo.domain.todo.service.TodoService.updateTodo(..))"
        + "|| execution(* com.sparta.mytodo.domain.todo.service.TodoService.finishTodo(..))")
    private void todoTransaction() {

    }

    @Pointcut(
        "execution(* com.sparta.mytodo.domain.comment.service.CommentService.createComment(..))"
            + "|| execution(* com.sparta.mytodo.domain.comment.service.CommentService.updateComment(..))"
            + "|| execution(* com.sparta.mytodo.domain.comment.service.CommentService.deleteComment(..))")
    private void commentTransaction() {

    }

    @Before("userTransaction() || todoTransaction() || commentTransaction()")
    private void beforeTransaction(JoinPoint joinPoint) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        log.info("======= Method Name = {} =======", method.getName());

        Object[] args = joinPoint.getArgs();
        if (args.length < 1) {
            log.info("No Parameter");
        } else {
            for (Object arg : args) {
                log.info("Parameter Type : {}, Value : {}", arg.getClass().getSimpleName(), arg);
            }
        }
    }

    @AfterReturning(pointcut = "userTransaction() || todoTransaction() || commentTransaction()", returning = "returnObj")
    private void afterTransaction(JoinPoint joinPoint, Object returnObj) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        log.info("======= Method Name = {} =======", method.getName());

        log.info("Return Type : {}, Value : {}", returnObj.getClass().getSimpleName(), returnObj);
    }

    @AfterThrowing(pointcut = "userTransaction() || todoTransaction() || commentTransaction()", throwing = "ex")
    private void afterFailTransaction(JoinPoint joinPoint, Throwable ex) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        log.info("======= Method Name = {} =======", method.getName());

        log.info("Exception : {}", ex.getMessage());
    }
}
