
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liftbuddy.data.entity.Food
import com.example.liftbuddy.data.repository.FoodRepository


class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    val searchResults: MutableLiveData<List<Food>> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    fun searchFoods(query: String) {
        foodRepository.searchFoods(query) { foods ->
            searchResults.postValue(foods)
        }
    }
}
