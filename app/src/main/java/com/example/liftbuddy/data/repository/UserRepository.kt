package com.example.liftbuddy.data.repository

import com.example.liftbuddy.data.entity.User.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserRepository(private val firebaseAuth: FirebaseAuth) {

    // Function to create a new user
    fun createUserAndRecord(email: String, password: String, name: String, callback: (User?, Exception?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val userId = firebaseUser?.uid ?: return@addOnCompleteListener

                    val newUser = User(
                        email = email,
                        password = password,
                        name = name,
                        age = 0,
                        weight = 0,
                        height = 0,
                        gender = "",
                        targetWeight = 0,
                        timeLimitInWeeks = 0
                    )

                    val database = FirebaseDatabase.getInstance()
                    val userRef = database.getReference("users").child(userId)

                    // Save the new user to the database
                    userRef.setValue(newUser).addOnCompleteListener { dbTask ->
                        if (dbTask.isSuccessful) {
                            callback(newUser, null)
                        } else {
                            callback(null, dbTask.exception)
                        }
                    }
                } else {
                    callback(null, authTask.exception)
                }
            }
    }

    // Function to sign in a user
    fun signInUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    // Function to sign out a user
    fun signOutUser() {
        firebaseAuth.signOut()
    }

    // Function to get the current user
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // Function to update a user and calculate recommended goals
    fun updateUserAndCalculateGoals(user: User, callback: (Boolean, Exception?) -> Unit) {
        val userId = getCurrentUser()?.uid ?: return

        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId)

        // Recalculate the recommended goals
        user.calculateRecommendedGoals()

        // Update the user's data in the database
        userRef.setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, task.exception)
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    // Function to retrieve user data from Firebase
    fun getUserData(userId: String, callback: (User?, Exception?) -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    callback(user, null)
                } else {
                    callback(null, NullPointerException("User not found"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null, databaseError.toException())
            }
        })
    }


}
