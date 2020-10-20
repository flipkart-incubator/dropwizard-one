package com.flipkart.utils.dwone.persistence.transaction;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.*;
import org.hibernate.context.internal.ManagedSessionContext;

import javax.inject.*;
import java.util.Optional;

@Slf4j
@Singleton
@NoArgsConstructor
public class TransactionInterceptor implements MethodInterceptor {

    @Inject
    private Provider<SessionFactory> sessionFactoryProvider;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;

        boolean isInnerTransaction = getExistingSession().isPresent();

        if (isInnerTransaction) {
            return invocation.proceed();
        }
        Session session = sessionFactoryProvider.get().openSession();
        TransactionManager transactionManager = new TransactionManager(session);

        try {
            ManagedSessionContext.bind(session);
            transactionManager.beginTransaction();

            result = invocation.proceed();

            transactionManager.commitTransaction();
        } catch (Exception e) {
            log.error("Error occurred during transaction: ", e);
            transactionManager.rollbackTransaction();
            throw e;
        } finally {
            onFinish(session);
        }

        return result;
    }

    private Optional<Session> getExistingSession() {
        try {
            return Optional.of(sessionFactoryProvider.get().getCurrentSession());
        } catch (HibernateException e) {
            return Optional.empty();
        }
    }

    private void onFinish(Session session) {
        try {
            if (session != null) {
                session.close();
            }
        } finally {
            ManagedSessionContext.unbind(sessionFactoryProvider.get());
        }
    }
}
