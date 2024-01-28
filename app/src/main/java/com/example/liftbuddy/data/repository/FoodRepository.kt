package com.example.liftbuddy.data.repository

import com.example.liftbuddy.data.entity.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("foods")

    private fun String?.toFloatOrNullFromMeasurement(): Float? {
        return this?.split(" ")?.firstOrNull()?.toFloatOrNull()
    }

    fun searchFoods(query: String, callback: (List<Food>) -> Unit) {
        databaseReference.orderByChild("name")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val foods = mutableListOf<Food>()
                    for (foodSnapshot in snapshot.children) {
                        val foodName = foodSnapshot.child("name").getValue(String::class.java)
                        val carbohydrateString = foodSnapshot.child("carbohydrate").getValue(String::class.java)
                        val proteinString = foodSnapshot.child("protein").getValue(String::class.java)
                        val fatString = foodSnapshot.child("fat").getValue(String::class.java)

                        val calories =
                            foodSnapshot.child("calories").getValue(Long::class.java)?.toInt()

                        println( "Carbs: $carbohydrateString, Protein: $proteinString, Fat: $fatString")

                        val carbohydrate = carbohydrateString.toFloatOrNullFromMeasurement()?.toInt()
                        val protein = proteinString.toFloatOrNullFromMeasurement()?.toInt()
                        val fat = fatString.toFloatOrNullFromMeasurement()?.toInt()


                        println("foodName: ${foodName}")
                        println("carbohydrate: ${carbohydrate}")
                        println("protein: ${protein}")
                        println("fat: ${fat}")
                        println("calories: ${calories}")


                        if (foodName != null && carbohydrate != null && protein != null && fat != null && calories != null) {
                            val food = Food(
                                name = foodName,
                                carbohydrates = carbohydrate,
                                protein = protein,
                                fats = fat,
                                calories = calories
                            )
                            foods.add(food)
                        }
                    }
                    callback(foods)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            })
    }


}
