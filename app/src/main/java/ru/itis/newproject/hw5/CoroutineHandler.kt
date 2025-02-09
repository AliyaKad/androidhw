package ru.itis.newproject.hw5

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*

class CoroutineHandler(
    private val coroutineJobs: MutableList<Job>,
    private val context: Context
) {
    private var completedCount = 0
    private var totalCount = 0

    fun runCoroutines(
        count: Int,
        executionType: ExecutionType,
        selectedDispatcher: DispatcherType,
    ) {
        completedCount = 0
        totalCount = count
        val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

        coroutineScope.launch {
            try {
                if (executionType == ExecutionType.PARALLEL) {
                    repeat(count) { i ->
                        val job = coroutineScope.async(selectedDispatcher.coroutineDispatcher) {
                            delay(1000L)
                            Log.i("Coroutine started", "Корутина $i")
                            completedCount++
                        }
                        coroutineJobs.add(job)
                    }
                } else {
                    for (i in 0 until count) {
                        val job = coroutineScope.async(selectedDispatcher.coroutineDispatcher) {
                            delay(1000L)
                            Log.i("Coroutine started", "Корутина $i")
                            completedCount++
                        }
                        coroutineJobs.add(job)
                        job.await()
                    }
                }

                coroutineJobs.forEach { it.join() }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun cancelCoroutines(onComplete: (Int) -> Unit) {
        if (coroutineJobs.isEmpty()) {
            onComplete(0)
            return
        }

        var cancelledCount = 0
        CoroutineScope(Dispatchers.Main).launch {
            coroutineJobs.forEach { job ->
                if (job.isActive) {
                    try {
                        job.cancelAndJoin()
                        cancelledCount++
                    } catch (e: Exception) {
                        handleException(e)
                    }
                }
            }
            coroutineJobs.clear()
            val remainingCount = totalCount - completedCount
            onComplete(remainingCount)
        }
    }

    private fun handleException(e: Exception) {
        Log.e("CoroutineHandler", "Ошибка выполнения корутины: ${e.message}")
        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
