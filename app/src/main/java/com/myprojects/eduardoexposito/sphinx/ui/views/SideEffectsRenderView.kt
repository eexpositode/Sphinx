package com.myprojects.eduardoexposito.sphinx.ui.views

import android.content.Context
import android.location.Location
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import com.myprojects.eduardoexposito.sphinx.R
import com.myprojects.eduardoexposito.sphinx.helpers.*
import com.myprojects.eduardoexposito.sphinx.models.*
import com.myprojects.eduardoexposito.sphinx.models.SideEffect.*
import com.myprojects.eduardoexposito.sphinx.modules.SideEffectsDelegateInterface
import com.myprojects.eduardoexposito.sphinx.modules.StateMachineManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main_container.view.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class SideEffectsRenderView : ConstraintLayout, SideEffectsDelegateInterface, PinContainerView.OnEnterCodeListener {

    private lateinit var stateMachineManager: StateMachineManager
    private var currentLocation: Location? = null
    private val pinEnterView by bind<PinContainerView>(R.id.pinEnterView)
    private val dialogView by bind<DialogContainerView>(R.id.dialogView)
    private val lockCodeView by bind<LockCodeContainer>(R.id.lockCodeView)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUp()
    }

    private fun setUp() {
        hideChildren()

        stateMachineManager = StateMachineManager(context, this)
        //TODO Depending on the initial state render different things
    }

    //=====================================================================
    // Location
    //=====================================================================
    fun onLocationChanged(currentLocation: Location) {
        this.currentLocation = currentLocation
    }

    //=====================================================================
    // Transitions
    //=====================================================================
    override fun performSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            RenderAppStartGetLocationState -> renderFirstAppStartGetLocationState()
            RenderAppStartGetLocationFailureState -> renderFirstAppStartGetLocationFailureState()
            RenderRetryGetLocationState -> renderRetryFirstAppStartGetLocationState()
            RenderAppIntroductionState -> renderAppIntroductionState()
            RenderEnterCodeState -> renderEnterCodeState()
            RenderEnterStartCodeFailureState -> renderEnterCodeFailureState()
            RenderRetryEnterStartCodeState -> renderRetryEnterCodeState()
            RenderPresentationState -> renderPresentationState()
            RenderPresentationDeclineState -> renderPresentationDeclineState()
            RenderRiddleToBerlinState -> renderRiddleToBerlinState()
            RenderRiddleToBerlinLocationFailureState -> renderRiddleToBerlinFailureState()
            RenderRetryRiddleToBerlinState -> renderRiddleToBerlinState()
            RenderRiddleToBerlinLocationSuccessState -> renderRiddleToBerlinSuccessState()
            RenderRiddleToGendarmenMarktState -> renderRiddleToGendarmenMarktState()
            RenderRiddleToGendarmenMarktLocationFailureState -> renderRiddleToGendarmenMarktFailureState()
            RenderRetryRiddleToGendarmenMarktState -> renderRiddleToGendarmenMarktState()
            RenderRiddleToGendarmenMarktLocationSuccessState -> renderRiddleToGendarmenMarktSuccessState()
            RenderShowLockCodeFromGendarmenMarktState -> renderShowLockFromGendarmenMarktState()
            RenderGetLocationBeforeCharlottenBurgRiddleState -> renderGetLocationBeforeCharlottenBurgRiddleState()
            RenderLocationBeforeCharlottenBurgRiddleFailureState -> renderGetLocationBeforeCharlottenBurgRiddleFailureState()
            RenderRetryGetLocationBeforeCharlottenBurgRiddleState -> renderGetLocationBeforeCharlottenBurgRiddleState()
            RenderRiddleToCharlottenBurgState -> renderRiddleGoToCharlottenBurgState()
            RenderRiddleToCharlottenBurgMarktLocationFailureState -> renderRiddleGoToCharlottenBurgMarktFailureState()
            RenderRetryRiddleToCharlottenBurgMarktState -> renderRiddleGoToCharlottenBurgState()
            RenderRiddleToCharlottenBurgMarktLocationSuccessState -> renderRiddleGoToCharlottenBurgMarktSuccessState()
            RenderShowLockCodeFromCharlottenBurgMarktState -> renderShowLockCodeFromCharlottenBurgMarktState()
            RenderLocationBeforeAlexanderPlatzRiddleState -> renderGetLocationBeforeAlexanderPlatzMarktRiddleState()
            RenderLocationBeforeAlexanderPlatzRiddleFailureState -> renderGetLocationBeforeAlexanderPlatzMarktRiddleFailureState()
            RenderRetryGetLocationBeforeAlexanderPlatzMarktRiddleState -> renderGetLocationBeforeAlexanderPlatzMarktRiddleState()
            RenderRiddleToAlexanderPlatzMarktState -> renderRiddleGoToAlexanderPlatzMarktState()
            RenderRiddleToAlexanderPlatzMarktLocationFailureState -> renderRiddleGoToAlexanderPlatzMarktFailureState()
            RenderRetryRiddleToAlexanderPlatzMarktState -> renderRiddleGoToAlexanderPlatzMarktState()
            RenderRiddleToAlexanderPlatzMarktLocationSuccessState -> renderRiddleGoToAlexanderPlatzMarktSuccessState()
            RenderShowLockCodeFromAlexanderPlatzMarktState -> renderShowLockCodeFromAlexanderPlatzMarktState()
            RenderLocationBeforeFinaleState -> renderGetLocationBeforeFinaleState()
            RenderLocationBeforeFinaleFailureState -> renderGetLocationBeforeFinaleFailureState()
            RenderRetryGetLocationBeforeFinaleState -> renderGetLocationBeforeFinaleState()
            RenderFinaleState -> renderFinaleState()
            RenderGrandFinaleState -> renderGrandFinaleState()
        }
        sphinxAvatarPic.bringToFront()
    }

    override fun renderInitialState(initialState: State) {
        performActionAfterDelay(
            3000L,
            { sphinxAvatarPic.fadeIn(3000L, 0L, AccelerateDecelerateInterpolator()) },
            { performSideEffect(stateToSideEffect.getOrElse(initialState) { StateMachineManager.INITIAL_SIDE_EFFECT }) }
        )
    }

    override fun renderFirstAppStartGetLocationState() {
        performActionAfterDelay(
            0L,
            {
                sphinxAvatarPic.fadeIn(0L, 0L, AccelerateDecelerateInterpolator())
            },
            {
                checkExpectedLocation(
                    GameLocation.TENERIFE,
                    Event.OnAppStartGetLocationSuccessEvent,
                    Event.OnAppStartGetLocationFailEvent
                )
            }
        )
    }

    override fun renderFirstAppStartGetLocationFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.app_start_get_location_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.app_start_get_location_failure_state_continue_button_txt,
                positiveButtonAction = { stateMachineManager.stateMachine.transition(Event.OnAppStartGetLocationFailEvent) }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRetryFirstAppStartGetLocationState() {
        performActionAfterDelay(
            1000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_CENTER, 1000L)
                animateBackgroundTo(BgAnimationType.BACK, 300)
                dialogView.fadeOut(300L)
            },
            {
                checkExpectedLocation(
                    GameLocation.TENERIFE,
                    Event.OnAppStartGetLocationSuccessEvent,
                    Event.OnAppStartGetLocationFailEvent
                )
            }
        )
    }

    override fun renderAppIntroductionState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.app_introduction_dialog_txt,
                positiveButtonTextResId = R.string.app_introduction_continue_button_txt,
                positiveButtonAction = { stateMachineManager.stateMachine.transition(Event.OnAppIntroductionContinueEvent) }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderEnterCodeState() {
        translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_TOP, 1000L)
        dialogView.fadeOut(300L)
        pinEnterView.apply {
            setUp(this@SideEffectsRenderView, R.string.enter_start_code_state_action_txt)
            fadeIn(700L, 300L, AccelerateDecelerateInterpolator())
        }
    }

    override fun renderEnterCodeFailureState() {
        performActionAfterDelay(
            3000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.PIC_TOP_TO_CENTER, 500L)
                animateBackgroundTo(BgAnimationType.TO_FAILURE, 500)
                pinEnterView.fadeOut(200L, 0L, AccelerateInterpolator())
                actionResultDisplay.apply {
                    setText(R.string.enter_code_failure_state_action_txt)
                    fadeIn(200L, 400L, AccelerateInterpolator())
                }
            },
            { stateMachineManager.stateMachine.transition(Event.OnEnterStartCodeFailEvent) }
        )
    }

    override fun renderRetryEnterCodeState() {
        translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_TOP, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        dialogView.fadeOut(300L)
        actionResultDisplay.fadeOut(300L)
        pinEnterView.apply {
            setUp(this@SideEffectsRenderView, getRandomStringFromStringArray(R.array.retry_enter_code_state_action_txt))
            fadeIn(700L, 300L, AccelerateDecelerateInterpolator())
        }
    }

    override fun renderPresentationState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        pinEnterView.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.presentation_state_dialog_txt,
                positiveButtonTextResId = R.string.presentation_state_accept_button_txt,
                positiveButtonAction = { stateMachineManager.stateMachine.transition(Event.OnPresentationStateAcceptEvent) },
                negativeButtonTextResId = R.string.presentation_state_decline_button_txt,
                negativeButtonAction = { stateMachineManager.stateMachine.transition(Event.OnPresentationStateDeclinedEvent) }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderPresentationDeclineState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.presentation_declined_state_dialog_txt,
                positiveButtonTextResId = R.string.presentation_declined_state_accept_button_txt,
                positiveButtonAction = { stateMachineManager.stateMachine.transition(Event.OnPresentationStateDeclinedEvent) })
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleToBerlinState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.riddle_to_berlin_state_dialog_txt,
                positiveButtonTextResId = R.string.riddle_to_berlin_state_solved_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN,
                        Event.OnBerlinLocationSuccessEvent,
                        Event.OnBerlinLocationFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleToBerlinSuccessState() {
        performActionAfterDelay(
            3000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_CENTER, 500L)
                animateBackgroundTo(BgAnimationType.TO_SUCCESS, 500)
                dialogView.fadeOut(200L, 0L, AccelerateInterpolator())
                actionResultDisplay.apply {
                    setText(R.string.riddle_to_berlin_location_success_action_txt)
                    fadeIn(200L, 400L, AccelerateInterpolator())
                }
            },
            { stateMachineManager.stateMachine.transition(Event.OnBerlinLocationSuccessEvent) }
        )
    }

    override fun renderRiddleToBerlinFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.riddle_to_berlin_location_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.riddle_to_berlin_location_failure_retry_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnBerlinLocationFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleToGendarmenMarktState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        actionResultDisplay.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.riddle_to_gendarmentmarkt_dialog_txt,
                positiveButtonTextResId = R.string.riddle_to_gendarmentmarkt_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN_GENDARMENMARKT,
                        Event.OnGendarmenMarktLocationSuccessEvent,
                        Event.OnGendarmenMarktLocationFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleToGendarmenMarktSuccessState() {
        performActionAfterDelay(
            3000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_CENTER, 500L)
                animateBackgroundTo(BgAnimationType.TO_SUCCESS, 500)
                dialogView.fadeOut(200L, 0L, AccelerateInterpolator())
                actionResultDisplay.apply {
                    setText(R.string.riddle_to_gendarmentmarkt_location_success_state_action_txt)
                    fadeIn(200L, 400L, AccelerateInterpolator())
                }
            },
            { stateMachineManager.stateMachine.transition(Event.OnGendarmenMarktLocationSuccessEvent) }
        )
    }

    override fun renderRiddleToGendarmenMarktFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.riddle_to_gendarmentmarkt_location_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.riddle_to_gendarmentmarkt_location_failure_state_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnGendarmenMarktLocationFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderShowLockFromGendarmenMarktState() {
        translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_TOP, 1000L)
        animateBackgroundTo(BgAnimationType.TO_SUCCESS, 1000)
        dialogView.fadeOut(300L, 0L, AccelerateInterpolator())
        actionResultDisplay.fadeOut(300L)
        lockCodeView.apply {
            setUp(R.string.show_lock_from_gendarmenmarkt_action_txt)
            displayGendarmenMarktCode { stateMachineManager.stateMachine.transition(Event.ShowLockCodeFromGendarmentMarktContinueEvent) }
            fadeIn(300L, 900L)
        }
    }

    override fun renderGetLocationBeforeCharlottenBurgRiddleState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        lockCodeView.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.riddle_before_charlottenburg_riddle_state_dialog_txt,
                positiveButtonTextResId = R.string.riddle_before_charlottenburg_riddle_state_solved_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN,
                        Event.CheckLocationBeforeCharlottenBurgRiddleSuccessEvent,
                        Event.CheckLocationBeforeCharlottenBurgRiddleFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderGetLocationBeforeCharlottenBurgRiddleFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.check_location_before_charlottenburg_riddle_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.check_before_charlottenburg_riddle_failure_retry_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.CheckLocationBeforeCharlottenBurgRiddleFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleGoToCharlottenBurgState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        actionResultDisplay.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.riddle_to_charlottenburgmarkt_dialog_txt,
                positiveButtonTextResId = R.string.riddle_to_charlottenburgmarkt_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN_CHARLOTTENBURG,
                        Event.OnCharlottenBurgMarktLocationSuccessEvent,
                        Event.OnCharlottenBurgMarktLocationFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleGoToCharlottenBurgMarktSuccessState() {
        performActionAfterDelay(
            3000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_CENTER, 500L)
                animateBackgroundTo(BgAnimationType.TO_SUCCESS, 500)
                dialogView.fadeOut(200L, 0L, AccelerateInterpolator())
                actionResultDisplay.apply {
                    setText(R.string.riddle_to_charlottenburgmarkt_location_success_state_action_txt)
                    fadeIn(200L, 400L, AccelerateInterpolator())
                }
            },
            { stateMachineManager.stateMachine.transition(Event.OnCharlottenBurgMarktLocationSuccessEvent) }
        )
    }

    override fun renderRiddleGoToCharlottenBurgMarktFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.riddle_to_charlottenburgmarkt_location_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.riddle_to_charlottenburgmarkt_location_failure_state_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnCharlottenBurgMarktLocationFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderShowLockCodeFromCharlottenBurgMarktState() {
        translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_TOP, 1000L)
        animateBackgroundTo(BgAnimationType.TO_SUCCESS, 1000)
        dialogView.fadeOut(300L, 0L, AccelerateInterpolator())
        actionResultDisplay.fadeOut(300L)
        lockCodeView.apply {
            setUp(R.string.show_lock_from_charlottenburg_action_txt)
            displayCharlottenBurgCode { stateMachineManager.stateMachine.transition(Event.ShowLockCodeFromCharlottenBurgMarktContinueEvent) }
            fadeIn(300L, 900L)
        }
    }

    override fun renderGetLocationBeforeAlexanderPlatzMarktRiddleState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        lockCodeView.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.check_location_before_alexanderplatz_riddle_state_dialog_txt,
                positiveButtonTextResId = R.string.check_location_before_alexanderplatz_riddle_state_solved_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN,
                        Event.CheckLocationBeforeAlexanderPlatzMarktRiddleSuccessEvent,
                        Event.CheckLocationBeforeAlexanderPlatzMarktRiddleFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderGetLocationBeforeAlexanderPlatzMarktRiddleFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.check_location_before_alexanderplatz_riddle_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.check_before_alexanderplatz_riddle_failure_retry_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.CheckLocationBeforeAlexanderPlatzMarktRiddleFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleGoToAlexanderPlatzMarktState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        actionResultDisplay.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.riddle_to_alexanderplatzmarkt_dialog_txt,
                positiveButtonTextResId = R.string.riddle_to_alexanderplatzmarkt_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN_ALEXANDERPLATZ,
                        Event.OnAlexanderPlatzMarktLocationSuccessEvent,
                        Event.OnAlexanderPlatzMarktLocationFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderRiddleGoToAlexanderPlatzMarktSuccessState() {
        performActionAfterDelay(
            3000L,
            {
                translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_CENTER, 500L)
                animateBackgroundTo(BgAnimationType.TO_SUCCESS, 500)
                dialogView.fadeOut(200L, 0L, AccelerateInterpolator())
                actionResultDisplay.apply {
                    setText(R.string.riddle_to_alexanderplatzmarkt_location_success_state_action_txt)
                    fadeIn(200L, 400L, AccelerateInterpolator())
                }
            },
            { stateMachineManager.stateMachine.transition(Event.OnAlexanderPlatzMarktLocationSuccessEvent) }
        )
    }

    override fun renderRiddleGoToAlexanderPlatzMarktFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.riddle_to_alexanderplatzmarkt_location_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.riddle_to_alexanderplatzmarkt_location_failure_state_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnAlexanderPlatzMarktLocationFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderShowLockCodeFromAlexanderPlatzMarktState() {
        translateSphinxAvatar(SphinxAvatarAnimations.ICON_TO_PIC_TOP, 1000L)
        animateBackgroundTo(BgAnimationType.TO_SUCCESS, 1000)
        dialogView.fadeOut(300L, 0L, AccelerateInterpolator())
        actionResultDisplay.fadeOut(300L)
        lockCodeView.apply {
            setUp(R.string.show_lock_from_alexanderplatz_action_txt)
            displayAlexanderPlatzCode { stateMachineManager.stateMachine.transition(Event.ShowLockCodeFromAlexanderPlatzMarktContinueEvent) }
            fadeIn(300L, 900L)
        }
    }

    override fun renderGetLocationBeforeFinaleState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.BACK, 300)
        lockCodeView.fadeOut(300L)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.check_location_before_finale_state_dialog_txt,
                positiveButtonTextResId = R.string.check_location_before_finale_state_solved_button_txt,
                positiveButtonAction = {
                    checkExpectedLocation(
                        GameLocation.BERLIN,
                        Event.CheckLocationBeforeFinaleSuccessEvent,
                        Event.CheckLocationBeforeFinaleFailEvent
                    )
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderGetLocationBeforeFinaleFailureState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_FAILURE, 1000)
        dialogView.apply {
            setUp(
                dialogText = getRandomStringFromStringArray(R.array.check_location_before_finale_failure_state_dialog_txt),
                positiveButtonTextResId = R.string.check_before_finale_failure_retry_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnFinaleLocationFailEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderFinaleState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_SUCCESS, 1000)
        dialogView.apply {
            setUp(
                dialogTextResId = R.string.finale_state_dialog_txt,
                positiveButtonTextResId = R.string.finale_state_button_txt,
                positiveButtonAction = {
                    stateMachineManager.stateMachine.transition(Event.OnFinaleContinueEvent)
                }
            )
            fadeIn(500L, 500L)
        }
    }

    override fun renderGrandFinaleState() {
        translateSphinxAvatar(SphinxAvatarAnimations.PIC_TO_ICON, 1000L)
        animateBackgroundTo(BgAnimationType.TO_SUCCESS, 1000)
        dialogView.apply {
            setUp(R.string.grand_finale_state_dialog_txt)
            fadeIn(500L, 500L)
        }
    }

    override fun showTransitionError(state: State, event: Event) {
    }

    //=====================================================================
