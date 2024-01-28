package com.example.liftbuddy.data.entity.User

import com.example.liftbuddy.ui.viewmodel.Quadruple

data class User @JvmOverloads constructor(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    var age: Int = 0,
    var weight: Int = 0,
    var height: Int = 0,
    var gender: String = "",
    var targetWeight: Int = 0,
    var timeLimitInWeeks: Int = 0,
    var consumedCalories: Int = 0,
    var consumedProtein: Int = 0,
    var consumedCarbohydrate: Int = 0,
    var consumedFat: Int = 0,
    var recommendedCalories: Long = 0,
    var recommendedProtein: Long = 0,
    var recommendedCarbohydrate: Long = 0,
    var recommendedFat: Long = 0
) {
    init {
        calculateRecommendedGoals()
    }

    fun calculateRecommendedGoals() {
        val (calories, protein, carbohydrate, fat) = calculateGoals(
            weight, height, age, gender, targetWeight, timeLimitInWeeks
        )

        recommendedCalories = calories
        recommendedProtein = protein
        recommendedCarbohydrate = carbohydrate
        recommendedFat = fat
    }

    companion object {
        private fun calculateGoals(
            weight: Int,
            height: Int,
            age: Int,
            gender: String,
            targetWeight: Int,
            timeLimitInWeeks: Int
        ): Quadruple<Long, Long, Long, Long> {
            val bmr: Int = if (gender.toLowerCase() == "male") {
                (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)).toInt()
            } else {
                (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)).toInt()
            }

            val activityFactor = 1.55
            val tdee = (bmr * activityFactor).toInt()
            val averageWeightLossPerWeek = if (timeLimitInWeeks != 0) {
                (weight - targetWeight) / timeLimitInWeeks
            } else {
                0  // Default value or handle appropriately
            }
            val calorieDeficit = if (averageWeightLossPerWeek != 0) {
                (averageWeightLossPerWeek * 7700 / 7).toInt()
            } else {
                0  // Default value or handle appropriately
            }
            val dailyCalorieIntake = (tdee - calorieDeficit).toInt()

            val proteinPercentage = 0.20
            val carbohydratePercentage = 0.50
            val fatPercentage = 0.30

            val dailyProteinIntake = (weight * proteinPercentage).toLong()
            val dailyCarbohydrateIntake = (weight * carbohydratePercentage).toLong()
            val dailyFatIntake = (weight * fatPercentage).toLong()

            return Quadruple(
                dailyCalorieIntake.toLong(),
                dailyProteinIntake,
                dailyCarbohydrateIntake,
                dailyFatIntake
            )
        }
    }

    // Getters
    fun getConsumedCaloriesValue() = consumedCalories
    fun getConsumedProteinValue() = consumedProtein
    fun getConsumedCarbohydrateValue() = consumedCarbohydrate
    fun getConsumedFatValue() = consumedFat
    fun getRecommendedCaloriesValue() = recommendedCalories
    fun getRecommendedProteinValue() = recommendedProtein
    fun getRecommendedCarbohydrateValue() = recommendedCarbohydrate
    fun getRecommendedFatValue() = recommendedFat

    // Setters
//    fun setAge(value: Int) { age = value }
//    fun setWeight(value: Int) { weight = value }
//    fun setHeight(value: Int) { height = value }
//    fun setGender(value: String) { gender = value }
//    fun setTargetWeight(value: Int) { targetWeight = value }
//    fun setTimeLimitInWeeks(value: Int) { timeLimitInWeeks = value }
//    fun setConsumedCalories(value: Int) { consumedCalories = value }
//    fun setConsumedProtein(value: Int) { consumedProtein = value }
//    fun setConsumedCarbohydrate(value: Int) { consumedCarbohydrate = value }
//    fun setConsumedFat(value: Int) { consumedFat = value }
    // Setters for consumed values
    fun addConsumedCalories(calories: Int) {
        consumedCalories += calories
    }

    fun addConsumedProtein(protein: Int) {
        consumedProtein += protein
    }

    fun addConsumedCarbohydrate(carbohydrate: Int) {
        consumedCarbohydrate += carbohydrate
    }

    fun addConsumedFat(fat: Int) {
        consumedFat += fat
    }
}
