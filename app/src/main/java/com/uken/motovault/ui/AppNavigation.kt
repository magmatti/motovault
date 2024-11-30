package com.uken.motovault.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uken.motovault.sign_in.email_sign_in.EmailAuthState
import com.uken.motovault.sign_in.email_sign_in.EmailSignInViewModel
import com.uken.motovault.sign_in.google_sign_in.GoogleAuthUiClient
import com.uken.motovault.sign_in.google_sign_in.SignInViewModel
import com.uken.motovault.ui.screens.about_app.AboutAppScreen
import com.uken.motovault.ui.screens.account.AccountScreen
import com.uken.motovault.ui.screens.car_reminders.CarRemindersScreen
import com.uken.motovault.ui.screens.expenses.ExpensesScreen
import com.uken.motovault.ui.screens.home.HomeScreen
import com.uken.motovault.ui.screens.insights.InsightsScreen
import com.uken.motovault.ui.screens.login.LoginScreen
import com.uken.motovault.ui.screens.service.ServiceScreen
import com.uken.motovault.ui.screens.settings.SettingsScreen
import com.uken.motovault.ui.screens.sign_up.SignUpScreen
import com.uken.motovault.ui.screens.vehicle_info.VehicleInfoScreen
import com.uken.motovault.viewmodels.VehicleViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    context: Context,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN,
        builder = {
            composable(Routes.LOGIN_SCREEN) {
                val viewModel = viewModel<SignInViewModel>()
                val emailSignInViewModel = viewModel<EmailSignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val emailState = emailSignInViewModel.authState.observeAsState()

                LaunchedEffect(emailState.value) {
                    when(emailState.value) {
                        is EmailAuthState.Authenticated -> navController
                            .navigate(Routes.HOME_SCREEN)
                        is EmailAuthState.Error -> Toast.makeText(context,
                            (emailState.value as EmailAuthState.Error).message, Toast.LENGTH_SHORT).show()
                        else -> Unit
                    }
                }

                LaunchedEffect(key1 = Unit) {
                    if (googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate(Routes.HOME_SCREEN)
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            coroutineScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )
                LoginScreen(
                    navController,
                    state,
                    onGoogleSignInClick = {
                        coroutineScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    emailSignInViewModel
                )
            }
            composable(Routes.HOME_SCREEN) {
                HomeScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.EXPENSES_SCREEN) {
                ExpensesScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.SETTINGS_SCREEN) {
                SettingsScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.SERVICE_SCREEN) {
                ServiceScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.INSIGHTS_SCREEN) {
                InsightsScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.ACCOUNT_SCREEN) {
                AccountScreen(
                    navController,
                    googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut(
                        coroutineScope,
                        googleAuthUiClient,
                        context,
                        navController
                    )
                )
            }
            composable(Routes.VEHICLE_INFO_SCREEN) {
                val viewModel = viewModel<VehicleViewModel>()
                VehicleInfoScreen(navController, viewModel, "VF1BG0N0526997886")
            }

            composable(Routes.SIGN_UP_SCREEN) {
                val emailSignInViewModel = viewModel<EmailSignInViewModel>()
                val state = emailSignInViewModel.authState.observeAsState()

                LaunchedEffect(state.value) {
                    when(state.value) {
                        is EmailAuthState.Authenticated -> navController
                            .navigate(Routes.HOME_SCREEN)
                        is EmailAuthState.Error -> Toast.makeText(context,
                            (state.value as EmailAuthState.Error).message, Toast.LENGTH_SHORT).show()
                        else -> Unit
                    }
                }

                SignUpScreen(navController, emailSignInViewModel)
            }

            composable(Routes.ABOUT_APP_SCREEN) {
                AboutAppScreen(navController)
            }

            composable(Routes.CAR_REMINDERS_SCREEN) {
                CarRemindersScreen(navController)
            }
        }
    )
}

fun onSignOut(
    coroutineScope: CoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    navController: NavController
): () -> Unit {
    return {
        coroutineScope.launch {
            googleAuthUiClient.signOut()
            Toast.makeText(
                context,
                "Signed Out",
                Toast.LENGTH_LONG
            ).show()

            navController.popBackStack()
            navController.navigate(Routes.LOGIN_SCREEN)
        }
    }
}