//=====================================================================
    override fun onEnterRightCode() {
        stateMachineManager.stateMachine.transition(Event.OnEnterCodeSuccessEvent)
    }

    override fun onEnterWrongCode() {
        stateMachineManager.stateMachine.transition(Event.OnEnterStartCodeFailEvent)
    }

    //=====================================================================
//=====================================================================
    private var currentAction: Disposable? = null

    private fun <T> performActionAfterDelay(
        delay: Long,
        callable: () -> T,
        consumerAction: () -> Unit
    ) {
        if (currentAction?.isDisposed == false)
            currentAction!!.dispose()


        currentAction = Completable
            .fromCallable(callable)
            .delay(delay, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(consumerAction)
    }

    private fun checkExpectedLocation(
        expectedLocation: GameLocation,
        onSuccessEvent: Event,
        onFailEvent: Event
    ) {
        if (currentLocation?.checkGameLocationReached(expectedLocation) == true) {
            stateMachineManager.stateMachine.transition(onSuccessEvent)
        } else {
            stateMachineManager.stateMachine.transition(onFailEvent)
        }
    }

    private fun getRandomStringFromStringArray(stringArrayResId: Int) = resources.getStringArray(stringArrayResId)
        .toMutableList().let {
            it[Random.nextInt(0, it.size)]
        }
}
