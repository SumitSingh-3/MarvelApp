package com.marvelapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.marvelapp.base.App
import com.marvelapp.db.SearchHistory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object AppUtil {

    fun hideKeyboard(view: View) {
        view.clearFocus()
        val inputMethodManager: InputMethodManager? =
            App.INSTANCE.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun loadImage(view: ImageView?, url: String?) {
        if (view != null && url != null) {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .centerInside()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(view)
        }
    }


    fun getThisWeek(): String {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        val start = formatYYYYMMDD(currentCalendar.time)

        currentCalendar.add(Calendar.DATE, 6)

        val end = formatYYYYMMDD(currentCalendar.time)

        return "$start,$end"
    }

    fun getNextWeek(): String {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

        //add 1 so we get sunday of next week
        currentCalendar.add(Calendar.DATE, 1)

        val start = formatYYYYMMDD(currentCalendar.time)

        //add 6 so we get saturday of next week
        currentCalendar.add(Calendar.DATE, 6)

        val end = formatYYYYMMDD(currentCalendar.time)

        return "$start,$end"
    }

    fun getLastWeek(): String {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        //decrease by 1 so we get last week saturday
        currentCalendar.add(Calendar.DATE, -1)

        val end = formatYYYYMMDD(currentCalendar.time)

        currentCalendar.add(Calendar.DATE, -6)

        val start = formatYYYYMMDD(currentCalendar.time)

        return "$start,$end"
    }

    fun getThisMonth(): String {
        val today: LocalDate = LocalDate.now()
        val startDay = today.withDayOfMonth(1)
        val endDay = today.withDayOfMonth(today.lengthOfMonth())

        return "$startDay,$endDay"
    }

    fun formatYYYYMMDD(date: Date): String {
        return  SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date)
    }

    fun getSearchHistoryArray(list: List<SearchHistory>): Array<String?> {
        val history = arrayOfNulls<String>(list.size)

        for(i in history.indices){
            history[i] = list[i].search
        }

        return history
    }
}