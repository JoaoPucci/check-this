package tech.pucci.checkthis.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_person.view.*
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Person

class PeopleAdapter(private val context: Context,
                    private val people: List<Person>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_person, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(people[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(person: Person) {
            itemView.person_item_name.text = person.name
        }
    }

}