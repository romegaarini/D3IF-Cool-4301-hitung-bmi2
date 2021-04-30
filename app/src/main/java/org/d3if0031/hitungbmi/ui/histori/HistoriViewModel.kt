package org.d3if0031.hitungbmi.ui.histori

import android.icu.util.TimeUnit.values
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0031.hitungbmi.data.HasilBmi
import org.d3if0031.hitungbmi.data.HitungBmi
import org.d3if0031.hitungbmi.db.BmiDao
import org.d3if0031.hitungbmi.db.BmiEntity
import java.time.chrono.JapaneseEra.values

class HistoriViewModel(db: BmiDao) : ViewModel() {
}