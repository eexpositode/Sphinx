package com.myprojects.eduardoexposito.sphinx.models

sealed class State {

    object AppStartGetLocationState : State()
    object AppStartGetLocationFailureState : State()
    object AppIntroductionState : State()
    object EnterStartCodeState : State()
    object EnterStartCodeCodeFailureState : State()
    object PresentationState : State()
    object PresentationStateDeclinedState : State()
    object RiddleToBerlinState : State()
    object RiddleToBerlinLocationSuccessState : State()
    object RiddleToBerlinLocationFailureState : State()
    object RiddleToGendarmenMarktState : State()
    object RiddleToGendarmenMarktLocationSuccessState : State()
    object RiddleToGendarmenMarktLocationFailureState : State()
    object ShowLockCodeFromGendarmentMarktState : State()
    object GetLocationBeforeCharlottenBurgRiddleState : State()
    object GetLocationBeforeCharlottenBurgRiddleFailureState : State()
    object RiddleToCharlottenBurgMarktState : State()
    object RiddleToCharlottenBurgMarktLocationSuccessState : State()
    object RiddleToCharlottenBurgMarktLocationFailureState : State()
    object ShowLockCodeFromCharlottenBurgMarktState : State()
    object GetLocationBeforeAlexanderPlatzMarktRiddleState : State()
    object GetLocationBeforeAlexanderPlatzMarktRiddleFailureState : State()
    object RiddleToAlexanderPlatzMarktState : State()
    object RiddleToAlexanderPlatzMarktLocationSuccessState : State()
    object RiddleToAlexanderPlatzMarktLocationFailureState : State()
    object ShowLockCodeFromAlexanderPlatzMarktState : State()
    object GetLocationBeforeFinaleState : State()
    object GetLocationBeforeFinaleFailureState : State()
    object FinaleState : State()
    object GrandFinaleState : State()
}

sealed class Event {
    object OnAppStartGetLocationFailEvent : Event()
    object OnAppStartGetLocationSuccessEvent : Event()
    object OnAppIntroductionContinueEvent : Event()
    object OnEnterStartCodeFailEvent : Event()
    object OnEnterCodeSuccessEvent : Event()
    object OnPresentationStateDeclinedEvent : Event()
    object OnPresentationStateAcceptEvent : Event()
    object OnBerlinLocationFailEvent : Event()
    object OnBerlinLocationSuccessEvent : Event()
    object OnGendarmenMarktLocationFailEvent : Event()
    object OnGendarmenMarktLocationSuccessEvent : Event()
    object ShowLockCodeFromGendarmentMarktContinueEvent : Event()
    object CheckLocationBeforeCharlottenBurgRiddleFailEvent : Event()
    object CheckLocationBeforeCharlottenBurgRiddleSuccessEvent : Event()
    object OnCharlottenBurgMarktLocationFailEvent : Event()
    object OnCharlottenBurgMarktLocationSuccessEvent : Event()
    object ShowLockCodeFromCharlottenBurgMarktContinueEvent : Event()
    object CheckLocationBeforeAlexanderPlatzMarktRiddleFailEvent : Event()
    object CheckLocationBeforeAlexanderPlatzMarktRiddleSuccessEvent : Event()
    object OnAlexanderPlatzMarktLocationFailEvent : Event()
    object OnAlexanderPlatzMarktLocationSuccessEvent : Event()
    object ShowLockCodeFromAlexanderPlatzMarktContinueEvent : Event()
    object CheckLocationBeforeFinaleFailEvent : Event()
    object CheckLocationBeforeFinaleSuccessEvent : Event()
    object OnFinaleLocationFailEvent : Event()
    object OnFinaleContinueEvent : Event()
}

