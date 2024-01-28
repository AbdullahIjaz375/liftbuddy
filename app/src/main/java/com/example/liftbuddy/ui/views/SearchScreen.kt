package com.example.liftbuddy.ui.views

import FoodViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.entity.Food
import com.example.liftbuddy.data.repository.FoodRepository
import com.example.liftbuddy.di.FoodViewModelFactory
import com.example.liftbuddy.ui.theme.FontDefinitions
import com.example.liftbuddy.ui.viewmodel.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun SearchScreen(navController: NavController, userViewModel: UserViewModel) {
    val foodViewModel: FoodViewModel = viewModel(factory = FoodViewModelFactory(FoodRepository()))
    val searchResults by foodViewModel.searchResults.observeAsState(initial = emptyList())
    val firebaseUser = Firebase.auth.currentUser
    val userId = firebaseUser?.uid ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Column {
            Spacer(modifier = Modifier.weight(0.8f))
            SearchBar { query ->
                foodViewModel.searchFoods(query)
            }
            Spacer(modifier = Modifier.height(16.dp))
            FoodList(foods = searchResults, userId, navController, userViewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = searchText,
        placeholder = {
            Text("Search for a food...")
        },
        onValueChange = {
            searchText = it
        },
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchText.text)
            keyboardController?.hide()
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Composable
fun FoodList(foods: List<Food>?, userId: String, navController: NavController, userViewModel: UserViewModel) {
    foods?.let {
        LazyColumn {
            items(it) { food ->
                FoodItem(food = food, userId, navController, userViewModel)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun FoodItem(food: Food, userId: String, navController: NavController, userViewModel: UserViewModel) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.rectangle),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(60.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Add",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(15.dp)
                .clickable {
                    if (userId.isNotEmpty()) {
                        userViewModel.addFoodToUserConsumption(userId, food)
                        navController.navigate("dashboardFragment")
                    } else {
                        // Handle case where userId is not available
                    }
                }
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = food.name,
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontDefinitions.Montserrat
            )
            LazyRow(
                modifier = Modifier.padding(top = 4.dp),
                content = {
                    val nutritionLabels = listOf(
                        "Calories" to food.calories,
                        "Protein" to food.protein,
                        "Fats" to food.fats,
                        "Carbohydrates" to food.carbohydrates
                    )
                    items(nutritionLabels.size) { index ->
                        val (label, value) = nutritionLabels[index]
                        Text(
                            text = "$label: $value",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontFamily = FontDefinitions.Montserrat,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            )
        }
    }
}


