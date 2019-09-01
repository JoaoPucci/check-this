package tech.pucci.checkthis.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_event.view.*
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Event
import tech.pucci.checkthis.ui.activity.EventDetailActivity

class EventsAdapter(private val context: Context,
                    private val events: List<Event>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(events[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra(Event.EXTRA_EVENT, events[position].toJsonString())
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(event: Event) {
            itemView.event_item_title.text = event.title
        }
    }

}