package com.myprojects.eduardoexposito.sphinx.ui.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.myprojects.eduardoexposito.sphinx.R
import com.myprojects.eduardoexposito.sphinx.helpers.createInfoDialog
import com.myprojects.eduardoexposito.sphinx.models.Event
import com.myprojects.eduardoexposito.sphinx.models.SideEffect
import com.myprojects.eduardoexposito.sphinx.models.State
import com.myprojects.eduardoexposito.sphinx.modules.SideEffectsDelegateInterface
import com.myprojects.eduardoexposito.sphinx.modules.StateMachineManager
import kotlinx.android.synthetic.main.view_test_state_renderer.view.*

class PlaygroundSideEffectsRenderView : ConstraintLayout, SideEffectsDelegateInterface {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private lateinit var stateMachineManager: StateMachineManager

    fun setUp() {
        stateMachineManager = StateMachineManager(context, this)
    }

    override fun showTransitionError(state: State, event: Event) {
        createInfoDialog(
            context,
            R.string.transition_error_title,
            String.format(
                context.getString(R.string.transition_error_message),
                state::class.java.simpleName,
                event::class.java.simpleName
            )
        ).show()
    }

    override fun renderInitialState(initialState: State) {
        displayCurrentState(R.string.app_start_get_location_state)
        enableContinueButton(true)
        enableCancelButton(true)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationSuccessEvent) }
        setCancelButtonAction { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationFailEvent) }
    }

    override fun performSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            SideEffect.RenderAppStartGetLocationFailureState -> renderFirstAppStartGetLocationFailureState()
            SideEffect.RenderRetryGetLocationState -> renderRetryFirstAppStartGetLocationState()
            SideEffect.RenderEnterCodeState -> renderEnterCodeState()
            SideEffect.RenderEnterStartCodeFailureState -> renderEnterCodeFailureState()
            SideEffect.RenderRetryEnterStartCodeState -> renderRetryEnterCodeState()
            SideEffect.RenderPresentationState -> renderPresentationState()
            SideEffect.RenderPresentationDeclineState -> renderPresentationDeclineState()
            SideEffect.RenderRiddleToBerlinState -> renderRiddleToBerlinState()
        }
    }

    override fun renderFirstAppStartGetLocationState() {
    }

    override fun renderFirstAppStartGetLocationFailureState() {
        displayCurrentState(R.string.app_start_get_location_failure_state)
        enableContinueButton(true)
        enableCancelButton(false)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationFailEvent) }
    }

    override fun renderAppIntroductionState() {
    }

    override fun renderRetryFirstAppStartGetLocationState() {
        displayCurrentState(R.string.retry_get_location_state)
        enableContinueButton(true)
        enableCancelButton(true)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationSuccessEvent) }
        setCancelButtonAction { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationFailEvent) }
    }

    override fun renderEnterCodeState() {
        displayCurrentState(R.string.enter_start_code_state)
        enableContinueButton(true)
        enableCancelButton(true)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnEnterCodeSuccessEvent) }
        setCancelButtonAction { stateMachineManager.stateMachine.transition(Event.OnEnterStartCodeFailEvent) }
    }

    override fun renderEnterCodeFailureState() {
        displayCurrentState(R.string.enter_code_failure_state)
        enableContinueButton(true)
        enableCancelButton(false)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnEnterStartCodeFailEvent) }
    }

    override fun renderRetryEnterCodeState() {
        displayCurrentState(R.string.retry_enter_code_state)
        enableContinueButton(true)
        enableCancelButton(true)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnEnterCodeSuccessEvent) }
        setCancelButtonAction { stateMachineManager.stateMachine.transition(Event.OnEnterStartCodeFailEvent) }
    }

    override fun renderPresentationState() {
        displayCurrentState(R.string.presentation_state)
        enableContinueButton(true)
        enableCancelButton(true)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnPresentationStateAcceptEvent) }
        setCancelButtonAction { stateMachineManager.stateMachine.transition(Event.OnPresentationStateDeclinedEvent) }
    }

    override fun renderPresentationDeclineState() {
        displayCurrentState(R.string.presentation_declined_state)
        enableContinueButton(true)
        enableCancelButton(false)
        setContinueButtonAction { stateMachineManager.stateMachine.transition(Event.OnPresentationStateDeclinedEvent) }
    }

    override fun renderRiddleToBerlinState() {
        displayCurrentState(R.string.riddle_to_berlin_state)
    }

    override fun renderRiddleToBerlinSuccessState() {
    }

    override fun renderRiddleToBerlinFailureState() {
    }

    override fun renderRiddleToGendarmenMarktState() {
    }

    override fun renderRiddleToGendarmenMarktSuccessState() {
    }

    override fun renderRiddleToGendarmenMarktFailureState() {
    }

    override fun renderShowLockFromGendarmenMarktState() {
    }

    override fun renderGetLocationBeforeCharlottenBurgRiddleState() {
    }

    override fun renderGetLocationBeforeCharlottenBurgRiddleFailureState() {
    }

    override fun renderRiddleGoToCharlottenBurgState() {
    }

    override fun renderRiddleGoToCharlottenBurgMarktSuccessState() {
    }

    override fun renderRiddleGoToCharlottenBurgMarktFailureState() {
    }

    override fun renderShowLockCodeFromCharlottenBurgMarktState() {
    }

    override fun renderGetLocationBeforeAlexanderPlatzMarktRiddleState() {
    }

    override fun renderGetLocationBeforeAlexanderPlatzMarktRiddleFailureState() {
    }

    override fun renderRiddleGoToAlexanderPlatzMarktState() {
    }

    override fun renderRiddleGoToAlexanderPlatzMarktSuccessState() {
    }

    override fun renderRiddleGoToAlexanderPlatzMarktFailureState() {
    }

    override fun renderShowLockCodeFromAlexanderPlatzMarktState() {
    }

    override fun renderGetLocationBeforeFinaleState() {
    }

    override fun renderGetLocationBeforeFinaleFailureState() {
    }

    override fun renderFinaleState() {
    }

    override fun renderGrandFinaleState() {
    }

    //=====================================================================
    //=====================================================================
    private fun displayCurrentState(stateString: Int) {
        viewGameCurrentState.setText(stateString)
    }

    private fun enableContinueButton(isEnabled: Boolean) {
        buttonGameContinue.isEnabled = isEnabled
    }

    private fun enableCancelButton(isEnabled: Boolean) {
        buttonGameCancel.isEnabled = isEnabled
    }

    private fun setContinueButtonAction(onClick: () -> Unit) {
        buttonGameContinue.setOnClickListener {
            onClick()
        }
    }

    private fun setCancelButtonAction(onClick: () -> Unit) {
        buttonGameCancel.setOnClickListener {
            onClick()
        }
    }
}
