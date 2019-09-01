package tech.pucci.checkthis.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.pucci.checkthis.ui.adapter.EventsAdapter
import tech.pucci.checkthis.R
import tech.pucci.checkthis.network.RetrofitInitializer
import tech.pucci.checkthis.model.Event

class EventsActivity : AppCompatActivity() {

    lateinit var rvEvents: RecyclerView
    private val events: ArrayList<Event> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        rvEvents = findViewById(R.id.events_recycler_view)
        rvEvents.adapter = EventsAdapter(this, events)

        requestEvents()
    }

    private fun requestEvents() {
        RetrofitInitializer().eventsService().get().enqueue(object : Callback<List<Event>?> {
            override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                Toast.makeText(this@EventsActivity, "Error receiving events", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                response.body()?.let {
                    events.addAll(it)
                    rvEvents.adapter?.notifyDataSetChanged()
                }
            }
        })
    }
}
