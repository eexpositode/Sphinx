package com.myprojects.eduardoexposito.sphinx.modules

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.myprojects.eduardoexposito.sphinx.models.Event
import com.myprojects.eduardoexposito.sphinx.models.SideEffect
import com.myprojects.eduardoexposito.sphinx.models.State
import com.myprojects.eduardoexposito.sphinx.models.stateToSideEffect

class StateMachineManager(
    private val context: Context,
    private val sideEffectsInterface: SideEffectsDelegateInterface
) {

    companion object {
        private val INITIAL_STATE = State.AppStartGetLocationState
        val INITIAL_SIDE_EFFECT = SideEffect.RenderAppStartGetLocationState
        private const val STATE_CLASS_NAME = "state_class_simple_name"
    }

    lateinit var stateMachine: StateMachine<State, Event, SideEffect>

    init {
        setUpStateMachine()
    }

    private fun setUpStateMachine() {
        val initialState = getInitialState()
        stateMachine = StateMachine.create {
            initialState(initialState)
            defineAppStartGetLocationState()
            defineFirstAppStartLocationFailureState()
            defineAppIntroductionState()
            defineEnterCodeState()
            defineEnterCodeFailureState()
            definePresentationState()
            definePresentationDeclinedState()
            defineRiddleGoToBerlinState()
            defineRiddleGoToBerlinSuccessState()
            defineRiddleGoToBerlinFailureState()
            defineRiddleGoToGendarmenMarktState()
            defineRiddleGoToGendarmenMarktSuccessState()
            defineRiddleGoToGendarmenMarktFailureState()
            defineShowLockCodeFromGendarmenMarktState()
            defineGetLocationBeforeCharlottenBurgRiddleState()
            defineGetLocationBeforeCharlottenBurgRiddleFailureState()
            defineRiddleGoToCharlottenBurgState()
            defineRiddleGoToCharlottenBurgMarktSuccessState()
            defineRiddleGoToCharlottenBurgMarktFailureState()
            defineShowLockCodeFromCharlottenBurgMarktState()
            defineGetLocationBeforeAlexanderPlatzMarktRiddleState()
            defineGetLocationBeforeAlexanderPlatzMarktRiddleFailureState()
            defineRiddleGoToAlexanderPlatzMarktState()
            defineRiddleGoToAlexanderPlatzMarktSuccessState()
            defineRiddleGoToAlexanderPlatzMarktFailureState()
            defineShowLockCodeFromAlexanderPlatzMarktState()
            defineGetLocationBeforeFinaleState()
            defineGetLocationBeforeFinaleFailureState()
            defineFinaleState()
            defineGrandFinaleState()
            onTransition {
                evaluateTransition(it)
            }
        }
        sideEffectsInterface.renderInitialState(initialState)
    }

    private fun evaluateTransition(transition: StateMachine.Transition<State, Event, SideEffect>) {
        if (transition is StateMachine.Transition.Valid) {
            saveLastState(transition.toState)
            transition.sideEffect?.let { sideEffectsInterface.performSideEffect(it) }
        } else {
            sideEffectsInterface.showTransitionError(transition.fromState, transition.event)
        }
    }

    //=============================================================================================
    //=============================================================================================
    private fun getInitialState() = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(STATE_CLASS_NAME, INITIAL_STATE.getSimpleName())
        .let { savedStateName ->
            Log.w("STATE CLASS NAME", "--> $savedStateName")
            State::class.nestedClasses
                .map { it.objectInstance as State }
                .firstOrNull {
                    Log.w("STATE CLASS NAME", "${it.getSimpleName()} --> $savedStateName")
                    it.getSimpleName() == savedStateName
                }
        }
        ?: INITIAL_STATE

    private fun saveLastState(state: State) {
        if (stateToSideEffect.containsKey(state)) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(STATE_CLASS_NAME, state.getSimpleName())
                .apply()
        }
    }

    private fun clearSavedState() {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .remove(STATE_CLASS_NAME)
            .apply()
        setUpStateMachine()
    }

    private fun State.getSimpleName() = javaClass.simpleName
    //=============================================================================================
    //=============================================================================================
    //-> APP FIRST START STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineAppStartGetLocationState() {
        state<State.AppStartGetLocationState> {
            on<Event.OnAppStartGetLocationFailEvent> {
                transitionTo(State.AppStartGetLocationFailureState, SideEffect.RenderAppStartGetLocationFailureState)
            }
            on<Event.OnAppStartGetLocationSuccessEvent> {
                transitionTo(State.AppIntroductionState, SideEffect.RenderAppIntroductionState)
            }
        }
    }

    //-> LOCATION FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineFirstAppStartLocationFailureState() {
        state<State.AppStartGetLocationFailureState> {
            on<Event.OnAppStartGetLocationFailEvent> {
                transitionTo(State.AppStartGetLocationState, SideEffect.RenderRetryGetLocationState)
            }
        }
    }

    //-> APP INTRODUCTION STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineAppIntroductionState() {
        state<State.AppIntroductionState> {
            on<Event.OnAppIntroductionContinueEvent> {
                transitionTo(State.EnterStartCodeState, SideEffect.RenderEnterCodeState)
            }
        }
    }

    //-> ENTER CODE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineEnterCodeState() {
        state<State.EnterStartCodeState> {
            on<Event.OnEnterStartCodeFailEvent> {
                transitionTo(State.EnterStartCodeCodeFailureState, SideEffect.RenderEnterStartCodeFailureState)
            }
            on<Event.OnEnterCodeSuccessEvent> {
                transitionTo(State.PresentationState, SideEffect.RenderPresentationState)
            }
        }
    }

    //-> ENTER CODE FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineEnterCodeFailureState() {
        state<State.EnterStartCodeCodeFailureState> {
            on<Event.OnEnterStartCodeFailEvent> {
                transitionTo(State.EnterStartCodeState, SideEffect.RenderRetryEnterStartCodeState)
            }
        }
    }

    //-> PRESENTATION STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.definePresentationState() {
        state<State.PresentationState> {
            on<Event.OnPresentationStateDeclinedEvent> {
                transitionTo(State.PresentationStateDeclinedState, SideEffect.RenderPresentationDeclineState)
            }
            on<Event.OnPresentationStateAcceptEvent> {
                transitionTo(State.RiddleToBerlinState, SideEffect.RenderRiddleToBerlinState)
            }
        }
    }

    //-> PRESENTATION DECLINED STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.definePresentationDeclinedState() {
        state<State.PresentationStateDeclinedState> {
            on<Event.OnPresentationStateDeclinedEvent> {
                transitionTo(State.RiddleToBerlinState, SideEffect.RenderRiddleToBerlinState)
            }
        }
    }

    //-> RIDDLE GO TO BERLIN STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToBerlinState() {
        state<State.RiddleToBerlinState> {
            on<Event.OnBerlinLocationSuccessEvent> {
                transitionTo(
                    State.RiddleToBerlinLocationSuccessState,
                    SideEffect.RenderRiddleToBerlinLocationSuccessState
                )
            }
            on<Event.OnBerlinLocationFailEvent> {
                transitionTo(
                    State.RiddleToBerlinLocationFailureState,
                    SideEffect.RenderRiddleToBerlinLocationFailureState
                )
            }
        }
    }

    //-> RIDDLE GO TO BERLIN SUCCESS STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToBerlinSuccessState() {
        state<State.RiddleToBerlinLocationSuccessState> {
            on<Event.OnBerlinLocationSuccessEvent> {
                transitionTo(State.RiddleToGendarmenMarktState, SideEffect.RenderRiddleToGendarmenMarktState)
            }
        }
    }

    //-> RIDDLE GO TO BERLIN FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToBerlinFailureState() {
        state<State.RiddleToBerlinLocationFailureState> {
            on<Event.OnBerlinLocationFailEvent> {
                transitionTo(State.RiddleToBerlinState, SideEffect.RenderRetryRiddleToBerlinState)
            }
        }
    }

    //-> RIDDLE TO GENDARMENMARKT STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToGendarmenMarktState() {
        state<State.RiddleToGendarmenMarktState> {
            on<Event.OnGendarmenMarktLocationSuccessEvent> {
                transitionTo(
                    State.RiddleToGendarmenMarktLocationSuccessState,
                    SideEffect.RenderRiddleToGendarmenMarktLocationSuccessState
                )
            }
            on<Event.OnGendarmenMarktLocationFailEvent> {
                transitionTo(
                    State.RiddleToGendarmenMarktLocationFailureState,
                    SideEffect.RenderRiddleToGendarmenMarktLocationFailureState
                )
            }
        }
    }

    //-> RIDDLE TO GENDARMENMARKT SUCCESS STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToGendarmenMarktSuccessState() {
        state<State.RiddleToGendarmenMarktLocationSuccessState> {
            on<Event.OnGendarmenMarktLocationSuccessEvent> {
                transitionTo(
                    State.ShowLockCodeFromGendarmentMarktState,
                    SideEffect.RenderShowLockCodeFromGendarmenMarktState
                )
            }
        }
    }

    //-> RIDDLE TO GENDARMENMARKT FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToGendarmenMarktFailureState() {
        state<State.RiddleToGendarmenMarktLocationFailureState> {
            on<Event.OnGendarmenMarktLocationFailEvent> {
                transitionTo(State.RiddleToGendarmenMarktState, SideEffect.RenderRetryRiddleToGendarmenMarktState)
            }
        }
    }

    //-> SHOW LOCK CODE FROM GENDARMENMARKT STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineShowLockCodeFromGendarmenMarktState() {
        state<State.ShowLockCodeFromGendarmentMarktState> {
            on<Event.ShowLockCodeFromGendarmentMarktContinueEvent> {
                transitionTo(
                    State.GetLocationBeforeCharlottenBurgRiddleState,
                    SideEffect.RenderGetLocationBeforeCharlottenBurgRiddleState
                )
            }
        }
    }

    //-> GET LOCATION BEFORE CHARLOTTENBURG STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeCharlottenBurgRiddleState() {
        state<State.GetLocationBeforeCharlottenBurgRiddleState> {
            on<Event.CheckLocationBeforeCharlottenBurgRiddleSuccessEvent> {
                transitionTo(State.RiddleToCharlottenBurgMarktState, SideEffect.RenderRiddleToCharlottenBurgState)
            }
            on<Event.CheckLocationBeforeCharlottenBurgRiddleFailEvent> {
                transitionTo(
                    State.GetLocationBeforeCharlottenBurgRiddleFailureState,
                    SideEffect.RenderLocationBeforeCharlottenBurgRiddleFailureState
                )
            }
        }
    }

    //-> GET LOCATION BEFORE CHARLOTTENBURG FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeCharlottenBurgRiddleFailureState() {
        state<State.GetLocationBeforeCharlottenBurgRiddleFailureState> {
            on<Event.CheckLocationBeforeCharlottenBurgRiddleFailEvent> {
                transitionTo(
                    State.GetLocationBeforeCharlottenBurgRiddleState,
                    SideEffect.RenderRetryGetLocationBeforeCharlottenBurgRiddleState
                )
            }
        }
    }

    //-> RIDDLE TO CHARLOTTENBURG STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToCharlottenBurgState() {
        state<State.RiddleToCharlottenBurgMarktState> {
            on<Event.OnCharlottenBurgMarktLocationSuccessEvent> {
                transitionTo(
                    State.RiddleToCharlottenBurgMarktLocationSuccessState,
                    SideEffect.RenderRiddleToCharlottenBurgMarktLocationSuccessState
                )
            }
            on<Event.OnCharlottenBurgMarktLocationFailEvent> {
                transitionTo(
                    State.RiddleToCharlottenBurgMarktLocationFailureState,
                    SideEffect.RenderRiddleToCharlottenBurgMarktLocationFailureState
                )
            }
        }
    }

    //-> RIDDLE TO CHARLOTTENBURG SUCCESS STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToCharlottenBurgMarktSuccessState() {
        state<State.RiddleToCharlottenBurgMarktLocationSuccessState> {
            on<Event.OnCharlottenBurgMarktLocationSuccessEvent> {
                transitionTo(
                    State.ShowLockCodeFromCharlottenBurgMarktState,
                    SideEffect.RenderShowLockCodeFromCharlottenBurgMarktState
                )
            }
        }
    }

    //-> RIDDLE TO CHARLOTTENBURG FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToCharlottenBurgMarktFailureState() {
        state<State.RiddleToCharlottenBurgMarktLocationFailureState> {
            on<Event.OnCharlottenBurgMarktLocationFailEvent> {
                transitionTo(
                    State.RiddleToCharlottenBurgMarktState,
                    SideEffect.RenderRetryRiddleToCharlottenBurgMarktState
                )
            }
        }
    }

    //-> SHOW LOCK CODE FROM CHARLOTTENBURG STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineShowLockCodeFromCharlottenBurgMarktState() {
        state<State.ShowLockCodeFromCharlottenBurgMarktState> {
            on<Event.ShowLockCodeFromCharlottenBurgMarktContinueEvent> {
                transitionTo(
                    State.GetLocationBeforeAlexanderPlatzMarktRiddleState,
                    SideEffect.RenderLocationBeforeAlexanderPlatzRiddleState
                )
            }
        }
    }

    //-> GET LOCATION BEFORE ALEXANDERPLATZ STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeAlexanderPlatzMarktRiddleState() {
        state<State.GetLocationBeforeAlexanderPlatzMarktRiddleState> {
            on<Event.CheckLocationBeforeAlexanderPlatzMarktRiddleSuccessEvent> {
                transitionTo(State.RiddleToAlexanderPlatzMarktState, SideEffect.RenderRiddleToAlexanderPlatzMarktState)
            }
            on<Event.CheckLocationBeforeAlexanderPlatzMarktRiddleFailEvent> {
                transitionTo(
                    State.GetLocationBeforeAlexanderPlatzMarktRiddleFailureState,
                    SideEffect.RenderLocationBeforeAlexanderPlatzRiddleFailureState
                )
            }
        }
    }

    //-> GET LOCATION BEFORE ALEXANDERPLATZ FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeAlexanderPlatzMarktRiddleFailureState() {
        state<State.GetLocationBeforeAlexanderPlatzMarktRiddleFailureState> {
            on<Event.CheckLocationBeforeAlexanderPlatzMarktRiddleFailEvent> {
                transitionTo(
                    State.GetLocationBeforeAlexanderPlatzMarktRiddleState,
                    SideEffect.RenderRetryGetLocationBeforeAlexanderPlatzMarktRiddleState
                )
            }
        }
    }

    //-> RIDDLE TO ALEXANDERPLATZ STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToAlexanderPlatzMarktState() {
        state<State.RiddleToAlexanderPlatzMarktState> {
            on<Event.OnAlexanderPlatzMarktLocationSuccessEvent> {
                transitionTo(
                    State.RiddleToAlexanderPlatzMarktLocationSuccessState,
                    SideEffect.RenderRiddleToAlexanderPlatzMarktLocationSuccessState
                )
            }
            on<Event.OnAlexanderPlatzMarktLocationFailEvent> {
                transitionTo(
                    State.RiddleToAlexanderPlatzMarktLocationFailureState,
                    SideEffect.RenderRiddleToAlexanderPlatzMarktLocationFailureState
                )
            }
        }
    }

    //-> RIDDLE TO ALEXANDERPLATZ SUCCESS STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToAlexanderPlatzMarktSuccessState() {
        state<State.RiddleToAlexanderPlatzMarktLocationSuccessState> {
            on<Event.OnAlexanderPlatzMarktLocationSuccessEvent> {
                transitionTo(
                    State.ShowLockCodeFromAlexanderPlatzMarktState,
                    SideEffect.RenderShowLockCodeFromAlexanderPlatzMarktState
                )
            }
        }
    }

    //-> RIDDLE TO ALEXANDERPLATZ FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineRiddleGoToAlexanderPlatzMarktFailureState() {
        state<State.RiddleToAlexanderPlatzMarktLocationFailureState> {
            on<Event.OnAlexanderPlatzMarktLocationFailEvent> {
                transitionTo(
                    State.RiddleToAlexanderPlatzMarktState,
                    SideEffect.RenderRetryRiddleToAlexanderPlatzMarktState
                )
            }
        }
    }

    //-> SHOW LOCK CODE FROM ALEXANDERPLATZ STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineShowLockCodeFromAlexanderPlatzMarktState() {
        state<State.ShowLockCodeFromAlexanderPlatzMarktState> {
            on<Event.ShowLockCodeFromAlexanderPlatzMarktContinueEvent> {
                transitionTo(State.GetLocationBeforeFinaleState, SideEffect.RenderLocationBeforeFinaleState)
            }
        }
    }

    //-> GET LOCATION BEFORE FINALE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeFinaleState() {
        state<State.GetLocationBeforeFinaleState> {
            on<Event.CheckLocationBeforeFinaleSuccessEvent> {
                transitionTo(State.FinaleState, SideEffect.RenderFinaleState)
            }
            on<Event.CheckLocationBeforeFinaleFailEvent> {
                transitionTo(
                    State.GetLocationBeforeFinaleFailureState,
                    SideEffect.RenderLocationBeforeFinaleFailureState
                )
            }
        }
    }

    //-> GET LOCATION BEFORE FINALE FAILURE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGetLocationBeforeFinaleFailureState() {
        state<State.GetLocationBeforeFinaleFailureState> {
            on<Event.OnFinaleLocationFailEvent> {
                transitionTo(State.GetLocationBeforeFinaleState, SideEffect.RenderRetryGetLocationBeforeFinaleState)
            }
        }
    }

    //-> SHOW FINALE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineFinaleState() {
        state<State.FinaleState> {
            on<Event.OnFinaleContinueEvent> {
                transitionTo(State.GrandFinaleState, SideEffect.RenderGrandFinaleState)
            }
        }
    }

    //-> SHOW GRAND FINALE STATE
    private fun StateMachine.GraphBuilder<State, Event, SideEffect>.defineGrandFinaleState() {
        state<State.GrandFinaleState> {
        }
    }
}
