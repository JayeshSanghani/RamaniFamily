package com.ramanifamily.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.data.repository.MemberListRepositoryImpl
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.presentation.auth.DashboardScreen
import com.ramanifamily.presentation.auth.LoginScreen
import com.ramanifamily.presentation.auth.RegistrationScreen
import com.ramanifamily.presentation.auth.SplashScreen
import com.ramanifamily.presentation.viewmodel.SplashViewModel
import com.ramanifamily.presentation.viewmodel.SplashViewModelFactory
import com.ramanifamily.presentation.profile.AddFamilyMemberScreen
import com.ramanifamily.presentation.profile.BusinessDetailsScreen
import com.ramanifamily.presentation.profile.MaritalDetailsScreen
import com.ramanifamily.presentation.profile.PersonalDetailsScreen
import com.ramanifamily.presentation.profile.ProfileScreen
import com.ramanifamily.presentation.viewmodel.DashboardViewModel
import com.ramanifamily.presentation.viewmodel.DashboardViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            val splashViewModel: SplashViewModel = viewModel(factory = SplashViewModelFactory(AppModule.userDataStoreRepository))
            SplashScreen(viewModel = splashViewModel, onNavigateLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("registration") {
            RegistrationScreen(navController)
        }

        composable("dashboard") {

            val factory = DashboardViewModelFactory(
                userDataStoreRepository = AppModule.userDataStoreRepository,
                memberListRepository = AppModule.memberListRepository
            )
            val viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
            DashboardScreen(navController = navController, viewModel = viewModel)
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("personal_details") {
            PersonalDetailsScreen(navController)
        }

        composable("business_details") {
            BusinessDetailsScreen(navController)
        }

        composable("marital_details") {
            MaritalDetailsScreen(navController)
        }

        composable("add_family_member") {
            AddFamilyMemberScreen(navController)
        }
    }
}