sealed class SideEffect {
    object RenderAppStartGetLocationState : SideEffect()
    object RenderAppStartGetLocationFailureState : SideEffect()
    object RenderRetryGetLocationState : SideEffect()
    object RenderAppIntroductionState : SideEffect()
    object RenderEnterCodeState : SideEffect()
    object RenderEnterStartCodeFailureState : SideEffect()
    object RenderRetryEnterStartCodeState : SideEffect()
    object RenderPresentationState : SideEffect()
    object RenderPresentationDeclineState : SideEffect()
    object RenderRiddleToBerlinState : SideEffect()
    object RenderRiddleToBerlinLocationFailureState : SideEffect()
    object RenderRetryRiddleToBerlinState : SideEffect()
    object RenderRiddleToBerlinLocationSuccessState : SideEffect()
    object RenderRiddleToGendarmenMarktState : SideEffect()
    object RenderRiddleToGendarmenMarktLocationFailureState : SideEffect()
    object RenderRetryRiddleToGendarmenMarktState : SideEffect()
    object RenderRiddleToGendarmenMarktLocationSuccessState : SideEffect()
    object RenderShowLockCodeFromGendarmenMarktState : SideEffect()
    object RenderGetLocationBeforeCharlottenBurgRiddleState : SideEffect()
    object RenderLocationBeforeCharlottenBurgRiddleFailureState : SideEffect()
    object RenderRetryGetLocationBeforeCharlottenBurgRiddleState : SideEffect()
    object RenderRiddleToCharlottenBurgState : SideEffect()
    object RenderRiddleToCharlottenBurgMarktLocationFailureState : SideEffect()
    object RenderRetryRiddleToCharlottenBurgMarktState : SideEffect()
    object RenderRiddleToCharlottenBurgMarktLocationSuccessState : SideEffect()
    object RenderShowLockCodeFromCharlottenBurgMarktState : SideEffect()
    object RenderLocationBeforeAlexanderPlatzRiddleState : SideEffect()
    object RenderLocationBeforeAlexanderPlatzRiddleFailureState : SideEffect()
    object RenderRetryGetLocationBeforeAlexanderPlatzMarktRiddleState : SideEffect()
    object RenderRiddleToAlexanderPlatzMarktState : SideEffect()
    object RenderRiddleToAlexanderPlatzMarktLocationFailureState : SideEffect()
    object RenderRetryRiddleToAlexanderPlatzMarktState : SideEffect()
    object RenderRiddleToAlexanderPlatzMarktLocationSuccessState : SideEffect()
    object RenderShowLockCodeFromAlexanderPlatzMarktState : SideEffect()
    object RenderLocationBeforeFinaleState : SideEffect()
    object RenderLocationBeforeFinaleFailureState : SideEffect()
    object RenderRetryGetLocationBeforeFinaleState : SideEffect()
    object RenderFinaleState : SideEffect()
    object RenderGrandFinaleState : SideEffect()
}

val stateToSideEffect = hashMapOf(
    State.AppStartGetLocationState to SideEffect.RenderAppStartGetLocationState,
    State.AppIntroductionState to SideEffect.RenderAppIntroductionState,
    State.PresentationState to SideEffect.RenderPresentationState,
    State.RiddleToBerlinState to SideEffect.RenderRiddleToBerlinState,
    State.RiddleToGendarmenMarktState to SideEffect.RenderRiddleToGendarmenMarktState,
    State.ShowLockCodeFromGendarmentMarktState to SideEffect.RenderShowLockCodeFromGendarmenMarktState,
    State.RiddleToCharlottenBurgMarktState to SideEffect.RenderRiddleToCharlottenBurgState,
    State.ShowLockCodeFromCharlottenBurgMarktState to SideEffect.RenderShowLockCodeFromCharlottenBurgMarktState,
    State.RiddleToAlexanderPlatzMarktState to SideEffect.RenderRiddleToAlexanderPlatzMarktState,
    State.ShowLockCodeFromAlexanderPlatzMarktState to SideEffect.RenderShowLockCodeFromAlexanderPlatzMarktState,
    State.FinaleState to SideEffect.RenderFinaleState
)
