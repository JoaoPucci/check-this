package tech.pucci.checkthis.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Event
import tech.pucci.checkthis.network.RetrofitInitializer
import tech.pucci.checkthis.ui.adapter.EventsAdapter

class EventsActivity : AppCompatActivity() {

    private lateinit var srlEvents: SwipeRefreshLayout
    private lateinit var rvEvents: RecyclerView
    private lateinit var ivErrorLoading: ImageView
    private lateinit var tvErrorLoading: TextView

    private val events: ArrayList<Event> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        ivErrorLoading = findViewById(R.id.events_recycler_view_error_placeholder)
        tvErrorLoading = findViewById(R.id.events_recycler_view_error_text)
        srlEvents = findViewById(R.id.events_swipe_layout)
        srlEvents.setOnRefreshListener { requestEvents() }

        rvEvents = findViewById(R.id.events_recycler_view)
        rvEvents.adapter = EventsAdapter(this, events)
    }

    override fun onResume() {
        super.onResume()
        requestEvents()
    }

    private fun requestEvents() {
        srlEvents.isRefreshing = true

        RetrofitInitializer().eventsService().get().enqueue(object : Callback<List<Event>?> {
            override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                t.printStackTrace()

                updateEventList(listOf())

                ivErrorLoading.setImageDrawable(ContextCompat.getDrawable(this@EventsActivity, R.drawable.ic_refresh_blue_24dp))
                tvErrorLoading.text = getString(R.string.error_loading_event_list)
            }

            override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                response.body()?.let {
                    updateEventList(it)

                    if(events.isEmpty()) {
                        ivErrorLoading.setImageDrawable(ContextCompat.getDrawable(this@EventsActivity, R.drawable.ic_sentiment_dissatisfied_blue_24dp))
                        tvErrorLoading.text = getString(R.string.event_list_no_events)
                    }
                }
            }
        })
    }

    private fun updateEventList(eventList: List<Event>) {
        refreshEvents(eventList)
        srlEvents.isRefreshing = false
        showListPlaceholderIf(events.isEmpty())
    }

    private fun refreshEvents(refreshedEvents: List<Event>) {
        events.clear()
        events.addAll(refreshedEvents)
        rvEvents.adapter?.notifyDataSetChanged()
    }

    private fun showListPlaceholderIf(shouldShow: Boolean) {
        ivErrorLoading.visibility = if(shouldShow) View.VISIBLE else View.GONE
        tvErrorLoading.visibility = ivErrorLoading.visibility
        rvEvents.visibility = if(shouldShow) View.GONE else View.VISIBLE
    }
}
