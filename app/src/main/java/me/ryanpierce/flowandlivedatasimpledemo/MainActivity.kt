package me.ryanpierce.flowandlivedatasimpledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = PhotoRepository(DataSource.names, this)
        val useCase = UseCase(repository, DataSource.names)
        val viewModel = ViewModelProvider(this, FlowDemoViewModel.FACTORY(useCase))
            .get(FlowDemoViewModel::class.java)

        listView = findViewById(R.id.flowDemoListView)
        val adapter = UserListAdapter()
        listView.adapter = adapter

        // Choose whichever reactive mechanism you'd like to observe
        //   the stream of users. They all accomplish the same thing, that
        //   is, reactively observe a stream of users and add each user to
        //   a ListView.
        when (ReactWith.LIVE_DATA) {

            ReactWith.LIVE_DATA -> {

                // Use Android's LiveData to observe the flow of users.
                viewModel.userLiveData.observe(this, Observer { users ->
                    adapter.submitList(users)
                })
            }

            ReactWith.COLLECT -> {

                // Use Kotlin's Flow to observe the flow of users.
                lifecycleScope.launch {
                    viewModel.userFlow.collect { users ->
                        adapter.submitList(users)
                    }
                }
            }

            ReactWith.LAUNCH_IN -> {

                // Same as .collect {} but with
                //   fewer nested braces.
                viewModel.userFlow
                    .onEach { adapter.submitList(it) }
                    .launchIn(lifecycleScope)
            }
        }
    }

    enum class ReactWith {
        LIVE_DATA, COLLECT, LAUNCH_IN
    }
}
