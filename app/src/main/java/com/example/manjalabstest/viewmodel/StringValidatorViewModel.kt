package com.example.manjalabstest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.manjalabstest.model.ValidationResult

class StringValidatorViewModel : ViewModel() {
    private val _result = MutableStateFlow(ValidationResult(isValid = false, modifiedString = null))
    val result: StateFlow<ValidationResult> = _result

    fun validstring(input: String) {
        viewModelScope.launch {
            _result.value = checkstring(input)
        }
    }

    private fun checkstring(input: String): ValidationResult {
        val counts = input.groupingBy { it }.eachCount()
        val freqCounts = counts.values.groupingBy { it }.eachCount()

        return when {
            freqCounts.size == 1 -> ValidationResult(isValid = true, modifiedString = input)

            freqCounts.size == 2 -> {
                val (a, countA) = freqCounts.entries.first()
                val b = freqCounts.entries.last().key

                val (uniqueFreq, commonFreq) = if (countA == 1) {
                    a to b
                } else {
                    b to a
                }

                if (uniqueFreq == 1 || uniqueFreq == commonFreq + 1) {
                    val modified = StringBuilder(input)
                    var removed = false

                    for (i in input.indices.reversed()) { // Loop dr blakng
                        val char = input[i]
                        if (counts[char] == uniqueFreq && !removed) {
                            modified.deleteCharAt(i)
                            removed = true
                        }
                    }

                    ValidationResult(isValid = true, modifiedString = modified.toString())
                } else {
                    ValidationResult(isValid = false, modifiedString = null)
                }
            }

            else -> ValidationResult(isValid = false, modifiedString = null)
        }
    }
}