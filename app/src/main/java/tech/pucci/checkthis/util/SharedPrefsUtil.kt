package tech.pucci.checkthis.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Person

class SharedPrefsUtil(private val context: Context) {

    fun savePerson(person: Person) {
        val sharedPrefs = context.getSharedPreferences(context.resources.getString(R.string.prefs_user), MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        editor.putString(context.resources.getString(R.string.prefs_user_person), Gson().toJson(person))
        editor.apply()
    }

    fun getPerson(): Person? {
        val prefs = context.getSharedPreferences(context.resources.getString(R.string.prefs_user), MODE_PRIVATE)
        return  Gson().fromJson<Person>(prefs.getString(context.resources.getString(R.string.prefs_user_person), null), Person::class.java)
    }

}