package za.co.investorWithdrawal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.co.investorWithdrawal.constants.WithdrawalEvent;
import za.co.investorWithdrawal.constants.WithdrawalState;
import za.co.investorWithdrawal.service.repository.WithdrawalRepositoryService;

import java.math.BigDecimal;


@Service
public class Scheduler {
    @Autowired
    WithdrawalRepositoryService withdrawalRepositoryService;
    @Autowired
    private StateMachineFactory<WithdrawalState, WithdrawalEvent> withdrawalStStateMachine;

    private static Scheduler scheduler;

    @Bean
    private static Scheduler getScheduler() {
        if (scheduler == null) {
            scheduler = new Scheduler();

        }
        return scheduler;
    }


    public static void schedule(Long userId, BigDecimal amount, Long prodId) {
        Scheduler scheduler = getScheduler();
        scheduler.withdrawalStStateMachine.getStateMachine().startReactively().subscribe();
        WithdrawalEvent event = WithdrawalEvent.START;
        event.setUserId(userId);
        event.setProdId(prodId);
        event.setAmount(amount);
        scheduler.withdrawalStStateMachine.getStateMachine().sendEvent(Mono.just(MessageBuilder
                .withPayload(WithdrawalEvent.START)
                .build())).subscribe();
    }

    public static void start(StateMachine<WithdrawalState, WithdrawalEvent> stateMachine){
        execute(stateMachine);
    }
    public static void execute(StateMachine<WithdrawalState, WithdrawalEvent> stateMachine) {
        stateMachine.sendEvent(Mono.just(MessageBuilder
                .withPayload(WithdrawalEvent.EXECUTE)
                .build())).subscribe();
    }

    public static void finish(StateMachine<WithdrawalState, WithdrawalEvent> stateMachine) {
        stateMachine.sendEvent(Mono.just(MessageBuilder
                .withPayload(WithdrawalEvent.FINISH)
                .build())).subscribe();
    }
}
