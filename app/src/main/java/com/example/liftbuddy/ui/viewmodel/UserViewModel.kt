package com.example.liftbuddy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftbuddy.data.entity.Food
import com.example.liftbuddy.data.entity.User.User
import com.example.liftbuddy.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // LiveData for user operations feedback
    private val _userOperationStatus = MutableLiveData<String>()
    val userOperationStatus: LiveData<String> get() = _userOperationStatus

    // Function to create a new user
    fun createUser(email: String, password: String, name: String) = viewModelScope.launch {
        repository.createUserAndRecord(email, password, name) { user, exception ->
            if (exception == null) {
                _userOperationStatus.postValue("User created: $user")
            } else {
                _userOperationStatus.postValue("Error creating user: ${exception.message}")
            }
        }
    }

    // Function to sign in a user
    fun signInUser(email: String, password: String) = viewModelScope.launch {
        repository.signInUser(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _userOperationStatus.postValue("User signed in")
            } else {
                _userOperationStatus.postValue("Sign in failed: ${task.exception?.message}")
            }
        }
    }

    // Function to sign out a user
    fun signOutUser() = viewModelScope.launch {
        repository.signOutUser()
        _userOperationStatus.postValue("User signed out")
    }

    // Function to get the current FirebaseUser
    fun getCurrentFirebaseUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

    // Function to update a user and calculate recommended goals
    fun updateUserAndCalculateGoals(user: User) = viewModelScope.launch {
        repository.updateUserAndCalculateGoals(user) { success, exception ->
            if (success) {
                _userOperationStatus.postValue("User updated")
            } else {
                _userOperationStatus.postValue("Error updating user: ${exception?.message}")
            }
        }
    }


    private val _consumedValues = MutableLiveData<Quadruple<Long, Long, Long, Long>>()
    val consumedValues: LiveData<Quadruple<Long, Long, Long, Long>> get() = _consumedValues

    private val _recommendedValues = MutableLiveData<Quadruple<Long, Long, Long, Long>>()
    val recommendedValues: LiveData<Quadruple<Long, Long, Long, Long>> get() = _recommendedValues

    // Function to retrieve consumed values for the current user
    fun fetchConsumedValuesForCurrentUser() = viewModelScope.launch {
        val userId = repository.getCurrentUser()?.uid ?: return@launch

        repository.getUserData(userId) { user, exception ->
            if (exception == null && user != null) {
                _consumedValues.postValue(
                    Quadruple(
                        user.getConsumedCaloriesValue().toLong(),
                        user.getConsumedProteinValue().toLong(),
                        user.getConsumedCarbohydrateValue().toLong(),
                        user.getConsumedFatValue().toLong()
                    )
                )
            } else {
                _userOperationStatus.postValue("Error fetching consumed values: ${exception?.message}")
            }
        }
    }

    // Function to retrieve recommended values for the current user
    fun fetchRecommendedValuesForCurrentUser() = viewModelScope.launch {
        val userId = repository.getCurrentUser()?.uid ?: return@launch

        repository.getUserData(userId) { user, exception ->
            if (exception == null && user != null) {
                _recommendedValues.postValue(
                    Quadruple(
                        user.getRecommendedCaloriesValue(),
                        user.getRecommendedProteinValue(),
                        user.getRecommendedCarbohydrateValue(),
                        user.getRecommendedFatValue()
                    )
                )
            } else {
                _userOperationStatus.postValue("Error fetching recommended values: ${exception?.message}")
            }
        }
    }

    fun addFoodToConsumed(food: Food) = viewModelScope.launch {
        val currentUser = repository.getCurrentUser()
        val userId = currentUser?.uid ?: return@launch

        repository.getUserData(userId) { user, exception ->
            if (exception == null && user != null) {
                // Add the nutritional values of the selected food to the user's consumed values
                val updatedUser = user.apply {
                    food.calories = food.calories!! + food.calories!!
                    food.protein = food.protein!! + food.protein!!
                    food.carbohydrates = food.carbohydrates!! + food.carbohydrates!!
                    food.fats = food.fats!! + food.fats!!
                }

                // Update the user's consumed values in the repository and recalculate recommended goals
                repository.updateUserAndCalculateGoals(updatedUser) { success, exception ->
                    if (success) {
                        println("Updated user Successfully")
                    } else {
                        if (exception != null) {
                            println("Updated user Failure: ${exception.message}")
                        }
                    }
                }
            }
        }
    }


    private fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    fun addFoodToUserConsumption(userId: String, food: Food) {
        val userId = getCurrentUserId()
        if (userId == null) {
            // Handle the case when the user is not logged in or user ID is not available
            return
        }
        // Fetch the current user data from the database
        databaseReference.child(userId).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val user = dataSnapshot.getValue(User::class.java) ?: User()

                // Add consumed food values to user
                user.apply {
                    food.calories?.let { addConsumedCalories(it) }
                    food.protein?.let { addConsumedProtein(it) }
                    food.carbohydrates?.let { addConsumedCarbohydrate(it) }
                    food.fats?.let { addConsumedFat(it) }
                }

                // Update the user in the database
                databaseReference.child(userId).setValue(user)
            } else {
                // Handle the case when user data does not exist in the database
            }
        }.addOnFailureListener {
            // Handle failure (e.g., log the error or notify the user)
        }
    }

}

data class Quadruple<A, B, C, D>(
    val first: Long,
    val second: Long,
    val third: Long,
    val fourth: Long
)

