package ru.itis.newproject.hw5

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.*
import ru.itis.newproject.R

@Composable
fun ComposeManager(
    onRunCoroutines: (Int, ExecutionType, DispatcherType) -> Unit,
    onCancelCoroutines: () -> Unit,
    coroutineJobs: MutableList<Job>,
    context: Context,
    onCancellationTypeChange: (CancellationType) -> Unit
) {
    var numberOfCoroutines by remember { mutableStateOf("") }
    var executionType by remember { mutableStateOf(ExecutionType.SEQUENTIAL) }
    var cancellationType by remember { mutableStateOf(CancellationType.CANCEL_ON_PAUSE) }
    var selectedDispatcher by remember { mutableStateOf(DispatcherType.IO) }
    var errorMessage by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val dispatchers = DispatcherType.values()

    LaunchedEffect(cancellationType) {
        onCancellationTypeChange(cancellationType)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = numberOfCoroutines,
            onValueChange = { numberOfCoroutines = it },
            label = { Text(stringResource(R.string.coroutine_count)) },
            isError = errorMessage.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Text(stringResource(R.string.coroutine_type))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = executionType == ExecutionType.SEQUENTIAL,
                    onClick = { executionType = ExecutionType.SEQUENTIAL }
                )
                Text(stringResource(R.string.consistently), modifier = Modifier.padding(start = 8.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = executionType == ExecutionType.PARALLEL,
                    onClick = { executionType = ExecutionType.PARALLEL }
                )
                Text(stringResource(R.string.in_parallel), modifier = Modifier.padding(start = 8.dp))
            }
        }

        Text(stringResource(R.string.coroutine_type_of_withdrawal))


        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = cancellationType == CancellationType.CANCEL_ON_PAUSE,
                    onClick = {
                        cancellationType = CancellationType.CANCEL_ON_PAUSE
                    }
                )
                Text(stringResource(R.string.cancel_when_collapsing), modifier = Modifier.padding(start = 8.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = cancellationType == CancellationType.CONTINUE_ON_PAUSE,
                    onClick = {
                        cancellationType = CancellationType.CONTINUE_ON_PAUSE
                    }
                )
                Text(stringResource(R.string.continue_when_collapsing), modifier = Modifier.padding(start = 8.dp))
            }
        }


        Text(stringResource(R.string.select_a_thread_pool))
        Box {
            Text(
                text = selectedDispatcher.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dispatchers.forEach { dispatcher ->
                    DropdownMenuItem(
                        text = { Text(dispatcher.name) },
                        onClick = {
                            selectedDispatcher = dispatcher
                            expanded = false
                        }
                    )
                }
            }
        }

        Column {
            Button(
                onClick = {
                    val count = numberOfCoroutines.toIntOrNull()
                    if (count == null || count <= 0) {
                        errorMessage = context.getString(R.string.enter_correct_number)
                        return@Button
                    }
                    errorMessage = ""
                    onRunCoroutines(count, executionType, selectedDispatcher)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.run_coroutines))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (coroutineJobs.isEmpty()) {
                        Toast.makeText(context, context.getString(R.string.no_coroutines_running), Toast.LENGTH_SHORT).show()
                    } else {
                        onCancelCoroutines()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.cancel_coroutines))
            }
        }
    }
}

enum class ExecutionType {
    SEQUENTIAL,
    PARALLEL
}

enum class CancellationType {
    CANCEL_ON_PAUSE,
    CONTINUE_ON_PAUSE
}

enum class DispatcherType(val coroutineDispatcher: CoroutineDispatcher) {
    IO(Dispatchers.IO),
    Default(Dispatchers.Default),
    Main(Dispatchers.Main)
}

