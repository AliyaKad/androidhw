package ru.itis.newproject.hw5

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.Job
import ru.itis.newproject.R

class CoroutineFragment : BaseFragment(R.layout.fragment_compose) {
    private var coroutineJobs = mutableListOf<Job>()
    private lateinit var coroutineHandler: CoroutineHandler
    private var cancellationType: CancellationType = CancellationType.CANCEL_ON_PAUSE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroutineHandler = CoroutineHandler(coroutineJobs, context = requireContext())

        composeView?.setContent {
            ComposeManager(
                onRunCoroutines = { count, executionType, selectedDispatcher ->
                    coroutineHandler.runCoroutines(count, executionType, selectedDispatcher)
                },
                onCancelCoroutines = {
                    coroutineHandler.cancelCoroutines { cancelledCount ->
                        if (cancelledCount > 0) {
                            val message = context?.getString(R.string.coroutines_canceled, cancelledCount)
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, R.string.no_coroutines_to_cancel, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                coroutineJobs = coroutineJobs,
                context = requireContext(),
                onCancellationTypeChange = { selectedCancellationType ->
                    cancellationType = selectedCancellationType
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (cancellationType == CancellationType.CANCEL_ON_PAUSE) {
            coroutineHandler.cancelCoroutines { cancelledCount ->
                if (cancelledCount > 0) {
                    val message = context?.getString(R.string.coroutines_canceled, cancelledCount)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineJobs.clear()
    }
}
