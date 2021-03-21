package com.hossamelsharkawy.base.ui.recycel



import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Hossam Elsharkawy
on 27/06/17.
 */

class FastRec<Model>(
    var rec: RecyclerView?,
    private var adapter: BaseRecyclerAdapter<FastHolder<Model>, Model>?
) {


    fun reset() {
        //dataControl = null
        adapter = null
        rec = null
    }

    class Builder<Model>(private val act: Activity) {
        private lateinit var adapter: BaseRecyclerAdapter<FastHolder<Model>, Model>
        private lateinit var rec: RecyclerView

        private val defaultLayoutManager get() = LinearLayoutManager(this.act)
        private lateinit var onSetView: (itemView: View, m: Model, pos: Int) -> Unit
        private var onItemClick: ((v: View, model: Model, pos: Int) -> Unit)? = null
        private var value: IntArray? = null
        private var data: ArrayList<Model>? = null

        @LayoutRes
        private var rowId: Int = 0

        fun rec(rec: RecyclerView) = apply {
            this.rec = rec
        }

        fun row(@LayoutRes rowId: Int) = apply {
            this.rowId = rowId
        }


        @Deprecated("Use {@link #onItemView()} ")
        fun onView(onSetView: (itemView: View, model: Model, pos: Int) -> Unit) = apply {
            this.onSetView = onSetView
        }

        fun onItemView(onSetView: View.(model: Model, pos: Int) -> Unit) = apply {
            this.onSetView = onSetView
        }

        fun setData(date: ArrayList<Model>) = apply {
            this.data = date
        }


        fun onItemClick(vararg value: Int, onItemClick: (v: View, model: Model, pos: Int) -> Unit) =
            apply {
                this.onItemClick = onItemClick
                this.value = value
            }

        fun horizontal() = apply { rec.horizontal() }

        fun build(): Builder<Model> {

            this.adapter = FastAdapter(act, rowId, onSetView).apply {
                if (this@Builder.value != null) {
                    views = this@Builder.value?.toList()
                    this.onItemClick = this@Builder.onItemClick
                }
            }

            if (data != null) {
                adapter.setData(data!!)
            }

            this.rec.adapter = this.adapter

            if (this.rec.layoutManager == null) {
                this.rec.layoutManager = defaultLayoutManager
            }

            return this
        }

        val dataControl by lazy {
            DataControl(rec, adapter)
        }

    }


}
/*******************************************************/


fun RecyclerView.setGrid(column: Int, orientation: Int) {
    layoutManager =
        androidx.recyclerview.widget.GridLayoutManager(context, column, orientation, false)
}

fun RecyclerView.setGrid(column: Int) {
    layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, column)
}

fun RecyclerView.vertical() {
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.horizontal() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.setLayoutManager(orientation: Int) {
    layoutManager = LinearLayoutManager(context, orientation, false)
}


abstract class BaseBuilder<Model> {

    internal lateinit var rec: RecyclerView
    internal var act: Activity? = null

    internal val defaultLayoutManager get() = LinearLayoutManager(this.act)


    @LayoutRes
    internal var rowId: Int = 0


    fun horizontal() = apply { rec.horizontal() }

}
/*******************************************************/
abstract class FastHolder< Model>(v: View) : RecyclerView.ViewHolder(v) {
    abstract fun setView(m: Model, p: Int)
}


abstract class BaseRecyclerAdapter<Holder : RecyclerView.ViewHolder, Model>

    (context: Activity) : RecyclerView.Adapter<Holder>() {

    var inflater: LayoutInflater = context.layoutInflater

    var onItemClick: ((v: View, model: Model, pos: Int) -> Unit)? = null

    private var data: ArrayList<Model> = ArrayList()

    fun getData() = data

    val size: Int
        get() = data.size

    fun setData(data: ArrayList<Model>) {
        this.data = data
        notifyDataSetChanged()
    }


    fun addNewData(data: ArrayList<Model>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addNewItem(item: Model, position: Int) {
        this.data.add(item)
        notifyItemInserted(position)
    }

    fun addNewItem(item: Model): Int {
        this.data.add(item)
        notifyItemInserted(data.size)
        return data.size - 1
    }

    fun removeItem(item: Model) {
        if (data.isEmpty()) return
        this.data.remove(item)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        if (data.isEmpty()) return
        this.data.removeAt(index).also {
            notifyItemRemoved(index).also {
                notifyItemRangeChanged(index, size)
            }
        }
    }

    fun replaceData(data: ArrayList<Model>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    fun setDataNoNotify(data: ArrayList<Model>) {
        this.data = data
    }

    fun updateItem(pos: Int) = notifyItemChanged(pos)

    fun updateItem(pos: Int, model: Model) = notifyItemChanged(pos, model)

    operator fun get(adapterPosition: Int) = data[adapterPosition]

    override fun getItemCount() = data.size


}
/*******************************************************/

class FastAdapter<Model>(
    context: Activity,
    private val rowItemId: Int,
    val onSetView: (itemView: View, m: Model, pos: Int) -> Unit
) : BaseRecyclerAdapter<FastHolder<Model>, Model>(context) {

    var views: List<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastHolder<Model> {
        val itemView = getItemView(parent)
        return object : FastHolder<Model>(itemView) {
            override fun setView(m: Model, p: Int) {
                onSetView.invoke(itemView, m, p)
                views?.let { views ->
                    itemView.setOnClickListener {
                        onItemClick?.invoke(it, m, p)
                    }
                    views.forEach {
                        itemView.findViewById<View>(it)
                            .setOnClickListener { onItemClick?.invoke(it, m, p) }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(h: FastHolder<Model>, p: Int) = h.setView(get(p), p)
    private fun getItemView(parent: ViewGroup) = inflater.inflate(rowItemId, parent, false)

}
/*******************************************************/


open class DataControl<Model>(
    private val rec: RecyclerView,
    private val adapter: BaseRecyclerAdapter<FastHolder<Model>, Model>
) {
    open fun setData(data: ArrayList<Model>) = adapter.setData(data)

    open fun addNewData(data: ArrayList<Model>) = adapter.addNewData(data)

    fun replaceData(data: ArrayList<Model>) = adapter.replaceData(data)

    fun getData() = adapter.getData()

    fun addNewItem(item: Model): Int {
        val pos = adapter.addNewItem(item)
        moveTo(pos)
        return pos
    }

    fun removeItem(item: Model) = adapter.removeItem(item)

    fun removeItemByIndex(index: Int) = adapter.removeItem(index)

    fun updateItemByIndex(index: Int) = adapter.updateItem(index)

    fun updateItem(model: Model) {
        val index = getData().indexOf(model)
        if (index != -1) adapter.updateItem(index, model)
    }

    fun replaceItemByHash(model: Model) = with(getData()) {
        val index = indexOf(model)
        set(index, model)
        updateItem(index, model)
    }

    fun updateItem(index: Int, model: Model) = adapter.notifyItemChanged(index, model)

    fun removeFromToEnd(index: Int) {
        val data = adapter.getData()
        data.subList(index, data.size).clear()
        adapter.notifyDataSetChanged()
    }

    fun getItem(pos: Int) = adapter.getData()[pos]

    fun moveToLast() {
        rec.smoothScrollToPosition(adapter.size)
    }

    private fun moveTo(pos: Int) {
        rec.smoothScrollToPosition(pos)
    }

}
