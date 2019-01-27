package com.myprojects.eduardoexposito.sphinx.modules

import com.myprojects.eduardoexposito.sphinx.models.Event
import com.myprojects.eduardoexposito.sphinx.models.SideEffect
import com.myprojects.eduardoexposito.sphinx.models.State

interface SideEffectsDelegateInterface {

    fun performSideEffect(sideEffect: SideEffect)

    fun renderInitialState(initialState: State)
    fun renderFirstAppStartGetLocationState()
    fun renderFirstAppStartGetLocationFailureState()
    fun renderRetryFirstAppStartGetLocationState()
    fun renderAppIntroductionState()
    fun renderEnterCodeState()
    fun renderEnterCodeFailureState()
    fun renderRetryEnterCodeState()
    fun renderPresentationState()
    fun renderPresentationDeclineState()
    fun renderRiddleToBerlinState()
    fun renderRiddleToBerlinSuccessState()
    fun renderRiddleToBerlinFailureState()
    fun renderRiddleToGendarmenMarktState()
    fun renderRiddleToGendarmenMarktSuccessState()
    fun renderRiddleToGendarmenMarktFailureState()
    fun renderShowLockFromGendarmenMarktState()
    fun renderGetLocationBeforeCharlottenBurgRiddleState()
    fun renderGetLocationBeforeCharlottenBurgRiddleFailureState()
    fun renderRiddleGoToCharlottenBurgState()
    fun renderRiddleGoToCharlottenBurgMarktSuccessState()
    fun renderRiddleGoToCharlottenBurgMarktFailureState()
    fun renderShowLockCodeFromCharlottenBurgMarktState()
    fun renderGetLocationBeforeAlexanderPlatzMarktRiddleState()
    fun renderGetLocationBeforeAlexanderPlatzMarktRiddleFailureState()
    fun renderRiddleGoToAlexanderPlatzMarktState()
    fun renderRiddleGoToAlexanderPlatzMarktSuccessState()
    fun renderRiddleGoToAlexanderPlatzMarktFailureState()
    fun renderShowLockCodeFromAlexanderPlatzMarktState()
    fun renderGetLocationBeforeFinaleState()
    fun renderGetLocationBeforeFinaleFailureState()
    fun renderFinaleState()
    fun renderGrandFinaleState()
    fun showTransitionError(state: State, event: Event)
}
