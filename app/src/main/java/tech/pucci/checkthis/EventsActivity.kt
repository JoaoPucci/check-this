package tech.pucci.checkthis

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventsActivity : AppCompatActivity() {

    lateinit var rvEvents: RecyclerView
    private val events: ArrayList<Event> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        rvEvents = findViewById(R.id.events_activity_events_recycler_view)
        rvEvents.adapter = EventsAdapter(this, events)
    }

    override fun onResume() {
        super.onResume()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://5b840ba5db24a100142dcd8c.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsApi::class.java)

        retrofit.get().enqueue(object : Callback<List<Event>?> {
            override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                Toast.makeText(this@EventsActivity, "Error receiving events", Toast.LENGTH_LONG).show()
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
