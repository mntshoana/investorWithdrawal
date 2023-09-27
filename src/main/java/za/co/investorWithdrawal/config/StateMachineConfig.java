package za.co.investorWithdrawal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;
import za.co.investorWithdrawal.constants.WithdrawalEvent;
import za.co.investorWithdrawal.constants.WithdrawalState;
import za.co.investorWithdrawal.service.Scheduler;
import za.co.investorWithdrawal.service.repository.WithdrawalRepositoryService;

import java.math.BigDecimal;
import java.util.EnumSet;


@Configuration
@EnableStateMachineFactory
public class StateMachineConfig
        extends StateMachineConfigurerAdapter<WithdrawalState, WithdrawalEvent> {

    @Autowired
    WithdrawalRepositoryService withdrawalRepositoryService;

    public static void schedule(StateMachine<WithdrawalState, WithdrawalEvent> withdrawalStStateMachine,
                                BigDecimal amount, Long prodId) {
        withdrawalStStateMachine.startReactively().subscribe();
        WithdrawalEvent event = WithdrawalEvent.START;
        event.setProdId(prodId);
        event.setAmount(amount);
        withdrawalStStateMachine.sendEvent(Mono.just(MessageBuilder
                .withPayload(WithdrawalEvent.START)
                .build())).subscribe();
    }

    @Override
    public void configure(StateMachineStateConfigurer<WithdrawalState, WithdrawalEvent> config) throws Exception {
        config.withStates()
                .initial(WithdrawalState.IDLE)
                .end(WithdrawalState.DONE)
                .states(EnumSet.allOf(WithdrawalState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<WithdrawalState, WithdrawalEvent> transitions) throws Exception {

        transitions
                // IDLE -> STARTED
                .withExternal()
                .source(WithdrawalState.IDLE)
                .event(WithdrawalEvent.START)
                .target(WithdrawalState.STARTED)
                .action(context -> {
                    withdrawalRepositoryService.onStart(context.getEvent().getProdId(), context.getEvent().getAmount());
                    Scheduler.execute(context.getStateMachine());
                }, errorContext -> {
                    System.out.println("[STATE ERROR]: Could not proceed with withdrawal for account: " + errorContext.getEvent().getProdId());
                })

                // DURING STARTED
                .and().withInternal()
                .source(WithdrawalState.IDLE)
                .event(WithdrawalEvent.EXECUTE)
                .action(context -> Scheduler.execute(context.getStateMachine()))


                // STARTED -> EXECUTING
                .and()
                .withExternal()
                .source(WithdrawalState.STARTED)
                .event(WithdrawalEvent.EXECUTE)
                .target(WithdrawalState.EXECUTING)
                .action(context -> {
                    withdrawalRepositoryService.onExecute();
                    Scheduler.finish(context.getStateMachine());
                }, errorContext -> {
                    System.out.println("[STATE ERROR]: Could not proceed with withdrawal for account: " + errorContext.getEvent().getProdId());
                })

                // DURING EXECUTING
                .and().withInternal()
                .source(WithdrawalState.STARTED)
                .event(WithdrawalEvent.FINISH)
                .action(context -> Scheduler.finish(context.getStateMachine()))

                // EXECUTING -> DONE
                .and().withExternal()
                .source(WithdrawalState.EXECUTING)
                .event(WithdrawalEvent.FINISH)
                .target(WithdrawalState.DONE)
                .action((context) -> {
                    withdrawalRepositoryService.onDone();
                    Scheduler.finish(context.getStateMachine());
                })

                // DURING EXECUTING
                .and().withInternal()
                .source(WithdrawalState.EXECUTING)
                .state(WithdrawalState.DONE)
                .event(WithdrawalEvent.FINISH)
                .action((context) -> context.getStateMachine().stopReactively().subscribe());


    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<WithdrawalState, WithdrawalEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener() {
                    @Override
                    public void stateChanged(State state1, State state2) {
                        String from = state1 != null ? state1.getId().toString() : "null";
                        String to = state2 != null ? state2.getId().toString() : "null";

                        System.out.println("[STATE]: State changed from " + from + " to " + to);
                    }

                    @Override
                    public void stateEntered(State state) {
                        System.out.println("[STATE]: State entered " + (state != null ? state.getId().toString() : "null"));
                    }

                    @Override
                    public void stateExited(State state) {
                        System.out.println("[STATE]: State exited " + (state != null ? state.getId().toString() : "null"));
                    }

                    @Override
                    public void eventNotAccepted(Message message) {
//                        System.out.println("state exited " +  (state != null ? state.toString() : "null"));
                    }

                    @Override
                    public void transition(Transition transition) {

                    }

                    @Override
                    public void transitionStarted(Transition transition) {

                    }

                    @Override
                    public void transitionEnded(Transition transition) {

                    }

                    @Override
                    public void stateMachineStarted(StateMachine stateMachine) {

                    }

                    @Override
                    public void stateMachineStopped(StateMachine stateMachine) {

                    }

                    @Override
                    public void stateMachineError(StateMachine stateMachine, Exception e) {

                    }

                    @Override
                    public void extendedStateChanged(Object o, Object o1) {

                    }

                    @Override
                    public void stateContext(StateContext stateContext) {

                    }
                });
    }

}
