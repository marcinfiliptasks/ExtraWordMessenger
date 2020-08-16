package mf.patomessenger.presentation.base

import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected abstract val data: List<T>

    protected fun inflate(parent: ViewGroup, @LayoutRes res: Int): View =
        from(parent.context).inflate(res, parent, false)

    override fun getItemCount() = data.size

    fun loadData(items: List<T>) {
        (data as ArrayList<T>).clear()
        (data as ArrayList<T>).addAll(items)
        notifyDataSetChanged()
    }
}