import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.repository.Repository

class ViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}